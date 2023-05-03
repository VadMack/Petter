package com.vadmack.petter.chat.message;

import com.vadmack.petter.chat.RSAUtils;
import com.vadmack.petter.chat.message.dto.ChatMessageDto;
import com.vadmack.petter.file.metadata.AttachmentType;
import com.vadmack.petter.file.metadata.FileMetadata;
import com.vadmack.petter.file.ImageService;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ChatMessageService {

  private final ChatMessageRepository chatMessageRepository;
  private final ImageService imageService;

  private final ModelMapper modelMapper;

  public @NotNull List<ChatMessageDto> getDtoByChatRoomId(@NotNull String chatRoomId, int skip, int limit) {
    return chatMessageRepository.findByChatRoomIdOrderBySentTime(chatRoomId, skip, limit)
            .stream().map(this::entityToDecryptedDto).toList();
  }

  public @NotNull ChatMessage createNewMessage(@NotNull ChatMessageDto dto) {
    ChatMessage msg = dtoToEntity(dto);
    msg.setSentTime(Instant.now());
    return chatMessageRepository.save(msg);
  }

  public String addImage(@NotNull MultipartFile image, @NotNull String userId) {
    FileMetadata fileMetadata = imageService.save(image, userId, AttachmentType.CHAT_ROOM, null);
    return fileMetadata.getRelativePath();
  }

  private ChatMessage dtoToEntity(@NotNull ChatMessageDto dto) {
    return modelMapper.map(dto, ChatMessage.class);
  }

  public ChatMessageDto entityToDecryptedDto(@NotNull ChatMessage entity) {
    ChatMessageDto dto = modelMapper.map(entity, ChatMessageDto.class);
    dto.setContent(RSAUtils.decrypt(dto.getContent(), dto.getChatRoomId()));
    return dto;
  }

}
