package com.cmpe275.finalProject.cloudEventCenter.model;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.stereotype.Component;

import com.cmpe275.finalProject.cloudEventCenter.enums.ForumTypes;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.*;
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
@Table(name = "ForumQuestions", catalog = "EVENT_CENTER")
public class ForumQuestions {
	
	/**
	 * question_id
	 * created_by
	 * text
	 * assets
	 * created_at
	 * updated_at
	 */
	@Id
	@GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name = "QUESTION_ID")
	private String id;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID")
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler","$$_hibernate_interceptor"
		 ,"eventToOrganize","participant_events","roles","address", "email", "password", "gender", "verificationCode", "enabled", 
		 "passcode", "events"
	})
	private User user;
	
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler","$$_hibernate_interceptor"
		 ,"eventToOrganize","participant_events","startTime","endTime",
		 "deadline", "minParticipants", "maxParticipants", "fee", 
		 "approvalRequired", "organizer", "address", "participants", "maxParticipants"
		 
	})
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name = "EVENT_ID", referencedColumnName = "EVENT_ID")
//	@Column(name = "EVENT_ID")
	private Event event;
	
	@Column(name = "IMAMGE_URL")
	private String imageUrl;
	
	@Column(name = "TEXT")
	private String text;
	
	@Column(name = "FORUM_TYPE")
	private ForumTypes forumType; 
	
	@CreationTimestamp
	@Column(name = "CREATED_AT")
	private LocalDateTime createdAt;
	
    @UpdateTimestamp
	@Column(name = "MODIFIED_AT")
	private LocalDateTime modifiedAt;
	
	
}
