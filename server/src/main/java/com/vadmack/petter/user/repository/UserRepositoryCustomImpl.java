package com.vadmack.petter.user.repository;

import com.vadmack.petter.ad.Ad;
import com.vadmack.petter.app.repository.CustomMongoRepository;
import com.vadmack.petter.file.FileMetadata;
import com.vadmack.petter.user.User;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryCustomImpl extends CustomMongoRepository implements UserRepositoryCustom {

  @Override
  public void addImage(FileMetadata fileMetadata, String userId) {
    Update update = new Update();
    update.addToSet("images", fileMetadata);
    Criteria criteria = Criteria.where("_id").is(userId);
    mongoTemplate.updateFirst(Query.query(criteria), update, User.class);
  }

  @Override
  public void addAd(Ad ad, String userId) {
    Update update = new Update();
    update.addToSet("ads", ad);
    Criteria criteria = Criteria.where("_id").is(userId);
    mongoTemplate.updateFirst(Query.query(criteria), update, User.class);
  }

  @Override
  public void addFavouriteAd(Ad ad, String userId) {
    Update update = new Update();
    update.addToSet("favoriteAds", ad);
    Criteria criteria = Criteria.where("_id").is(userId);
    mongoTemplate.updateFirst(Query.query(criteria), update, User.class);
  }
}
