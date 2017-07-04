package scrapper.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import scrapper.model.Apartment;

@Repository
public interface ApartmentRepository extends MongoRepository<Apartment, String> {

    Apartment findByExternalIdAndSource(String externalId, String source);

    Page<Apartment> findByRecommendedTrue(Pageable pageable);
}
