package scrapper.processor.extractor;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import scrapper.model.ApartmentSource;
import scrapper.processor.ProcessingMode;

import static scrapper.model.ApartmentSource.NEKRETNINE_RS;

public class NekretnineRSApartmentExtractor extends ApartmentExtractorTemplate {

    private final ProcessingMode processingMode;

    public NekretnineRSApartmentExtractor(ProcessingMode processingMode) {
        this.processingMode = processingMode;
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

    @Override
    protected ProcessingMode getProcessingMode() {
        return processingMode;
    }
}
