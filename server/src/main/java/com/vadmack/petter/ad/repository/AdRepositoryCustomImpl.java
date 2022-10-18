package com.vadmack.petter.ad.repository;

import com.vadmack.petter.ad.Ad;
import com.vadmack.petter.app.repository.CustomMongoRepository;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

@Repository
public class AdRepositoryCustomImpl extends CustomMongoRepository implements AdRepositoryCustom {

  @Override
  public void addImage(String imagePath, String adId) {
    Update update = new Update();
    update.addToSet("imagePaths", imagePath);
    Criteria criteria = Criteria.where("_id").is(adId);
    mongoTemplate.updateFirst(Query.query(criteria), update, Ad.class);
  }
}
