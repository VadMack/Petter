package com.vadmack.petter.user;

import com.vadmack.petter.ad.Ad;
import com.vadmack.petter.ad.AdService;
import com.vadmack.petter.ad.dto.AdGetListDto;
import com.vadmack.petter.app.utils.AppUtils;
import com.vadmack.petter.file.AttachmentType;
import com.vadmack.petter.file.FileMetadata;
import com.vadmack.petter.file.FileMetadataService;
import com.vadmack.petter.file.ImageService;
import com.vadmack.petter.user.dto.UserCreateDto;
import com.vadmack.petter.user.dto.UserGetDto;
import com.vadmack.petter.user.dto.UserUpdateDto;
import com.vadmack.petter.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.jetbrains.annotations.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor(onConstructor_ = {@Lazy})
@Service
public class UserService {

  private final UserRepository userRepository;
  private final ImageService imageService;
  private final AdService adService;
  private final FileMetadataService fileMetadataService;

  private final ModelMapper modelMapper;
  private final PasswordEncoder passwordEncoder;

  public void create(UserCreateDto dto) {
    User user = dtoToEntity(dto);
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    userRepository.save(user);
  }

  public @NotNull List<UserGetDto> findAllDto() {
    return userRepository.findAll().stream().map(this::entityToDto)
            .toList();
  }

  public @NotNull UserGetDto getDtoById(String id) {
    return entityToDto(getById(id));
  }

  public @NotNull User getById(String id) {
    return AppUtils.checkFound(findById(id),
            String.format("User with id=%s not found", id));
  }

  private Optional<User> findById(String id) {
    return userRepository.findById(new ObjectId(id));
  }

  public @NotNull List<AdGetListDto> getFavoriteAds(User user) {
    return adService.getByIdIn(user.getFavoriteAdsIds());
  }

  public void save(User user) {
    userRepository.save(user);
  }

  public void updateById(UserUpdateDto userUpdateDto, String userId) {
    userRepository.updateById(userUpdateDto, userId);
  }

  @Transactional
  public void setAvatar(MultipartFile image, User user) {
    String existedAvatarPath = user.getAvatarPath();
    if (existedAvatarPath != null) {
      imageService.deleteByRelativePath(existedAvatarPath);
    }
    String userId = user.getId();
    FileMetadata fileMetadata = imageService.save(image, userId, AttachmentType.USER, userId);
    user.setAvatarPath(fileMetadata.getRelativePath());
    userRepository.save(user);
  }

  public void addAd(Ad ad, String userId) {
    userRepository.addAd(ad, userId);
  }

  public void addFavoriteAd(Ad ad, String userId) {
    userRepository.addFavouriteAd(ad, userId);
  }

  private User dtoToEntity(UserCreateDto dto) {
    return modelMapper.map(dto, User.class);
  }

  private UserGetDto entityToDto(User entity) {
    return modelMapper.map(entity, UserGetDto.class);
  }
}
