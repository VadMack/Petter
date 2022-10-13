package com.vadmack.petter.user;

import com.vadmack.petter.ad.Ad;
import com.vadmack.petter.app.model.MongoModel;
import com.vadmack.petter.file.FileMetadata;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.HashSet;
import java.util.Set;

@Hidden
@EqualsAndHashCode(callSuper = true)
@Data
@Document(collection = "users")
public class User extends MongoModel implements UserDetails {
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
  private FileMetadata avatar;
  @DBRef(lazy = true)
  private Set<Ad> ads = new HashSet<>();
  @DBRef(lazy = true)
  private Set<Ad> favoriteAds = new HashSet<>();

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
