package com.cmpe275.finalProject.cloudEventCenter.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.cmpe275.finalProject.cloudEventCenter.model.ForumQuestions;
import com.cmpe275.finalProject.cloudEventCenter.model.ForumQuestionsAnswers;

public interface ForumQuestionsAnswersRepository extends JpaRepository<ForumQuestionsAnswers, String>{
	public List<ForumQuestionsAnswers> findByQuestion(ForumQuestions question, Sort sort);
}
