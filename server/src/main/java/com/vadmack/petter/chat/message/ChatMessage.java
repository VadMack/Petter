package com.vadmack.petter.chat.message;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Data
@NoArgsConstructor
@Document("chatMessages")
public class ChatMessage {
  private String id;
  private String chatRoomId;
  private String content;
  private String senderId;
  private String recipientId;
  private Instant sentTime;

  public ChatMessage(String content, String senderId, String recipientId) {
    this.content = content;
    this.senderId = senderId;
    this.recipientId = recipientId;
  }
}