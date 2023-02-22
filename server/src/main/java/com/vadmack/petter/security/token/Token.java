package com.vadmack.petter.security.token;

import com.vadmack.petter.app.model.MongoModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@Document(collection = "tokens")
public class Token extends MongoModel {
  private String userId;
  @Indexed(unique = true)
  private String value;
  private TokenType type;
  private LocalDateTime expiration;
}
