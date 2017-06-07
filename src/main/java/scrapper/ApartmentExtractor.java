package scrapper;

import freemarker.template.TemplateException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import javax.mail.MessagingException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class ApartmentExtractor {

    private static final Logger log = LoggerFactory.getLogger(ApartmentExtractor.class);
    private static final String HALO_OGLASI_URL_PREFIX = "https://www.halooglasi.com";

    private Document fetchApartmentsPage() {
        RestTemplate restTemplate = new RestTemplate();

        String page = restTemplate.getForObject("https://www.halooglasi.com/nekretnine/prodaja-stanova/beograd?cena_d_to=60000&cena_d_unit=4&kvadratura_d_from=60&kvadratura_d_unit=1", String.class);

        log.info("Fetched page");

        return Jsoup.parse(page);
    }

    public void fetchApartments() {
        RestTemplate restTemplate = new RestTemplate();

        String page = restTemplate.getForObject("https://www.halooglasi.com/nekretnine/prodaja-stanova/beograd?cena_d_to=60000&cena_d_unit=4&kvadratura_d_from=60&kvadratura_d_unit=1", String.class);

        System.out.println("Fetched page");

        Document doc = Jsoup.parse(page);

        Elements apartments = doc.select(getAppartmentSelector());

        Iterator<Element> apartmentIterator = apartments.iterator();

        List<Appartment> apartmentList = new ArrayList<>();

        while (apartmentIterator.hasNext()) {
            Element el = apartmentIterator.next();

            String priceStr =el.select("div.central-feature span i").html();


            Double price = priceConverter(priceExtractor(priceStr));

            if (StringUtils.isEmpty(price) || price <= 10000){
                continue;
            }

            String url = urlExtractor(el);

            String description = descriptionExtractor(el);

            String address = addressExtractor(el);

            Double area = areaExtractor(el);

            Double rooms = roomExtractor(el);


            Appartment appartment = new Appartment();
            appartment.setPrice(price);
            appartment.setUrl(HALO_OGLASI_URL_PREFIX + url);
            appartment.setDescription(description);
            appartment.setAddress(address);
            appartment.setArea(area);
            appartment.setNoOfRooms(rooms);

            apartmentList.add(appartment);

        }

        try {
            sendEmail(apartmentList);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        //Document document = Jsoup.parseBodyFragment(appartments.toString());


        //printHtmlToFile("app.html", document);


    }


    private static String getAppartmentSelector() {
        return ".product-item.product-list-item";
    }

    private static void printHtmlToFile(String filename, Document document) {
        File file = new File(filename);

        try (FileWriter fos = new FileWriter(file)){
            fos.write(document.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String priceExtractor(String rawPrice) {
        int index = rawPrice.indexOf("nbsp");

        if (index > 0) {
            return rawPrice.substring(0, index - 1) + ",00";
        }

        return "";
    }

    private static Double priceConverter(String rawPrice) {
        if (StringUtils.isEmpty(rawPrice)) {
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
