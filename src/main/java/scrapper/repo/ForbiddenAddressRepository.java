package scrapper.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import scrapper.model.ForbiddenAddress;

@Repository
public interface ForbiddenAddressRepository extends MongoRepository<ForbiddenAddress, String> {


}
