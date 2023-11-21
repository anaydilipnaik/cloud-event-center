package com.cmpe275.finalProject.cloudEventCenter.scheduled;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.cmpe275.finalProject.cloudEventCenter.model.EEventStatus;
import com.cmpe275.finalProject.cloudEventCenter.model.Event;
import com.cmpe275.finalProject.cloudEventCenter.model.EventParticipant;
import com.cmpe275.finalProject.cloudEventCenter.model.User;
import com.cmpe275.finalProject.cloudEventCenter.repository.EventParticipantRepository;
import com.cmpe275.finalProject.cloudEventCenter.repository.EventRepository;
import com.cmpe275.finalProject.cloudEventCenter.repository.UserRepository;
import com.cmpe275.finalProject.cloudEventCenter.controller.MimicClockTimeController;
import com.cmpe275.finalProject.cloudEventCenter.mail.service.NotificationMailService;

@Component
public class ScheduledTasks {

	@Autowired
	EventRepository eventRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	EventParticipantRepository eventParticipantRepository;

	@Autowired
	NotificationMailService notificationMailService;
	
	@Autowired
	TransactionalScheduledService transactionalScheduledService;
	

	@Scheduled(fixedDelay = 2000, initialDelay = 5000)
	public void cancelEvent() {
		
		List<Event> allEvents = eventRepository.findAll();
		LocalDateTime mimicDateTime = MimicClockTimeController.getMimicDateTime();
		
		
		List<Event> eventsToBeCancelled = allEvents.stream()
				.filter(event -> event.getDeadline().isBefore(mimicDateTime)
						&& event.getParticipants().size() < event.getMinParticipants()
						&& !event.getStatus().equals(EEventStatus.CANCELLED))
				.collect(Collectors.toList());
		for (Event event : eventsToBeCancelled) {

			transactionalScheduledService.updateCancelledEventStatus(event);
			HashMap<String, String> params = new HashMap<>();
			params.put("[EVENT_NAME]", event.getTitle());
			for (EventParticipant participant : event.getParticipants()) {
				User user = userRepository.getById(participant.getId().getParticipantId());

				notificationMailService.sendNotificationEmail(user.getEmail(), "eventCancel", params);
			}

		}

	}
	
	@Scheduled(fixedDelay = 2000, initialDelay = 1500)
	public void maxRegistrationsOrdeadline() {
		
		
		List<Event> allEvents = eventRepository.findAll();
		LocalDateTime mimicDateTime = MimicClockTimeController.getMimicDateTime();

		List<Event> eventsRegsToBeClosed = allEvents.stream()
				.filter(event -> (event.getParticipants().size() >= event.getMaxParticipants()|| event.getDeadline().isBefore(mimicDateTime))
						&& !event.getStatus().equals(EEventStatus.REG_CLOSED)
						&& event.getStatus().equals(EEventStatus.REG_OPEN))
				.collect(Collectors.toList());
		for (Event event : eventsRegsToBeClosed) {
			transactionalScheduledService.maxRegistrations(event);

		}

	}
	
	
	
	
	@Scheduled(fixedDelay = 2000, initialDelay = 1000)
	public void eventStart() {
		
		
		List<Event> allEvents = eventRepository.findAll();
		
		LocalDateTime mimicDateTime = MimicClockTimeController.getMimicDateTime();
		List<Event> eventsinProgress = allEvents.stream()
				.filter(event -> event.getStartTime().isBefore(mimicDateTime)
						&& event.getParticipants().size() >= event.getMinParticipants()
						&& !event.getStatus().equals(EEventStatus.EVENT_PROGRESS)
						&& !event.getStatus().equals(EEventStatus.CLOSED)
						&& !event.getStatus().equals(EEventStatus.CANCELLED))
				.collect(Collectors.toList());
		for (Event event : eventsinProgress) {
			transactionalScheduledService.eventStart(event);
			HashMap<String, String> params = new HashMap<>();
			params.put("[EVENT_NAME]", event.getTitle());
			for (EventParticipant participant : event.getParticipants()) {
				User user = userRepository.getById(participant.getId().getParticipantId());
				notificationMailService.sendNotificationEmail(user.getEmail(), "eventStart", params);
			}

			
		}

	}
	
	@Scheduled(fixedDelay = 2000, initialDelay = 2000)
	public void eventDone() {
		
		List<Event> allEvents = eventRepository.findAll();
		
		LocalDateTime mimicDateTime = MimicClockTimeController.getMimicDateTime();
		List<Event> eventsClosed = allEvents.stream()
				.filter(event -> event.getEndTime().isBefore(mimicDateTime)
						&& event.getParticipants().size() >= event.getMinParticipants()
						&& !event.getStatus().equals(EEventStatus.CLOSED)
						&& !event.getStatus().equals(EEventStatus.CANCELLED)
						&& event.getStatus().equals(EEventStatus.EVENT_PROGRESS))
				.collect(Collectors.toList());
		for (Event event : eventsClosed) {
			transactionalScheduledService.eventDone(event);

		}

	}
	
	
	@Scheduled(fixedDelay = 2000, initialDelay = 3000)
	public void OpenParticipantForum() {
		
		List<Event> allEvents = eventRepository.findAll();
		
		LocalDateTime mimicDateTime = MimicClockTimeController.getMimicDateTime();
		List<Event> eventsPastDeadline = allEvents.stream()
				.filter(event ->  event.getDeadline().isBefore(mimicDateTime)
						&& !event.getStatus().equals(EEventStatus.CLOSED)
						&& !event.getStatus().equals(EEventStatus.CANCELLED)
						&& event.getParticipants().size() >= event.getMinParticipants()
						&& !event.isPForumOpen())
				.collect(Collectors.toList());
		for (Event event : eventsPastDeadline) {
			transactionalScheduledService.OpenParticipantForum(event);

		}

	}
	

	@Scheduled(fixedDelay = 2000, initialDelay = 4000)
	public void closeParticipantForum() {
		
		List<Event> allEvents = eventRepository.findAll();
		
		LocalDateTime mimicDateTime = MimicClockTimeController.getMimicDateTime();
		List<Event> forumsToBeClosed = allEvents.stream()
				.filter(event ->  event.getEndTime().plusDays(3).isBefore(mimicDateTime)
						&& event.getStatus().equals(EEventStatus.CLOSED)
						&& event.getParticipants().size() >= event.getMinParticipants()
						&& event.isPForumOpen())
				.collect(Collectors.toList());
		//System.out.println("forumsToBeClosed: "+forumsToBeClosed.size());
		for (Event event : forumsToBeClosed) {
			transactionalScheduledService.closeParticipantForum(event);

		}

	}

}
