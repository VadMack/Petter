package com.vadmack.petter.chat.message;

import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ChatMessageRepository extends MongoRepository<ChatMessage, String> {

  @Aggregation(pipeline = {
          "{ '$match' : { 'chatRoomId' :  ?0 } }",
          "{ '$sort' : { 'sentTime' : -1 } }",
          "{ '$skip' : ?1 }",
          "{ '$limit' : ?2 }"
  })
  List<ChatMessage> findByChatRoomIdOrderBySentTime(String chatRoomId, int skip, int limit);
  void deleteByChatRoomId(String chatRoomId);
}
