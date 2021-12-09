package org.tekloka.discussion.dto.mapper;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;
import org.tekloka.discussion.document.Answer;
import org.tekloka.discussion.document.Question;
import org.tekloka.discussion.dto.AnswerDTO;
import org.tekloka.discussion.dto.QuestionDTO;
import org.tekloka.discussion.security.SecurityCache;
import org.tekloka.discussion.util.DataUtil;

@Component
public class QuestionMapper {
	
	private final DataUtil dataUtil;
	private final SecurityCache securityCache;
	
	public QuestionMapper(DataUtil dataUtil, SecurityCache securityCache) {
		this.dataUtil = dataUtil;
		this.securityCache = securityCache;
	}
	
	public Question toQuestion(Optional<Question> questionOptional, QuestionDTO questionDTO) {
		var question = new Question();
		if(questionOptional.isPresent()) {
			question = questionOptional.get();
		}
		question.setSummary(questionDTO.getSummary());
		question.setDescription(questionDTO.getDescription());
		question.setUrlPath(questionDTO.getUrlPath());
		return question;
	}
	
	public QuestionDTO toQuestionDTO(Question question) {
		var questionDTO = new QuestionDTO();
		questionDTO.setQuestionId(question.getQuestionId());
		questionDTO.setSummary(question.getSummary());
		questionDTO.setDescription(question.getDescription());
		questionDTO.setAuthorId(question.getCreatedBy());
		questionDTO.setUrlPath(question.getUrlPath());
		questionDTO.setAuthorName(securityCache.getUserName(question.getCreatedBy()));
		questionDTO.setCreatedOn(dataUtil.convertLocalDateTimeToString(question.getCreatedDate()));
		questionDTO.setModifiedOn(dataUtil.convertLocalDateTimeToString(question.getLastModifiedDate()));
		return questionDTO;
	}
	
	public Set<QuestionDTO> toQuestionDTOSet(Set<Question> questions){
		return questions.stream().map(this::toQuestionDTO).collect(Collectors.toSet());
	}
	
	public Answer toAnswer(Optional<Answer> answerOptional, AnswerDTO answerDTO) {
		var answer = new Answer();
		if(answerOptional.isPresent()) {
			answer = answerOptional.get();
		}
		answer.setDescription(answerDTO.getDescription());
		answer.setQuestionId(answerDTO.getQuestionId());
		return answer;
	}
	
	public AnswerDTO toAnswerDTO(Answer answer) {
		var answerDTO = new AnswerDTO();
		answerDTO.setAnswerId(answer.getAnswerId());
		answerDTO.setDescription(answer.getDescription());
		answerDTO.setQuestionId(answer.getQuestionId());
		answerDTO.setAuthorId(answer.getCreatedBy());
		return answerDTO;
	}
	
	public Set<AnswerDTO> toAnswerDTOSet(Set<Answer> answers){
		if(null != answers) {
			return answers.stream().map(this::toAnswerDTO).collect(Collectors.toSet());
		}else {
			return new HashSet<>();
		}
	}

}
