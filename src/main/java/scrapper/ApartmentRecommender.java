package scrapper;

import org.springframework.stereotype.Component;

import static java.lang.String.format;
import static scrapper.RecommenderResponseBuilder.responseWith;

@Component
public class ApartmentRecommender {

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

    private String[] excludedRegions() {
        return new String[] {
                "Borča",
                "Altina",
                "Višnjička Banja",
                "Rakovica"
        };
    }

}
