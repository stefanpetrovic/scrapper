package scrapper.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import scrapper.model.Apartment;
import scrapper.repo.ApartmentRepository;

import java.util.ArrayList;
import java.util.List;

@Component
public class ApartmentStorage {

    private static final Logger log = LoggerFactory.getLogger(ApartmentStorage.class);

    @Autowired
    private ApartmentRepository apartmentRepository;

    //return only newly stored
    @Transactional
    public List<Apartment> storeApartments(List<Apartment> apartments) {
        List<Apartment> storedApartments = new ArrayList<>();
        if (apartments == null || apartments.isEmpty()) {
            return storedApartments;
        }

        for (Apartment apartment : apartments) {
            Apartment apartmentToStore =
                    apartmentRepository.findByExternalIdAndSource(apartment.getExternalId(), apartment.getSource().name());

            if (apartmentToStore == null) {
                Apartment storedApartment = apartmentRepository.save(apartment);
                log.debug("Apartment stored in DB: [externalId: {}, url: {}", apartment.getExternalId(), apartment.getUrl());
                storedApartments.add(storedApartment);
            } else {
                log.debug("Apartment already exists in DB: [externalId: {}, url: {}]",
                        apartment.getExternalId(), apartment.getUrl());
            }
        }

        return storedApartments;
    }
}
