package scrapper;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.List;

public abstract class ApartmentExtractorTemplate {

    public final Apartment extractApartment(Element element) {
        //String priceStr = element.select("div.central-feature span i").html();
        String priceStr = extractPriceString(element);

        Double price = extractPrice(priceStr);

        if (price > 10000){
            String url = extractUrl(element);

            String description = extractDescription(element);

            String address = extractAddress(element);

            Double area = extractArea(element);

            Double rooms = extractRooms(element);

            String externalId = extractExternalId(element);

            Apartment apartment = new Apartment();
            apartment.setPrice(price);
            apartment.setUrl(url);
            apartment.setDescription(description);
            apartment.setAddress(address);
            apartment.setArea(area);
            apartment.setNoOfRooms(rooms);
            apartment.setSource(getSource());
            apartment.setExternalId(externalId);

            return apartment;
        }

        return null;
    }

    public abstract Document fetchApartmentsPage(int pageNum);

    public abstract List<Apartment> extractApartmentsElements(Document document);

    protected abstract String extractPriceString(Element element);

    protected abstract Double extractPrice(String rawPrice);

    protected abstract String extractUrl(Element element);

    protected abstract String extractDescription(Element element);

    protected abstract String extractAddress(Element element);

    protected abstract Double extractArea(Element element);

    protected abstract Double extractRooms(Element element);

    protected abstract String extractExternalId(Element element);

    protected abstract ApartmentSource getSource();

}
