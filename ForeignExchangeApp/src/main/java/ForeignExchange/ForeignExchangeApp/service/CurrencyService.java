package ForeignExchange.ForeignExchangeApp.service;

import ForeignExchange.ForeignExchangeApp.model.Currency;
import ForeignExchange.ForeignExchangeApp.repository.CurrencyRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CurrencyService {

    private CurrencyRepository currencyRepository;

    public CurrencyService(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    public List<Currency> getAllCurrencies() {
        List<Currency> currencyList = this.currencyRepository.findAll();
        return currencyList;
    }

}
