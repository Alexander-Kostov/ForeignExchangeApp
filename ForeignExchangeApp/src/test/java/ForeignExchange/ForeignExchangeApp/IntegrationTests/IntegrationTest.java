package ForeignExchange.ForeignExchangeApp.IntegrationTests;

import ForeignExchange.ForeignExchangeApp.model.ConversionCurrency;
import ForeignExchange.ForeignExchangeApp.model.Currency;
import ForeignExchange.ForeignExchangeApp.repository.CurrencyRepository;
import ForeignExchange.ForeignExchangeApp.service.CurrencyService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@SpringBootTest
@AutoConfigureMockMvc
public class IntegrationTest {

    @Autowired
    private CurrencyService currencyService;

    @MockBean
    private CurrencyRepository currencyRepository;

    @Autowired
    private MockMvc mockMvc;

    private RestTemplate restTemplate;
    private String basePath = "http://localhost:8080";

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        restTemplate = new RestTemplate();
    }

    @Test
    public void convertShouldBeSuccessful() {
        Currency currencyUSD = new Currency("USD", 1.084775);
        Currency currencyEUR = new Currency("EUR", 1);

        Mockito.when(currencyRepository.findById("USD")).thenReturn(Optional.of(currencyUSD));
        Mockito.when(currencyRepository.findById("EUR")).thenReturn(Optional.of(currencyEUR));

        ConversionCurrency conversionCurrency = new ConversionCurrency("USD", "EUR", 10);

        ResponseEntity<Double> response = restTemplate
                .postForEntity(basePath + "/currency-converter",
                        conversionCurrency, Double.class);

        Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);

    }
}