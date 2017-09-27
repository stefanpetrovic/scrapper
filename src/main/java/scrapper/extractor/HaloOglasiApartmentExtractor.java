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

import static org.springframework.util.StringUtils.isEmpty;

@Component
public class HaloOglasiApartmentExtractor extends ApartmentExtractorTemplate {

    private static final Logger log = LoggerFactory.getLogger(HaloOglasiApartmentExtractor.class);
    private static final String HALO_OGLASI_URL_PREFIX = "https://www.halooglasi.com";

    @Override
    public Document fetchApartmentsPage(int pageNum) {
        RestTemplate restTemplate = new RestTemplate();

        String page = restTemplate.getForObject("https://www.halooglasi.com/nekretnine/prodaja-stanova/beograd?cena_d_to=60000&cena_d_unit=4&kvadratura_d_from=60&kvadratura_d_unit=1&page={page}", String.class, pageNum);

        log.debug("Fetched Hallo oglasi page");

        return Jsoup.parse(page);
    }

    @Override
    protected Elements getApartmentsElements(Document document) {
        return document.select(".product-item.product-list-item");
    }

    @Override
    protected String extractPriceString(Element element) {
        return element.select("div.central-feature span i").html();
    }

    @Override
    protected Double extractPrice(String rawPrice) {
        String priceString = "";
        int index = rawPrice.indexOf("nbsp");

        if (index > 0) {
            priceString = rawPrice.substring(0, index - 1) + ",00";
        }

        if (isEmpty(priceString)) {
            return 0.0;
        }

        try {
            return Double.parseDouble(priceString);
        } catch (NumberFormatException e) {
            try {
                String[] parts = priceString.split(",");
                return Double.parseDouble(parts[0]) * 1000 + Double.parseDouble(parts[1]);
            } catch (Exception ex) {
                log.warn("Fatal error:", ex);
                return 0.0;
            }
        }
    }

    @Override
    protected String extractUrl(Element element) {
        return HALO_OGLASI_URL_PREFIX + element.select("figure.pi-img-wrapper a").attr("href");
    }

    @Override
    protected String extractDescription(Element element) {
        return element.select(".text-description-list.ad-description.short-desc").html();
    }

    @Override
    protected String extractAddress(Element element) {
        return element.select(".subtitle-places").html();
    }

    @Override
    protected Double extractArea(Element element) {
        String areaRaw = element.select(".ad-features li:eq(1) .value-wrapper").html();

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

    @Override
    protected Double extractRooms(Element element) {
        String roomRaw = element.select(".ad-features li:eq(2) .value-wrapper").html();

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

    @Override
    protected String extractExternalId(Element element) {
        return element.attr("data-id");
    }

    @Override
    protected ApartmentSource getSource() {
        return ApartmentSource.HALO_OGLASI;
    }
}
