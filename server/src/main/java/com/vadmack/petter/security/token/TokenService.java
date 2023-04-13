package com.vadmack.petter.security.token;

import com.auth0.jwt.JWT;
import com.vadmack.petter.app.exception.ValidationException;
import com.vadmack.petter.app.utils.AppUtils;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TokenService {

  private final TokenRepository tokenRepository;

  public void addJwtToBlacklist(String jwt) {
    tokenRepository.save(new Token(jwt, TokenType.BLACKLIST_JWT, JWT.decode(jwt).getExpiresAtAsInstant()));
  }

  public void addDeviceToken(@NotNull String userId, String value) {
    tokenRepository.save(new Token(userId, value, TokenType.DEVICE_TOKEN));
  }

  public @NotNull String createRefreshToken(@NotNull String userId) {
    return createToken(userId, TokenType.REFRESH);
  }

  public @NotNull String createToken(@NotNull String userId, @NotNull TokenType type) {
    return tokenRepository.save(
            new Token(userId, UUID.randomUUID().toString(), type,
                    Instant.now().plus(30, ChronoUnit.DAYS))
    ).getValue();
  }

  public @NotNull Token getByValue(@NotNull String value) {
    return AppUtils.checkFound(findByValue(value),
            String.format("Token=%s not found", value));
  }

  public Optional<Token> findByValue(@NotNull String value) {
    return tokenRepository.findByValue(value);
  }

  public Optional<Token> findByTypeAndValue(@NotNull TokenType type, @NotNull String value) {
    return tokenRepository.findByTypeAndValue(type, value);
  }

  public List<Token> getAllByTypeAndUserId(@NotNull TokenType type, @NotNull String userId) {
    return tokenRepository.findAllByTypeAndUserId(type, userId);
  }

  /**
   * @return userId of token owner
   */
  public String validate(@NotNull String value) {
    Token token = getByValue(value);
    if (token.getExpiration().isBefore(Instant.now())) {
      throw new ValidationException("Refresh token expired");
    }
    return token.getUserId();
  }

  public void deleteByValue(@NotNull String value) {
    tokenRepository.deleteByValue(value);
  }

  public void deleteByTypeAndValue(@NotNull TokenType type, @NotNull String value) {
    tokenRepository.deleteByTypeAndValue(type, value);
  }
}
