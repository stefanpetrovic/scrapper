package scrapper.extractor;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import scrapper.model.ApartmentSource;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static scrapper.model.ApartmentSource.NEKRETNINE_RS;

@Component
public class NekretnineRSApartmentExtractor extends ApartmentExtractorTemplate {

    private static final Logger log = LoggerFactory.getLogger(NekretnineRSApartmentExtractor.class);

    @Override
    public Document fetchApartmentsPage(int pageNum) {
        RestTemplate restTemplate = new RestTemplate();

        String page = restTemplate.getForObject("http://www.nekretnine.rs/stambeni-objekti/stanovi/izdavanje-prodaja/prodaja/grad/beograd/cena/10000_100000/poslednja/7/samo-sa-slikom/poredjaj-po/datumu_nanize/lista/po_stranici/20/stranica/{pageNum}", String.class, pageNum);

        log.info("Fetched page");

        Document doc =Jsoup.parse(page);

        File f = new File("test.html");

        try (FileWriter fileWriter = new FileWriter(f)){
            fileWriter.write(doc.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return doc;
    }

    @Override
    protected Elements getApartmentsElements(Document document) {
        return document.select(".resultList");
    }

    @Override
    protected String extractPriceString(Element element) {
        String rawPrice = element.select(".resultListPrice").html();

        int index = rawPrice.indexOf("m2");
        int index2 = rawPrice.indexOf("EUR");

        return rawPrice.substring(index + 3, index2).trim();
    }

    @Override
    protected Double extractPrice(String rawPrice) {
        return Double.parseDouble(rawPrice) * 1000;
    }

    @Override
    protected String extractUrl(Element element) {
        return element.getElementsByClass("resultImg").get(0).attr("href");
    }

    @Override
    protected String extractDescription(Element element) {
        return element.getElementsByClass("marginB_5").get(0).getElementsByTag("a").attr("title");
    }

    @Override
    protected String extractAddress(Element element) {
        return element.getElementsByClass("resultData").get(0).html();
    }

    @Override
    protected Double extractArea(Element element) {
        String rawArea = element.select(".resultListPrice").html();

        int index = rawArea.indexOf("m2");

        return Double.parseDouble(rawArea.substring(0, index));
    }

    @Override
    protected Double extractRooms(Element element) {
        return null;
    }

    @Override
    protected String extractExternalId(Element element) {
        return element.getElementsByClass("resultList").get(0).id();
    }

    @Override
    protected ApartmentSource getSource() {
        return NEKRETNINE_RS;
    }
}
