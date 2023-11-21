package com.cmpe275.finalProject.cloudEventCenter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cmpe275.finalProject.cloudEventCenter.service.ReportService;

@RestController
@RequestMapping("/api/report")
public class ReportsController {
	
	@Autowired
	ReportService userReportService;
	
	@GetMapping("/user/{id}")
	public ResponseEntity<?> generateParticipantReport(@PathVariable String id){
		return userReportService.generateParticipantReport(id);
	}
	
	@GetMapping("/organizer/{id}")
	public ResponseEntity<?> generateOrganizerReport(@PathVariable String id){
		return userReportService.generateOrganizerReport(id);
	}
	
	@GetMapping("/system")
	public ResponseEntity<?> generateSystemReport(){
		return userReportService.generateSystemReport();
	}
	
}
