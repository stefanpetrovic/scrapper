package scrapper.service;

import scrapper.model.Token;

public interface TokenService {

    Token generateUniqueToken();

    boolean isTokenValid(String token);
}
