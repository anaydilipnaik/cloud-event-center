package com.cmpe275.finalProject.cloudEventCenter.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cmpe275.finalProject.cloudEventCenter.model.EEventRole;
import com.cmpe275.finalProject.cloudEventCenter.model.Reviews;

public interface ReviewsRepository extends JpaRepository<Reviews, String> {
	
	// this method is to GET all the reviews of
	// a user as an organizer
	// a user as a participant
	public List<Reviews> findByReviewForAndReviewType(String reviewFor, EEventRole reviewType);
	
	// This method is to ensure uniqueness of reviews across events
	// for participants and organizers
	public List<Reviews> findByReviewerAndReviewForAndReviewTypeAndEventId(
			String reviewer, 
			String reviewFor, 
			EEventRole reviewType, 
			String eventId
	);
	
	
}