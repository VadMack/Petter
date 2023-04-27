package com.vadmack.petter.chat.room;

import com.vadmack.petter.app.utils.AppUtils;
import com.vadmack.petter.chat.RSAUtils;
import com.vadmack.petter.chat.message.ChatMessageDto;
import com.vadmack.petter.chat.room.dto.ChatRoomCreateDto;
import com.vadmack.petter.chat.room.dto.ChatRoomGetDto;
import com.vadmack.petter.user.User;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.jetbrains.annotations.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ChatRoomService {

  private final ChatRoomRepository chatRoomRepository;

  private final ModelMapper modelMapper;

  public ChatRoom getById(@NotNull String id) {
    return AppUtils.checkFound(findById(id),
            String.format("Chat room with id=%s not found", id));
  }

  private Optional<ChatRoom> findById(@NotNull String id) {
    return chatRoomRepository.findById(id);
  }

  public @NotNull List<ChatRoomGetDto> getDtoByUserId(@NotNull String userId) {
    return findByUserId(userId).stream().map(this::entityToDto).toList();
  }

  private @NotNull List<ChatRoom> findByUserId(@NotNull String userId) {
    return chatRoomRepository.findByUser1OrUser2(userId, userId);
  }

  public @NotNull ChatRoomGetDto findByParticipantsOrCreate(@NotNull String user1Id, @NotNull String user2Id) {
    Optional<ChatRoom> optional = findByParticipants(user1Id, user2Id);
    ChatRoom room = optional.orElseGet(() -> {
      String chatRoomId = new ObjectId().toString();
      return chatRoomRepository.save(new ChatRoom(chatRoomId, user1Id, user2Id, RSAUtils.getPublicKey(chatRoomId)));
    });
    ChatRoomGetDto dto = entityToDto(room);
    dto.setPrivateKey(Base64.getEncoder().encodeToString(RSAUtils.getKeyPair(room.getId()).getPrivate().getEncoded()));
    return dto;
  }

  private @NotNull Optional<ChatRoom> findByParticipants(@NotNull String user1Id, @NotNull String user2Id) {
    Optional<ChatRoom> found;
    if (user1Id.compareTo(user2Id) < 1) {
      found = chatRoomRepository.findByUser1AndUser2(user1Id, user2Id);
    } else {
      found = chatRoomRepository.findByUser1AndUser2(user2Id, user1Id);
    }
    return found;
  }

  public void save(@NotNull ChatRoom chatRoom) {
    chatRoomRepository.save(chatRoom);
  }

  public ChatRoomGetDto entityToDto(@NotNull ChatRoom entity) {
    ChatRoomGetDto dto = modelMapper.map(entity, ChatRoomGetDto.class);
    ChatMessageDto lastMessage = dto.getLastMessage();
    if (lastMessage != null) {
      lastMessage.setContent(RSAUtils.decrypt(lastMessage.getContent(), dto.getId()));
    }
    return modelMapper.map(entity, ChatRoomGetDto.class);
  }

  private ChatRoom dtoToEntity(@NotNull ChatRoomCreateDto dto) {
    return modelMapper.map(dto, ChatRoom.class);
  }

  public boolean isParticipant(@NotNull User user, @NotNull String roomId) {
    ChatRoom room = getById(roomId);
    return room.isParticipant(user.getId());
  }
}
