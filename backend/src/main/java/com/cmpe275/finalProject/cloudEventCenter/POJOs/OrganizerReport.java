package com.cmpe275.finalProject.cloudEventCenter.POJOs;

import lombok.Data;

@Data
public class OrganizerReport {
	int createdEvents;
	
	int paidEvents;
	
	double percentagePaidEvents;
	
	int cancelledEvents;
	
	double participationRequestsbyMin;
	
	int finishedEvents;
	
	double avgParticipantsFshedEvts;
	
	int finishedPaidEvents;
	
	double finishedPaidEventsRevenue;
	
}
