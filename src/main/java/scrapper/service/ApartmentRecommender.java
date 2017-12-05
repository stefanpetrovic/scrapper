package scrapper.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import scrapper.model.Apartment;
import scrapper.model.ForbiddenAddress;
import scrapper.model.RecommendationResponse;
import scrapper.model.RecommenderConfig;
import scrapper.repo.ForbiddenAddressRepository;

import java.util.List;

import static java.lang.String.format;
import static java.util.stream.Collectors.toList;
import static scrapper.model.RecommenderResponseBuilder.responseWith;

@Component
public class ApartmentRecommender {

    @Autowired
    private ForbiddenAddressRepository repository;

    @Autowired
    private RecommenderConfigService recommenderConfigService;

    public RecommendationResponse analyzeApartment(Apartment apartment) {
        Double price = apartment.getPrice();
        Double area = apartment.getArea();
        String address = apartment.getAddress();

        RecommenderConfig config = recommenderConfigService.get();

        if (price == null || area == null || address == null) {
            return responseWith().recommended(false).message("Price, area or address is null").build();
        }

        if (price > config.getMaxPrice() || price < config.getMinPrice()) {
            String message = format("Price is greater than %s or smaller than %s, price: %s", config.getMaxPrice(), config.getMinPrice(), price);
            return responseWith().recommended(false).message(message).build();
        }

        if (area > config.getMaxArea() || area < config.getMinArea()) {
            String message = format("Area is greater than %s or smaller than %s square meters, area: %s", config.getMaxArea(), config.getMinArea(), area);
            return responseWith().recommended(false).message(message).build();
        }

        for (String region : excludedRegions()) {
            if (address.contains(region)) {
                String message = format("Area is forbidden, [address: %s, region: %s]", address, region);
                return responseWith().recommended(false).message(message).build();
            }
        }

        Double pricePerSquareMeter = price / area;

        if (pricePerSquareMeter > config.getMaxPriceOfSquareMeter()) {
            String message = format("Price per square meter is too high: [price: %s, allowed: %s", pricePerSquareMeter, config.getMaxPriceOfSquareMeter());
            return responseWith().recommended(false).message(message).build();
        }

        return responseWith().recommended(true).build();
    }

    private List<String> excludedRegions() {
        List<ForbiddenAddress> forbiddenAddresses = repository.findAll();

        return forbiddenAddresses.stream().map(ForbiddenAddress::getAddress).collect(toList());
    }

}
