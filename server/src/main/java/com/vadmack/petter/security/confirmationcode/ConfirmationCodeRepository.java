package com.vadmack.petter.security.confirmationcode;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ConfirmationCodeRepository extends MongoRepository<ConfirmationCode, String> {

  boolean existsByCodeAndUserIdAndType(short code, String userId, ConfirmationCodeType type);
  void deleteByCodeAndUserIdAndType(short code, String userId, ConfirmationCodeType type);
}
