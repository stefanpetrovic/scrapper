package scrapper.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import scrapper.model.Apartment;
import scrapper.repo.ApartmentRepository;

@Service
public class ApartmentServiceImpl implements ApartmentService {

    @Autowired
    private ApartmentRepository repository;

    @Override
    public Page<Apartment> findAllApartments(boolean onlyRecommended, Pageable pageable) {
        if (onlyRecommended) {
            return repository.findByRecommendedTrue(pageable);
        }

        return repository.findAll(pageable);
    }
}
