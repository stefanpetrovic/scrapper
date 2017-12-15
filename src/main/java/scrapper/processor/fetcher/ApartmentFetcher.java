package scrapper.processor.fetcher;

import org.jsoup.nodes.Document;

public abstract class ApartmentFetcher {

    public abstract Document fetchApartments(int pageNumber);
}
