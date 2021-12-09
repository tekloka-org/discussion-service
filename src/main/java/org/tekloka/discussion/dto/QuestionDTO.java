package org.tekloka.discussion.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestionDTO {
	
	private String questionId;
	private String summary;
	private String description;
	private String authorId;
	private String urlPath;
	private String authorName;
	private String createdOn;
	private String modifiedOn;
}
