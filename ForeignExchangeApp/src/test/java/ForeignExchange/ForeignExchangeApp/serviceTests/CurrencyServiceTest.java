package ForeignExchange.ForeignExchangeApp.serviceTests;

import ForeignExchange.ForeignExchangeApp.model.ConversionCurrency;
import ForeignExchange.ForeignExchangeApp.model.Currency;
import ForeignExchange.ForeignExchangeApp.repository.CurrencyRepository;
import ForeignExchange.ForeignExchangeApp.service.CurrencyService;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class CurrencyServiceTest {

    @Mock
    private CurrencyRepository currencyRepository;
    private CurrencyService subject;

    @BeforeEach
    public void setup() {
        subject = new CurrencyService(currencyRepository);
    }

    @Test
    public void getAllCurrenciesShouldReturnEmptyTest() {
        Mockito.when(currencyRepository.findAll()).thenReturn(Arrays.asList());

        List<Currency> allCurrencies = subject.getAllCurrencies();
        Assertions.assertTrue(allCurrencies.isEmpty());
    }

    @Test
    public void getAllCurrenciesShouldBeOrderedTest() {

        Currency currencyUSD = new Currency("USD", 2);
        Currency currencyEUR = new Currency("EUR", 1.25);
        Currency currencyBGN = new Currency("BGN", 6.50);
        Currency currencyAED = new Currency("AED", 10);
        Mockito.when(currencyRepository.findAll()).thenReturn(Arrays.asList(currencyUSD, currencyEUR, currencyBGN, currencyAED));

        List<Currency> allCurrencies = subject.getAllCurrencies();
        Assertions.assertEquals(allCurrencies.get(0).getName(), "AED");
        Assertions.assertEquals(allCurrencies.get(1).getName(), "BGN");
        Assertions.assertEquals(allCurrencies.get(2).getName(), "EUR");
        Assertions.assertEquals(allCurrencies.get(3).getName(), "USD");
    }


    @Test
    public void getAllCurrenciesGivesNullPointerExceptionTest() {
        Mockito.when(currencyRepository.findAll()).thenReturn(null);

        Assertions.assertThrows(NullPointerException.class, () -> {
            subject.getAllCurrencies();
        });
    }

    @Test
    public void convertShouldReturnEmptyWhenNegativeValueTest() {
        Currency currencyUSD = new Currency("USD", 2);
        Currency currencyEUR = new Currency("EUR", 1.25);

        Mockito.when(currencyRepository.findById("USD")).thenReturn(Optional.of(currencyUSD));
        Mockito.when(currencyRepository.findById("EUR")).thenReturn(Optional.of(currencyEUR));

        ConversionCurrency conversionCurrency = new ConversionCurrency("EUR", "USD", -10);

        Optional<Double> result = this.subject.convert(conversionCurrency);

        Assertions.assertNotNull(result);
        Assertions.assertFalse(result.isPresent());
    }

    @Test
    public void convertShouldReturnEmptyWhenCurrencyDoesNotExistTest() {
        Currency currencyEUR = new Currency("EUR", 1.25);

        Mockito.when(currencyRepository.findById("USD")).thenReturn(Optional.empty());
        Mockito.when(currencyRepository.findById("EUR")).thenReturn(Optional.of(currencyEUR));

        ConversionCurrency conversionCurrency = new ConversionCurrency("EUR", "USD", 0);

        Optional<Double> result = this.subject.convert(conversionCurrency);

        Assertions.assertNotNull(result);
        Assertions.assertFalse(result.isPresent());
    }

    @Test
    public void convertShouldReturnValidValueTest() {
        Currency currencyEUR = new Currency("EUR", 1);
        Currency currencyUSD = new Currency("USD", 1.085452);

        Mockito.when(currencyRepository.findById("USD")).thenReturn(Optional.of(currencyUSD));
        Mockito.when(currencyRepository.findById("EUR")).thenReturn(Optional.of(currencyEUR));

        ConversionCurrency conversionCurrency = new ConversionCurrency("EUR", "USD", 10);

        Optional<Double> result = this.subject.convert(conversionCurrency);

        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(Double.valueOf(9.212751922701326), result.get());
    }
}
