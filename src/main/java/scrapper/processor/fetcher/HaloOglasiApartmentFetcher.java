package scrapper.processor.fetcher;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;
import scrapper.processor.ProcessingMode;

import static scrapper.processor.ProcessingMode.PRODAJA;

public class HaloOglasiApartmentFetcher extends ApartmentFetcher {

    private static final Logger log = LoggerFactory.getLogger(HaloOglasiApartmentFetcher.class);

    private static final String PRODAJA_URL_TEMPLATE = "https://www.halooglasi.com/nekretnine/prodaja-stanova/beograd?cena_d_to=60000&cena_d_unit=4&kvadratura_d_from=60&kvadratura_d_unit=1&page={page}";
    private static final String IZDAVANJE_URL_TEMPLATE = "https://www.halooglasi.com/nekretnine/izdavanje-stanova/beograd?cena_d_to=60000&cena_d_unit=4&kvadratura_d_from=60&kvadratura_d_unit=1&page={page}";

    private final ProcessingMode processingMode;

    public HaloOglasiApartmentFetcher(ProcessingMode processingMode) {
        this.processingMode = processingMode;
    }

    @Override
    public Document fetchApartments(int pageNumber) {
        RestTemplate restTemplate = new RestTemplate();

        String url = processingMode.equals(PRODAJA) ? PRODAJA_URL_TEMPLATE : IZDAVANJE_URL_TEMPLATE;

        String page = restTemplate.getForObject(url, String.class, pageNumber);

        log.debug("Fetched Hallo oglasi page for [{}]", processingMode.name());

        return Jsoup.parse(page);
    }
}
