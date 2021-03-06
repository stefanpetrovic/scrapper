package scrapper.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import scrapper.service.SmokingTestAnswerService;
import scrapper.service.TokenService;

@Controller
public class SmokingTestController {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private SmokingTestAnswerService smokingTestAnswerService;

    @RequestMapping("/smoking-test")
    public String answerNoted(@RequestParam("smoked") boolean smoked, @RequestParam("token") String token) {
        if (!tokenService.isTokenValid(token)) {
            return "invalid-token";
        }

        smokingTestAnswerService.saveAnswer(smoked, token);

        return "answer-noted";
    }
}
