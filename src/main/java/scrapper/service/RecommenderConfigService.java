package scrapper.service;

import scrapper.model.RecommenderConfig;

public interface RecommenderConfigService {

    RecommenderConfig get();

    RecommenderConfig save(RecommenderConfig recommenderConfig);
}
