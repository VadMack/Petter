package com.vadmack.petter.websocket;


import com.vadmack.petter.chat.RSAUtils;
import com.vadmack.petter.chat.message.ChatMessageDto;
import com.vadmack.petter.chat.message.ChatMessageRepository;
import com.vadmack.petter.chat.room.ChatRoom;
import com.vadmack.petter.chat.room.ChatRoomRepository;
import com.vadmack.petter.security.JwtTokenUtil;
import com.vadmack.petter.user.User;
import com.vadmack.petter.user.repository.UserRepository;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.messaging.converter.GsonMessageConverter;
import org.springframework.messaging.converter.StringMessageConverter;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class WebSocketsIT {
  private WebSocketStompClient client;
  @LocalServerPort
  private Integer port;
  private static String TOKEN;
  private static User savedUser;
  private static ChatRoom savedChatRoom;

  @BeforeAll
  static void setUp(@Autowired PasswordEncoder passwordEncoder,
                    @Autowired UserRepository userRepository,
                    @Autowired JwtTokenUtil jwtTokenUtil,
                    @Autowired ChatRoomRepository chatRoomRepository) {
    User user = new User();
    user.setUsername("USERNAME");
    user.setPassword(passwordEncoder.encode("PASSWORD"));
    user.setEnabled(true);
    savedUser = userRepository.save(user);
    TOKEN = jwtTokenUtil.generateAccessToken(savedUser);
    savedChatRoom = chatRoomRepository.save(new ChatRoom(savedUser.getId(), savedUser.getId()));
  }

  @BeforeEach
  void setup() {
    client = new WebSocketStompClient(new SockJsClient(
            List.of(new WebSocketTransport(new StandardWebSocketClient()))));
  }

  @Test
  void sendAndReceiveString() throws Exception {
    BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(1);
    client.setMessageConverter(new StringMessageConverter());
    StompSession session = connect();

    subscribe(session, "/topic/hello", String.class,
            payload -> blockingQueue.add((String) payload));

    session.send("/app/hello", "Hello world!");

    await()
            .atMost(1, SECONDS)
            .untilAsserted(() -> assertEquals("Hello world!", blockingQueue.poll()));
  }

  @Test
  void sendAndReceiveMessage() throws Exception {
    BlockingQueue<ChatMessageDto> blockingQueue = new ArrayBlockingQueue<>(1);
    client.setMessageConverter(new GsonMessageConverter());
    StompSession session = connect();

    subscribe(session, "/topic/chat/" + savedChatRoom.getId(), ChatMessageDto.class,
            payload -> blockingQueue.add((ChatMessageDto) payload));

    String initialContent = "Hello";
    String encryptedContent = RSAUtils.encrypt(initialContent, savedChatRoom.getId());
    ChatMessageDto msg = new ChatMessageDto(encryptedContent,
            savedUser.getId(), savedUser.getId());
    msg.setChatRoomId(savedChatRoom.getId());

    session.send("/app/chat/" + savedChatRoom.getId(), msg);

    AtomicReference<ChatMessageDto> received = new AtomicReference<>();
    await()
            .atMost(1, SECONDS)
            .until(() -> {
              received.set(blockingQueue.poll());
              return received.get() != null;
            });

    assertEquals(initialContent, received.get().getContent());
  }

  @Test
  void subscribeForeignTopicNotAllowed() throws Exception {
    client.setMessageConverter(new GsonMessageConverter());
    StompSession session = connect();

    subscribe(session, "/topic/chat/" + "not_my_topic", ChatMessageDto.class,
            payload -> {});

    await()
            .atMost(1, SECONDS)
            .untilAsserted(() -> assertFalse(session.isConnected()));
  }

  private StompSession connect() throws Exception {
    StompHeaders connectHeaders = new StompHeaders();
    connectHeaders.add("Authorization", "Bearer " + TOKEN);
    await().atMost(5, SECONDS).until(() -> port != null);
    return client
            .connect("ws://localhost:" + port + "/ws",
                    new WebSocketHttpHeaders(), connectHeaders, new StompSessionHandlerAdapter() {})
            .get(1, SECONDS);
  }

  private void subscribe(StompSession session, String topicPath, Type messageType, Consumer<Object> task) {
    session.subscribe(topicPath, new StompFrameHandler() {
      @Override
      public @NotNull Type getPayloadType(@NotNull StompHeaders headers) {
        return messageType;
      }

      @Override
      public void handleFrame(@NotNull StompHeaders headers, Object payload) {
        task.accept(payload);
      }
    });
  }

  @AfterAll
  static void clenUp(@Autowired UserRepository userRepository,
                     @Autowired ChatRoomRepository chatRoomRepository,
                     @Autowired ChatMessageRepository chatMessageRepository) {
    userRepository.delete(savedUser);
    chatRoomRepository.delete(savedChatRoom);
    chatMessageRepository.deleteByChatRoomId(savedChatRoom.getId());
  }
}
