package com.vadmack.petter.app.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

public abstract class CustomMongoRepository {
  protected MongoTemplate mongoTemplate;

  @Autowired
  public final void setMongoTemplate(MongoTemplate mongoTemplate) {
    this.mongoTemplate = mongoTemplate;
  }
}
