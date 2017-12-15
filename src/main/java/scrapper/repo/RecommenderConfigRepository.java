package scrapper.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import scrapper.model.RecommenderConfig;
import scrapper.processor.ProcessingMode;

@Repository
public interface RecommenderConfigRepository extends MongoRepository<RecommenderConfig, String> {

    RecommenderConfig findFirstByProcessingMode(ProcessingMode processingMode);
}
