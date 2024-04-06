package ForeignExchange.ForeignExchangeApp.service;

import ForeignExchange.ForeignExchangeApp.model.ConversionCurrency;
import ForeignExchange.ForeignExchangeApp.model.ConversionHistory;
import ForeignExchange.ForeignExchangeApp.model.Currency;
import ForeignExchange.ForeignExchangeApp.repository.CurrencyMySQLRepository;
import ForeignExchange.ForeignExchangeApp.repository.CurrencyRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Component
public class CurrencyService {

    private CurrencyRepository currencyRepository;

    private CurrencyMySQLRepository currencyMySQLRepository;

    public CurrencyService(CurrencyRepository currencyRepository, CurrencyMySQLRepository currencyMySQLRepository) {
        this.currencyRepository = currencyRepository;
        this.currencyMySQLRepository = currencyMySQLRepository;
    }

    public List<Currency> getAllCurrencies() {
        List<Currency> currencyList = this.currencyRepository.findAll();
        currencyList.sort(Comparator.comparing(Currency::getName));
        return currencyList;
    }

    public Optional<Double> convert(ConversionCurrency conversionCurrency) {
        Optional<Currency> toOptional = this.currencyRepository.findById(conversionCurrency.getTo().toUpperCase());
        Optional<Currency> fromOptional = this.currencyRepository.findById(conversionCurrency.getFrom().toUpperCase());

        if (toOptional.isPresent() && fromOptional.isPresent()) {
            if (conversionCurrency.getValue() < 0) {
                return Optional.empty();
            }
            Currency to = toOptional.get();
            Currency from = fromOptional.get();

            System.out.println();
            Double toValue = to.getValue();
            Double fromValue = from.getValue();

            Double result = toValue * conversionCurrency.getValue() / fromValue;
            if (result > 0) {
                saveTransactionToMySQLDatabase(to, from, result, conversionCurrency.getValue());
            }
            return Optional.of(result);
        }

        return Optional.empty();
    }

    private void saveTransactionToMySQLDatabase(Currency to, Currency from, Double result, Double amount) {
        String fromName = from.getName();
        double fromValue = from.getValue();

        String toName = to.getName();
        double toValue = to.getValue();

        ConversionHistory conversionHistory = new ConversionHistory(fromName, fromValue, toName, toValue, amount, result, LocalDate.now());

        this.currencyMySQLRepository.save(conversionHistory);
    }

}
