package com.vadmack.petter.ad;

import com.vadmack.petter.ad.dto.*;
import com.vadmack.petter.ad.repository.AdRepository;
import com.vadmack.petter.app.utils.AppUtils;
import com.vadmack.petter.file.metadata.AttachmentType;
import com.vadmack.petter.file.metadata.FileMetadata;
import com.vadmack.petter.file.ImageService;
import com.vadmack.petter.user.User;
import com.vadmack.petter.user.UserService;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class AdService {

  private final AdRepository adRepository;
  private final UserService userService;
  private final ImageService imageService;

  private final ModelMapper modelMapper;

  @Transactional
  public AdGetDto create(@NotNull AdCreateDdo dto, @NotNull User user) {
    Ad ad = dtoToEntity(dto);
    ad.setOwnerId(user.getId());
    ad.setState(AdState.OPEN);
    if (dto.getAddress() == null) {
      ad.setAddress(user.getAddress());
    }
    ad.setCreationDate(LocalDate.now());
    Ad createdAd = adRepository.save(ad);
    userService.addAd(createdAd.getId(), user.getId());
    return entityToDto(createdAd, user.getFavoriteAdIds());
  }

  public @NotNull AdGetDto getDtoById(@NotNull String id, @NotNull Set<String> favoriteAdIds) {
    return entityToDto(getById(id), favoriteAdIds);
  }

  public @NotNull Ad getById(@NotNull String id) {
    return AppUtils.checkFound(findById(id),
            String.format("Ad with id=%s not found", id));
  }

  public Optional<Ad> findById(@NotNull String id) {
    return adRepository.findById(id);
  }

  public @NotNull List<AdGetListDto> getDtoByIdIn(@NotNull Set<String> ids) {
    return entityListToDto(adRepository.findByIdIn(ids), ids);
  }

  public @NotNull List<AdGetListDto> getDtoByProperties(@NotNull AdFilterDto filter,
                                                        Pageable pageable,
                                                        @NotNull Set<String> favoriteAdIds) {
    return entityListToDto(adRepository.findByProperties(filter, pageable), favoriteAdIds);
  }

  public void save(Ad ad) {
    adRepository.save(ad);
  }

  public void updateById(@NotNull AdUpdateDto dto, @NotNull String id) {
    adRepository.updateById(dto, id);
  }

  @Transactional
  public void changeLikeStatus(@NotNull String adId, boolean enable, @NotNull String userId) {
    getById(adId);
    if (enable) {
      userService.addFavoriteAd(adId, userId);
    } else {
      userService.removeFavoriteAd(adId, userId);
    }
  }

  @Transactional
  public void addImage(@NotNull MultipartFile image, @NotNull String adId, @NotNull String userId) {
    FileMetadata fileMetadata = imageService.save(image, userId, AttachmentType.AD, adId);
    adRepository.addImage(fileMetadata.getRelativePath(), adId);
  }

  /**
   * Deletes an ad with attached images
   */
  @Transactional
  public void deleteWithDependenciesById(@NotNull String id) {
    deleteAttachedImagesById(id);
    deleteById(id);
  }
  public void deleteById(@NotNull String id) {
    adRepository.deleteById(id);
  }

  public void deleteWithDependenciesByIdIn(@NotNull Collection<String> ids) {
    ids.forEach(this::deleteAttachedImagesById);
    adRepository.deleteByIdIn(ids);
  }

  private void deleteAttachedImagesById(@NotNull String id) {
    Ad ad = getById(id);
    Set<String> imagePaths = ad.getImagePaths();
    imageService.deleteByRelativePaths(imagePaths);
  }

  private Ad dtoToEntity(@NotNull AdCreateDdo dto) {
    return modelMapper.map(dto, Ad.class);
  }

  private AdGetDto entityToDto(@NotNull Ad entity, @NotNull Set<String> favoriteAdIds) {
    AdGetDto dto = modelMapper.map(entity, AdGetDto.class);
    dto.setLiked(favoriteAdIds.contains(dto.getId()));
    return dto;
  }

  private List<AdGetListDto> entityListToDto(@NotNull Collection<Ad> entities, @NotNull Set<String> favoriteAdIds) {
    return AdMapper.entityListToDto(entities, favoriteAdIds);
  }

  public boolean isOwner(@NotNull User user, @NotNull String adId) {
    Ad ad = getById(adId);
    return ad.getOwnerId().equals(user.getId());
  }
}
