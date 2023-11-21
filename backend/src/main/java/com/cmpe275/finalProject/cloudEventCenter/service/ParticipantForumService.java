/**
 * 
 */
package com.cmpe275.finalProject.cloudEventCenter.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.HttpMethod;

import com.cmpe275.finalProject.cloudEventCenter.model.EEventStatus;
import com.cmpe275.finalProject.cloudEventCenter.model.Event;
import com.cmpe275.finalProject.cloudEventCenter.model.ForumQuestions;
import com.cmpe275.finalProject.cloudEventCenter.repository.ForumQuestionsRepository;
import com.cmpe275.finalProject.cloudEventCenter.repository.ForumQuestionsAnswersRepository;
import com.cmpe275.finalProject.cloudEventCenter.model.ForumQuestionsAnswers;
import com.cmpe275.finalProject.cloudEventCenter.model.User;
import com.cmpe275.finalProject.cloudEventCenter.controller.MimicClockTimeController;
import com.cmpe275.finalProject.cloudEventCenter.enums.ForumTypes;
import com.cmpe275.finalProject.cloudEventCenter.repository.EventRepository;
import com.cmpe275.finalProject.cloudEventCenter.repository.UserRepository;



/**
 * @author supreeth
 *
 */

@Service
@Component
public class ParticipantForumService {
	
	@Autowired
	private ForumQuestionsRepository questions_repository;
	
	@Autowired
	private ForumQuestionsAnswersRepository answers_repository;
	
	@Autowired
	private EventRepository events_repository;
	
	@Autowired
	private UserRepository users_repository;
	
	@Autowired
	private AwsS3Service s3_service;
	
	private String bucketName = "cloudeventcenter";
	
	private Boolean is_user_participant(User user,  Event event) {
		return true;
	};
	
	private Boolean can_user_post(User user, Event event) {
		// Only participants and the organizer can view and post in the participant forum. 
		//  open for posting until 72 hours after the end of the event
		// 		or
		// 	anytime the organizer manually closes the participant forum after the event finishes.
		
		// A closed participant forum is still readable to the participants and organizer.

		LocalDateTime currDateTime = MimicClockTimeController.getMimicDateTime();
		
		if (!this.is_user_participant(user, event)) return false;
		
		if (event.getStatus().equals(EEventStatus.REG_OPEN) || event.getStatus().equals(EEventStatus.CANCELLED)) 	
			return false;
		
		if (event.getStatus().equals(EEventStatus.CLOSED)) {
			if (currDateTime.isAfter(event.getEndTime().plusDays(3))) {
//			if (event.getDeadline().isBefore(LocalDateTime.now().minusDays(3))) {
				return false;
			}
		}
		
		// If participation forum has been closed
//		if (event.getPForumOpen() == false) return false;
		
		return true;
	};
	
	public ForumQuestions persist(User user, Event event, String text, String filePath) {
		return questions_repository
				.save(
					new ForumQuestions(
						null,
						user,
						event,
						filePath,
						text,
						ForumTypes.PARTICIPANT_FORUM,
						null,
						null
				));
	};
	
