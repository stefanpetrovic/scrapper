package scrapper.analyzer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import scrapper.model.Apartment;
import scrapper.model.ForbiddenAddress;
import scrapper.model.RecommendationResponse;
import scrapper.model.RecommenderConfig;
import scrapper.processor.ProcessingMode;
import scrapper.repo.ForbiddenAddressRepository;
import scrapper.service.ApartmentRecommender;
import scrapper.service.ApartmentStorage;
import scrapper.service.RecommenderConfigService;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ApartmentAnalyzer {

    @Autowired
    private ApartmentStorage apartmentStorage;

    @Autowired
    private ForbiddenAddressRepository repository;

    @Autowired
    private RecommenderConfigService recommenderConfigService;

    public List<Apartment> analyzeApartments(List<Apartment> apartments, ProcessingMode processingMode) {
        ApartmentRecommender apartmentRecommender = getApartmentRecommender(processingMode);

        for (Apartment a : apartments) {
            RecommendationResponse response = apartmentRecommender.analyzeApartment(a);

            boolean isRecommended = response.isRecommended();
            a.setRecommended(isRecommended);
            a.setMessage(response.getMessage());
        }

        return apartmentStorage.storeApartments(apartments);
    }

    private ApartmentRecommender getApartmentRecommender(ProcessingMode processingMode) {
        List<String> forbiddenAddresses = repository.findAll().stream().map(ForbiddenAddress::getAddress).collect(Collectors.toList());
        RecommenderConfig recommenderConfig = recommenderConfigService.get(processingMode);

        return new ApartmentRecommender(forbiddenAddresses, recommenderConfig);
    }
}
