package ForeignExchange.ForeignExchangeApp.serviceTests;

import ForeignExchange.ForeignExchangeApp.model.ConversionCurrency;
import ForeignExchange.ForeignExchangeApp.model.redis.Currency;
import ForeignExchange.ForeignExchangeApp.repository.mysql.ConversionHistoryRepository;
import ForeignExchange.ForeignExchangeApp.repository.redis.CurrencyRepository;
import ForeignExchange.ForeignExchangeApp.service.CurrencyConverterService;
import ForeignExchange.ForeignExchangeApp.service.CurrencyService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class CurrencyConverterServiceTest {
    @Mock
    private CurrencyService currencyService;

    @Mock
    private ConversionHistoryRepository conversionHistoryRepository;

    private CurrencyConverterService currencyConverterService;

    @BeforeEach
    public void setup() {
        this.currencyConverterService = new CurrencyConverterService(currencyService, conversionHistoryRepository);
    }

    @Test
    public void convertShouldReturnEmptyWhenNegativeValueTest() {
        Currency currencyUSD = new Currency("USD", 2);
        Currency currencyEUR = new Currency("EUR", 1.25);

        Mockito.when(currencyService.getCurrencyById("USD")).thenReturn(Optional.of(currencyUSD));
        Mockito.when(currencyService.getCurrencyById("EUR")).thenReturn(Optional.of(currencyEUR));

        ConversionCurrency conversionCurrency = new ConversionCurrency("EUR", "USD", -10);

        Optional<Double> result = currencyConverterService.convert(conversionCurrency);

        Assertions.assertNotNull(result);
        Assertions.assertFalse(result.isPresent());
    }

    @Test
    public void convertShouldReturnEmptyWhenCurrencyDoesNotExistTest() {
        Currency currencyEUR = new Currency("EUR", 1.25);

        Mockito.when(currencyService.getCurrencyById("USD")).thenReturn(Optional.empty());
        Mockito.when(currencyService.getCurrencyById("EUR")).thenReturn(Optional.of(currencyEUR));

        ConversionCurrency conversionCurrency = new ConversionCurrency("EUR", "USD", 0);

        Optional<Double> result = currencyConverterService.convert(conversionCurrency);

        Assertions.assertNotNull(result);
        Assertions.assertFalse(result.isPresent());
    }

    @Test
    public void convertShouldReturnValidValueTest() {
        Currency currencyEUR = new Currency("EUR", 1);
        Currency currencyUSD = new Currency("USD", 1.085452);

        Mockito.when(currencyService.getCurrencyById("USD")).thenReturn(Optional.of(currencyUSD));
        Mockito.when(currencyService.getCurrencyById("EUR")).thenReturn(Optional.of(currencyEUR));

        ConversionCurrency conversionCurrency = new ConversionCurrency("EUR", "USD", 10);

        Optional<Double> result = currencyConverterService.convert(conversionCurrency);

        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(Double.valueOf(9.212751922701326), result.get());
    }
}
