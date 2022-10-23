package com.vadmack.petter.ad.repository;

import com.vadmack.petter.ad.Ad;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Collection;
import java.util.List;

public interface AdRepository extends MongoRepository<Ad, String>, AdRepositoryCustom{
  List<Ad> findByIdIn(Collection<String> ids);
  void deleteByIdIn(Collection<String> ids);
}
