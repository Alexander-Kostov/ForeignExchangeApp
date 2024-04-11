package ForeignExchange.ForeignExchangeApp.service;

import ForeignExchange.ForeignExchangeApp.model.ConversionCurrency;
import ForeignExchange.ForeignExchangeApp.model.mysql.ConversionHistory;
import ForeignExchange.ForeignExchangeApp.model.redis.Currency;
import ForeignExchange.ForeignExchangeApp.repository.mysql.ConversionHistoryRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.Optional;

@Service
public class CurrencyConverterService {

    private final CurrencyService currencyService;

    private final ConversionHistoryRepository conversionHistoryRepository;

    public CurrencyConverterService(CurrencyService currencyService, ConversionHistoryRepository conversionHistoryRepository) {
        this.currencyService = currencyService;
        this.conversionHistoryRepository = conversionHistoryRepository;
    }

    public Optional<Double> convert(ConversionCurrency conversionCurrency) {
        Optional<Currency> toOptional = this.currencyService.getCurrencyById(conversionCurrency.getTo().toUpperCase());
        Optional<Currency> fromOptional = this.currencyService.getCurrencyById(conversionCurrency.getFrom().toUpperCase());

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

        this.conversionHistoryRepository.save(conversionHistory);
    }
}
