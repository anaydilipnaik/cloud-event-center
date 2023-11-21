package com.cmpe275.finalProject.cloudEventCenter.controller;

import java.time.LocalDateTime;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cmpe275.finalProject.cloudEventCenter.POJOs.EventData;
import com.cmpe275.finalProject.cloudEventCenter.POJOs.EventRequest;
import com.cmpe275.finalProject.cloudEventCenter.POJOs.ReviewRatingData;
import com.cmpe275.finalProject.cloudEventCenter.model.Address;
import com.cmpe275.finalProject.cloudEventCenter.service.ReviewService;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {
	
	@Autowired
	private ReviewService reviewService;
	
	@PostMapping("/{eventID}/organizer/{reviewerID}")
	public ResponseEntity<?> createReviewForOrganizer(@Valid @PathVariable("eventID") String eventID,
			@PathVariable("reviewerID") String reviewerID,
			@RequestBody ReviewRatingData reviewRatingData){
		
		if(eventID.isBlank() || reviewerID.isBlank())
			return ResponseEntity
	            .status(HttpStatus.BAD_REQUEST)
	            .body("Enter an eventID or reviewerID");
		
		if(reviewRatingData.getReview().isEmpty())
			return ResponseEntity
		            .status(HttpStatus.BAD_REQUEST)
		            .body("Enter a review");
		
		if(reviewRatingData.getRating() < 1 || reviewRatingData.getRating() > 5)
			return ResponseEntity
		            .status(HttpStatus.BAD_REQUEST)
		            .body("Enter rating score between 1 - 5");
		
	    return reviewService.addReviewForOrganizer(eventID, reviewerID, reviewRatingData.getReview(), reviewRatingData.getRating());
	}
	
	@PostMapping("/{eventID}/participant/{participantID}")
	public ResponseEntity<?> createReviewForParticipant(@Valid @PathVariable("eventID") String eventID,
			@PathVariable("participantID") String participantID,
			@RequestBody ReviewRatingData reviewRatingData){
		
		if(eventID.isBlank() || participantID.isBlank())
			return ResponseEntity
	            .status(HttpStatus.BAD_REQUEST)
	            .body("Enter an eventID or participantID");
		
		if(reviewRatingData.getReview().isEmpty())
			return ResponseEntity
		            .status(HttpStatus.BAD_REQUEST)
		            .body("Enter a review");
		
		if(reviewRatingData.getRating() < 1 || reviewRatingData.getRating() > 5)
			return ResponseEntity
		            .status(HttpStatus.BAD_REQUEST)
		            .body("Enter rating score between 1 - 5");
		
	    return reviewService.addReviewForParticipant(eventID, participantID, reviewRatingData.getReview(), reviewRatingData.getRating());
	}
	
	@ResponseStatus(HttpStatus.OK)
	@GetMapping(value = "/participant/ratings/{participantID}")
	ResponseEntity<?> getAvgParticipantRatings(@PathVariable("participantID") String participantID){
		
		if(participantID.isBlank())
			return ResponseEntity
	            .status(HttpStatus.BAD_REQUEST)
	            .body("Enter an participantID");
		
		return reviewService.getAvgParticipantRatings(participantID);
		
	}
	
	@ResponseStatus(HttpStatus.OK)
	@GetMapping(value = "/organizer/ratings/{organizerID}")
	ResponseEntity<?> getAvgOrganizerRatings(@PathVariable("organizerID") String organizerID){
		
		if(organizerID.isBlank())
			return ResponseEntity
	            .status(HttpStatus.BAD_REQUEST)
	            .body("Enter an organizerID");
		
		return reviewService.getAvgOrganizerRatings(organizerID);
		
	}
	
	@ResponseStatus(HttpStatus.OK)
	@GetMapping(value = "/participant/reviews/{participantID}")
	ResponseEntity<?> getAvgParticipantReviews(@PathVariable("participantID") String participantID){
		
		if(participantID.isBlank())
			return ResponseEntity
	            .status(HttpStatus.BAD_REQUEST)
	            .body("Enter an participantID");
		
		return reviewService.getAvgParticipantReviews(participantID);
		
	}
	
	@ResponseStatus(HttpStatus.OK)
	@GetMapping(value = "/organizer/reviews/{organizerID}")
	ResponseEntity<?> getAvgOrganizerReviews(@PathVariable("organizerID") String organizerID){
		
		if(organizerID.isBlank())
			return ResponseEntity
	            .status(HttpStatus.BAD_REQUEST)
	            .body("Enter an organizerID");
		
		return reviewService.getAvgOrganizerReviews(organizerID);
		
	}
}
