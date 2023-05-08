package com.vadmack.petter.app.model;

import lombok.EqualsAndHashCode;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.MongoId;

@EqualsAndHashCode
public abstract class MongoModel {

  @MongoId
  private ObjectId id;

  public String getId() {
    if (id == null) {
      return null;
    } else {
      return id.toString();
    }
  }

  public void setId(String id) {
    this.id = new ObjectId(id);
  }
}
