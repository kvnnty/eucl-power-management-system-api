package com.kvn.eucl.v1.utils.helpers;

import java.security.SecureRandom;

import org.springframework.stereotype.Component;

import com.kvn.eucl.v1.repositories.tokens.TokenRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TokenUtil {

  private final TokenRepository tokenRepository;
  private static final SecureRandom random = new SecureRandom();

  public String generateUniqueToken() {
    String token;
    do {
      token = generateRawToken();
    } while (tokenRepository.existsByToken(token));
    return token;
  }

  private String generateRawToken() {
    StringBuilder sb = new StringBuilder(16);
    for (int i = 0; i < 16; i++) {
      sb.append(random.nextInt(10));
    }
    return sb.toString();
  }

  /**
   * Converts 16-digit raw token into readable format: XXXX-XXXX-XXXX-XXXX
   */
  public String formatToken(String rawToken) {
    if (rawToken == null || rawToken.length() != 16 || !rawToken.matches("\\d{16}")) {
      throw new IllegalArgumentException("Invalid token format: must be 16 digits");
    }
    return rawToken.replaceAll("(.{4})(?!$)", "$1-");
  }
}
