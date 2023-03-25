package com.vadmack.petter.chat.room;

import com.vadmack.petter.app.controller.SecuredRestController;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("api/chat-room")
@RestController
public class ChatRoomController implements SecuredRestController {

  private final ChatRoomService chatRoomService;

  @GetMapping
  public ResponseEntity<List<ChatRoomDto>> getByUserId(@RequestParam String userId) {
    return ResponseEntity.ok(chatRoomService.getDtoByUserId(userId));
  }
}
