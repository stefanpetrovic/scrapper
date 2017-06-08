package scrapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class ApartmentStorage {

    private static final Logger log = LoggerFactory.getLogger(ApartmentStorage.class);

    @Autowired
    private ApartmentRepository apartmentRepository;

    @Transactional
    public void storeApartments(List<Apartment> apartments) {
        if (apartments == null || apartments.isEmpty()) {
            return;
        }

        for(Apartment apartment : apartments) {
            Apartment storedApartment =
                    apartmentRepository.findByExternalIdAndSource(apartment.getExternalId(), apartment.getSource().name());

            if (storedApartment == null) {
                apartmentRepository.save(apartment);
                log.info("Apartment stored in DB: [externalId: {}, url: {}", apartment.getExternalId(), apartment.getUrl());
            } else {
                log.info("Apartment already exists in DB: [externalId: {}, url: {}]",
                        apartment.getExternalId(), apartment.getUrl());
            }
        }

    }
}
