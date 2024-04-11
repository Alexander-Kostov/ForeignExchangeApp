package ForeignExchange.ForeignExchangeApp.service;

import ForeignExchange.ForeignExchangeApp.model.redis.Currency;
import ForeignExchange.ForeignExchangeApp.repository.redis.CurrencyRepository;
import org.springframework.stereotype.Service;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class CurrencyService {
    private final CurrencyRepository currencyRepository;

    public CurrencyService(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    public Optional<Currency> getCurrencyById(String upperCase) {
        return this.currencyRepository.findById(upperCase);
    }

    public List<Currency> getAllCurrencies() {
        List<Currency> currencyList = this.currencyRepository.findAll();
        System.out.println("(Checking if cache is working) From database");
        currencyList.sort(Comparator.comparing(Currency::getName));
        return currencyList;
    }

}
