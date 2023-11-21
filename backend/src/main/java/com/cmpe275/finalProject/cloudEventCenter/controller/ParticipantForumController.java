package com.cmpe275.finalProject.cloudEventCenter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cmpe275.finalProject.cloudEventCenter.service.ParticipantForumService;

/**
 * 
 * @author supreeth
 */

@RestController
@RequestMapping("/api/forums")
public class ParticipantForumController {
	
	@Autowired
	private ParticipantForumService forumService;
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(
			value = "/participant/{eventId}/questions", 
			method = RequestMethod.POST, 
			produces=MediaType.APPLICATION_JSON_VALUE
	)
	ResponseEntity<?> createSignUpForumQuestion(
			@PathVariable(value =  "eventId") String eventId,
			@RequestParam("userId") String userId,
			@RequestParam("text") String text,
			@RequestParam(value = "file", required = false) MultipartFile file
	) {	
			return forumService.createQuestion(userId, eventId, text, file);
    }
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(
			value = "/participant/questions/{questionId}/answers", 
			method = RequestMethod.POST, 
			produces=MediaType.APPLICATION_JSON_VALUE
	)
	ResponseEntity<?> createAnswer(
			@PathVariable(value =  "questionId") String questionId,
			@RequestParam("userId") String userId,
			@RequestParam("text") String text
	) {
			return forumService.createAnswer(userId, questionId, text);
    }
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(
			value = "/participant/{eventId}/questions", 
			method = RequestMethod.GET, 
			produces=MediaType.APPLICATION_JSON_VALUE
	)
	ResponseEntity<?> getSignUpForumQuestions(
			@RequestParam("userId") String userId,
			@PathVariable(value =  "eventId") String eventId
	) {
			return forumService.getQuestions(userId, eventId);
    }
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(
			value = "/participant/questions/{questionId}/answers", 
			method = RequestMethod.GET, 
			produces=MediaType.APPLICATION_JSON_VALUE
	)
	ResponseEntity<?> getSignUpForumQuestionsAnswers(
			@RequestParam("userId") String userId,
			@PathVariable(value =  "questionId") String questionId
	) {
			return forumService.getQuestionAnswers(userId, questionId);
    }
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(

			value = "/participant/questions/{questionId}/images", 
			method = RequestMethod.POST, 
			produces=MediaType.APPLICATION_JSON_VALUE
	)
	ResponseEntity<?> getImageUploadURL(
			@PathVariable(value =  "questionId") String questionId,
			@RequestParam("userId") String userId
	) {
			return forumService.uploadImage(userId, questionId);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(
			value = "/participant/{eventId}/close", 
			method = RequestMethod.POST, 
			produces=MediaType.APPLICATION_JSON_VALUE
	)
	ResponseEntity<?> closeForum(
			@PathVariable(value =  "eventId") String eventId,
			@RequestParam("userId") String userId,
			@RequestParam("text") String text
	) {
			return forumService.closeForum(userId, eventId, text);
	}
}
