package scrapper.processor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import scrapper.analyzer.ApartmentAnalyzer;
import scrapper.model.ApartmentSource;
import scrapper.processor.extractor.html.HaloOglasiHTMLExtractor;
import scrapper.processor.extractor.html.NekretnineRSHTMLExtractor;
import scrapper.processor.extractor.rest.CityExpertRESTExtractor;
import scrapper.processor.fetcher.html.HaloOglasiApartmentFetcher;
import scrapper.processor.fetcher.html.NekretnineRSApartmentFetcher;
import scrapper.processor.fetcher.rest.CityExpertApartmentFetcher;

@Component
public class ApartmentProcessorFactory {

    @Autowired
    private ApartmentAnalyzer apartmentAnalyzer;

    public ApartmentProcessor getProcessorFor(ApartmentSource apartmentSource, ProcessingMode processingMode) {
        switch (apartmentSource) {
            case HALO_OGLASI:
                return new ApartmentProcessorImpl<>(
                        new HaloOglasiApartmentFetcher(processingMode),
                        new HaloOglasiHTMLExtractor(processingMode),
                        apartmentAnalyzer,
                        processingMode);
            case NEKRETNINE_RS:
                return new ApartmentProcessorImpl<>(
                        new NekretnineRSApartmentFetcher(processingMode),
                        new NekretnineRSHTMLExtractor(processingMode),
                        apartmentAnalyzer,
                        processingMode);
            case CITY_EXPERT:
                return new ApartmentProcessorImpl<>(
                        new CityExpertApartmentFetcher(processingMode),
                        new CityExpertRESTExtractor(processingMode),
                        apartmentAnalyzer,
                        processingMode
                );
            default:
                throw new RuntimeException("Unknown ApartmentSource provided: " + apartmentSource.name());
        }
    }
}
