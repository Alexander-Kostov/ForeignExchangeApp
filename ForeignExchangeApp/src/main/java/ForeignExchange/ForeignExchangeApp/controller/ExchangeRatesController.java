package ForeignExchange.ForeignExchangeApp.controller;

import ForeignExchange.ForeignExchangeApp.service.CurrencyService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ExchangeRatesController {

    private CurrencyService currencyService;

    @GetMapping("/exchange-rates")
    public String getExchangeRates() {
        return "exchange-rates";
    }
}
