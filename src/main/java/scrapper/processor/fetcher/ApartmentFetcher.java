package scrapper.processor.fetcher;

public interface ApartmentFetcher<T> {

    T fetchApartments(int pageNumber);
}
