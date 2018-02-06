package scrapper.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import scrapper.model.SmokingTestAnswer;
import scrapper.repo.SmokingTestAnswerRepository;

import javax.validation.ValidationException;
import java.util.Date;
import java.util.List;

@Service
public class SmokingTestAnswerServiceImpl implements SmokingTestAnswerService {

    @Autowired
    private SmokingTestAnswerRepository repository;

    @Autowired
    private TokenService tokenService;

    @Override
    public void saveAnswer(boolean smoked, String token) {
        SmokingTestAnswer smokingTestAnswer = new SmokingTestAnswer();
        smokingTestAnswer.setSmoked(smoked);
        smokingTestAnswer.setAnswerDate(new Date());

        repository.save(smokingTestAnswer);

        tokenService.invalidateToken(token);
    }

    @Override
    public List<SmokingTestAnswer> findAll() {
        return repository.findAll();
    }

    @Override
    public void useAnswers(int quantity) {
        List<SmokingTestAnswer> availableAnswers = repository.findAllByUsedFalseAndSmokedFalseOrderByAnswerDateAsc();

        if (availableAnswers.size() >= quantity) {
            for (int i = 0; i < quantity; i++) {
                availableAnswers.get(i).setUsed(true);
            }
        } else {
            throw new ValidationException("Not enough answers to use.");
        }

        repository.save(availableAnswers);
    }
}
