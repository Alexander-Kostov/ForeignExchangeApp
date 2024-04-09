package ForeignExchange.ForeignExchangeApp.service;

import ForeignExchange.ForeignExchangeApp.model.mysql.ConversionHistory;
import ForeignExchange.ForeignExchangeApp.repository.mysql.ConversionHistoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ConversionHistoryService {

    private final ConversionHistoryRepository conversionHistoryRepository;

    public ConversionHistoryService(ConversionHistoryRepository conversionHistoryRepository) {
        this.conversionHistoryRepository = conversionHistoryRepository;
    }

    public Page<ConversionHistory> getAllConversions(Pageable pageable) {
        return conversionHistoryRepository.findAll(pageable);
    }

    public Page<ConversionHistory> getAllConversionsFilteredByDate(LocalDate from, LocalDate to, Pageable pageable) {
        return this.conversionHistoryRepository.findByLocalDateBetween(from, to, pageable);
    }

    public Page<ConversionHistory> getAllConversionsFilteredById(Integer fromId, Integer toId, Pageable pageable) {
        return this.conversionHistoryRepository.findByIdBetween(fromId, toId, pageable);
    }
}
