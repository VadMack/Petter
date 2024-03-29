package com.vadmack.petter.chat.message.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ChatMessageDto {
  private String id;
  private String chatRoomId;
  private String content;
  private String senderId;
  private String recipientId;
  private String sentTime;
  private String imagePath;

  public ChatMessageDto(String content, String senderId, String recipientId) {
    this.content = content;
    this.senderId = senderId;
    this.recipientId = recipientId;
  }
}
