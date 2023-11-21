package com.cmpe275.finalProject.cloudEventCenter.POJOs;

import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class SignupRequest {

	private String id;

	@NotBlank
	@Size(max = 50)
	@Email
	private String email;

	@NotBlank
	@Size(min = 4, max = 40)
	private String password;

	private String fullName;

	private String screenName;

	private String gender;

	private String description;

	private String verificationCode;

	private Set<String> role;
	private String number;
	private String street;
	private String city;
	private String state;
	private String zip;

}
