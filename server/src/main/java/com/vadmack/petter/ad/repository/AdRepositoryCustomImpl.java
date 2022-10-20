package com.vadmack.petter.ad.repository;

import com.vadmack.petter.ad.Ad;
import com.vadmack.petter.ad.AdState;
import com.vadmack.petter.ad.Gender;
import com.vadmack.petter.ad.Species;
import com.vadmack.petter.ad.dto.AdUpdateDto;
import com.vadmack.petter.app.repository.CustomMongoRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class AdRepositoryCustomImpl extends CustomMongoRepository implements AdRepositoryCustom {

  @Override
  public void addImage(String imagePath, String adId) {
    Update update = new Update();
    update.addToSet("imagePaths", imagePath);
    Criteria criteria = Criteria.where("_id").is(adId);
    mongoTemplate.updateFirst(Query.query(criteria), update, Ad.class);
  }

  @Override
  public List<Ad> findByProperties(String ownerId,
                                   AdState state,
                                   Species species,
                                   String breed,
                                   Gender gender,
                                   Pageable page) {
    final Query query = new Query().with(page);
    final List<Criteria> criteria = new ArrayList<>();

    if (ownerId != null) {
      criteria.add(Criteria.where("ownerId").is(ownerId));
    }
    if (state != null) {
      criteria.add(Criteria.where("state").is(state));
    }
    if (species != null) {
      criteria.add(Criteria.where("species").is(species));
    }
    if (breed != null) {
      criteria.add(Criteria.where("breed").is(breed));
    }
    if (gender != null) {
      criteria.add(Criteria.where("gender").is(gender));
    }
    if (ownerId != null) {
      criteria.add(Criteria.where("ownerId").is(ownerId));
    }

    if (!criteria.isEmpty())
      query.addCriteria(new Criteria().andOperator(criteria.toArray(new Criteria[0])));
    return mongoTemplate.find(query, Ad.class);
  }

  @Override
  public void updateById(AdUpdateDto dto, String id) {
    super.updateById(dto, id, Ad.class);
  }

}
