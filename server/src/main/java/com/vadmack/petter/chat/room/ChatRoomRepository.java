package com.vadmack.petter.chat.room;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface ChatRoomRepository extends MongoRepository<ChatRoom, String> {
  Optional<ChatRoom> findByUser1AndUser2(String user1Id, String user2Id);
  List<ChatRoom> findByUser1OrUser2(String user1Id, String user2Id);
}
