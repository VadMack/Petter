package com.vadmack.petter.ad;

import com.vadmack.petter.ad.dto.AdCreateDdo;
import com.vadmack.petter.ad.dto.AdGetDto;
import com.vadmack.petter.ad.repository.AdRepository;
import com.vadmack.petter.app.utils.AppUtils;
import com.vadmack.petter.file.FileMetadata;
import com.vadmack.petter.file.ImageService;
import com.vadmack.petter.user.User;
import com.vadmack.petter.user.UserService;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.jetbrains.annotations.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AdService {

  private final AdRepository adRepository;
  private final UserService userService;
  private final ImageService imageService;

  private final ModelMapper modelMapper;

  public List<AdGetDto> findAllDto() {
    return adRepository.findAll().stream().map(this::entityToDto)
            .toList();
  }

  @Transactional
  public void create(AdCreateDdo dto, String userId) {
    Ad ad = dtoToEntity(dto);
    ad.setOwnerId(userId);
    adRepository.save(ad);
    userService.addAd(ad, userId);
  }

  @Transactional
  public void like(String adId, String userId) {
    Ad ad = AppUtils.checkFound(findById(adId),
            String.format("File metadata with id=%s not found", adId));
    userService.addFavoriteAd(ad, userId);
  }

  @Transactional
  public void addImage(MultipartFile image, String adId, String userId) {
    FileMetadata fileMetadata = imageService.save(image, userId);
    adRepository.addImage(fileMetadata.getRelativePath(), adId);
  }

  public Optional<Ad> findById(String id) {
    return adRepository.findById(new ObjectId(id));
  }

  public @NotNull Ad getById(String id) {
    return AppUtils.checkFound(findById(id),
            String.format("Ad with id=%s not found", id));
  }

  private Ad dtoToEntity(AdCreateDdo dto) {
    return modelMapper.map(dto, Ad.class);
  }

  private AdGetDto entityToDto(Ad entity) {
    return modelMapper.map(entity, AdGetDto.class);
  }

  public boolean isOwner(User user, String adId) {
    Ad ad = getById(adId);
    return ad.getOwnerId().equals(user.getId());
  }
}
