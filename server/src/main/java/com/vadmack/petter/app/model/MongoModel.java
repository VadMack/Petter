package com.vadmack.petter.app.model;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.MongoId;

public abstract class MongoModel {

  @MongoId
  private ObjectId id;

  public String getId() {
    return id.toString();
  }

  public void setId(String id) {
    this.id = new ObjectId(id);
  }
}
