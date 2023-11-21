package com.cmpe275.finalProject.cloudEventCenter.POJOs;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class LoginRequest {
	@NotBlank
  private String email;

	@NotBlank
	private String password;


}
