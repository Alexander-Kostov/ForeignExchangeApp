package ForeignExchange.ForeignExchangeApp.service;

import ForeignExchange.ForeignExchangeApp.model.redis.Currency;
import ForeignExchange.ForeignExchangeApp.repository.mysql.ConversionHistoryRepository;
import ForeignExchange.ForeignExchangeApp.repository.redis.CurrencyRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class CurrencyService {
    private final CurrencyRepository currencyRepository;

    private final ConversionHistoryRepository conversionHistoryRepository;

    public CurrencyService(CurrencyRepository currencyRepository, ConversionHistoryRepository conversionHistoryRepository) {
        this.currencyRepository = currencyRepository;
        this.conversionHistoryRepository = conversionHistoryRepository;
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
