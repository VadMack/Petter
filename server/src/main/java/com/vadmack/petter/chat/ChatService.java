package com.vadmack.petter.chat;

import com.google.firebase.messaging.FirebaseMessagingException;
import com.vadmack.petter.app.exception.ValidationException;
import com.vadmack.petter.app.utils.AppUtils;
import com.vadmack.petter.chat.message.ChatMessage;
import com.vadmack.petter.chat.message.dto.ChatMessageDto;
import com.vadmack.petter.chat.message.ChatMessageService;
import com.vadmack.petter.chat.room.ChatRoom;
import com.vadmack.petter.chat.room.ChatRoomService;
import com.vadmack.petter.file.metadata.Attachment;
import com.vadmack.petter.file.metadata.AttachmentType;
import com.vadmack.petter.file.metadata.FileMetadata;
import com.vadmack.petter.file.metadata.FileMetadataService;
import com.vadmack.petter.security.token.Token;
import com.vadmack.petter.security.token.TokenService;
import com.vadmack.petter.security.token.TokenType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

import static com.vadmack.petter.file.ImageService.USERS_PHOTO_STORAGE_FOLDER_NAME;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChatService {

  private final ChatMessageService chatMessageService;
  private final ChatRoomService chatRoomService;
  private final FirebaseMessagingService fmService;
  private final TokenService tokenService;
  private final FileMetadataService fileMetadataService;

  @Transactional
  public @NotNull ChatMessageDto saveMessageAndNotify(ChatMessageDto msg, String senderName) {
    ChatRoom chatRoom = chatRoomService.getById(msg.getChatRoomId());
    msg.setChatRoomId(chatRoom.getId());
    ChatMessage savedMessage = chatMessageService.createNewMessage(msg);
    chatRoom.setLastMessage(savedMessage);
    chatRoomService.save(chatRoom);

    String imagePath = msg.getImagePath();
    if (imagePath != null) {
      updateAttachmentMetadata(imagePath, chatRoom.getId());
    }

    // Content will be decrypted
    ChatMessageDto dto = chatMessageService.entityToDecryptedDto(savedMessage);

    sendFirebaseNotification(dto, senderName);

    return dto;
  }

  private void updateAttachmentMetadata(String imagePath, String chatRoomId) {
    FileMetadata metadata = fileMetadataService.getByRelativePath(
            Paths.get(USERS_PHOTO_STORAGE_FOLDER_NAME, imagePath).toString());
    Attachment attachment = metadata.getAttachment();
    if (attachment == null || attachment.getType() != AttachmentType.CHAT_ROOM) {
      throw new ValidationException("Invalid imagePath");
    }
    attachment.setId(chatRoomId);
    fileMetadataService.save(metadata);
  }

  private void sendFirebaseNotification(ChatMessageDto dto, String senderName) {
    List<Token> deviceTokens = tokenService.getAllByTypeAndUserId(TokenType.DEVICE_TOKEN, dto.getRecipientId());
    deviceTokens.forEach(token -> {
      NotificationDto notification = new NotificationDto(senderName, dto.getContent(),
              token.getValue(), Collections.singletonMap("model", AppUtils.objectToJSON(dto)));
      try {
        fmService.send(notification);
      } catch (FirebaseMessagingException ex) {
        log.warn("Firebase notification is not sent: " + ex.getMessage());
      }
    });
  }
}
