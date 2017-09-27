package scrapper.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import scrapper.model.Apartment;
import scrapper.service.ApartmentService;

import java.util.List;

@RestController
@RequestMapping("/apartments")
public class ApartmentRest {

    @Autowired
    private ApartmentService apartmentService;
/*
    @GetMapping
    public Page<Apartment> findAllApartments(@RequestParam boolean onlyRecommended, @RequestParam int pageNum, @RequestParam int pageSize) {
        return apartmentService.findAllApartments(onlyRecommended, new PageRequest(pageNum, pageSize));
    }
    */
    @GetMapping
    public List<Apartment> findAll() {
        return apartmentService.findAllApartments();
    }

    @PutMapping
    public Apartment updateApartment(@RequestBody Apartment apartment) {
        return apartmentService.updateApartment(apartment);
    }
}
