package com.cmpe275.finalProject.cloudEventCenter.service;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cmpe275.finalProject.cloudEventCenter.POJOs.MessageResponse;
import com.cmpe275.finalProject.cloudEventCenter.POJOs.OrganizerReport;
import com.cmpe275.finalProject.cloudEventCenter.POJOs.ParticipantReport;
import com.cmpe275.finalProject.cloudEventCenter.POJOs.SystemReport;
import com.cmpe275.finalProject.cloudEventCenter.controller.MimicClockTimeController;
import com.cmpe275.finalProject.cloudEventCenter.model.EEventStatus;
import com.cmpe275.finalProject.cloudEventCenter.model.Event;
import com.cmpe275.finalProject.cloudEventCenter.model.EventParticipant;
import com.cmpe275.finalProject.cloudEventCenter.model.ParticipantStatus;
import com.cmpe275.finalProject.cloudEventCenter.repository.EventParticipantRepository;
import com.cmpe275.finalProject.cloudEventCenter.repository.EventRepository;
import com.cmpe275.finalProject.cloudEventCenter.repository.UserRepository;

@Service
public class ReportService {

	@Autowired
	private EventParticipantRepository eventParticipantRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	EventRepository eventRepository;

	public ResponseEntity<?> generateParticipantReport(String userId) {

		if (!userRepository.existsById(userId)) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: invalid userId!"));

		}

		List<EventParticipant> registeredEvents = eventParticipantRepository.findById_ParticipantId(userId);
		Period days_90 = Period.ofDays(90);
		LocalDate currentMimicTime = MimicClockTimeController.getMimicDateTime().toLocalDate();
		LocalDate ago_90 = currentMimicTime.minus(days_90);
		System.out.println(ago_90);
		List<EventParticipant> findAllInRange = registeredEvents.stream()
				.filter(registration -> registration.getRegistrationDate().isAfter(ago_90)
						)
				.collect(Collectors.toList());
		List<EventParticipant> eventRejects = findAllInRange.stream()
				.filter(registration -> registration.getStatus().equals(ParticipantStatus.Rejected))
				.collect(Collectors.toList());

		List<EventParticipant> eventsApproved = findAllInRange.stream()
				.filter(registration -> registration.getStatus().equals(ParticipantStatus.Approved))
				.collect(Collectors.toList());

		List<EventParticipant> finishedEvents = findAllInRange.stream()
				.filter(registration -> registration.getEvent().getEndTime().toLocalDate().isBefore(currentMimicTime))
				.collect(Collectors.toList());

