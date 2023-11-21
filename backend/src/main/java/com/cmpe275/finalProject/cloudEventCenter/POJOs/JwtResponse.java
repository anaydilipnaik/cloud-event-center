package com.cmpe275.finalProject.cloudEventCenter.POJOs;

import java.util.List;

import lombok.Data;

@Data
public class JwtResponse {
	
	  private String token;
	  private String type = "Bearer";
	  private String refreshToken;
	  private String id;
	  private String email;
	  private String city;
	  private List<String> roles;
	
	public JwtResponse(String accessToken,String refreshToken, String id, String username, String email,String city, List<String> roles) {
	    this.token = accessToken;
	    this.refreshToken = refreshToken;
	    this.id = id;
	    this.email = email;
	    this.city=city;
	    this.roles = roles;
	  }
	
	}
