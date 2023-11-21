/**
 * 
 */
package com.cmpe275.finalProject.cloudEventCenter.controller;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.Optional;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

//import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cmpe275.finalProject.cloudEventCenter.POJOs.EventData;
import com.cmpe275.finalProject.cloudEventCenter.POJOs.EventRequest;
import com.cmpe275.finalProject.cloudEventCenter.POJOs.SignupRequest;
import com.cmpe275.finalProject.cloudEventCenter.model.Address;
import com.cmpe275.finalProject.cloudEventCenter.model.User;
import com.cmpe275.finalProject.cloudEventCenter.repository.UserRepository;
import com.cmpe275.finalProject.cloudEventCenter.service.EventService;

/**
 * @author shrey
 *
 */

@RestController
@RequestMapping("/api")
public class EventController {
	
	@Autowired
	private EventService eventService;
	
	@Autowired
	private UserRepository userRepository;
	
//	@PreAuthorize("hasRole('ORGANIZER') or hasRole('PERSON')")
	@PostMapping("/event")
	public ResponseEntity<?> createEvent(@Valid @RequestBody EventRequest eventRequest){
		LocalDateTime converted_startTime = LocalDateTime.parse(eventRequest.getStartTime());
		LocalDateTime converted_endTime = LocalDateTime.parse(eventRequest.getEndTime());
		LocalDateTime converted_deadline = LocalDateTime.parse(eventRequest.getDeadline());
		Address address = new Address(eventRequest.getStreet(), eventRequest.getNumber(), 
				eventRequest.getCity(), eventRequest.getState(), eventRequest.getZip());
		EventData newEvent = new EventData(eventRequest.getTitle(), 
				eventRequest.getDescription(), 
				converted_startTime, 
				converted_endTime, 
				converted_deadline,  
				address,
				eventRequest.getMinParticipants(), 
				eventRequest.getMaxParticipants(), 
				eventRequest.getFee(), 
				eventRequest.isApprovalReq(), 
				eventRequest.getOrganizerID());
		
		System.out.println("reached1");
	    return eventService.addEvent(newEvent);
	}
	
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/event/{id}", method = RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<?> getEvent(@PathVariable("id") String id){
		return eventService.getEventByID(id);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/event/organizer/{organizerID}", method = RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<?> getAllEventsByOrganizerID(@PathVariable("organizerID") String organizerID){
		return eventService.getAllEventsByOrganizerID(organizerID);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/event/{eventID}", method = RequestMethod.PUT, produces=MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<?> CancelEvent(@PathVariable("eventID") String id){
		return eventService.cancelEvent(id);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@GetMapping(value = "/event/{page}/search")
	ResponseEntity<?> searchEvent(
			@PathVariable("page") int page,
			@RequestParam(required = false) String city,
			@RequestParam(required = false) String status,
			@RequestParam(required = false) String startTime,
			@RequestParam(required = false) String endTime,
			@RequestParam(required = false) String keyword,
			@RequestParam(required = false) String organizer
			
			){
		
		return eventService.searchEvent(city, status, startTime, endTime, keyword, organizer,page);
		
	}
	
}
