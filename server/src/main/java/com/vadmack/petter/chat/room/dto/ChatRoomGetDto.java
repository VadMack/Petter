package com.vadmack.petter.chat.room.dto;

import com.vadmack.petter.chat.message.dto.ChatMessageDto;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ChatRoomGetDto {
  private String id;
  private String user1;
  private String user2;
  private ChatMessageDto lastMessage;
  private String publicKey;
}
