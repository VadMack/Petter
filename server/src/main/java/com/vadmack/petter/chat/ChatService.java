package com.vadmack.petter.chat;

import com.vadmack.petter.chat.message.ChatMessage;
import com.vadmack.petter.chat.message.ChatMessageDto;
import com.vadmack.petter.chat.message.ChatMessageService;
import com.vadmack.petter.chat.room.ChatRoom;
import com.vadmack.petter.chat.room.ChatRoomService;
import com.vadmack.petter.security.token.Token;
import com.vadmack.petter.security.token.TokenService;
import com.vadmack.petter.security.token.TokenType;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class ChatService {

  private final ChatMessageService chatMessageService;
  private final ChatRoomService chatRoomService;
  private final FirebaseMessagingService fmService;
  private final TokenService tokenService;

  @Transactional
  public @NotNull ChatMessageDto saveMessageAndNotify(ChatMessageDto msg, String senderName) {
    String senderId = msg.getSenderId();
    ChatRoom chatRoom = chatRoomService.getById(msg.getChatRoomId());
    msg.setChatRoomId(chatRoom.getId());
    ChatMessage savedMessage = chatMessageService.createNewMessage(msg);
    chatRoom.setLastMessage(savedMessage);
    chatRoomService.save(chatRoom);

    List<Token> deviceTokens = tokenService.getAllByTypeAndUserId(TokenType.DEVICE_TOKEN, senderId);
    deviceTokens.forEach(token -> {
      NotificationDto notification = new NotificationDto(senderName, msg.getContent(),
              token.getValue(), Map.of("userId", senderId, "chatRoomId", chatRoom.getId()));
      fmService.send(notification);
    });

    return chatMessageService.entityToDto(savedMessage);
  }
}
