package com.vadmack.petter.chat;

import com.vadmack.petter.chat.message.ChatMessage;
import com.vadmack.petter.chat.message.ChatMessageDto;
import com.vadmack.petter.chat.message.ChatMessageService;
import com.vadmack.petter.chat.room.ChatRoom;
import com.vadmack.petter.chat.room.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ChatService {

  private final ChatMessageService chatMessageService;
  private final ChatRoomService chatRoomService;

  @Transactional
  public @NotNull ChatMessageDto saveMessage(ChatMessageDto msg) {
    ChatRoom chatRoom = chatRoomService.getById(msg.getChatRoomId());
    msg.setChatRoomId(chatRoom.getId());
    ChatMessage savedMessage = chatMessageService.createNewMessage(msg);
    chatRoom.setLastMessage(savedMessage);
    chatRoomService.save(chatRoom);
    return chatMessageService.entityToDto(savedMessage);
  }

}
