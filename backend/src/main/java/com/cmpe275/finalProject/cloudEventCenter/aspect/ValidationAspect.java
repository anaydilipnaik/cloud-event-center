package com.cmpe275.finalProject.cloudEventCenter.aspect;

import java.util.Optional;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import com.cmpe275.finalProject.cloudEventCenter.POJOs.EventData;
import com.cmpe275.finalProject.cloudEventCenter.model.ERole;
import com.cmpe275.finalProject.cloudEventCenter.model.Role;
import com.cmpe275.finalProject.cloudEventCenter.model.User;
import com.cmpe275.finalProject.cloudEventCenter.repository.UserRepository;


@Aspect
@Component
@EnableAspectJAutoProxy
@Order(0)
public class ValidationAspect {
	
	@Autowired
	private UserRepository userRepository;
	
	@Before("execution(public * com.cmpe275.finalProject.cloudEventCenter.service.EventService.addEvent(..)) && args(eventData)")
	public void CreateEventValidationAdvice(JoinPoint joinPoint, EventData eventData) {
		
//		System.out.printf("Permission check before the executuion of the method %s\n", joinPoint.getSignature().getName());

		//Validation should be updated
//		if(eventData.getTitle().isBlank() || eventData.getDescription().isBlank())
//			throw new IllegalArgumentException("Enter a valid title and desciption");
//		
//		if(eventData.getStartTime() == null || eventData.getEndTime() == null)
//			throw new IllegalArgumentException("Enter a valid startTime/endTime");
//		
//		if(eventData.getEndTime().isBefore(eventData.getStartTime()) || eventData.getEndTime().isEqual(eventData.getStartTime())) {
//			throw new IllegalArgumentException("Enter a startTime before endTime");
//		}
//			
//		if(eventData.getStartTime().isBefore(eventData.getDeadline())) {
//			throw new IllegalArgumentException("Enter a startTime after deadline");
//		}
//		
//		if(eventData.getAddress().getCity().isBlank() || eventData.getAddress().getState().isBlank() || eventData.getAddress().getZip().isBlank()) {
//			throw new IllegalArgumentException("Enter a city, state and zipcode for the event");
//		}
//		
//		if(eventData.getMinParticipants() < 0) {
//			throw new IllegalArgumentException("Enter a positive minimum number of participants");
//		}
//		
//		if(eventData.getMaxParticipants() > Integer.MAX_VALUE) {
//			throw new IllegalArgumentException("Enter a valid number of max participants");
//		}
//		
//		User organizer  = userRepository.findById(eventData.getOrganizerID()).orElse(null);
//		Role organizerRole = organizer.getRoles().iterator().next();
//		
//		if(eventData.getFee() > 0 && organizerRole.getName().equals(ERole.ROLE_PERSON)) {
//			throw new IllegalArgumentException("Participant cant charge a fee");
//		}
		
	}
	
	@Before("execution(public * com.cmpe275.finalProject.cloudEventCenter.service.EventService.getEventByID(..)) && args(id)")
	public void getEventValidationAdvice(JoinPoint joinPoint, String id) {
//		if(id.isBlank())
//			throw new IllegalArgumentException("Enter an event ID"); 
	}
	
	@Before("execution(public * com.cmpe275.finalProject.cloudEventCenter.service.EventService.cancelEvent(..)) && args(id)")
	public void deleteEventValidationAdvice(JoinPoint joinPoint, String id) {
//		if(id.isBlank())
//			throw new IllegalArgumentException("Enter an event ID"); 
	}
	
	@Before("execution(public * com.cmpe275.finalProject.cloudEventCenter.service.EventService.getAllEventsByOrganizerID(..)) && args(organizerID)")
	public void getAllEventsValidationAdvice(JoinPoint joinPoint, String organizerID) {
//		if(organizerID.isBlank())
//			throw new IllegalArgumentException("Enter an organizerID"); 
	}
	
	@Before("execution(public * com.cmpe275.finalProject.cloudEventCenter.service.UserService.getAllEventsByUserID(..)) && args(userID)")
	public void getAllEventsByUserIDValidationAdvice(JoinPoint joinPoint, String userID) {
//		if(userID.isBlank())
//			throw new IllegalArgumentException("Enter an userID"); 
	}
}
