package scrapper.service;

import scrapper.model.RecommenderConfig;
import scrapper.processor.ProcessingMode;

import java.util.List;

public interface RecommenderConfigService {

    RecommenderConfig get(ProcessingMode processingMode);

    List<RecommenderConfig> getAll();

    RecommenderConfig save(RecommenderConfig recommenderConfig);
}
