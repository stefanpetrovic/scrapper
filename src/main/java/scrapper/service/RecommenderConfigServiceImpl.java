package scrapper.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import scrapper.model.RecommenderConfig;
import scrapper.repo.RecommenderConfigRepository;

@Service
public class RecommenderConfigServiceImpl implements RecommenderConfigService {

    @Autowired
    private RecommenderConfigRepository repository;

    @Override
    public RecommenderConfig get() {
        return repository.findFirstBy();
    }

    @Override
    @Transactional
    public RecommenderConfig save(RecommenderConfig recommenderConfig) {
        RecommenderConfig dbRecommenderConfig = repository.findFirstBy();

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
