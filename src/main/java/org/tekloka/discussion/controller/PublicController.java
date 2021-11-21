package org.tekloka.discussion.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tekloka.discussion.service.QuestionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping(value = "/public")
public class PublicController {

	private final QuestionService questionService;
	
	public PublicController(QuestionService discussionService) {
		super();
		this.questionService = discussionService;
	}

	@Operation(summary = "Get all questions")
	@ApiResponses(value = {
	     @ApiResponse(responseCode = "200", description = "Questions found"),
	     @ApiResponse(responseCode = "500", description = "Internal Server Error")
	})
	@GetMapping(path = "/get-all-questions", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getAllActiveQuestions(HttpServletRequest request) {
		return questionService.getAllActiveQuestions(request);
	}
	
	@Operation(summary = "Get question")
	@ApiResponses(value = {
	     @ApiResponse(responseCode = "200", description = "Question found"),
	     @ApiResponse(responseCode = "500", description = "Internal Server Error")
	})
	@GetMapping(path = "get-question/{userId}/{questionUrlPath}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getQuestion(HttpServletRequest request, @PathVariable String userId, 
			@PathVariable String questionUrlPath) {
		 return questionService.getQuestion(request, userId, questionUrlPath);
	}
	
	@Operation(summary = "Get all answers")
	@ApiResponses(value = {
	     @ApiResponse(responseCode = "200", description = "Answers found"),
	     @ApiResponse(responseCode = "500", description = "Internal Server Error")
	})
	@GetMapping(path = "get-all-answers/{userId}/{questionUrlPath}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getAllAnswers(HttpServletRequest request, @PathVariable String userId, 
			@PathVariable String questionUrlPath) {
		 return questionService.getAllAnswers(request, userId, questionUrlPath);
	}
	
}
