package scrapper.processor.extractor;

import scrapper.model.Apartment;

import java.util.List;

public interface ApartmentExtractor<T> {

    List<Apartment> extractApartmentsElements(T source);
}
