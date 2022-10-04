package com.vadmack.petter.security;

import com.vadmack.petter.user.User;
import com.vadmack.petter.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@RequiredArgsConstructor
@Component
public class UserDetailsServiceImpl implements UserDetailsService {

  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Optional<User> oUser = userRepository.findByUsername(username);
    if (oUser.isEmpty()) {
      throw new UsernameNotFoundException(String.format("User with username=%s not found", username));
    }
    return oUser.get();
  }
}
