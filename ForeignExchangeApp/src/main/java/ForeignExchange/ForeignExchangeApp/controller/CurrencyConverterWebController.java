package ForeignExchange.ForeignExchangeApp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class CurrencyConverterWebController {
    @GetMapping("/")
    public String serverWebPage() {
        return "index";
    }
}
