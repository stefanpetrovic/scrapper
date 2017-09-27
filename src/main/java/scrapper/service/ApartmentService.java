package scrapper.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import scrapper.model.Apartment;

import java.util.List;

public interface ApartmentService {

    Page<Apartment> findAllApartments(boolean onlyRecommended, Pageable pageable);

    List<Apartment> findAllApartments();

    void cleanUpStaleRecords();

    Apartment updateApartment(Apartment apartment);
}
