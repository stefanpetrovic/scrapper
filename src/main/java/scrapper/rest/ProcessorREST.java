package scrapper.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import scrapper.service.Processor;

@RestController
@RequestMapping("/processor")
public class ProcessorREST {

    @Autowired
    private Processor processor;

    @PostMapping
    public void startProcess() {
        processor.process();
    }
}
