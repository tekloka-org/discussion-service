package org.tekloka.discussion.service.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.tekloka.discussion.constants.AppConstants;
import org.tekloka.discussion.constants.DataConstants;
import org.tekloka.discussion.constants.ResponseConstants;
import org.tekloka.discussion.document.Answer;
import org.tekloka.discussion.document.Question;
import org.tekloka.discussion.dto.AnswerDTO;
import org.tekloka.discussion.dto.QuestionDTO;
import org.tekloka.discussion.dto.mapper.QuestionMapper;
import org.tekloka.discussion.repository.AnswerRepository;
import org.tekloka.discussion.repository.QuestionRepository;
import org.tekloka.discussion.service.QuestionService;
import org.tekloka.discussion.util.ResponseUtil;

@Service
public class QuestionServiceImpl implements QuestionService{

	private final Logger logger = LoggerFactory.getLogger(QuestionServiceImpl.class);
	private final QuestionRepository questionRepository;
	private final QuestionMapper questionMapper;
	private final ResponseUtil responseUtil;
	private final AnswerRepository answerRepository;
	
	public QuestionServiceImpl(QuestionRepository questionRepository,
			QuestionMapper questionMapper, ResponseUtil responseUtil,
			AnswerRepository answerRepository) {
		this.questionRepository = questionRepository;
		this.questionMapper = questionMapper;
		this.responseUtil = responseUtil;
		this.answerRepository = answerRepository;
	}
	
	public Question toQuestion(Optional<Question> questionOptional, QuestionDTO questionDTO) {
		return questionMapper.toQuestion(questionOptional, questionDTO);
	}
	
	public QuestionDTO toQuestionDTO(Question question) {
		return questionMapper.toQuestionDTO(question);
	}

	public Set<QuestionDTO> toQuestionDTOSet(Set<Question> questions){
		return questionMapper.toQuestionDTOSet(questions);
	}
	
	public Question save(Question question) {
		return questionRepository.save(question);
	}
	
	public Optional<Question> findByQuestionId(String questionId){
		return questionRepository.findById(questionId);
	}
	
	public Set<Question> findByActive(boolean active){
		return questionRepository.findByActive(active);
	}
	
	public Optional<Question> findByCreatedByAndUrlPathAndActive(String createdBy, String urlPath, boolean active){
		return questionRepository.findByCreatedByAndUrlPathAndActive(createdBy, urlPath, active);
	}
	
	public Answer toAnswer(Optional<Answer> answerOptional, AnswerDTO answerDTO) {
		return questionMapper.toAnswer(answerOptional, answerDTO);
	}
	
	public AnswerDTO toAnswerDTO(Answer answer) {
		return questionMapper.toAnswerDTO(answer);
	}
	
	public Set<AnswerDTO> toAnswerDTOSet(Set<Answer> answers){
		return questionMapper.toAnswerDTOSet(answers);
	}
	
	public Answer saveAnswer(Answer answer) {
		return answerRepository.save(answer);
	}
	
	public Optional<Answer> findAnswerById(String answerId){
		return answerRepository.findById(answerId);
	}
	
	@Override
	public ResponseEntity<Object> save(HttpServletRequest request, QuestionDTO questionDTO) {
		Map<String, Object> dataMap = new HashMap<>();
		try {
			Optional<Question> questionOptional = Optional.empty();
			var question = toQuestion(questionOptional, questionDTO);
			question.setActive(true);
			question = save(question);
			dataMap.put(DataConstants.QUESTION, toQuestionDTO(question));
			return responseUtil.generateResponse(dataMap, ResponseConstants.QUESTION_SAVED);
		}catch (Exception e) {
			logger.error(AppConstants.LOG_FORMAT, ResponseConstants.QUESTION_NOT_SAVED, e);
			return responseUtil.generateResponse(dataMap, ResponseConstants.QUESTION_NOT_SAVED);
		}
	}

	@Override
	public ResponseEntity<Object> update(HttpServletRequest request, QuestionDTO questionDTO) {
		Map<String, Object> dataMap = new HashMap<>();
		Optional<Question> questionOptional = findByQuestionId(questionDTO.getQuestionId());		
		if(questionOptional.isPresent()) {
			try {
				var questtion = toQuestion(questionOptional, questionDTO);
				questtion = save(questtion);
				dataMap.put(DataConstants.QUESTION, toQuestionDTO(questtion));
				return responseUtil.generateResponse(dataMap, ResponseConstants.QUESTION_UPDATED);
			}catch (Exception e) {
				logger.error(AppConstants.LOG_FORMAT, ResponseConstants.QUESTION_NOT_UPDATED, e);
				return responseUtil.generateResponse(dataMap, ResponseConstants.QUESTION_NOT_UPDATED);
			}
		}else {
			return responseUtil.generateResponse(dataMap, ResponseConstants.QUESTION_NOT_FOUND);
		}
	}

	@Override
	public ResponseEntity<Object> delete(HttpServletRequest request, QuestionDTO questionDTO) {
		Map<String, Object> dataMap = new HashMap<>();
		Optional<Question> questionOptional = findByQuestionId(questionDTO.getQuestionId());		
		if(questionOptional.isPresent()) {
			try {
				var question = questionOptional.get();
				save(question);
				return responseUtil.generateResponse(dataMap, ResponseConstants.QUESTION_DELETED);
			}catch (Exception e) {
				logger.error(AppConstants.LOG_FORMAT, ResponseConstants.QUESTION_NOT_DELETED, e);
				return responseUtil.generateResponse(dataMap, ResponseConstants.QUESTION_NOT_DELETED);
			}
		}else {
			return responseUtil.generateResponse(dataMap, ResponseConstants.QUESTION_NOT_FOUND);
		}
	}

