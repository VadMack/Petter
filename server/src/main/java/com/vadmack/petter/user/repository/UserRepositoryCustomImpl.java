package com.vadmack.petter.user.repository;

import com.vadmack.petter.app.repository.CustomMongoRepository;
import com.vadmack.petter.user.User;
import com.vadmack.petter.user.dto.UserUpdateDto;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryCustomImpl extends CustomMongoRepository implements UserRepositoryCustom {

  @Override
  public void addAdId(String adId, String userId) {
    Update update = new Update();
    update.addToSet("adIds", adId);
    updateFirstByUserId(update, userId);
  }

  @Override
  public void addFavouriteAdId(String adId, String userId) {
    Update update = new Update();
    update.addToSet("favoriteAdIds", adId);
    updateFirstByUserId(update, userId);
  }

  @Override
  public void removeFavouriteAdId(String adId, String userId) {
    Update update = new Update();
    update.pull("favoriteAdIds", adId);
    updateFirstByUserId(update, userId);
  }

  @Override
  public void updateById(UserUpdateDto dto, String userId) {
    super.updateById(dto, userId, User.class);
  }

  private void updateFirstByUserId(Update update, String userId) {
    Criteria criteria = Criteria.where("_id").is(userId);
    mongoTemplate.updateFirst(Query.query(criteria), update, User.class);
  }
}
