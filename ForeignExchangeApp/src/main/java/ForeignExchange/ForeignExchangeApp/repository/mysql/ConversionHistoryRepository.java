package ForeignExchange.ForeignExchangeApp.repository.mysql;

import ForeignExchange.ForeignExchangeApp.model.mysql.ConversionHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConversionHistoryRepository extends JpaRepository<ConversionHistory, Long> {

}
