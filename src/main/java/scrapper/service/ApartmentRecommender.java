package scrapper.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import scrapper.model.Apartment;
import scrapper.model.ForbiddenAddress;
import scrapper.model.RecommendationResponse;
import scrapper.repo.ForbiddenAddressRepository;

import java.util.List;

import static java.lang.String.format;
import static java.util.stream.Collectors.toList;
import static scrapper.model.RecommenderResponseBuilder.responseWith;

@Component
public class ApartmentRecommender {

    @Autowired
    private ForbiddenAddressRepository repository;

    public RecommendationResponse analyzeApartment(Apartment apartment) {
        Double price = apartment.getPrice();
        Double area = apartment.getArea();
        String address = apartment.getAddress();

        if (price == null || area == null || address == null) {
            return responseWith().recommended(false).message("Price, area or address is null").build();
        }

        if (price > 100000 || price < 10000) {
            String message = format("Price is greater than 100000 or smaller than 10000, price: %s", price);
            return responseWith().recommended(false).message(message).build();
        }

        if (area > 100 || area < 20) {
            String message = format("Area is greater than 100 or smaller than 20 square meters, area: %s", area);
            return responseWith().recommended(false).message(message).build();
        }

        for (String region : excludedRegions()) {
            if (address.contains(region)) {
                String message = format("Area is forbidden, [address: %s, region: %s]", address, region);
                return responseWith().recommended(false).message(message).build();
            }
        }

        Double pricePerSquareMeter = price / area;

        if (pricePerSquareMeter > 1200) {
            String message = format("Price per square meter is too high: [price: %s, allowed: 1200", pricePerSquareMeter);
            return responseWith().recommended(false).message(message).build();
        }

        return responseWith().recommended(true).build();
    }

    private List<String> excludedRegions() {
        List<ForbiddenAddress> forbiddenAddresses = repository.findAll();

        return forbiddenAddresses.stream().map(ForbiddenAddress::getAddress).collect(toList());
    }

}
