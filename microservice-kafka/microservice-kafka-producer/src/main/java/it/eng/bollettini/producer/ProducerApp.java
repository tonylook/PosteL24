package it.eng.bollettini.producer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"it.eng.bollettini"})
public class ProducerApp {

	public static void main(String[] args) {
		SpringApplication.run(ProducerApp.class, args);
	}

}
