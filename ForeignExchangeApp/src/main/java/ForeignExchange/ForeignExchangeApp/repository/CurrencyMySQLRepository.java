package ForeignExchange.ForeignExchangeApp.repository;

import ForeignExchange.ForeignExchangeApp.model.ConversionHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyMySQLRepository extends JpaRepository<ConversionHistory, Long> {

}
