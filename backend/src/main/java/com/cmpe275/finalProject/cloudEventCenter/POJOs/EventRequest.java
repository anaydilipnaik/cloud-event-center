package com.cmpe275.finalProject.cloudEventCenter.POJOs;

import lombok.Data;

@Data
public class EventRequest {
	String title;
	String description;
	String startTime;
	String endTime;
	String deadline;
	String street;
	String number;
	String city;
	String state;
	String zip;
	int minParticipants;
	int maxParticipants; 
	double fee;
	boolean approvalReq;
	String organizerID;
}
