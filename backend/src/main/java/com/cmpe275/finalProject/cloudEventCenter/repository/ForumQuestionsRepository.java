package com.cmpe275.finalProject.cloudEventCenter.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.cmpe275.finalProject.cloudEventCenter.enums.ForumTypes;
import com.cmpe275.finalProject.cloudEventCenter.model.Event;
import com.cmpe275.finalProject.cloudEventCenter.model.ForumQuestions;

public interface ForumQuestionsRepository extends JpaRepository<ForumQuestions, String>{
	public List<ForumQuestions> findByEvent(Event event, Sort sort);
	
	public List<ForumQuestions> findByEventAndForumType(Event event, ForumTypes forumType, Sort sort);
}
