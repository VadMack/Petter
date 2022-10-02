package com.vadmack.petter.user;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {

  private final UserRepository userRepository;

  private final ModelMapper modelMapper;

  public void create(UserCreateDto dto) {
    userRepository.save(dtoToEntity(dto));
  }

  public List<UserGetDto> findAll() {
    return userRepository.findAll().stream().map(this::entityToDto).toList();
  }

  private User dtoToEntity(UserCreateDto dto) {
    return modelMapper.map(dto, User.class);
  }

  private UserGetDto entityToDto(User entity) {
    return modelMapper.map(entity, UserGetDto.class);
  }
}
