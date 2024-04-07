package ForeignExchange.ForeignExchangeApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ForeignExchangeAppApplication {
	public static void main(String[] args) {
		SpringApplication.run(ForeignExchangeAppApplication.class, args);
	}

}
