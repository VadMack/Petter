package com.vadmack.petter.security.confirmationcode;

import com.vadmack.petter.app.model.MongoModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@EqualsAndHashCode(callSuper = true)
@Data
@CompoundIndex(name = "userId_type", def = "{'userId': 1, 'type': 1}", unique = true)
@Document(collection = "confirmationCodes")
public class ConfirmationCode extends MongoModel {
  private String userId;
  @Min(1000)
  @Max(9999)
  private short code;
  private ConfirmationCodeType type;
}
