package com.vadmack.petter.ad.repository;

import com.vadmack.petter.ad.Ad;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Collection;
import java.util.List;

public interface AdRepository extends MongoRepository<Ad, ObjectId>, AdRepositoryCustom{
  List<Ad> findByIdIn(Collection<String> ids);
}
