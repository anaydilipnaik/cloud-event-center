package com.cmpe275.finalProject.cloudEventCenter.model;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.cmpe275.finalProject.cloudEventCenter.model.ForumQuestions;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Component
@Table(name = "ForumQuestionAnswers", catalog = "EVENT_CENTER")
public class ForumQuestionsAnswers {
	
	@Id
	@GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name = "FORUM_ID")
	private String id;

	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID")
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler","$$_hibernate_interceptor"
		 ,"eventToOrganize","participant_events","roles","address", "email", "password", "gender", "verificationCode", "enabled", 
		 "passcode", "events"
	})
	private User user;
	
	@Column(name = "EVENT_ID")
	private String event_id;
		
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name = "QUESTION_ID", referencedColumnName = "QUESTION_ID")
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler","$$_hibernate_interceptor",
		 "user", "event", 
	})
	private ForumQuestions question;
	
	@Column(name = "label")
	private String text;
	
	// Assets
	// private List<MediaAsset<String>> assets
	
	@CreationTimestamp
	@Column(name = "CREATED_AT")
	private LocalDateTime createdAt;
	
    @UpdateTimestamp
	@Column(name = "MODIFIED_AT")
	private LocalDateTime modifiedAt;
	
	
}
