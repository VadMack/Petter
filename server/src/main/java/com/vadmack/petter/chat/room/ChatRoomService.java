package com.vadmack.petter.chat.room;

import com.vadmack.petter.app.utils.AppUtils;
import com.vadmack.petter.user.User;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ChatRoomService {

  private final ChatRoomRepository chatRoomRepository;

  private final ModelMapper modelMapper;

  private ChatRoom getById(@NotNull String id) {
    return AppUtils.checkFound(findById(id),
            String.format("Chat room with id=%s not found", id));
  }

  private Optional<ChatRoom> findById(@NotNull String id) {
    return chatRoomRepository.findById(id);
  }

  public @NotNull List<ChatRoomDto> getDtoByUserId(@NotNull String userId) {
    return findByUserId(userId).stream().map(this::entityToDto).toList();
  }

  private @NotNull List<ChatRoom> findByUserId(@NotNull String userId) {
    return chatRoomRepository.findByUser1OrUser2(userId, userId);
  }

  /**
   * Finds existed room or creates new ChatRoom object without saving in DB
   */
  public @NotNull ChatRoom findByParticipantsOrCreate(@NotNull String user1Id, @NotNull String user2Id) {
    Optional<ChatRoom> optional = findByParticipants(user1Id, user2Id);
    return optional.orElse((new ChatRoom(user1Id, user2Id)));
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

  public ChatRoomDto entityToDto(@NotNull ChatRoom entity) {
    return modelMapper.map(entity, ChatRoomDto.class);
  }

  public boolean isParticipant(@NotNull User user, @NotNull String roomId) {
    ChatRoom room = getById(roomId);
    return room.isParticipant(user.getId());
  }
}