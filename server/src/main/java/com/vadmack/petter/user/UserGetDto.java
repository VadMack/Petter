package com.vadmack.petter.user;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import org.bson.types.ObjectId;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class UserGetDto {
  @JsonSerialize(using = ToStringSerializer.class)
  private ObjectId id;
  @Email
  private String email;
  @NotBlank
  private String username;
  @NotBlank
  private String displayName;
}
