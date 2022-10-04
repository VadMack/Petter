package com.vadmack.petter.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("api/users")
@RestController
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
