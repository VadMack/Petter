package com.vadmack.petter.chat.message;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ChatMessageService {

  private final ChatMessageRepository chatMessageRepository;

  private final ModelMapper modelMapper;

  public @NotNull List<ChatMessageDto> getDtoByChatRoomId(@NotNull String chatRoomId, int skip, int limit) {
    return chatMessageRepository.findByChatRoomIdOrderBySentTime(chatRoomId, skip, limit)
            .stream().map(this::entityToDto).toList();
  }

  public @NotNull ChatMessage createNewMessage(@NotNull ChatMessageDto dto) {
    ChatMessage msg = dtoToEntity(dto);
    msg.setSentTime(Instant.now());
    return chatMessageRepository.save(msg);
  }

  private ChatMessage dtoToEntity(@NotNull ChatMessageDto dto) {
    return modelMapper.map(dto, ChatMessage.class);
  }

  public ChatMessageDto entityToDto(@NotNull ChatMessage entity) {
    return modelMapper.map(entity, ChatMessageDto.class);
  }

}
