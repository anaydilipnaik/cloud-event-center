package com.cmpe275.finalProject.cloudEventCenter.service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.cmpe275.finalProject.cloudEventCenter.repository.RefreshTokenRepository;
import com.cmpe275.finalProject.cloudEventCenter.repository.UserRepository;
import com.cmpe275.finalProject.cloudEventCenter.exception.TokenRefreshException;
import com.cmpe275.finalProject.cloudEventCenter.model.RefreshToken;

@Service
public class RefreshTokenService {
  @Value("${cmpe275.app.jwtRefreshExpirationMs}")
  private Long refreshTokenDurationMs;
  
  @Autowired
  private RefreshTokenRepository refreshTokenRepository;
  
  @Autowired
  private UserRepository userRepository;
  
  public Optional<RefreshToken> findByToken(String token) {
    return refreshTokenRepository.findByToken(token);
  }
  
  public RefreshToken createRefreshToken(String userId) {
    RefreshToken refreshToken = new RefreshToken();
    refreshToken.setUser(userRepository.findById(userId).get());
    refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
    refreshToken.setToken(UUID.randomUUID().toString());
    System.out.println("Before Save token");
    refreshToken = refreshTokenRepository.save(refreshToken);
    System.out.println("After Save token"+refreshToken.getId());
    return refreshToken;
  }
  public RefreshToken verifyExpiration(RefreshToken token) {
	 
    if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
      refreshTokenRepository.delete(token);
      throw new TokenRefreshException(token.getToken(), "Refresh token was expired. Please make a new signin request");
    }
    return token;
  }
  @Transactional
  public int deleteByUserId(String id) {
    return refreshTokenRepository.deleteByUser(userRepository.findById(id).get());
  }
}
