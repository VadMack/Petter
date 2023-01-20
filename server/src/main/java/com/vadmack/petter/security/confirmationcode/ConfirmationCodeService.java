package com.vadmack.petter.security.confirmationcode;

import com.vadmack.petter.app.exception.ValidationException;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;

@RequiredArgsConstructor
@Service
public class ConfirmationCodeService {

  private final ConfirmationCodeRepository confirmationCodeRepository;

  public short create(@NotNull String userId, @NotNull ConfirmationCodeType type) {
    ConfirmationCode code = new ConfirmationCode();
    code.setUserId(userId);
    code.setType(type);
    code.setCode(generateCode());
    confirmationCodeRepository.save(code);
    return code.getCode();
  }

  public void validateCode(short code, @NotNull String userId,
                           @NotNull ConfirmationCodeType type) {
    if (!existsByCodeAndUserIdAndType(code, userId, type)) {
      throw new ValidationException("Invalid code or userId");
    }
  }

  private boolean existsByCodeAndUserIdAndType(short code, @NotNull String userId,
                                               @NotNull ConfirmationCodeType type) {
    return confirmationCodeRepository.existsByCodeAndUserIdAndType(code, userId, type);
  }

  public void deleteByCodeAndUserIdAndType(short code, @NotNull String userId,
                                           @NotNull ConfirmationCodeType type) {
    confirmationCodeRepository.deleteByCodeAndUserIdAndType(code, userId, type);
  }

  private short generateCode() {
    return (short) ThreadLocalRandom.current().nextInt(1000, 10000);
  }
}
