package scrapper.processor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import scrapper.analyzer.ApartmentAnalyzer;
import scrapper.processor.extractor.HaloOglasiApartmentExtractor;
import scrapper.processor.extractor.NekretnineRSApartmentExtractor;
import scrapper.processor.fetcher.HaloOglasiApartmentFetcher;
import scrapper.processor.fetcher.NekretnineRSApartmentFetcher;
import scrapper.model.ApartmentSource;

@Component
public class ApartmentProcessorFactory {

    @Autowired
    private ApartmentAnalyzer apartmentAnalyzer;

    public ApartmentProcessor getProcessorFor(ApartmentSource apartmentSource, ProcessingMode processingMode) {
        switch(apartmentSource) {
            case HALO_OGLASI:
                return new ApartmentProcessorImpl(new HaloOglasiApartmentFetcher(processingMode), new HaloOglasiApartmentExtractor(processingMode), apartmentAnalyzer, processingMode);
            case NEKRETNINE_RS:
                return new ApartmentProcessorImpl(new NekretnineRSApartmentFetcher(processingMode), new NekretnineRSApartmentExtractor(processingMode), apartmentAnalyzer, processingMode);
            default:
                throw new RuntimeException("Unknown ApartmentSource provided: " + apartmentSource.name());
        }
    }
}
