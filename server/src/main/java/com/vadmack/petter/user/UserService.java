package com.vadmack.petter.user;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

  private final UserRepository userRepository;

  private final ModelMapper modelMapper;
  private final PasswordEncoder passwordEncoder;

  public void create(UserCreateDto dto) {
    User user = dtoToEntity(dto);
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    userRepository.save(user);
  }

  public List<UserGetDto> findAll() {
    return userRepository.findAll().stream().map(this::entityToDto).toList();
  }

  public Optional<User> findByUsername(String username) {
    return userRepository.findByUsername(username);
  }

  private User dtoToEntity(UserCreateDto dto) {
    return modelMapper.map(dto, User.class);
  }

  private UserGetDto entityToDto(User entity) {
    return modelMapper.map(entity, UserGetDto.class);
  }
}
