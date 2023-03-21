package com.vadmack.petter.chat.message;

import com.vadmack.petter.app.controller.SecuredRestController;
import com.vadmack.petter.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("api/chat-message")
@RestController
public class ChatMessageController implements SecuredRestController {

  private final ChatMessageService chatMessageService;

  @PreAuthorize("@chatRoomService.isParticipant(#user, #chatRoomId)")
  @GetMapping
  public ResponseEntity<List<ChatMessageDto>> getByRoomId(@AuthenticationPrincipal User user,
                                                          @RequestParam String chatRoomId,
                                                          Pageable pageable) {
    return ResponseEntity.ok(chatMessageService.getDtoByChatRoomId(chatRoomId, pageable));
  }
}