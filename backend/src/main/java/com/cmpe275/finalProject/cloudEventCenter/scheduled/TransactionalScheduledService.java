package com.cmpe275.finalProject.cloudEventCenter.scheduled;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmpe275.finalProject.cloudEventCenter.model.EEventStatus;
import com.cmpe275.finalProject.cloudEventCenter.model.Event;
import com.cmpe275.finalProject.cloudEventCenter.repository.EventRepository;

@Service
public class TransactionalScheduledService {
	
	@Autowired
	EventRepository eventRepository;


	
	@Transactional
	public void updateCancelledEventStatus(Event event) {
		event.setStatus(EEventStatus.CANCELLED);
		event.setActive(false);
		eventRepository.saveAndFlush(event);
		
	}
	
	@Transactional
	public void maxRegistrations(Event event) {
		event.setStatus(EEventStatus.REG_CLOSED);
		eventRepository.saveAndFlush(event);
	}
	
	@Transactional
	public void eventStart(Event event) {
		event.setStatus(EEventStatus.EVENT_PROGRESS);
		eventRepository.saveAndFlush(event);
	}
	
	@Transactional
	public void eventDone(Event event) {
		event.setStatus(EEventStatus.CLOSED);
		event.setActive(false);
		eventRepository.saveAndFlush(event);
	}
	
	@Transactional
	public void OpenParticipantForum(Event event) {
		event.setPForumOpen(true);
		eventRepository.saveAndFlush(event);
		
	}
	
	@Transactional
	public void closeParticipantForum(Event event) {
		event.setPForumOpen(false);
		eventRepository.saveAndFlush(event);
		
	}

}
