package scrapper.processor.extractor.rest;

import scrapper.model.Apartment;
import scrapper.model.RESTResponse;
import scrapper.processor.extractor.ApartmentExtractor;

import java.util.List;

public interface RESTApartmentExtractor<T> extends ApartmentExtractor<RESTResponse<T>> {

    @Override
    List<Apartment> extractApartmentsElements(RESTResponse<T> source);
}
