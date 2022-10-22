package com.vadmack.petter.ad;

import com.vadmack.petter.ad.dto.*;
import com.vadmack.petter.ad.repository.AdRepository;
import com.vadmack.petter.app.utils.AppUtils;
import com.vadmack.petter.file.AttachmentType;
import com.vadmack.petter.file.FileMetadata;
import com.vadmack.petter.file.ImageService;
import com.vadmack.petter.user.User;
import com.vadmack.petter.user.UserService;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.jetbrains.annotations.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AdService {

  private final AdRepository adRepository;
  private final UserService userService;
  private final ImageService imageService;

  private final ModelMapper modelMapper;

  public @NotNull List<AdGetListDto> getDtoByProperties(AdFilterDto filter, Pageable pageable) {
    return entityListToDto(adRepository.findByProperties(
            filter.getOwnerId(),
            filter.getState(),
            filter.getSpecies(),
            filter.getBreed(),
            filter.getGender(),
            pageable));
  }

  public @NotNull List<AdGetListDto> getByIdIn(Collection<String> ids) {
    return entityListToDto(adRepository.findByIdIn(ids));
  }

  @Transactional
  public void create(AdCreateDdo dto, String userId) {
    Ad ad = dtoToEntity(dto);
    ad.setOwnerId(userId);
    adRepository.save(ad);
    userService.addAd(ad, userId);
  }

  public void save(Ad ad) {
    adRepository.save(ad);
  }

  public void updateById(AdUpdateDto dto, String id) {
    adRepository.updateById(dto, id);
  }

  @Transactional
  public void like(String adId, String userId) {
    Ad ad = AppUtils.checkFound(findById(adId),
            String.format("File metadata with id=%s not found", adId));
    userService.addFavoriteAd(ad, userId);
  }

  @Transactional
  public void addImage(MultipartFile image, String adId, String userId) {
    FileMetadata fileMetadata = imageService.save(image, userId, AttachmentType.AD, adId);
    adRepository.addImage(fileMetadata.getRelativePath(), adId);
  }

  public @NotNull AdGetDto getDtoById(String id) {
    return entityToDto(getById(id));
  }

  public @NotNull Ad getById(String id) {
    return AppUtils.checkFound(findById(id),
            String.format("Ad with id=%s not found", id));
  }

  public Optional<Ad> findById(String id) {
    return adRepository.findById(new ObjectId(id));
  }

  private Ad dtoToEntity(AdCreateDdo dto) {
    return modelMapper.map(dto, Ad.class);
  }

  private AdGetDto entityToDto(Ad entity) {
    return modelMapper.map(entity, AdGetDto.class);
  }

  private List<AdGetListDto> entityListToDto(Collection<Ad> entities) {
    return AdMapper.entityToListDto(entities);
  }

  public boolean isOwner(User user, String adId) {
    Ad ad = getById(adId);
    return ad.getOwnerId().equals(user.getId());
  }

  public boolean adContainsImage(String adId, String imagePath) {
    return getById(adId).getImagePaths().contains(imagePath);
  }

  public boolean checkCanDeleteImage(User user, String adId, String imagePath) {
    return isOwner(user, adId) && adContainsImage(adId, imagePath);
  }
}
