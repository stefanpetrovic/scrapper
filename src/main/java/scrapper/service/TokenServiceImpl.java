package scrapper.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import scrapper.model.Token;
import scrapper.repo.TokenRepository;

import java.util.Date;
import java.util.Random;
import java.util.UUID;

@Service
public class TokenServiceImpl implements TokenService {

    @Autowired
    private TokenRepository tokenRepository;

    @Override
    public Token generateUniqueToken() {
        Date expirationDate = new Date(new Date().getTime() + 1000 * 60 * 60 * 3);

        String tokenValue = UUID.randomUUID().toString();

        while (tokenRepository.findByValueAndExpirationTimestampAfter(tokenValue, new Date()) != null) {
            tokenValue = UUID.randomUUID().toString();
        }

        Token token = new Token();
        token.setValue(tokenValue);
        token.setExpirationTimestamp(expirationDate);

        return token;
    }

    @Override
    public boolean isTokenValid(String token) {
        Token dbToken = tokenRepository.findByValueAndExpirationTimestampAfter(token, new Date());

        return dbToken != null;
    }
}
