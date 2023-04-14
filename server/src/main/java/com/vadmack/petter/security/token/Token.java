package com.vadmack.petter.security.token;

import com.vadmack.petter.app.model.MongoModel;
import lombok.*;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "tokens")
public class Token extends MongoModel {
  private String userId;
  @Indexed(unique = true)
  private String value;
  private TokenType type;
  private Instant expiration;

  public Token(String value, TokenType type, Instant expiration) {
    this.value = value;
    this.type = type;
    this.expiration = expiration;
  }

  public Token(String userId, String value, TokenType type) {
    this.userId = userId;
    this.value = value;
    this.type = type;
  }
}
