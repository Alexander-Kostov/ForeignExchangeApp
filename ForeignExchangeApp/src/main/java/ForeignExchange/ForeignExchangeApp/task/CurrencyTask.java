package ForeignExchange.ForeignExchangeApp.task;

import ForeignExchange.ForeignExchangeApp.model.Currency;
import ForeignExchange.ForeignExchangeApp.model.CurrencyDTO;
import ForeignExchange.ForeignExchangeApp.repository.CurrencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;


@Component
public class CurrencyTask {
    @Value("${fixer.io.apiKey}")
    private String fixerIoApiKey;
    @Autowired
    private CurrencyRepository currencyRepository;

    // Runs every 2 hours
    @Scheduled(fixedRate = 2 * 1000 * 60 * 60)
    private void getRatesTask() {
        try {
            RestTemplate restTemplate = new RestTemplate();
            CurrencyDTO forObject = restTemplate.getForObject(fixerIoApiKey, CurrencyDTO.class);


            forObject.getRates().forEach((key, value) -> {
                Currency currency = new Currency(key, value);
                this.currencyRepository.save(currency);
            });

        } catch (RestClientException ex) {
            System.out.println((ex.getMessage()));
        }
    }
}
