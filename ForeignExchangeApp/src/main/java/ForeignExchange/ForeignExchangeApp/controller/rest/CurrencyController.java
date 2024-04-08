package ForeignExchange.ForeignExchangeApp.controller.rest;

import ForeignExchange.ForeignExchangeApp.model.redis.Currency;
import ForeignExchange.ForeignExchangeApp.service.CurrencyService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CurrencyController {

    private CurrencyService currencyService;

    public CurrencyController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @GetMapping("/currencies")
    @Cacheable("currencies")
    public List<Currency> getAllCurrencies() {

        return this.currencyService.getAllCurrencies();
    }
}
