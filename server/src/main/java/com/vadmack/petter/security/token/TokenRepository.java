package com.vadmack.petter.security.token;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface TokenRepository extends MongoRepository<Token, String> {

  Optional<Token> findByValue(String value);
  void deleteByValue(String value);
}
