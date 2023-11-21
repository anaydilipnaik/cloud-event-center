package com.cmpe275.finalProject.cloudEventCenter.POJOs;

import lombok.Data;

@Data
public class ParticipantReport {
	
	int signedUpeventsCount;
	
	int rejectsCount;
	int approvalCount;
	
	int finishedEventsCount;

}
