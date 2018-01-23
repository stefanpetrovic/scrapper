package scrapper.processor.fetcher.rest;

import scrapper.model.RESTResponse;
import scrapper.processor.fetcher.ApartmentFetcher;

public interface RESTApartmentFetcher<T> extends ApartmentFetcher<RESTResponse<T>> {

    RESTResponse<T> fetchApartments(int pageNumber);
}
