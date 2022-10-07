package com.vadmack.petter.user.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.vadmack.petter.user.Address;
import lombok.Data;
import org.bson.types.ObjectId;

import java.util.HashSet;
import java.util.Set;

@Data
public class UserGetDto {
  @JsonSerialize(using = ToStringSerializer.class)
  private ObjectId id;
  private String email;
  private String username;
  private String displayName;
  private String phoneNumber;
  private Address address;
  @JsonSerialize(using = ToStringSerializer.class)
  private Set<ObjectId> imageIds = new HashSet<>();

}
