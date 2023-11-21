package com.cmpe275.finalProject.cloudEventCenter.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.cmpe275.finalProject.cloudEventCenter.model.Event;
import com.cmpe275.finalProject.cloudEventCenter.model.User;

public interface EventRepository extends JpaRepository<Event, String>{
	
	@Query("SELECT e FROM Event e WHERE e.organizer=?1")
	public Set<Event> findByOrganizerID(String organizerID);
	
	public List<Event> findByOrganizer(User organizer);
	
	public List<Event> findByOrganizer_Id(String userId);
	    
	
	public boolean existsByOrganizer(User organizer);
	
	@Query(value="select e.* from event e, participant_events p where e.event_id=p.event_id and p.user_id=?1", nativeQuery = true)
	public Set<Event> findEventsByUserId(String userId);
	

	@Query(value="select e.* from event e, user u where e.organizer_id=u.user_id and "
			+ "(?1 is null or (MATCH (e.event_title,e.event_desc) AGAINST (?1))"
			+ "or lower(e.event_title) like %?1% or lower(e.event_desc) like %?1%) "
			+ "and (?2 is null or lower(e.city) =?2) and "
			+ "(?3 is null or e.status=?3) "
			+ "and ((?5 is null and event_start_time>=DATE_FORMAT(?4,'%Y-%m-%d %H:%i:%s')) "
			+ "or (?5 is not null and event_start_time>=DATE_FORMAT(?5,'%Y-%m-%d %H:%i:%s'))) "
			+ "and (?6 is null or event_end_time<=DATE_FORMAT(?6,'%Y-%m-%d %H:%i:%s')) "
			+ "and (((?7=0 || ?7=1 ) && is_active=?7)||((?7=2) && (is_active=1||is_active=0)))"
			+" and (?8 is null or lower(u.screen_name) like %?8%)",
			countQuery="select count(*) from event e, user u where e.organizer_id=u.user_id and "
					+ "(?1 is null or (MATCH (e.event_title,e.event_desc) AGAINST (?1))"
					+ "or lower(e.event_title) like %?1% or lower(e.event_desc) like %?1%) "
					+ "and (?2 is null or lower(e.city) =?2) and "
					+ "(?3 is null or e.status=?3) "
					+ "and ((?5 is null and event_start_time>=DATE_FORMAT(?4,'%Y-%m-%d %H:%i:%s')) "
					+ "or (?5 is not null and event_start_time>=DATE_FORMAT(?5,'%Y-%m-%d %H:%i:%s'))) "
					+ "and (?6 is null or event_end_time<=DATE_FORMAT(?6,'%Y-%m-%d %H:%i:%s')) "
					+ "and (((?7=0 || ?7=1 ) && is_active=?7)||((?7=2) && (is_active=1||is_active=0)))"
					+" and (?8 is null or lower(u.screen_name) like %?8%)",
			
			
			nativeQuery = true)
	public Page<Event> searchForEvents(
			 String keyword,String city,String status,String mimicTime,
			 String startTime,String endTime,int active,String organizer,Pageable pageable
			);
}