package com.vadmack.petter.security.token;

import com.vadmack.petter.app.config.Cleaner;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Slf4j
@Data
@RequiredArgsConstructor
@EnableScheduling
@Configuration
public class ExpiredTokenCleaner implements Cleaner {

  private final TokenService tokenService;

  @Value("${token-cleaner-cron}")
  private String cron;

  @Scheduled(cron = "#{@expiredTokenCleaner.getCron()}")
  public void clean() {
    tokenService.deleteExpired();
    log.info("Expired tokens have been deleted");
  }
}
