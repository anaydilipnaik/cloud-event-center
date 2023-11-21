package com.cmpe275.finalProject.cloudEventCenter.POJOs;

import lombok.Data;

@Data
public class SystemReport {

	int createdEvents;
	
	double paidEventsPercent;
	
	int cancelledEvents;
	
	double cancelEventPartciReq;
	
	int finishedEvents;
	
	double finishedEventsAvgPartici;
}
