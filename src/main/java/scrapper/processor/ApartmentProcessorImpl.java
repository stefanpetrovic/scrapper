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
    private ApartmentProcessor nextProcessor;

    public ApartmentProcessorImpl(ApartmentFetcher fetcher, ApartmentExtractorTemplate extractorTemplate, ApartmentAnalyzer apartmentAnalyzer, ProcessingMode processingMode) {
        this.fetcher = fetcher;
        this.extractorTemplate = extractorTemplate;
        this.apartmentAnalyzer = apartmentAnalyzer;
        this.processingMode = processingMode;
    }

    @Override
    public void setNextProcessor(ApartmentProcessor apartmentProcessor) {
        this.nextProcessor = apartmentProcessor;
    }

    @Override
    public List<Apartment> process(int pageNumber) {
        List<Apartment> apartments = processApartments(pageNumber);

        if (nextProcessor != null) {
            apartments.addAll(nextProcessor.process(pageNumber));
        }

        return apartments;
    }

    private List<Apartment> processApartments(int pageNumber) {
        return apartmentAnalyzer.analyzeApartments(extractorTemplate.extractApartmentsElements(fetcher.fetchApartments(pageNumber)), processingMode);
    }
}