	@Transactional
	public ResponseEntity<?> createQuestion(
			String userId, 
			String eventId,
			String text,
			MultipartFile file
		) {
		
		try {
			
			Event event = events_repository.findById(eventId).orElse(null);
			if (event == null) {
				return ResponseEntity
			            .status(HttpStatus.NOT_FOUND)
			            .body("Event not found");
			};
			
			User user = users_repository.findById(userId).orElse(null);
			if (user == null) {
				return ResponseEntity
			            .status(HttpStatus.UNAUTHORIZED)
			            .body("Unauthorized User");
			};
			
//			if (!this.can_user_post(user, event)) {
//				return ResponseEntity
//			            .status(HttpStatus.FORBIDDEN)
//			            .body("You are not permitted to do this action");
//			};
			
			// Upload an image
			String S3URL = "";
			if (file != null) {
				Map<String, String> metadata = new HashMap<String, String>();
		        metadata.put("Content-Type", file.getContentType());
		        metadata.put("Content-Length", String.valueOf(file.getSize()));
		        
		        Optional<Map<String, String>> md = Optional.ofNullable(metadata);
	
		        String bucketPath = "/events/" + 
						eventId + 
						"/forums/sign_up/" + 
						UUID.randomUUID();
		        String filePath = this.bucketName + bucketPath;
		        String fileName = String.format("%s", file.getOriginalFilename());
		        S3URL = "https://" + this.bucketName + ".s3." + 
		        		"us-east-2" + 
		        		".amazonaws.com" + 
		        		bucketPath + "/" + fileName;
		        s3_service.upload(filePath, fileName, file.getInputStream(), md);
			};
			
			ForumQuestions savedQuestion = this.persist(user, event, text, S3URL);
						
			return ResponseEntity
					.status(HttpStatus.CREATED)
					.contentType(MediaType.APPLICATION_JSON)
					.body(savedQuestion);
				
		} catch(Exception e) {
			e.printStackTrace(System.out);
			return ResponseEntity
		            .status(HttpStatus.INTERNAL_SERVER_ERROR)
		            .body("Internal Server Error");
		}
	}
	
	@Transactional
	public ResponseEntity<?> getQuestions(String userId, String eventId) {
		try {
			
			Event event = events_repository.findById(eventId).orElse(null);
			if (event == null) {
				return ResponseEntity
			            .status(HttpStatus.NOT_FOUND)
			            .body("Event not found");
			};
						
			User user = users_repository.findById(userId).orElse(null);
			if (user == null) {
				return ResponseEntity
			            .status(HttpStatus.UNAUTHORIZED)
			            .body("Unauthorized User");
			};
			
			if (!this.is_user_participant(user, event)) {
				return ResponseEntity
			            .status(HttpStatus.FORBIDDEN)
			            .body("You are not permitted to do this action");
			};
			
			List<ForumQuestions> questions = 
					questions_repository.findByEventAndForumType(
							event,
							ForumTypes.PARTICIPANT_FORUM,
							Sort.by(Sort.Direction.DESC, "createdAt")
					); 
			return ResponseEntity
					.status(HttpStatus.OK)
					.contentType(MediaType.APPLICATION_JSON)
	                .body(questions);
				
		} catch(Exception e) {
			e.printStackTrace(System.out);
			return ResponseEntity
		            .status(HttpStatus.INTERNAL_SERVER_ERROR)
		            .body(e.toString());
		}
	}
	
	@Transactional
	public ResponseEntity<?> getQuestionAnswers(String userId, String questionId) {
		try {
			ForumQuestions question = questions_repository.findById(questionId).orElse(null);
			if (question == null) {
				return ResponseEntity
			            .status(HttpStatus.NOT_FOUND)
			            .body("Forum Question not found");
			}
			
			User user = users_repository.findById(userId).orElse(null);
			if (user == null) {
				return ResponseEntity
			            .status(HttpStatus.UNAUTHORIZED)
			            .body("Unauthorized User");
			};
			
			if (!this.is_user_participant(user, question.getEvent())) {
				return ResponseEntity
			            .status(HttpStatus.FORBIDDEN)
			            .body("You are not permitted to do this action");
			};
			
			List<ForumQuestionsAnswers> answers = answers_repository
					.findByQuestion(question, Sort.by(Sort.Direction.DESC, "createdAt")); 
			return ResponseEntity
					.status(HttpStatus.OK)
					.contentType(MediaType.APPLICATION_JSON)
	                .body(answers);
				
		} catch(Exception e) {
			e.printStackTrace(System.out);
			return ResponseEntity
		            .status(HttpStatus.INTERNAL_SERVER_ERROR)
		            .body(e.toString());
		}
	}
	
