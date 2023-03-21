package com.vadmack.petter.chat.message;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ChatMessageRepository extends MongoRepository<ChatMessage, String> {
  List<ChatMessage> findByChatRoomId(String chatRoomId, Pageable pageable);
}
