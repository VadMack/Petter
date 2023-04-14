package com.vadmack.petter.chat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

/**
 * Only for manual testing due to the inability to get a real device token
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FirebaseMessagingIT {

  private final static String HARDCODED_DEVICE_TOKEN = "place_for_your_token";

  @Autowired
  private FirebaseMessagingService fmService;

  @Test
  void send() {
    assertDoesNotThrow(() -> fmService.send(
            new NotificationDto("senderName", "message", HARDCODED_DEVICE_TOKEN,
            Map.of("userId", "senderId_example", "chatRoomId", "chatRoomId_example"))));
  }
}
