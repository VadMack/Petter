package com.vadmack.petter.user;

import com.vadmack.petter.file.FileMetadata;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.HashSet;
import java.util.Set;

@Data
@Document(collection = "users")
public class User implements UserDetails {
  @Id
  private ObjectId id;
  @Indexed(unique = true)
  private String email;
  @Indexed(unique = true)
  private String username;
  private String password;
  private String displayName;
  private Set<UserAuthority> authorities = new HashSet<>();
  private String phoneNumber;
  private Address address;
  @DBRef
  private Set<FileMetadata> images = new HashSet<>();

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
