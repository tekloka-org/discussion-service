package org.tekloka.discussion.repository;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.tekloka.discussion.document.Question;

@Repository
public interface QuestionRepository extends MongoRepository<Question, String>{
	
	Set<Question> findByActive(boolean active);
	
	Optional<Question> findByCreatedByAndUrlPathAndActive(String createdBy, String urlPath, boolean active);
	
}

	
	