package com.vadmack.petter.chat;

import com.google.firebase.messaging.FirebaseMessagingException;
import com.vadmack.petter.chat.message.ChatMessage;
import com.vadmack.petter.chat.message.ChatMessageDto;
import com.vadmack.petter.chat.message.ChatMessageService;
import com.vadmack.petter.chat.room.ChatRoom;
import com.vadmack.petter.chat.room.ChatRoomService;
import com.vadmack.petter.security.token.Token;
import com.vadmack.petter.security.token.TokenService;
import com.vadmack.petter.security.token.TokenType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChatService {

  private final ChatMessageService chatMessageService;
  private final ChatRoomService chatRoomService;
  private final FirebaseMessagingService fmService;
  private final TokenService tokenService;

  @Transactional
  public @NotNull ChatMessageDto saveMessageAndNotify(ChatMessageDto msg, String senderName) {
    ChatRoom chatRoom = chatRoomService.getById(msg.getChatRoomId());
    msg.setChatRoomId(chatRoom.getId());
    ChatMessage savedMessage = chatMessageService.createNewMessage(msg);
    chatRoom.setLastMessage(savedMessage);
    chatRoomService.save(chatRoom);
    //String decryptedMsg = RSAUtils.decrypt(msg.getContent(), chatRoom.getId());

    // Firebase notification
    List<Token> deviceTokens = tokenService.getAllByTypeAndUserId(TokenType.DEVICE_TOKEN, msg.getRecipientId());
    deviceTokens.forEach(token -> {
      NotificationDto notification = new NotificationDto(senderName, msg.getContent(),
              token.getValue(), Map.of("userId", msg.getSenderId(), "chatRoomId", chatRoom.getId()));
      try {
        fmService.send(notification);
      } catch (FirebaseMessagingException ex) {
        log.warn("Firebase notification is not sent: " + ex.getMessage());
      }
    });

    ChatMessageDto dto = chatMessageService.entityToDto(savedMessage);
    dto.setContent(msg.getContent());
    return dto;
  }
}
