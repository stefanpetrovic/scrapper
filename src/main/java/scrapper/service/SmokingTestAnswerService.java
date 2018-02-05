package scrapper.service;

import scrapper.model.SmokingTestAnswer;

import java.util.List;

public interface SmokingTestAnswerService {

    void saveAnswer(boolean smoked, String token);

    List<SmokingTestAnswer> findAll();
}
