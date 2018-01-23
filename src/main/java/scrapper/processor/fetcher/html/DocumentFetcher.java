package scrapper.processor.fetcher.html;

import org.jsoup.nodes.Document;
import scrapper.processor.fetcher.ApartmentFetcher;

public interface DocumentFetcher extends ApartmentFetcher<Document> {

    @Override
    Document fetchApartments(int pageSize);
}
