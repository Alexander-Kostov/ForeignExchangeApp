package ForeignExchange.ForeignExchangeApp.service;

import ForeignExchange.ForeignExchangeApp.model.mysql.ConversionHistory;
import ForeignExchange.ForeignExchangeApp.repository.mysql.ConversionHistoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ConversionHistoryService {

    private final ConversionHistoryRepository conversionHistoryRepository;

    public ConversionHistoryService(ConversionHistoryRepository conversionHistoryRepository) {
        this.conversionHistoryRepository = conversionHistoryRepository;
    }

    public Page<ConversionHistory> getAllConversions(Pageable pageable) {
        return conversionHistoryRepository.findAll(pageable);
    }

}
