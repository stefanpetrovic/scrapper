package scrapper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.client.RestTemplate;

import java.util.Iterator;

@SpringBootApplication
public class Scrapper {

    private static final Logger log = LoggerFactory.getLogger(Scrapper.class);

    public static void main(String[] args) {
        RestTemplate restTemplate = new RestTemplate();

        String page = restTemplate.getForObject("https://www.halooglasi.com/nekretnine/prodaja-stanova/beograd?cena_d_to=60000&cena_d_unit=4&kvadratura_d_from=60&kvadratura_d_unit=1", String.class);

        System.out.println("Fetched page");

        Document doc = Jsoup.parse(page);

        doc.getElementsByTag("script").remove();

        Elements elems = doc.getElementsByAttribute("data-value");

        Iterator<Element> iterable = elems.iterator();
        while (iterable.hasNext()) {

            Element el = iterable.next();
            try {
                Double price = Double.parseDouble(el.attr("data-value"));
            } catch (NumberFormatException ex) {
                log.info("Failed to convert price: ", ex.getMessage());
                log.info("Element: {}", el);
            }
            System.out.println();
        }


    }

}
