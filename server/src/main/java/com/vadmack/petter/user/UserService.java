package com.vadmack.petter.user;

import com.vadmack.petter.ad.Ad;
import com.vadmack.petter.file.FileMetadata;
import com.vadmack.petter.file.ImageService;
import com.vadmack.petter.user.dto.UserCreateDto;
import com.vadmack.petter.user.dto.UserGetDto;
import com.vadmack.petter.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserService {

  private final UserRepository userRepository;

  private final ModelMapper modelMapper;
  private final PasswordEncoder passwordEncoder;
  private final ImageService imageService;

  public void create(UserCreateDto dto) {
    User user = dtoToEntity(dto);
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    userRepository.save(user);
  }

  public void addImage(FileMetadata fileMetadata, String userId) {
    userRepository.addImage(fileMetadata, userId);
  }

  public void addAd(Ad ad, String userId) {
    userRepository.addAd(ad, userId);
  }

  public void addFavoriteAd(Ad ad, String userId) {
    userRepository.addFavouriteAd(ad, userId);
  }

  public List<UserGetDto> findAll() {
    return userRepository.findAll().stream().map(user -> {
      UserGetDto dto = entityToDto(user);
      dto.setImageIds(user.getImages().stream().map(FileMetadata::getId).collect(Collectors.toSet()));
      return dto;
    })
            .toList();
  }

  public Optional<User> findByUsername(String username) {
    return userRepository.findByUsername(username);
  }

  @Transactional
  public void addImage(MultipartFile image, String userId) {
    FileMetadata fileMetadata = imageService.save(image, userId);
    userRepository.addImage(fileMetadata, userId);
  }

  private User dtoToEntity(UserCreateDto dto) {
    return modelMapper.map(dto, User.class);
  }

  private UserGetDto entityToDto(User entity) {
    return modelMapper.map(entity, UserGetDto.class);
  }


}
