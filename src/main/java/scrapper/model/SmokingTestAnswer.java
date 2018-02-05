package scrapper.model;

import org.springframework.data.annotation.Id;

import java.util.Date;

public class SmokingTestAnswer {

    @Id
    private String id;
    private boolean smoked;
    private Date answerDate;

    public boolean isSmoked() {
        return smoked;
    }

    public void setSmoked(boolean smoked) {
        this.smoked = smoked;
    }

    public Date getAnswerDate() {
        return answerDate;
    }

    public void setAnswerDate(Date answerDate) {
        this.answerDate = answerDate;
    }
}
