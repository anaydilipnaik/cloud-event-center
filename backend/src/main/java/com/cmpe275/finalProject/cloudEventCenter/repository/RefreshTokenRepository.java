package com.cmpe275.finalProject.cloudEventCenter.repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import com.cmpe275.finalProject.cloudEventCenter.model.RefreshToken;
import com.cmpe275.finalProject.cloudEventCenter.model.User;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, String> {

    Optional<RefreshToken> findByToken(String token);
    
    @Modifying
    int deleteByUser(User user);
}
