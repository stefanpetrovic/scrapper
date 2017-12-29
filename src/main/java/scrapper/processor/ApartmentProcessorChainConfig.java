package scrapper.processor;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import scrapper.model.ApartmentSource;

import java.util.List;
import java.util.Map;

@Component
@ConfigurationProperties(prefix = "processorConfig")
public class ApartmentProcessorChainConfig {

    Map<ApartmentSource, List<ProcessingMode>> enabledProcessors;

    public Map<ApartmentSource, List<ProcessingMode>> getEnabledProcessors() {
        return enabledProcessors;
    }

    public void setEnabledProcessors(Map<ApartmentSource, List<ProcessingMode>> enabledProcessors) {
        this.enabledProcessors = enabledProcessors;
    }
}
