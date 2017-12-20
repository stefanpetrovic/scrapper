package scrapper.processor;

import scrapper.model.Apartment;

import java.util.List;

public interface ApartmentProcessor {

    void setNextProcessor(ApartmentProcessor apartmentProcessor);

    List<Apartment> process(int pageNumber);
}
