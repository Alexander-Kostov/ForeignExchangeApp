package ForeignExchange.ForeignExchangeApp.unitTests;

import ForeignExchange.ForeignExchangeApp.model.redis.Currency;
import ForeignExchange.ForeignExchangeApp.repository.redis.CurrencyRepository;
import ForeignExchange.ForeignExchangeApp.service.CurrencyService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class CurrencyServiceTest {

    @Mock
    private CurrencyRepository currencyRepository;

    private CurrencyService currencyService;

    @BeforeEach
    public void setup() {
        this.currencyService = new CurrencyService(this.currencyRepository);
    }

    @Test
    public void getAllCurrenciesShouldReturnEmptyTest() {
        Mockito.when(currencyRepository.findAll()).thenReturn(Arrays.asList());

        List<Currency> allCurrencies = currencyService.getAllCurrencies();
        Assertions.assertTrue(allCurrencies.isEmpty());
    }

    @Test
    public void getAllCurrenciesShouldBeOrderedTest() {

        Currency currencyUSD = new Currency("USD", 2);
        Currency currencyEUR = new Currency("EUR", 1.25);
        Currency currencyBGN = new Currency("BGN", 6.50);
        Currency currencyAED = new Currency("AED", 10);
        Mockito.when(currencyRepository.findAll()).thenReturn(Arrays.asList(currencyUSD, currencyEUR, currencyBGN, currencyAED));

        List<Currency> allCurrencies = currencyService.getAllCurrencies();
        Assertions.assertEquals(allCurrencies.get(0).getName(), "AED");
        Assertions.assertEquals(allCurrencies.get(1).getName(), "BGN");
        Assertions.assertEquals(allCurrencies.get(2).getName(), "EUR");
        Assertions.assertEquals(allCurrencies.get(3).getName(), "USD");
    }


    @Test
    public void getAllCurrenciesGivesNullPointerExceptionTest() {
        Mockito.when(currencyRepository.findAll()).thenReturn(null);

        Assertions.assertThrows(NullPointerException.class, () -> {
            currencyService.getAllCurrencies();
        });
    }
}
