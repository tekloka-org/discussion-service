package org.tekloka.discussion.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.tekloka.discussion.dto.AnswerDTO;
import org.tekloka.discussion.dto.QuestionDTO;

public interface QuestionService {

	ResponseEntity<Object> save(HttpServletRequest request, QuestionDTO discussionDTO);

	ResponseEntity<Object> update(HttpServletRequest request, QuestionDTO discussionDTO);

	ResponseEntity<Object> delete(HttpServletRequest request, QuestionDTO discussionDTO);

	ResponseEntity<Object> getAllActiveQuestions(HttpServletRequest request);

	ResponseEntity<Object> getQuestion(HttpServletRequest request, String userId, String discussionUrlPath);

	ResponseEntity<Object> saveAnswer(HttpServletRequest request, AnswerDTO answerDTO);

	ResponseEntity<Object> updateAnswer(HttpServletRequest request, AnswerDTO answerDTO);

	ResponseEntity<Object> deleteAnswer(HttpServletRequest request, AnswerDTO answerDTO);

	ResponseEntity<Object> getAllAnswers(HttpServletRequest request, String userId, String questionUrlPath);

}
