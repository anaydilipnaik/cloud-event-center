package com.cmpe275.finalProject.cloudEventCenter.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Component
@Table(name = "Reviews", catalog = "EVENT_CENTER")
public class Reviews {
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name = "REVIEW_ID")
	private String reviewId;
	
	// TODO: This field can remain as a String
	// There is NO need to join this column with USERS table
	@Column(name = "REVIEWER_ID")
	private String reviewer;
	
	@Column(name = "REVIEW_FOR")
	private String reviewFor;
	
	// Event ID is needed to ensure that
	// reviewer + reviewFor + eventId + reviewType 
	// gives uniqueness constraint
	@Column(name = "EVENT_ID")
	private String eventId;
	
	// TODO: Enum <PARTICIPANT, ORGANIZER>
	// Queries under this column are
	// a. As an organizer, I want to get all participant reviews of an organizer
	// b. As a participant, I want to get all the organizer reviews of a user
	
	
	/** TODO: Remove
	 *  Alex, Blake, Cathy, Dennis
	 *  
	 *  As an organizer, GET reviews of (Alex, Blake, Cathy) as participants
	 *  As a participant, GET reviews of (Alex, Dennis) as organizers
	 *  
	 *  Dennis creates an event
	 *  Alex creates an event
	 *  
	 *  Rated_by   	Person Rated	Rated for
	 *  Alex		Dennis			Organizer
	 *  Blake		Dennis			Organizer
	 *  Cathy		Dennis			Organizer
	 *  Dennis		Alex			Participant
	 *  Dennis		Blake			Participant
	 *  Dennis		Cathy			Participant
	 *  
	 *  Rated_by   	Person Rated	Rated for
	 *  Blake		Alex			Organizer
	 *  Cathy		Alex			Organizer
	 *  Dennis		Alex			Organizer
	 *  Alex		Dennis			Participant
	 *  Alex		Blake			Participant
	 *  Alex		Cathy			Participant
	 */
	@Enumerated(EnumType.STRING)
	@Column(name = "REVIEW_TYPE")
	private EEventRole reviewType;
	
	@Column(name = "text")
	private String review;
	
	@Column(name = "rating")
	@Min(value = 1)
	@Max(value = 5)
	private int rating;
	
	@CreationTimestamp
	@Column(name = "CREATED_AT")
	private LocalDateTime createdAt;
	
}