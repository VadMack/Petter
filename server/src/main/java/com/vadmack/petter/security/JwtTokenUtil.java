package com.vadmack.petter.security;

import com.vadmack.petter.app.exception.UnauthorizedException;
import com.vadmack.petter.security.token.TokenService;
import com.vadmack.petter.security.token.TokenType;
import com.vadmack.petter.user.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;
import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenUtil {

  private final TokenService tokenService;

  @Value("${authorization.secret}")
  private String secret;

  private SecretKey secretKey;

  @PostConstruct
  private void init() {
    secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
  }

  public String generateAccessToken(User user) {
    return Jwts.builder()
            .setSubject(String.format("%s,%s", user.getId(), user.getUsername()))
            .claim("Authorities", user.getAuthorities())
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000)) // 24 hours
            .signWith(secretKey, SignatureAlgorithm.HS256)
            .compact();
  }

  public boolean validate(String token) {
    if (tokenService.findByTypeAndValue(TokenType.BLACKLIST_JWT, token).isPresent()) {
      throw new UnauthorizedException("Token is inactive since the logout was committed");
    }

    try {
      Jwts.parserBuilder()
              .setSigningKey(secretKey)
              .build()
              .parseClaimsJws(token);
      return true;
    } catch (MalformedJwtException ex) {
      log.error("Invalid JWT token - {}", ex.getMessage());
    } catch (ExpiredJwtException ex) {
      log.error("Expired JWT token - {}", ex.getMessage());
    } catch (UnsupportedJwtException ex) {
      log.error("Unsupported JWT token - {}", ex.getMessage());
    } catch (IllegalArgumentException ex) {
      log.error("JWT claims string is empty - {}", ex.getMessage());
    }
    return false;
  }

  public String getUsername(String token) {
    Claims claims = Jwts.parserBuilder()
            .setSigningKey(secretKey)
            .build()
            .parseClaimsJws(token)
            .getBody();
    return claims.getSubject().split(",")[1];
  }

}