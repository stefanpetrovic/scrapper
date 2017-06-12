package scrapper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class NekretnineRSApartmentExtractor extends ApartmentExtractorTemplate {

    private static final Logger log = LoggerFactory.getLogger(NekretnineRSApartmentExtractor.class);

    @Override
    public Document fetchApartmentsPage(int pageNum) {
        RestTemplate restTemplate = new RestTemplate();

        String page = restTemplate.getForObject("http://www.nekretnine.rs/stambeni-objekti/stanovi/izdavanje-prodaja/prodaja/grad/beograd/cena/10000_100000/poslednja/7/samo-sa-slikom/lista/po_stranici/50/stranica/{stranica}", String.class, pageNum);

        log.info("Fetched page");

        return Jsoup.parse(page);
    }

    @Override
    public List<Apartment> extractApartmentsElements(Document document) {
        return null;
    }

    @Override
    protected String extractPriceString(Element element) {
        return null;
    }

    @Override
    protected Double extractPrice(String rawPrice) {
        return null;
    }

    @Override
    protected String extractUrl(Element element) {
        return null;
    }

    @Override
    protected String extractDescription(Element element) {
        return null;
    }

    @Override
    protected String extractAddress(Element element) {
        return null;
    }

    @Override
    protected Double extractArea(Element element) {
        return null;
    }

    @Override
    protected Double extractRooms(Element element) {
        return null;
    }

    @Override
    protected String extractExternalId(Element element) {
        return null;
    }

    @Override
    protected ApartmentSource getSource() {
        return null;
    }
}
