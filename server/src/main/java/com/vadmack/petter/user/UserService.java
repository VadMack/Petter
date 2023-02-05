package com.vadmack.petter.user;

import com.vadmack.petter.ad.AdService;
import com.vadmack.petter.ad.dto.AdGetListDto;
import com.vadmack.petter.app.exception.ValidationException;
import com.vadmack.petter.app.utils.AppUtils;
import com.vadmack.petter.file.AttachmentType;
import com.vadmack.petter.file.FileMetadata;
import com.vadmack.petter.file.ImageService;
import com.vadmack.petter.user.dto.UserCreateDto;
import com.vadmack.petter.user.dto.UserGetDto;
import com.vadmack.petter.user.dto.UserUpdateDto;
import com.vadmack.petter.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor(onConstructor_ = {@Lazy})
@Service
public class UserService {

  private final UserRepository userRepository;
  private final ImageService imageService;
  private final AdService adService;

  private final ModelMapper modelMapper;
  private final PasswordEncoder passwordEncoder;

  public @NotNull User create(@NotNull UserCreateDto dto) {
    checkNotExistsByUsernameOrEmail(dto.getUsername(), dto.getEmail());
    User user = dtoToEntity(dto);
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    return userRepository.save(user);
  }

  public @NotNull List<UserGetDto> findAllDto() {
    return userRepository.findAll().stream().map(this::entityToDto)
            .toList();
  }

  public @NotNull UserGetDto getDtoById(@NotNull String id) {
    return entityToDto(getById(id));
  }

  public @NotNull User getById(@NotNull String id) {
    return AppUtils.checkFound(findById(id),
            String.format("User with id=%s not found", id));
  }

  private Optional<User> findById(@NotNull String id) {
    return userRepository.findById(id);
  }

  public @NotNull User getByEmail(@NotNull String email) {
    return AppUtils.checkFound(findByEmail(email),
            String.format("User with email=%s not found", email));
  }

  private Optional<User> findByEmail(@NotNull String id) {
    return userRepository.findByEmail(id);
  }

  private void checkNotExistsByUsernameOrEmail(@NotNull String username, @NotNull String email) {
    checkNotExistsByUsername(username);
    checkNotExistsByEmail(email);
  }

  private void checkNotExistsByUsername(@NotNull String username) {
    if (userRepository.existsByUsername(username)) {
      throw new ValidationException(String.format("User with username=%s already exists", username));
    }
  }

  private void checkNotExistsByEmail(@NotNull String email) {
    if (userRepository.existsByEmail(email)) {
      throw new ValidationException(String.format("User with email=%s already exists", email));
    }
  }

  public @NotNull List<AdGetListDto> getFavoriteAds(@NotNull User user) {
    return adService.getDtoByIdIn(user.getFavoriteAdIds());
  }

  public void save(User user) {
    userRepository.save(user);
  }

  public void updateById(@NotNull UserUpdateDto userUpdateDto, @NotNull String userId) {
    userRepository.updateById(userUpdateDto, userId);
  }

  @Transactional
  public void setAvatar(@NotNull MultipartFile image, @NotNull User user) {
    String existedAvatarPath = user.getAvatarPath();
    if (existedAvatarPath != null) {
      imageService.unlinkAndDeleteByRelativePath(existedAvatarPath);
    }
    String userId = user.getId();
    FileMetadata fileMetadata = imageService.save(image, userId, AttachmentType.USER, userId);
    user.setAvatarPath(fileMetadata.getRelativePath());
    userRepository.save(user);
  }

  /**
   * Deletes a user with attached avatar image and ads with their images
   */
  @Transactional
  public void deleteWithDependencies(@NotNull User user) {
    String avatarPath = user.getAvatarPath();
    if (avatarPath != null) {
      imageService.deleteByRelativePath(avatarPath);
    }

    Set<String> adIds = user.getAdIds();
    adService.deleteWithDependenciesByIdIn(adIds);

    delete(user);
  }

  private void delete(@NotNull User user) {
    userRepository.delete(user);
  }

  public void addAd(@NotNull String adId, @NotNull String userId) {
    userRepository.addAdId(adId, userId);
  }

  public void addFavoriteAd(@NotNull String adId, @NotNull String userId) {
    userRepository.addFavouriteAdId(adId, userId);
  }

  private User dtoToEntity(@NotNull UserCreateDto dto) {
    return modelMapper.map(dto, User.class);
  }

  public UserGetDto entityToDto(@NotNull User entity) {
    return modelMapper.map(entity, UserGetDto.class);
  }
}
