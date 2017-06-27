package scrapper.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
public class LoginController {

    @RequestMapping(value = "/login", method = GET)
    public String getLoginPage(@RequestParam(value = "error", required = false) String error) {
        System.out.println("ERROR " + error);
        return "login";
    }

    @RequestMapping(value = "/asdf", method = GET)
    public String getTEst() {
        return "login.html";
    }
}
