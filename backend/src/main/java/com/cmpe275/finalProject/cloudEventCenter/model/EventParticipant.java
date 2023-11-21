package com.cmpe275.finalProject.cloudEventCenter.model;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

@Data
@Builder
@Entity
@Table(name = "participant_events", catalog = "event_center")
@JsonAutoDetect
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler", "$$_hibernate_interceptor" })
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class EventParticipant {
	

	@EmbeddedId
	private EventParticipentId id;

	@ManyToOne(fetch = FetchType.LAZY)
    @MapsId("id")
	@JsonIgnoreProperties({"organizer","participants"})
    private Event event;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @MapsId("id")
	@JsonIgnoreProperties({"eventToOrganize","roles","events"})
    private User participant;
	
	@Column(name="REGISTRATION_DATE")
	private LocalDate registrationDate;
	
	@Column(name="FEE")
	double fee;
	
	@Enumerated(EnumType.STRING)
	@Column(name="STATUS")
	private ParticipantStatus status;
	
	
}
