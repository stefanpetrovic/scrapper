package scrapper.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
        try {
            Thread.currentThread().sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return service.saveNewForbiddenAddress(newAddress);
    }

    @DeleteMapping("/{forbiddenAddressId}")
    public void deleteForbiddenAddress(@PathVariable String forbiddenAddressId) {
        service.deleteForbiddenAddress(forbiddenAddressId);
    }

}
