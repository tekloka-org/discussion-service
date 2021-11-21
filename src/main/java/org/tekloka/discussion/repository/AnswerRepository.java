package org.tekloka.discussion.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.tekloka.discussion.document.Answer;

@Repository
public interface AnswerRepository extends MongoRepository<Answer, String>{

}
