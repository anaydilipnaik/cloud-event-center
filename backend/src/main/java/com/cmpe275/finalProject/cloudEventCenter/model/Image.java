package com.cmpe275.finalProject.cloudEventCenter.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

public class Image {
	
	@Id
	@GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name = "IMAGE_ID")
	private long id;
	
	@Column(name = "IMAGE_URL")
	private String url;

}
