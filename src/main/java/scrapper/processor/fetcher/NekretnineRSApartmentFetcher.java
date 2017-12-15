package scrapper.processor.fetcher;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;
import scrapper.processor.ProcessingMode;

import static scrapper.processor.ProcessingMode.PRODAJA;

public class NekretnineRSApartmentFetcher extends ApartmentFetcher {

    private static final Logger log = LoggerFactory.getLogger(NekretnineRSApartmentFetcher.class);

    private static final String PRODAJA_URL_TEMPLATE = "https://www.nekretnine.rs/stambeni-objekti/stanovi/izdavanje-prodaja/prodaja/grad/beograd/cena/10000_100000/poslednja/7/samo-sa-slikom/poredjaj-po/datumu_nanize/lista/po_stranici/20/stranica/{pageNum}";
    private static final String IZDAVANJE_URL_TEMPLATE = "https://www.nekretnine.rs/stambeni-objekti/stanovi/izdavanje-prodaja/izdavanje/grad/beograd/cena/10000_100000/poslednja/7/samo-sa-slikom/poredjaj-po/datumu_nanize/lista/po_stranici/20/stranica/{pageNum}";

    private final ProcessingMode processingMode;

    public NekretnineRSApartmentFetcher(ProcessingMode processingMode) {
        this.processingMode = processingMode;
    }

    @Override
    public Document fetchApartments(int pageNumber) {
        RestTemplate restTemplate = new RestTemplate();

        String url = processingMode.equals(PRODAJA) ? PRODAJA_URL_TEMPLATE : IZDAVANJE_URL_TEMPLATE;

        String page = restTemplate.getForObject(url, String.class, pageNumber);

        log.debug("Fetched Nekretnine.rs page");

        return Jsoup.parse(page);
    }
}
