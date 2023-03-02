package com.vadmack.petter.security.confirmationcode;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ConfirmationCodeRepository extends MongoRepository<ConfirmationCode, String> {

  boolean existsByCodeAndUserIdAndType(short code, String userId, ConfirmationCodeType type);
  Optional<ConfirmationCode> findByUserIdAndType(String userId, ConfirmationCodeType type);
  void deleteByCodeAndUserIdAndType(short code, String userId, ConfirmationCodeType type);
}
