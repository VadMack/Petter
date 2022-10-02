package com.vadmack.petter.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("api/users")
@Controller
public class UserController {

  private final UserService userService;

  @PostMapping
  public ResponseEntity<?> create(@RequestBody UserCreateDto dto) {
    userService.create(dto);
    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  @GetMapping
  public ResponseEntity<List<UserGetDto>> getAll() {
    return ResponseEntity.ok(userService.findAll());
  }
}
