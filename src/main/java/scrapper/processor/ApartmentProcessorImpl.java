package scrapper.processor;

import scrapper.analyzer.ApartmentAnalyzer;
import scrapper.model.Apartment;
import scrapper.processor.extractor.ApartmentExtractor;
import scrapper.processor.fetcher.ApartmentFetcher;

import java.util.List;

public class ApartmentProcessorImpl<T> implements ApartmentProcessor {

    private ApartmentFetcher<T> fetcher;
    private ApartmentExtractor<T> extractor;
    private ApartmentAnalyzer apartmentAnalyzer;
    private ProcessingMode processingMode;
    private ApartmentProcessor nextProcessor;

    public ApartmentProcessorImpl(ApartmentFetcher<T> fetcher, ApartmentExtractor<T> extractor, ApartmentAnalyzer apartmentAnalyzer, ProcessingMode processingMode) {
        this.fetcher = fetcher;
        this.extractor = extractor;
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
        return apartmentAnalyzer.analyzeApartments(extractor.extractApartmentsElements(fetcher.fetchApartments(pageNumber)), processingMode);
    }
}
