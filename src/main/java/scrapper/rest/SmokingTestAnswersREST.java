package scrapper.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import scrapper.model.SmokingTestAnswer;
import scrapper.service.SmokingTestAnswerService;

import java.util.List;

@RestController
@RequestMapping("/smokingtestanswers")
public class SmokingTestAnswersREST {

    private static final Logger log = LoggerFactory.getLogger(SmokingTestAnswersREST.class);

    @Autowired
    private SmokingTestAnswerService service;

    @GetMapping
    public List<SmokingTestAnswer> getAllAnswers() {
        return service.findAll();
    }

    @PostMapping
    public void useAnswers(@RequestParam("quantity") int quantity) {
        log.info("Received REST request useAnswers: [quantity: {}]", quantity);

        service.useAnswers(quantity);
    }
}
