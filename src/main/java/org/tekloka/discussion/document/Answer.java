package org.tekloka.discussion.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document(collection = "answers")
public class Answer extends AuditMetadata{

	@Id
	private String answerId;
	private String description;
	private String questionId;
	private boolean active;
	
}
