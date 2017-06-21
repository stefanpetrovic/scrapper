package scrapper.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import scrapper.model.ForbiddenAddress;
import scrapper.repo.ForbiddenAddressRepository;

import javax.validation.ValidationException;
import java.util.List;

@Service
public class ForbiddenAddressServiceImpl implements ForbiddenAddressService {

    @Autowired
    private ForbiddenAddressRepository repository;

    @Override
    public ForbiddenAddress saveNewForbiddenAddress(String address) {
        ForbiddenAddress forbiddenAddress = new ForbiddenAddress();
        forbiddenAddress.setAddress(address);
        return repository.save(forbiddenAddress);
    }

    @Override
    public ForbiddenAddress updateForbiddenAddress(ForbiddenAddress forbiddenAddress) {
        ForbiddenAddress addressToUpdate = repository.findOne(forbiddenAddress.getId());

        if (addressToUpdate == null) {
            throw new ValidationException("Unable to find address to update");
        }

        addressToUpdate.setAddress(forbiddenAddress.getAddress());
        return repository.save(addressToUpdate);
    }

    @Override
    public List<ForbiddenAddress> getAllForbiddenAddresses() {
        return repository.findAll();
    }

    @Override
    public void deleteForbiddenAddress(String forbiddenAddressId) {
        ForbiddenAddress addressToDelete = repository.findOne(forbiddenAddressId);

        if (addressToDelete == null) {
            throw new ValidationException("Address to delete not found");
        }

        repository.delete(addressToDelete);
    }
}
