package ForeignExchange.ForeignExchangeApp.model.redis;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@RedisHash("Currency")
public class Currency implements Serializable {
    @Id
    private String name;
    private double value;

    public Currency() {
    }

    public Currency(String name, double value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Currency{" +
                "name='" + name + '\'' +
                ", valueInEuros=" + value +
                '}';
    }
}
