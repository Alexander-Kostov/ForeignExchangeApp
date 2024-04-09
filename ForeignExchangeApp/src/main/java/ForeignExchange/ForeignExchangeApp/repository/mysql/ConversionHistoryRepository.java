package ForeignExchange.ForeignExchangeApp.repository.mysql;

import ForeignExchange.ForeignExchangeApp.model.mysql.ConversionHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ConversionHistoryRepository extends JpaRepository<ConversionHistory, Long> {

    Page<ConversionHistory> findByLocalDateBetween(LocalDate from, LocalDate to, Pageable pageable);

    Page<ConversionHistory> findByIdBetween(Integer fromId, Integer toId, Pageable pageable);
}
