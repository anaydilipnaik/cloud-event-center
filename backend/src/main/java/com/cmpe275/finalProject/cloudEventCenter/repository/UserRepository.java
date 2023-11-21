package com.cmpe275.finalProject.cloudEventCenter.repository;

import java.util.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cmpe275.finalProject.cloudEventCenter.model.Event;
import com.cmpe275.finalProject.cloudEventCenter.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, String>{
	
	Optional<User> findByScreenName(String screenName);
	Optional<User> findByEmail(String email);
	Boolean existsByScreenName(String screenName);
	Boolean existsByEmail(String email);
	
    @Query("SELECT u FROM User u WHERE u.verificationCode = ?1")
    public User findByVerificationCode(String code);
    
 
    
   

}
