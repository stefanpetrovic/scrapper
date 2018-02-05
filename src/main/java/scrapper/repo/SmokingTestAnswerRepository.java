package scrapper.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import scrapper.model.SmokingTestAnswer;

@Repository
public interface SmokingTestAnswerRepository extends MongoRepository<SmokingTestAnswer, String> {
}
