package com.vadmack.petter.chat.message;

import com.vadmack.petter.app.controller.SecuredRestController;
import com.vadmack.petter.chat.message.dto.ChatMessageDto;
import com.vadmack.petter.chat.message.dto.UploadImageResponse;
import com.vadmack.petter.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Min;
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
                                                          @RequestParam @Min(0) int skip,
                                                          @RequestParam @Min(0) int limit) {
    return ResponseEntity.ok(chatMessageService.getDtoByChatRoomId(chatRoomId, skip, limit));
  }

  @PostMapping(value = "/attachment", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<UploadImageResponse> uploadImage(@AuthenticationPrincipal User user,
                                                         @RequestParam MultipartFile image) {
    return ResponseEntity.ok(new UploadImageResponse(chatMessageService.addImage(image, user.getId())));
  }
}
