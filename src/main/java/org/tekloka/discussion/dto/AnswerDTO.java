package org.tekloka.discussion.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnswerDTO{

	private String answerId;
	private String description;
	private String questionId;
	private String authorId;
	
}
