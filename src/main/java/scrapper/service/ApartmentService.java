package scrapper.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import scrapper.model.Apartment;

public interface ApartmentService {

    Page<Apartment> findAllApartments(boolean onlyRecommended, Pageable pageable);

    void cleanUpStaleRecords();
}
