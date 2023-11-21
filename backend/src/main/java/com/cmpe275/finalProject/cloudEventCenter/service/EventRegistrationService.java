package com.cmpe275.finalProject.cloudEventCenter.service;

import java.util.HashMap;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.cmpe275.finalProject.cloudEventCenter.mail.service.NotificationMailService;
import com.cmpe275.finalProject.cloudEventCenter.model.Event;
import com.cmpe275.finalProject.cloudEventCenter.model.EventParticipant;
import com.cmpe275.finalProject.cloudEventCenter.model.ParticipantStatus;
import com.cmpe275.finalProject.cloudEventCenter.model.User;
import com.cmpe275.finalProject.cloudEventCenter.repository.EventParticipantRepository;
import com.cmpe275.finalProject.cloudEventCenter.repository.EventRepository;
import com.cmpe275.finalProject.cloudEventCenter.repository.UserRepository;

@Service
@Component
public class EventRegistrationService {
	
	@Autowired
	private EventParticipantRepository eventParticipantRepository;
	
	@Autowired
	private EventRepository eventRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	NotificationMailService notificationMailService;
	
	@Transactional
	public ResponseEntity<?> approveParticipant(String eventID, String userID) {
		try {
			if(eventID.isBlank() || userID.isBlank()) {
				return ResponseEntity
			            .status(HttpStatus.BAD_REQUEST)
			            .body("Enter an event ID and participant ID");
			}
			
			if(userRepository.findById(userID).orElse(null) == null) {
				return ResponseEntity
			            .status(HttpStatus.NOT_FOUND)
			            .body("No such participant exists");
			}
			
			if(eventRepository.findById(eventID).orElse(null) == null) {
				return ResponseEntity
			            .status(HttpStatus.NOT_FOUND)
			            .body("No such event exists");
			}
			
			EventParticipant attendee = eventParticipantRepository.findById_EventIdAndId_ParticipantId(eventID, userID);
			
			if(attendee == null) {
				return ResponseEntity
			            .status(HttpStatus.NOT_FOUND)
			            .body("No such participant has registered for this event");
			}
			
			attendee.setStatus(ParticipantStatus.Approved);
			eventParticipantRepository.save(attendee);
			
			User user = userRepository.findById(userID).orElse(null);
			Event event = eventRepository.findById(eventID).orElse(null);
			
			HashMap<String, String> params = new HashMap<>();
			params.put("[EVENT_NAME]", event.getTitle());
			
			notificationMailService.sendNotificationEmail(user.getEmail(), "signupApproval", params);
			
			return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body("Partipant: " + attendee.getParticipant().getId() + "\n is approved for event: " + attendee.getEvent().getId());

		} catch (Exception e) {
			e.printStackTrace(System.out);
			System.out.println("IN approveParticipant EXCEPTION BLOCK");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.toString());
		}
	}
	
	@Transactional
	public ResponseEntity<?> rejectParticipant(String eventID, String userID) {
		try {
			if(eventID.isBlank() || userID.isBlank()) {
				return ResponseEntity
			            .status(HttpStatus.BAD_REQUEST)
			            .body("Enter an event ID and participant ID");
			}
			
			if(userRepository.findById(userID).orElse(null) == null) {
				return ResponseEntity
			            .status(HttpStatus.NOT_FOUND)
			            .body("No such participant exists");
			}
			
			if(eventRepository.findById(eventID).orElse(null) == null) {
				return ResponseEntity
			            .status(HttpStatus.NOT_FOUND)
			            .body("No such event exists");
			}
			
			EventParticipant attendee = eventParticipantRepository.findById_EventIdAndId_ParticipantId(eventID, userID);
			
			if(attendee == null) {
				return ResponseEntity
			            .status(HttpStatus.NOT_FOUND)
			            .body("No such participant has registered for this event");
			}
			
			attendee.setStatus(ParticipantStatus.Rejected);
			eventParticipantRepository.save(attendee);
			
			User user = userRepository.findById(userID).orElse(null);
			Event event = eventRepository.findById(eventID).orElse(null);
			
			HashMap<String, String> params = new HashMap<>();
			params.put("[EVENT_NAME]", event.getTitle());
			
			notificationMailService.sendNotificationEmail(user.getEmail(), "signupReject", params);
			
			return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body("Partipant: " + attendee.getParticipant().getId() + "\n is rejected for event: " + attendee.getEvent().getId());

		} catch (Exception e) {
			e.printStackTrace(System.out);
			System.out.println("IN RejectParticipant EXCEPTION BLOCK");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.toString());
		}
	}
	
	@Transactional
	public ResponseEntity<?> getAllPendingRegistrations(String eventID) {
		try {
			if(eventID.isBlank()) {
				return ResponseEntity
			            .status(HttpStatus.BAD_REQUEST)
			            .body("Enter an event ID");
			}
			
			List<EventParticipant> eventParticipants = eventParticipantRepository.findByPending(eventID);
			
			return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(eventParticipants);
		}
		catch (Exception e) {
			e.printStackTrace(System.out);
			System.out.println("IN getAllPendingRegistrations EXCEPTION BLOCK");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.toString());
		}		
	}
	
}
