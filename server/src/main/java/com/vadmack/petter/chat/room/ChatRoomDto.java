package com.vadmack.petter.chat.room;

import com.vadmack.petter.chat.message.ChatMessageDto;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ChatRoomDto {
  private String id;
  private String user1;
  private String user2;
  private ChatMessageDto lastMessage;
}
