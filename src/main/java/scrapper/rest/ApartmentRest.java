package scrapper.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import scrapper.model.Apartment;
import scrapper.model.ApartmentPurpose;
import scrapper.service.ApartmentService;

import java.util.List;

@RestController
@RequestMapping("/apartments")
public class ApartmentRest {

    private static final Logger log = LoggerFactory.getLogger(ApartmentRest.class);

    @Autowired
    private ApartmentService apartmentService;

    @GetMapping
    public List<Apartment> findAll(@RequestParam("purpose")ApartmentPurpose purpose) {
        log.info("Received REST request findAll:[purpose: {}]", purpose);
        return apartmentService.findAllApartments(purpose);
    }

    @PutMapping
    public Apartment updateApartment(@RequestBody Apartment apartment) {
        log.info("Received REST request updateApartment: [id: {}]", apartment.getExternalId());
        return apartmentService.updateApartment(apartment);
    }
}
