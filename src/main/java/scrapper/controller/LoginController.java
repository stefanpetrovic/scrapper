package scrapper.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import scrapper.model.User;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
public class LoginController {

    private static final Logger log = LoggerFactory.getLogger(LoginController.class);

    @RequestMapping(value = "/login", method = GET)
    public String getLoginPage(@RequestParam(value = "error", required = false) String error) {
        if (!StringUtils.isEmpty(error)) {
            log.info("ERROR {}", error);
        }

        return "login";
    }

    @RequestMapping(value = "/login/google", method = POST)
    @ResponseBody
    public void loginViaGoogle(@RequestBody User user) {
        log.info("Received request loginViaGoogle: [token: {}]", user.getToken());

    }
}
