package scrapper.processor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import scrapper.model.ApartmentSource;

import javax.validation.ValidationException;
import java.util.List;
import java.util.Map;

@Component
public class ApartmentProcessorChainBuilder {

    @Autowired
    ApartmentProcessorChainConfig config;

    @Autowired
    private ApartmentProcessorFactory apartmentProcessorFactory;

    public ApartmentProcessor build() {
        if (config == null) {
            throw new ValidationException("ApartmentProcessorChainConfig must not be null.");
        }

        if (config.getEnabledProcessors() == null) {
            throw new ValidationException("Map of enabled processors must not be null.");
        }

        Map<ApartmentSource, List<ProcessingMode>> enabledProcessors = config.getEnabledProcessors();
        if (enabledProcessors.size() == 0) {
            throw new ValidationException("Map of enabled processors must not be empty.");
        }

        ApartmentProcessor chainHead = null;
        ApartmentProcessor lastChainElement = null;

        for (Map.Entry<ApartmentSource, List<ProcessingMode>> entrySet : enabledProcessors.entrySet()) {
            for (ProcessingMode processingMode: entrySet.getValue()) {
                ApartmentProcessor apartmentProcessor =
                        apartmentProcessorFactory.getProcessorFor(entrySet.getKey(), processingMode);

                if (lastChainElement != null) {
                    lastChainElement.setNextProcessor(apartmentProcessor);
                    lastChainElement = apartmentProcessor;
                } else {
                    chainHead = apartmentProcessor;
                    lastChainElement = apartmentProcessor;
                }
            }
        }

        return chainHead;
    }
}
