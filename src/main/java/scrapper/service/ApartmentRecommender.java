package scrapper.service;

import scrapper.model.Apartment;
import scrapper.model.RecommendationResponse;
import scrapper.model.RecommenderConfig;

import java.util.List;

import static java.lang.String.format;
import static scrapper.model.RecommenderResponseBuilder.responseWith;

public class ApartmentRecommender {

    private List<String> excludedRegions;
    private RecommenderConfig config;

    public ApartmentRecommender(List<String> excludedRegions, RecommenderConfig recommenderConfig) {
        this.excludedRegions = excludedRegions;
        this.config = recommenderConfig;
    }

    public RecommendationResponse analyzeApartment(Apartment apartment) {
        Double price = apartment.getPrice();
        Double area = apartment.getArea();
        String address = apartment.getAddress();

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

        for (String region : excludedRegions) {
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

}
