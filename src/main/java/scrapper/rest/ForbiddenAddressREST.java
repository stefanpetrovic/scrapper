package scrapper.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import scrapper.model.ForbiddenAddress;
import scrapper.service.ForbiddenAddressService;

import java.util.List;

@RestController
@RequestMapping("/forbiddenAddresses")
public class ForbiddenAddressREST {

    @Autowired
    private ForbiddenAddressService service;

    @GetMapping
    public List<ForbiddenAddress> findAllForbiddenAddresses() {
        return service.getAllForbiddenAddresses();
    }

    @PostMapping
    public ForbiddenAddress createNewForbiddenAddress(@RequestParam String newAddress) {
        return service.saveNewForbiddenAddress(newAddress);
    }

    @PutMapping
    public ForbiddenAddress updateForbiddenAddress(@RequestBody ForbiddenAddress forbiddenAddress) {
        return service.updateForbiddenAddress(forbiddenAddress);
    }

    @DeleteMapping("/{forbiddenAddressId}")
    public void deleteForbiddenAddress(@PathVariable String forbiddenAddressId) {
        service.deleteForbiddenAddress(forbiddenAddressId);
    }

}
