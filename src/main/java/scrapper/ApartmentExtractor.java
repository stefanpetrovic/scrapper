package scrapper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.springframework.util.StringUtils.isEmpty;
import static scrapper.ApartmentSource.HALO_OGLASI;

@Component
public class ApartmentExtractor {

    private static final Logger log = LoggerFactory.getLogger(ApartmentExtractor.class);
    private static final String HALO_OGLASI_URL_PREFIX = "https://www.halooglasi.com";
    private static final String PAGE_PARAM = "&page={page}";

    public Document fetchApartmentsPage(int pageNum) {
        RestTemplate restTemplate = new RestTemplate();

        String page = restTemplate.getForObject("https://www.halooglasi.com/nekretnine/prodaja-stanova/beograd?cena_d_to=60000&cena_d_unit=4&kvadratura_d_from=60&kvadratura_d_unit=1&page={page}", String.class, pageNum);

        log.info("Fetched page");

        return Jsoup.parse(page);
    }

    public Apartment extractApartment(Element element) {
        String priceStr = element.select("div.central-feature span i").html();

        Double price = priceConverter(priceExtractor(priceStr));

        if (price > 10000){
            String url = urlExtractor(element);

            String description = descriptionExtractor(element);

            String address = addressExtractor(element);

            Double area = areaExtractor(element);

            Double rooms = roomExtractor(element);

            String externalId = externalIdExtractor(element);

            Apartment apartment = new Apartment();
            apartment.setPrice(price);
            apartment.setUrl(HALO_OGLASI_URL_PREFIX + url);
            apartment.setDescription(description);
            apartment.setAddress(address);
            apartment.setArea(area);
            apartment.setNoOfRooms(rooms);
            apartment.setSource(HALO_OGLASI);
            apartment.setExternalId(externalId);

            return apartment;
        }

        return null;
    }

    public List<Apartment> extractApartmentsElements(Document document) {
        Elements apartments = document.select(getAppartmentSelector());

        Iterator<Element> apartmentIterator = apartments.iterator();

        List<Apartment> apartmentList = new ArrayList<>();

        while (apartmentIterator.hasNext()) {
            Element el = apartmentIterator.next();

            Apartment apartment = extractApartment(el);

            if (apartment != null) {
                log.debug("Extracted apartment: [externalId: {}, url: {}]", apartment.getExternalId(), apartment.getUrl());
                apartmentList.add(apartment);
            }
        }

        log.info("Extracted {} apartments", apartmentList.size());

        return apartmentList;
    }

    private static String getAppartmentSelector() {
        return ".product-item.product-list-item";
    }

    private static String externalIdExtractor(Element element) {
        return element.attr("data-id");
    }

    private static String priceExtractor(String rawPrice) {
        int index = rawPrice.indexOf("nbsp");

        if (index > 0) {
            return rawPrice.substring(0, index - 1) + ",00";
        }

        return "";
    }

    private static Double priceConverter(String rawPrice) {
        if (isEmpty(rawPrice)) {
            return 0.0;
        }

        try {
            return Double.parseDouble(rawPrice);
        } catch (NumberFormatException e) {
            try {
                String[] parts = rawPrice.split(",");
                return Double.parseDouble(parts[0]) * 1000 + Double.parseDouble(parts[1]);
            } catch (Exception ex) {
                log.warn("Fatal error:", ex);
                return 0.0;
            }
        }
    }

    private static String urlExtractor(Element appartmentElement) {
        return appartmentElement.select("figure.pi-img-wrapper a").attr("href");
    }

    private static String descriptionExtractor(Element appartmentElement) {
        return appartmentElement.select(".text-description-list.ad-description.short-desc").html();
    }

    private static String addressExtractor(Element appartmentElement) {
        return appartmentElement.select(".subtitle-places").html();
    }

    private static Double areaExtractor(Element appartmentElement) {
        String areaRaw = appartmentElement.select(".ad-features li:eq(1) .value-wrapper").html();

        String areaStr = areaRaw.substring(0, areaRaw.indexOf("nbsp") - 1);
        try {
            return Double.parseDouble(areaStr);
        } catch (NumberFormatException e) {
            try {
                String[] parts = areaStr.split(",");
                return Double.parseDouble(parts[0]) + Double.parseDouble(parts[1]);
            } catch (Exception ex) {
                log.warn("Error parsing area:", ex);
                return 0.0;
            }
        } catch (Exception ex) {
            log.warn("Error extracting area:", ex);
            return 0.0;
        }
    }

    private static Double roomExtractor(Element appartmentElement) {
        String roomRaw = appartmentElement.select(".ad-features li:eq(2) .value-wrapper").html();

        String roomStr = roomRaw.substring(0, roomRaw.indexOf("nbsp") - 1);

        try {
            return Double.parseDouble(roomStr);
        } catch (NumberFormatException e) {
            try {
                String[] parts = roomStr.split(",");
                return Double.parseDouble(parts[0]) + Double.parseDouble(parts[1]);
            } catch (Exception ex) {
                log.warn("Error parsing rooms:", ex);
                return 0.0;
            }
        } catch (Exception ex) {
            log.warn("Error extracting rooms:", ex);
            return 0.0;
        }
    }
}
