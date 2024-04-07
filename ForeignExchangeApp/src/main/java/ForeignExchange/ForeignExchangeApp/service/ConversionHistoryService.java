package ForeignExchange.ForeignExchangeApp.service;

import ForeignExchange.ForeignExchangeApp.model.ConversionHistory;
import ForeignExchange.ForeignExchangeApp.repository.CurrencyMySQLRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ConversionHistoryService {

    private CurrencyMySQLRepository currencyMySQLRepository;

    public ConversionHistoryService(CurrencyMySQLRepository currencyMySQLRepository) {
        this.currencyMySQLRepository = currencyMySQLRepository;
    }

    public Page<ConversionHistory> getAllConversions(Pageable pageable) {
        return currencyMySQLRepository.findAll(pageable);
    }

}
