package com.vadmack.petter.chat.message;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DataMongoTest
class ChatMessageRepositoryIT {

  private static final String CHAT_ROOM_ID = "Besties";

  @Autowired
  private ChatMessageRepository chatMessageRepository;

  private List<ChatMessage> messages;

  @BeforeAll
  void beforeAll() {
    ChatMessage m1 = new ChatMessage();
    m1.setSentTime(Instant.now().minusSeconds(20));
    m1.setContent("1");

    ChatMessage m2 = new ChatMessage();
    m2.setSentTime(Instant.now());
    m2.setContent("2");

    ChatMessage m3 = new ChatMessage();
    m3.setSentTime(Instant.now().plusSeconds(20));
    m3.setContent("3");

    messages = List.of(m1, m2, m3);
    messages.forEach(m -> m.setChatRoomId(CHAT_ROOM_ID));

    messages = chatMessageRepository.saveAll(messages);
  }

  @Test
  void findByChatRoomIdOrderBySentTime() {
    // It is expected that messages will come starting from the latest
    List<ChatMessage> reversed = new ArrayList<>(messages);
    Collections.reverse(reversed);

    List<ChatMessage> received = chatMessageRepository.findByChatRoomIdOrderBySentTime(CHAT_ROOM_ID, 0, 3);
    assertEquals(reversed.stream().map(ChatMessage::getContent).toList(),
            received.stream().map(ChatMessage::getContent).toList());
  }

  @Test
  void findByChatRoomIdOrderBySentTimeSkip() {
    List<String> expectedContent = List.of("2", "1");

    List<ChatMessage> received = chatMessageRepository.findByChatRoomIdOrderBySentTime(CHAT_ROOM_ID, 1, 3);
    assertEquals(expectedContent,
            received.stream().map(ChatMessage::getContent).toList());
  }

  @Test
  void findByChatRoomIdOrderBySentTimeSkipAndLimit() {
    List<String> expectedContent = List.of("2");

    List<ChatMessage> received = chatMessageRepository.findByChatRoomIdOrderBySentTime(CHAT_ROOM_ID, 1, 1);
    assertEquals(expectedContent,
            received.stream().map(ChatMessage::getContent).toList());
  }

  @AfterEach
  void tearDown() {
    chatMessageRepository.deleteAll(messages);
  }
}
