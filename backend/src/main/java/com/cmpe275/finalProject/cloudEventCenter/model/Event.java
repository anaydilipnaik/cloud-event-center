package com.cmpe275.finalProject.cloudEventCenter.model;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
//import org.joda.time.LocalDateTime;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "EVENT",catalog = "EVENT_CENTER")
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler","$$_hibernate_interceptor"})
@JsonInclude(Include.NON_NULL)
@Component
public class Event {
	
	@Id
	@GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name = "EVENT_ID")
	private String id;
	
	@Column(name = "EVENT_TITLE")
	private String title;
	
	@Column(name = "EVENT_DESC")
	private String description;
	
	@Column(name = "EVENT_START_TIME")
	private LocalDateTime startTime;
	 
	@Column(name="EVENT_END_TIME")
	private LocalDateTime endTime;
	 
	@Column(name="EVENT_DEADLINE")
	private LocalDateTime deadline;
	 
	@Column(name="MIN_PARTICIPANTS")
	private int minParticipants;
	 
	@Column(name="MAX_PARTICIPANTS")
	private int maxParticipants;
	 
	@Column(name="EVENT_FEE")
	private double fee;
	 
	//	 @Column(name="IS_FREE")
	//	 private boolean isFree;
	 
	//	 @Column(name="IS_FIRST_COME")
	//	 private String isFirstCome;
	 
	@Column(name="APPROVAL_REQ")
	private boolean approvalRequired;
	
//	@OneToOne(fetch=FetchType.LAZY,cascade = CascadeType.ALL)
//	@JoinColumn(name="ORGANIZER_ID",referencedColumnName = "USER_ID")
//	private User Organizer;
	
	@ManyToOne(fetch=FetchType.LAZY,cascade = CascadeType.ALL)
	@JoinColumn(name="ORGANIZER_ID",referencedColumnName = "USER_ID")
	@JsonIgnoreProperties({"events", "eventToOrganize","address","roles",
		"hibernateLazyInitializer"})
	private User organizer;
	
    @Embedded
    @AttributeOverrides(value = {
    		 @AttributeOverride(name = "street", column = @Column(name = "STREET")),
    		 @AttributeOverride(name = "number", column = @Column(name = "NUMBER")),
    		 @AttributeOverride(name = "city", column = @Column(name = "CITY")),
    		 @AttributeOverride(name = "state", column = @Column(name = "STATE")),
    		 @AttributeOverride(name = "zip", column = @Column(name = "ZIP"))
    })
	private Address address;
    
//    @ManyToMany(mappedBy = "events", cascade = CascadeType.ALL)
//    private Set<User> participants;
    
	/*
	 * @ManyToMany(cascade = CascadeType.MERGE)
	 * 
	 * @JoinTable(name = "participant_events", joinColumns = @JoinColumn(name =
	 * "event_id"), inverseJoinColumns = @JoinColumn(name = "user_id")) private
	 * List<User> participants;
	 */
    
    @OneToMany(mappedBy="event",cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<EventParticipant> participants;
    
	@Enumerated(EnumType.STRING)
	@Column(name = "STATUS")
	private EEventStatus  status;
	
	@Column(name = "IS_ACTIVE")
	private boolean isActive;
	
	@Column(name = "CREATED_ON")
	private LocalDate createdOn;
	
	@Column(name = "PTS_FORUM_ACTIVE")
	private boolean pForumOpen;
	
	@Column(name = "PTS_FORUM_CANCEL_DESC")
	private String pForumCancelDesc;
}
