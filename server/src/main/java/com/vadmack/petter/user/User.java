package com.vadmack.petter.user;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "users")
public class User {
  @Id
  private ObjectId id;
  private String email;
  private String username;
  private String password;
  private String displayName;
  private String authorities;
}