	@Override
	public ResponseEntity<Object> getAllActiveQuestions(HttpServletRequest request) {
		Map<String, Object> dataMap = new HashMap<>();
		Set<Question> questions = findByActive(true);
		dataMap.put(DataConstants.QUESTION_LIST, toQuestionDTOSet(questions));
		return responseUtil.generateResponse(dataMap, ResponseConstants.QUESTION_LIST_FOUND);
	}

	@Override
	public ResponseEntity<Object> getQuestion(HttpServletRequest request, String userId, String questionUrlPath) {
		Map<String, Object> dataMap = new HashMap<>();
		Optional<Question> questionOptional = findByCreatedByAndUrlPathAndActive(userId, questionUrlPath, true);
		if(questionOptional.isPresent()) {
			dataMap.put(DataConstants.QUESTION, toQuestionDTO(questionOptional.get()));
			return responseUtil.generateResponse(dataMap, ResponseConstants.QUESTION_FOUND);
		}else {
			return responseUtil.generateResponse(dataMap, ResponseConstants.QUESTION_NOT_FOUND);
		}
	}

	@Override
	public ResponseEntity<Object> saveAnswer(HttpServletRequest request, AnswerDTO answerDTO) {
		Map<String, Object> dataMap = new HashMap<>();
		Optional<Question> questionOptional = findByQuestionId(answerDTO.getQuestionId());
		if(questionOptional.isPresent()) {
			var question = questionOptional.get();
			try {
				Optional<Answer> answerOptional = Optional.empty();
				var answer = toAnswer(answerOptional, answerDTO);
				answer = saveAnswer(answer);
				dataMap.put(DataConstants.ANSWER, toAnswerDTO(answer));
				Set<Answer> existingAnswers = question.getAnswers();
				if(null == existingAnswers) {
					existingAnswers = new HashSet<>();
				}
				existingAnswers.add(answer);
				question.setAnswers(existingAnswers);
				save(question);
				return responseUtil.generateResponse(dataMap, ResponseConstants.ANSWER_SAVED);
			}catch (Exception e) {
				logger.error(AppConstants.LOG_FORMAT, ResponseConstants.ANSWER_NOT_SAVED, e);
				return responseUtil.generateResponse(dataMap, ResponseConstants.ANSWER_NOT_SAVED);
			}	
		}else {
			return responseUtil.generateResponse(dataMap, ResponseConstants.QUESTION_NOT_FOUND);
		}
	}
	
	@Override
	public ResponseEntity<Object> updateAnswer(HttpServletRequest request, AnswerDTO answerDTO) {
		Map<String, Object> dataMap = new HashMap<>();
		try {
			Optional<Answer> answerOptional = findAnswerById(answerDTO.getAnswerId());
			if(answerOptional.isPresent()) {
				var answer = toAnswer(answerOptional, answerDTO);
				answer = saveAnswer(answer);
				dataMap.put(DataConstants.ANSWER, toAnswerDTO(answer));
				return responseUtil.generateResponse(dataMap, ResponseConstants.ANSWER_UPDATED);
			}else {
				return responseUtil.generateResponse(dataMap, ResponseConstants.ANSWER_NOT_FOUND);
			}
		}catch (Exception e) {
			logger.error(AppConstants.LOG_FORMAT, ResponseConstants.ANSWER_NOT_UPDATED, e);
			return responseUtil.generateResponse(dataMap, ResponseConstants.ANSWER_NOT_UPDATED);
		}
	}

	@Override
	public ResponseEntity<Object> deleteAnswer(HttpServletRequest request, AnswerDTO answerDTO) {
		Map<String, Object> dataMap = new HashMap<>();
		try {
			Optional<Answer> answerOptional = findAnswerById(answerDTO.getAnswerId());
			if(answerOptional.isPresent()) {
				var answer = answerOptional.get();
				answer.setActive(false);
				saveAnswer(answer);
				return responseUtil.generateResponse(dataMap, ResponseConstants.ANSWER_DELETED);
			}else {
				return responseUtil.generateResponse(dataMap, ResponseConstants.ANSWER_NOT_FOUND);
			}
		}catch (Exception e) {
			logger.error(AppConstants.LOG_FORMAT, ResponseConstants.ANSWER_NOT_DELETED, e);
			return responseUtil.generateResponse(dataMap, ResponseConstants.ANSWER_NOT_DELETED);
		}
	}


	@Override
	public ResponseEntity<Object> getAllAnswers(HttpServletRequest request, String userId, String questionUrlPath) {
		Map<String, Object> dataMap = new HashMap<>();
		Optional<Question> questionOptional = findByCreatedByAndUrlPathAndActive(userId, questionUrlPath, true);
		if(questionOptional.isPresent()) {
			Set<Answer> answers = questionOptional.get().getAnswers();			
			dataMap.put(DataConstants.ANSWER_LIST, toAnswerDTOSet(answers));
			return responseUtil.generateResponse(dataMap, ResponseConstants.ANSWER_LIST_FOUND);
		}else {
			return responseUtil.generateResponse(dataMap, ResponseConstants.QUESTION_NOT_FOUND);
		}	
	}

}
