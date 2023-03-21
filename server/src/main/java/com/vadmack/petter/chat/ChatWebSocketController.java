package com.vadmack.petter.chat;

import com.vadmack.petter.chat.message.ChatMessageDto;
import com.vadmack.petter.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller
public class ChatWebSocketController {

  private final SimpMessagingTemplate messagingTemplate;
  private final ChatService chatService;

  /**
   * Simple endpoint to test ws connection with String
   */
  @MessageMapping("/hello")
  @SendTo("/topic/hello")
  public String sendHello(@AuthenticationPrincipal User user, @Payload String msg) {
    return msg;
  }

  @MessageMapping("/chat/{id}")
  public void sendPrivate(@DestinationVariable String id, @Payload ChatMessageDto msg) {
    ChatMessageDto savedMessage = chatService.saveMessage(msg);
    messagingTemplate.convertAndSendToUser(msg.getRecipientId(), "/queue/messages", savedMessage);
  }
}