		ParticipantReport participantReport = new ParticipantReport();
		participantReport.setSignedUpeventsCount(findAllInRange.size());
		participantReport.setApprovalCount(eventsApproved.size());
		participantReport.setRejectsCount(eventRejects.size());
		participantReport.setFinishedEventsCount(finishedEvents.size());
		return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(participantReport);

	}

	public ResponseEntity<?> generateOrganizerReport(String userId) {

		if (!userRepository.existsById(userId)) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: invalid userId!"));

		}
		List<Event> paidEvents=new ArrayList<>();
		List<Event> cancelledEvents=new ArrayList<>();
		List<Event> finishedEvents=new ArrayList<>();
		double cancelledEventsCase2 = 0;
		int cancelledEventsMinPartiptsSum=0;
		List<Event> allevents = eventRepository.findByOrganizer_Id(userId);
		Period days_90 = Period.ofDays(90);
		double revenue=0.0,avgParticipantsFinishedEvents=0.0;
		List<Event> paidFinishedEvents=new ArrayList<>();
		LocalDate currentMimicTime = MimicClockTimeController.getMimicDateTime().toLocalDate();
		LocalDate ago_90 = currentMimicTime.minus(days_90);
		List<Event> findAllInRange = allevents.stream().filter(event -> event.getCreatedOn().isAfter(ago_90))
				.collect(Collectors.toList());
		if(findAllInRange.size()>0) {
		 paidEvents = findAllInRange.stream().filter(event -> event.getFee() > 0.0)
				.collect(Collectors.toList());
		 
		 
		 cancelledEvents = findAllInRange.stream()
					.filter(event -> event.getStatus().equals(EEventStatus.CANCELLED)).collect(Collectors.toList());

			int cancelledEventsPartiRqsts = cancelledEvents.stream().map(Event::getParticipants).mapToInt(Collection::size)
					.sum();
			 cancelledEventsMinPartiptsSum = cancelledEvents.stream().mapToInt(Event::getMinParticipants).sum();

			
			if (cancelledEventsMinPartiptsSum != 0) {
				cancelledEventsCase2 = (double)cancelledEventsPartiRqsts / cancelledEventsMinPartiptsSum;
			}

			 finishedEvents = findAllInRange.stream()
					.filter(event -> event.getEndTime().toLocalDate().isBefore(currentMimicTime))
					.collect(Collectors.toList());
			 if(finishedEvents.size()>0) {
			 avgParticipantsFinishedEvents = finishedEvents.stream().map(Event::getParticipants)
					.mapToInt(Collection::size).average().getAsDouble();

			 paidFinishedEvents = finishedEvents.stream().filter(event -> event.getFee() > 0)
					.collect(Collectors.toList());

			 revenue =  paidFinishedEvents.stream().map(Event::getParticipants)
					 .flatMap(Collection::stream).mapToDouble(EventParticipant::getFee).sum();
					 
					 paidFinishedEvents.stream().mapToDouble(Event::getFee).sum();
			 }
		 
		}
		
		OrganizerReport organizerReport = new OrganizerReport();
		organizerReport.setCreatedEvents(findAllInRange.size());
		double paidEventsPercent = 0;
		if (findAllInRange.size() > 0) {
			paidEventsPercent = Math.round(((double)paidEvents.size() / findAllInRange.size()) * 100);
		}
		organizerReport.setPaidEvents(cancelledEventsMinPartiptsSum);
		organizerReport.setPercentagePaidEvents(paidEventsPercent);
		organizerReport.setCancelledEvents(cancelledEvents.size());
		organizerReport.setParticipationRequestsbyMin(cancelledEventsCase2);
		organizerReport.setFinishedEvents(finishedEvents.size());
		organizerReport.setAvgParticipantsFshedEvts(Math.round(avgParticipantsFinishedEvents*100)/100);
		organizerReport.setFinishedPaidEvents(paidFinishedEvents.size());
		organizerReport.setFinishedPaidEventsRevenue(revenue);
		return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(organizerReport);

	}

	public ResponseEntity<?> generateSystemReport() {

		List<Event> allEvents = eventRepository.findAll();
		Period days_90 = Period.ofDays(90);
		LocalDate currentMimicTime = MimicClockTimeController.getMimicDateTime().toLocalDate();
		LocalDate ago_90 = currentMimicTime.minus(days_90);
		System.out.println(ago_90);
		List<Event> paidEvents = new ArrayList<Event>();
		List<Event> cancelledEvents = new ArrayList<Event>();
		double cancelledEventsCase2 = 0;
		List<Event> finishedEvents = new ArrayList<Event>();
		double avgParticipantsFinishedEvents = 0;
		double paidEventsPercent=0.0;

		List<Event> findAllInRange = allEvents.stream().filter(event -> event.getCreatedOn().isAfter(ago_90))
				.collect(Collectors.toList());

		if (findAllInRange.size() > 0) {
			paidEvents = findAllInRange.stream().filter(event -> event.getFee() > 0.0).collect(Collectors.toList());
			System.out.println("paidEvents: "+paidEvents.size());
			
			cancelledEvents = findAllInRange.stream().filter(event -> event.getStatus().equals(EEventStatus.CANCELLED))
					.collect(Collectors.toList());
			if (cancelledEvents.size() > 0) {
				int cancelledEventsPartiRqsts = cancelledEvents.stream().map(Event::getParticipants)
						.mapToInt(Collection::size).sum();
				System.out.println("cancelledEventsPartiRqsts: "+cancelledEventsPartiRqsts);
				int cancelledEventsMinPartiptsSum = cancelledEvents.stream().mapToInt(Event::getMinParticipants).sum();
				
				System.out.println("cancelledEventsMinPartiptsSum: "+cancelledEventsMinPartiptsSum);

				if (cancelledEventsMinPartiptsSum > 0) {
					cancelledEventsCase2 = (double) cancelledEventsPartiRqsts / cancelledEventsMinPartiptsSum;
				System.out.println("cancelledEventsCase2 :"+cancelledEventsCase2);
				}
			}
			finishedEvents = findAllInRange.stream()
					.filter(registration -> registration.getEndTime().toLocalDate().isBefore(currentMimicTime))
					.collect(Collectors.toList());
			if (finishedEvents.size() > 0) {
				List<Event> finishedPaidEvents = finishedEvents.stream().filter(event -> event.getFee() > 0.0).collect(Collectors.toList());

				System.out.println("finishedPaidEvents: "+finishedPaidEvents.size());
				avgParticipantsFinishedEvents = finishedPaidEvents.stream().map(Event::getParticipants)
						.mapToInt(Collection::size).average().getAsDouble();
			}
			paidEventsPercent =((paidEvents.size()* 100 )/ findAllInRange.size() );
		}

		SystemReport systemReport = new SystemReport();
		systemReport.setCreatedEvents(findAllInRange.size());
		 
		systemReport.setPaidEventsPercent(paidEventsPercent);
		systemReport.setCancelledEvents(cancelledEvents.size());

		systemReport.setCancelEventPartciReq(cancelledEventsCase2);
		systemReport.setFinishedEvents(finishedEvents.size());
		systemReport.setFinishedEventsAvgPartici(avgParticipantsFinishedEvents);
		return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(systemReport);

	}
}
