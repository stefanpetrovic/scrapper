package scrapper.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/keepAlive")
public class KeepAliveREST {

    private static final Logger log = LoggerFactory.getLogger(KeepAliveREST.class);

    @GetMapping
    public void keepAlive() {
        log.info("Received keepAlive call.");
    }
}
