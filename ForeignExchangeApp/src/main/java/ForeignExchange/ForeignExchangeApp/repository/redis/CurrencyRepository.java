package ForeignExchange.ForeignExchangeApp.repository.redis;

import ForeignExchange.ForeignExchangeApp.model.redis.Currency;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CurrencyRepository extends CrudRepository<Currency, String> {

    @Override
    List<Currency> findAll();
}
