package ForeignExchange.ForeignExchangeApp.controller.rest;

import ForeignExchange.ForeignExchangeApp.model.ConversionCurrency;
import ForeignExchange.ForeignExchangeApp.service.CurrencyConverterService;
import io.github.bucket4j.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.time.Duration;
import java.util.Optional;

@RestController
public class CurrencyConverterController {
    private final CurrencyConverterService currencyConverterService;

    private final Bucket bucket;
    public CurrencyConverterController(CurrencyConverterService currencyConverterService) {
        this.currencyConverterService = currencyConverterService;

        Bandwidth limit = Bandwidth.classic(50, Refill.intervally(5, Duration.ofMinutes(1)));
        this.bucket = Bucket4j.builder()
                .addLimit(limit)
                .build();
    }

    @RequestMapping(value = "/currency-converter", produces = {"application/json"}, method = RequestMethod.POST)
    public ResponseEntity<Double> convertCurrencies(@RequestBody ConversionCurrency conversionCurrency) {
        ConsumptionProbe probe = bucket.tryConsumeAndReturnRemaining(1);
        if (probe.isConsumed()) {

            Optional<Double> resultOptional = this.currencyConverterService.convert(conversionCurrency);
            if (resultOptional.isPresent()) {
                return new ResponseEntity<>(resultOptional.get(), HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(HttpStatus.TOO_MANY_REQUESTS);
        }
    }
}


