package it.eng.bollettini.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
@EnableMongoRepositories(basePackages="it.eng.bollettini.repository")
@SpringBootApplication(scanBasePackages = {"it.eng.bollettini"})
public class ConsumerApp {

	public static void main(String[] args) {
		SpringApplication.run(ConsumerApp.class, args);
	}

}
