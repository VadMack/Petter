package com.vadmack.petter.app.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.MongoId;

@EqualsAndHashCode
public abstract class MongoModel {

  @MongoId
  private ObjectId id;

  public String getId() {
    return id.toString();
  }

  public void setId(String id) {
    this.id = new ObjectId(id);
  }

  @JsonProperty("id")
  public void setId(ObjectId id) {
    this.id = id;
  }
}
