package scrapper.processor;

import scrapper.analyzer.ApartmentAnalyzer;
import scrapper.processor.extractor.ApartmentExtractorTemplate;
import scrapper.processor.fetcher.ApartmentFetcher;
import scrapper.model.Apartment;

import java.util.List;

public class ApartmentProcessorImpl implements ApartmentProcessor {

    private ApartmentFetcher fetcher;
    private ApartmentExtractorTemplate extractorTemplate;
    private ApartmentAnalyzer apartmentAnalyzer;
    private ProcessingMode processingMode;

    public ApartmentProcessorImpl(ApartmentFetcher fetcher, ApartmentExtractorTemplate extractorTemplate, ApartmentAnalyzer apartmentAnalyzer, ProcessingMode processingMode) {
        this.fetcher = fetcher;
        this.extractorTemplate = extractorTemplate;
        this.apartmentAnalyzer = apartmentAnalyzer;
        this.processingMode = processingMode;
    }

    @Override
    public List<Apartment> processApartments(int pageNumber) {
        return apartmentAnalyzer.analyzeApartments(extractorTemplate.extractApartmentsElements(fetcher.fetchApartments(pageNumber)), processingMode);
    }
}
