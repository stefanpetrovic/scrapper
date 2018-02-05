package scrapper.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import scrapper.model.Token;

import java.util.Date;

@Repository
public interface TokenRepository extends MongoRepository<Token, String> {

    Token findByValueAndExpirationTimestampAfter(String value, Date expirationTimestampAfter);


}
