package org.tekloka.discussion.document;

import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document(collection = "questions")
public class Question extends AuditMetadata{

	@Id
	private String questionId;
	private String summary;
	private String description;
	private String urlPath;
	private boolean active;
	private boolean approved;
	
	@DBRef(lazy = true)
	private Set<Answer> answers; 
	
}
