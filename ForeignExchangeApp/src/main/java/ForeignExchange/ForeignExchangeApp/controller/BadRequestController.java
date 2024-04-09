package ForeignExchange.ForeignExchangeApp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BadRequestController {
    @GetMapping("/bad-request-page")
    public String badRequest() {
        return "bad-request";
    }
}
