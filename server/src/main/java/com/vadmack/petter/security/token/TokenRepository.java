package com.vadmack.petter.security.token;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface TokenRepository extends MongoRepository<Token, String> {

  Optional<Token> findByValue(String value);
  Optional<Token> findByTypeAndValue(TokenType type, String value);
  List<Token> findAllByTypeAndUserId(TokenType type, String userId);
  void deleteByValue(String value);
  void deleteByTypeAndValue(TokenType type, String value);
  void deleteByExpirationBefore(Instant time);
}
