package com.vadmack.petter.security.token;

import com.vadmack.petter.app.exception.ValidationException;
import com.vadmack.petter.app.utils.AppUtils;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TokenService {

  private final TokenRepository tokenRepository;

  public @NotNull String createRefreshToken(@NotNull String userId) {
    return createToken(userId, TokenType.REFRESH);
  }

  public @NotNull String createToken(@NotNull String userId, @NotNull TokenType type) {
    return tokenRepository.save(
            new Token(userId, UUID.randomUUID().toString(), type, LocalDateTime.now().plusMonths(1))
    ).getValue();
  }

  public @NotNull Token getByValue(@NotNull String value) {
    return AppUtils.checkFound(findByValue(value),
            String.format("Token=%s not found", value));
  }

  private Optional<Token> findByValue(@NotNull String value) {
    return tokenRepository.findByValue(value);
  }

  /**
   * @return userId of token owner
   */
  public String validate(String value) {
    Token token = getByValue(value);
    if (token.getExpiration().isBefore(LocalDateTime.now())) {
      throw new ValidationException("Refresh token expired");
    }
    return token.getUserId();
  }

  public void deleteByValue(String value) {
    tokenRepository.deleteByValue(value);
  }
}
