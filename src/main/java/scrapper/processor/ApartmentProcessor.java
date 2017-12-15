package scrapper.processor;

import scrapper.model.Apartment;

import java.util.List;

public interface ApartmentProcessor {

    List<Apartment> processApartments(int pageNumber);
}
