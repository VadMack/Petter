package com.vadmack.petter.chat.room;

import com.vadmack.petter.app.controller.SecuredRestController;
import com.vadmack.petter.chat.room.dto.ChatRoomCreateDto;
import com.vadmack.petter.chat.room.dto.ChatRoomGetDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("api/chat-room")
@RestController
public class ChatRoomController implements SecuredRestController {

  private final ChatRoomService chatRoomService;

  @PostMapping
  public ResponseEntity<ChatRoomGetDto> findByParticipantsOrCreate(@RequestBody ChatRoomCreateDto dto) {
    return ResponseEntity.ok(chatRoomService.findByParticipantsOrCreate(dto.getUser1(), dto.getUser2()));
  }

  @GetMapping
  public ResponseEntity<List<ChatRoomGetDto>> getByUserId(@RequestParam String userId) {
    return ResponseEntity.ok(chatRoomService.getDtoByUserId(userId));
  }
}
