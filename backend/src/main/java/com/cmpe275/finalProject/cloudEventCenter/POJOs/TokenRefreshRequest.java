package com.cmpe275.finalProject.cloudEventCenter.POJOs;

import javax.validation.constraints.NotBlank;

import lombok.Data;
@Data
public class TokenRefreshRequest {
	  @NotBlank
	  private String refreshToken;
	 
	}
