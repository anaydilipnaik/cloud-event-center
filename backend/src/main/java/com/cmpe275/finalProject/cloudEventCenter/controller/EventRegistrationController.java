package com.cmpe275.finalProject.cloudEventCenter.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cmpe275.finalProject.cloudEventCenter.POJOs.EventData;
import com.cmpe275.finalProject.cloudEventCenter.service.EventRegistrationService;
import com.cmpe275.finalProject.cloudEventCenter.service.EventService;

@RestController
@RequestMapping("/api/registration")
public class EventRegistrationController {
	
	@Autowired
	private EventService eventService;
	
	@Autowired
	private EventRegistrationService eventRegistrationService;
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/{eventID}/{userID}", method = RequestMethod.PUT, produces=MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<?> addParticipant(@PathVariable("eventID") String eventID, @PathVariable("userID") String userID){
		return eventService.addParticipant(eventID, userID);
	}
	
	@PutMapping("/approve/{eventID}/{userID}")
	ResponseEntity<?> approveParticipant(@PathVariable("eventID") String eventID, @PathVariable("userID") String userID){
		return eventRegistrationService.approveParticipant(eventID, userID);
	}
	
	@PutMapping("/reject/{eventID}/{userID}")
	ResponseEntity<?> rejectParticipant(@PathVariable("eventID") String eventID, @PathVariable("userID") String userID){
		return eventRegistrationService.rejectParticipant(eventID, userID);
	}
	
	@GetMapping("/{eventID}")
	ResponseEntity<?> getAllPendingRegistrations(@PathVariable("eventID") String eventID){
		return eventRegistrationService.getAllPendingRegistrations(eventID);
	}
}
