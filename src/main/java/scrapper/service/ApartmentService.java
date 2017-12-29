package scrapper.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import scrapper.model.Apartment;
import scrapper.model.ApartmentPurpose;

import java.util.List;

public interface ApartmentService {

    Page<Apartment> findAllApartments(boolean onlyRecommended, Pageable pageable);

    List<Apartment> findAllApartments(ApartmentPurpose purpose);

    void cleanUpStaleRecords();

    Apartment updateApartment(Apartment apartment);
}
