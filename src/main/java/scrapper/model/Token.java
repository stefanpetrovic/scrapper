package scrapper.model;

import org.springframework.data.annotation.Id;

import java.util.Date;

public class Token {

    @Id
    private String id;
    private String value;
    private Date expirationTimestamp;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Date getExpirationTimestamp() {
        return expirationTimestamp;
    }

    public void setExpirationTimestamp(Date expirationTimestamp) {
        this.expirationTimestamp = expirationTimestamp;
    }
}
