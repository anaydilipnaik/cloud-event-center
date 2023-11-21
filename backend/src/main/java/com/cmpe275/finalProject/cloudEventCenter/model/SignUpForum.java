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

import com.cmpe275.finalProject.cloudEventCenter.enums.ForumTypes;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Component
@Table(name = "SignUpForum", catalog = "EVENT_CENTER")
public class SignUpForum {
	
	@Id
	@GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name = "FORUM_ID")
	private String id;
		
	// TBD: Foreign Key
	@Column(name = "EVENT_ID")
	private String event_id;
	
	@Column(name = "label")
	private String text;
	
	@CreationTimestamp
	@Column(name = "CREATED_AT")
	private LocalDateTime created_at;
	
    @UpdateTimestamp
	@Column(name = "MODIFIED_AT")
	private LocalDateTime modified_at;
	
	
}