	@Transactional
	public ResponseEntity<?> createAnswer(String userId, String questionId, String text) {
		try {			
			User user = users_repository.findById(userId).orElse(null);
			if (user == null) {
				return ResponseEntity
			            .status(HttpStatus.UNAUTHORIZED)
			            .body("Unauthorized User");
			};
			
			ForumQuestions question = questions_repository.findById(questionId).orElse(null);
			if (question == null) {
				return ResponseEntity
			            .status(HttpStatus.NOT_FOUND)
			            .body("Forum Question not found");
			}
			
			String eventId = question.getEvent().getId();
			Event event = events_repository.findById(eventId).orElse(null);
			if (event == null) {
				return ResponseEntity
			            .status(HttpStatus.NOT_FOUND)
			            .body("Event not found");
			};
			
//			if (!this.can_user_post(user, event)) {
//				return ResponseEntity
//			            .status(HttpStatus.FORBIDDEN)
//			            .body("You are not permitted to do this action");
//			};
			
			ForumQuestionsAnswers answer = new ForumQuestionsAnswers(
					null,
					user,
					eventId,
					question,
					text,
					null,
					null
			);
			
			ForumQuestionsAnswers savedAnswer = answers_repository.save(answer); 
			
			return ResponseEntity
					.status(HttpStatus.OK)
					.contentType(MediaType.APPLICATION_JSON)
	                .body(savedAnswer);
		} catch(Exception e) {
			e.printStackTrace(System.out);
			return ResponseEntity
		            .status(HttpStatus.INTERNAL_SERVER_ERROR)
		            .body(e.toString());
		}
	}

	@Transactional
	public ResponseEntity<?> uploadImage(
			String userId,
			String questionId
	) {
		ForumQuestions question = questions_repository.findById(questionId).orElse(null);
		if (question == null) {
			return ResponseEntity
		            .status(HttpStatus.NOT_FOUND)
		            .body("Forum Question not found");
		}
		
		if (!userId.equals(question.getUser().getId())) {
			return ResponseEntity
		            .status(HttpStatus.FORBIDDEN)
		            .body("You are not permitted to perform this action");
		};
		
		String filePath = "cec/events/" + 
					question.getEvent().getId() + 
					"/forums/participant/" + 
					question.getId();
		
		// TODO: Bucket Name
		String bucketName = "us-east-2";
		String preSignedUrl = s3_service.generatePreSignedURL(
				filePath, 
				bucketName, 
				HttpMethod.PUT
		);
		
		HashMap<String, ArrayList<String>> response = new HashMap<>();
		ArrayList<String> urls = new ArrayList<String>();
		urls.add(preSignedUrl);
		response.put("data", urls);
		
//		TODO: Set the question with said image
		
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(response);
	}

	@Transactional
	public ResponseEntity<?> closeForum(String userId, String eventId, String description) {
		try {
			Event event = events_repository.findById(eventId).orElse(null);
			if (event == null) {
				return ResponseEntity
			            .status(HttpStatus.NOT_FOUND)
			            .body("Event not found");
			};
			
			User user = users_repository.findById(userId).orElse(null);
			if (user == null) {
				return ResponseEntity
			            .status(HttpStatus.UNAUTHORIZED)
			            .body("Unauthorized User");
			};
			
	//		if (!this.can_user_post(user, event)) {
	//		return ResponseEntity
	//	            .status(HttpStatus.FORBIDDEN)
	//	            .body("You are not permitted to do this action");
			
			//
			event.setPForumOpen(false);
			event.setPForumCancelDesc(description);
			
			Event updatedEvent = events_repository.save(event);
			
			return ResponseEntity
		            .status(HttpStatus.OK)
		            .body(updatedEvent);
			
		} catch(Exception ex) {
			System.out.println(ex.getStackTrace());
			return ResponseEntity
		            .status(HttpStatus.INTERNAL_SERVER_ERROR)
		            .body("Internal Server Error");
		}
	};
}
