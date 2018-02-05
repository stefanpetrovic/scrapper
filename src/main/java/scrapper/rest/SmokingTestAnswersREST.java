package scrapper.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import scrapper.model.SmokingTestAnswer;
import scrapper.service.SmokingTestAnswerService;

import java.util.List;

@RestController
@RequestMapping("/smokingtestanswers")
public class SmokingTestAnswersREST {

    @Autowired
    private SmokingTestAnswerService service;

    public List<SmokingTestAnswer> getAllAnswers() {
        return service.findAll();
    }
}
