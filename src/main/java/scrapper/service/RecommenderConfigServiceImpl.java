package scrapper.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import scrapper.model.RecommenderConfig;
import scrapper.processor.ProcessingMode;
import scrapper.repo.RecommenderConfigRepository;

import java.util.List;

@Service
public class RecommenderConfigServiceImpl implements RecommenderConfigService {

    @Autowired
    private RecommenderConfigRepository repository;

    @Override
    public RecommenderConfig get(ProcessingMode processingMode) {
        return repository.findFirstByProcessingMode(processingMode);
    }

    @Override
    public List<RecommenderConfig> getAll() {
        return repository.findAll();
    }

    @Override
    @Transactional
    public RecommenderConfig save(RecommenderConfig recommenderConfig) {
        RecommenderConfig dbRecommenderConfig = repository.findFirstByProcessingMode(recommenderConfig.getProcessingMode());

        if (dbRecommenderConfig == null) {
            return repository.save(recommenderConfig);
        }

        dbRecommenderConfig.setMinPrice(recommenderConfig.getMinPrice());
        dbRecommenderConfig.setMaxPrice(recommenderConfig.getMaxPrice());
        dbRecommenderConfig.setMinArea(recommenderConfig.getMinArea());
        dbRecommenderConfig.setMaxArea(recommenderConfig.getMaxArea());
        dbRecommenderConfig.setMaxPriceOfSquareMeter(recommenderConfig.getMaxPriceOfSquareMeter());

        return repository.save(dbRecommenderConfig);
    }
}
