package scrapper.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import scrapper.model.Apartment;
import scrapper.service.ApartmentService;

@RestController
@RequestMapping("/apartments")
public class ApartmentRest {

    @Autowired
    private ApartmentService apartmentService;

    @GetMapping
    public Page<Apartment> findAllApartments(@RequestParam boolean onlyRecommended, @RequestParam int pageNum, @RequestParam int pageSize) {
        return apartmentService.findAllApartments(onlyRecommended, new PageRequest(pageNum, pageSize));
    }
}
