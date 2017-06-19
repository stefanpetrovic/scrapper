package scrapper.service;

import scrapper.model.ForbiddenAddress;

import java.util.List;

public interface ForbiddenAddressService {

    ForbiddenAddress saveNewForbiddenAddress(String address);

    List<ForbiddenAddress> getAllForbiddenAddresses();

    void deleteForbiddenAddress(String forbiddenAddressId);
}
