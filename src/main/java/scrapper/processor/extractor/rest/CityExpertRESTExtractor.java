package scrapper.processor.extractor.rest;

import org.springframework.util.StringUtils;
import scrapper.model.Apartment;
import scrapper.model.ApartmentPurpose;
import scrapper.model.ApartmentSource;
import scrapper.model.CityExpertApartment;
import scrapper.model.RESTResponse;
import scrapper.processor.ProcessingMode;

import java.util.List;
import java.util.stream.Collectors;

import static scrapper.processor.ProcessingMode.PRODAJA;

public class CityExpertRESTExtractor implements RESTApartmentExtractor<CityExpertApartment> {

    private final ProcessingMode processingMode;

    public CityExpertRESTExtractor(ProcessingMode processingMode) {
        this.processingMode = processingMode;
    }

    @Override
    public List<Apartment> extractApartmentsElements(RESTResponse<CityExpertApartment> source) {
        return source.getContent().stream().map(this::convert).collect(Collectors.toList());
    }

    private Apartment convert(CityExpertApartment source) {
        Apartment apartment = new Apartment();
        apartment.setSource(ApartmentSource.CITY_EXPERT);
        apartment.setPrice(source.getPrice());
        apartment.setUrl(getApartmentURL(source));
        apartment.setDescription(source.getStructure());
        apartment.setAddress(getAddress(source));
        apartment.setArea(source.getSize());
        apartment.setNoOfRooms(extractNumberOfRooms(source));
        apartment.setPurpose((processingMode.equals(PRODAJA)) ? ApartmentPurpose.PRODAJA : ApartmentPurpose.IZDAVANJE);
        apartment.setExternalId(source.getPropId().toString());
        apartment.setImageURL("https://img.cityexpert.rs/sites/default/files/styles/470x/public/image/" + source.getCoverPhoto());

        return apartment;
    }

    private String getApartmentURL(CityExpertApartment apartment) {
        return "https://cityexpert.rs/" + (processingMode.equals(PRODAJA) ? "prodaja/" : "izdavanje/") + "apartment/" +
                apartment.getPropId() + "/" + apartment.getStructure().replaceAll("\\.","") + "-rooms-" + apartment.getId() + "-" + apartment.getMunicipality();
    }

    private String getAddress(CityExpertApartment apartment) {
        return StringUtils.arrayToCommaDelimitedString(apartment.getPolygons());
    }

    private Double extractNumberOfRooms(CityExpertApartment apartment) {
        String nice = apartment.getStructure().replaceAll("\\+", "");

        return Double.parseDouble(nice);
    }
}
