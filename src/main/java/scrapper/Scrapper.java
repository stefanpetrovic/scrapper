package scrapper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class Scrapper {

    public static void main(String[] args) {
        SpringApplication.run(Scrapper.class, args);
    }

}
