package com.cmpe275.finalProject.cloudEventCenter.POJOs;

import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class UpdateUserRequest {

	

	@NotBlank
	private String fullName;
	
	@NotBlank
	private String screenName;

	private String gender;

	private String description;


	private String number;
	private String street;
	private String city;
	private String state;
	private String zip;

}
