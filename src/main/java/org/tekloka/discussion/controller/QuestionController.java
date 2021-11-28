package org.tekloka.discussion.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tekloka.discussion.constants.PermissionConstants;
import org.tekloka.discussion.dto.AnswerDTO;
import org.tekloka.discussion.dto.QuestionDTO;
import org.tekloka.discussion.security.AccessPermissions;
import org.tekloka.discussion.service.QuestionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping(value = "/question")
public class QuestionController {

	private final QuestionService questionService;

	public QuestionController(QuestionService questionService) {
		this.questionService = questionService;
	}
	
	@Operation(summary = "Add Question")
	@ApiResponses(value = {
	     @ApiResponse(responseCode = "200", description = "Question added successfully"),
	     @ApiResponse(responseCode = "500", description = "Internal Server Error")
	})
	@PostMapping(path = "/save", produces = MediaType.APPLICATION_JSON_VALUE)
	@AccessPermissions(value = PermissionConstants.SAVE_QUESTION)
	public ResponseEntity<Object> save(HttpServletRequest request, @RequestBody QuestionDTO questionDTO) {
		return questionService.save(request, questionDTO);
	}
	
	@Operation(summary = "Update Question")
	@ApiResponses(value = {
	     @ApiResponse(responseCode = "200", description = "Question updated successfully"),
	     @ApiResponse(responseCode = "500", description = "Internal Server Error")
	})
	@PostMapping(path = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
	@AccessPermissions(value = PermissionConstants.UPDATE_QUESTION)
	public ResponseEntity<Object> update(HttpServletRequest request, @RequestBody QuestionDTO questionDTO) {
		return questionService.update(request, questionDTO);
	}
	
	@Operation(summary = "Delete Question")
	@ApiResponses(value = {
	     @ApiResponse(responseCode = "200", description = "Question deleted successfully"),
	     @ApiResponse(responseCode = "500", description = "Internal Server Error")
	})
	@PostMapping(path = "/delete", produces = MediaType.APPLICATION_JSON_VALUE)
	@AccessPermissions(value = PermissionConstants.DELETE_QUESTION)
	public ResponseEntity<Object> delete(HttpServletRequest request, @RequestBody QuestionDTO questionDTO) {
		return questionService.delete(request, questionDTO);
	}
	
	
	@Operation(summary = "Add Answer")
	@ApiResponses(value = {
	     @ApiResponse(responseCode = "200", description = "Answer added successfully"),
	     @ApiResponse(responseCode = "500", description = "Internal Server Error")
	})
	@PostMapping(path = "/save-answer", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> saveAnswer(HttpServletRequest request, @RequestBody AnswerDTO answerDTO) {
		return questionService.saveAnswer(request, answerDTO);
	}
	
	@Operation(summary = "Update Answer")
	@ApiResponses(value = {
	     @ApiResponse(responseCode = "200", description = "Answer updated successfully"),
	     @ApiResponse(responseCode = "500", description = "Internal Server Error")
	})
	@PostMapping(path = "/update-answer", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> updateAnswer(HttpServletRequest request, @RequestBody AnswerDTO answerDTO) {
		return questionService.updateAnswer(request, answerDTO);
	}
	
	@Operation(summary = "Delete Answer")
	@ApiResponses(value = {
	     @ApiResponse(responseCode = "200", description = "Answer deleted successfully"),
	     @ApiResponse(responseCode = "500", description = "Internal Server Error")
	})
	@PostMapping(path = "/delete-answer", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> deleteAnswer(HttpServletRequest request, @RequestBody AnswerDTO answerDTO) {
		return questionService.deleteAnswer(request, answerDTO);
	}
	
}
