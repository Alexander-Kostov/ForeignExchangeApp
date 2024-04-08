package ForeignExchange.ForeignExchangeApp.controller.rest;

import ForeignExchange.ForeignExchangeApp.model.ConversionCurrency;
import ForeignExchange.ForeignExchangeApp.service.CurrencyConverterService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class CurrencyConverterController {
    private final CurrencyConverterService currencyConverterService;
    public CurrencyConverterController(CurrencyConverterService currencyConverterService) {
        this.currencyConverterService = currencyConverterService;
    }

    @RequestMapping(value = "/currency-converter", produces = { "application/json" }, method = RequestMethod.POST)
    public ResponseEntity<Double> convertCurrencies(@RequestBody ConversionCurrency conversionCurrency) {
        Optional<Double> resultOptional = this.currencyConverterService.convert(conversionCurrency);
        if (resultOptional.isPresent()) {
            return new ResponseEntity<>(resultOptional.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}
