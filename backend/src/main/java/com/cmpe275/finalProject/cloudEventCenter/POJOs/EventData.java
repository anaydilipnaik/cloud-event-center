package com.cmpe275.finalProject.cloudEventCenter.POJOs;

import java.time.LocalDateTime;

import com.cmpe275.finalProject.cloudEventCenter.model.Address;
import com.cmpe275.finalProject.cloudEventCenter.model.User;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EventData {
	String title;
	String description;
	LocalDateTime startTime; 
	LocalDateTime endTime;
	LocalDateTime deadline;  
	Address address;
	int minParticipants;
	int maxParticipants; 
	double fee;
	boolean approvalReq;
	String organizerID;
}
