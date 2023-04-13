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

  private final static String HARDCODED_DEVICE_TOKEN = "abcdefg890-tzdEBZ1nuKr:APA91bHf2ZrB2WpIJeGt63jnKmcg8wBEui6jxgR2028mtKKhcGXriy_CnVjy1WIaS7z0M3r3TNS-5EAaFB1qsCgPNY8lpfw7IeJ2NBSHuEO0lXna3qDseB8LhRn-4AQMouEBZcBgdN8B";

  @Autowired
  private FirebaseMessagingService fmService;

  @Test
  void send() {
    assertDoesNotThrow(() -> fmService.send(
            new NotificationDto("senderName", "message", HARDCODED_DEVICE_TOKEN,
            Map.of("userId", "senderId_example", "chatRoomId", "chatRoomId_example"))));
  }
}
