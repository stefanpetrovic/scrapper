package scrapper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class Scrapper {

    public static void main(String[] args) {
        RestTemplate restTemplate = new RestTemplate();

        String page = restTemplate.getForObject("https://www.halooglasi.com/nekretnine/prodaja-stanova/beograd?cena_d_to=60000&cena_d_unit=4&kvadratura_d_from=60&kvadratura_d_unit=1", String.class);

        Document doc = Jsoup.parse(page);

        System.out.println(doc.charset());

        Elements elems = doc.getElementsByClass("product-item");

        System.out.println(elems.size());
    }

}
