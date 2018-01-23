package scrapper.processor.fetcher.rest;

import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.TrustStrategy;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import scrapper.model.CityExpertApartment;
import scrapper.model.CityExpertResponse;
import scrapper.model.RESTResponse;
import scrapper.processor.ProcessingMode;

import javax.net.ssl.SSLContext;
import java.security.cert.X509Certificate;

public class CityExpertApartmentFetcher implements RESTApartmentFetcher<CityExpertApartment> {

    private static final int PAGE_SIZE = 20;

    private ProcessingMode processingMode;

    public CityExpertApartmentFetcher(ProcessingMode processingMode) {
        this.processingMode = processingMode;
    }

    @Override
    public RESTResponse<CityExpertApartment> fetchApartments(int pageNumber) {
        if (processingMode == ProcessingMode.PRODAJA) {
            throw new RuntimeException("Processing Mode PRODAJA not supported.");
        }

        TrustStrategy acceptingTrustStrategy = (X509Certificate[] chain, String authType) -> true;

        SSLContext sslContext = null;
        try {
            sslContext = org.apache.http.ssl.SSLContexts.custom()
                    .loadTrustMaterial(null, acceptingTrustStrategy)
                    .build();

            SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext);

            CloseableHttpClient httpClient = HttpClients.custom()
                    .setSSLSocketFactory(csf)
                    .build();

            HttpComponentsClientHttpRequestFactory requestFactory =
                    new HttpComponentsClientHttpRequestFactory();

            requestFactory.setHttpClient(httpClient);
            RestTemplate restTemplate = new RestTemplate(requestFactory);

            return restTemplate.exchange("https://cityexpert.rs/api/Search", HttpMethod.POST, request(pageNumber), CityExpertResponse.class).getBody();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private HttpEntity<String> request(int pageNumber) {
        HttpHeaders httpHeaders = new HttpHeaders();

        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        return new HttpEntity<>(getRequestAsJsonString(pageNumber), httpHeaders);
    }

    private String getRequestAsJsonString(int pageNumber) {
        return "{\n" +
                "  \"ptId\": [\n" +
                "    1\n" +
                "  ],\n" +
                "  \"cityId\": 1,\n" +
                "  \"rentOrSale\": \"r\",\n" +
                "  \"currentPage\": " + pageNumber + 1 + ",\n" +
                "  \"resultsPerPage\": " + PAGE_SIZE + ",\n" +
                "  \"floor\": [],\n" +
                "  \"avFrom\": false,\n" +
                "  \"furnished\": [],\n" +
                "  \"furnishingArray\": [],\n" +
                "  \"heatingArray\": [],\n" +
                "  \"parkingArray\": [],\n" +
                "  \"petsArray\": [],\n" +
                "  \n" +
                "  \"polygonsArray\": [],\n" +
                "  \"searchSource\": \"regular\",\n" +
                "  \"sort\": \"datedsc\",\n" +
                "  \"structure\": [],\n" +
                "  \"filed\": [],\n" +
                "  \"ceiling\": [],\n" +
                "  \"bldgOptsArray\": [],\n" +
                "  \"joineryArray\": [],\n" +
                "  \"yearOfConstruction\": [],\n" +
                "  \"otherArray\": [],\n" +
                "  \n" +
                "  \"site\": \"SR\"\n" +
                "}";
    }
}
