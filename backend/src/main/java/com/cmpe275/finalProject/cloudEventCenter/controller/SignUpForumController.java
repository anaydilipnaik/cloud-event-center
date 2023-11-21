package com.cmpe275.finalProject.cloudEventCenter.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
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

import com.cmpe275.finalProject.cloudEventCenter.POJOs.EventData;
import com.cmpe275.finalProject.cloudEventCenter.model.Event;
import com.cmpe275.finalProject.cloudEventCenter.model.User;
import com.cmpe275.finalProject.cloudEventCenter.repository.UserRepository;
import com.cmpe275.finalProject.cloudEventCenter.service.SignUpForumService;

/**
 * 
 * @author supreeth
 *
 * Sign Up Forum
 * ALL users can post a question on the sign up forum
 * ALL users can reply to a question on the sign up forum
 */

@RestController
@RequestMapping("/api/forums")
public class SignUpForumController {
	
	@Autowired
	private SignUpForumService signUpForumService;
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(
			value = "/sign_up/{eventId}/questions", 
			method = RequestMethod.POST, 
			produces=MediaType.APPLICATION_JSON_VALUE
	)
	ResponseEntity<?> createSignUpForumQuestion(
			@PathVariable(value =  "eventId") String eventId,
			@RequestParam("userId") String userId,
			@RequestParam("text") String text,
			@RequestParam(value = "file", required = false) MultipartFile file
	) {	
			return signUpForumService.createQuestion(userId, eventId, text, file);
    }
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(
			value = "/sign_up/questions/{questionId}/answers", 
			method = RequestMethod.POST, 
			produces=MediaType.APPLICATION_JSON_VALUE
	)
	ResponseEntity<?> createAnswer(
			@PathVariable(value =  "questionId") String questionId,
			@RequestParam("userId") String userId,
			@RequestParam("text") String text
	) {
			return signUpForumService.createAnswer(userId, questionId, text);
    }
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(
			value = "/sign_up/{eventId}/questions", 
			method = RequestMethod.GET, 
			produces=MediaType.APPLICATION_JSON_VALUE
	)
	ResponseEntity<?> getSignUpForumQuestions(
			@PathVariable(value =  "eventId") String eventId
	) {
			return signUpForumService.getQuestions(eventId);
    }
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(
			value = "/sign_up/questions/{questionId}/answers", 
			method = RequestMethod.GET, 
			produces=MediaType.APPLICATION_JSON_VALUE
	)
	ResponseEntity<?> getSignUpForumQuestionsAnswers(
			@PathVariable(value =  "questionId") String questionId
	) {
			return signUpForumService.getQuestionAnswers(questionId);
    }
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(
			value = "/sign_up/questions/{questionId}/images", 
			method = RequestMethod.POST, 
			produces=MediaType.APPLICATION_JSON_VALUE
	)
	ResponseEntity<?> getImageUploadURL(
			@PathVariable(value =  "questionId") String questionId,
			@RequestParam("userId") String userId
	) {
			return signUpForumService.uploadImage(userId, questionId);
    }
}
