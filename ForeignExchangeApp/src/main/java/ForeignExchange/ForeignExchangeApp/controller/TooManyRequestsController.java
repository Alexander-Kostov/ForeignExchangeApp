package ForeignExchange.ForeignExchangeApp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class TooManyRequestsController {

    @GetMapping("too-many-requests-page")
    public String tooManyRequests() {
        return "too-many-requests-page";
    }
}
