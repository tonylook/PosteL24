package it.eng.bollettini.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import it.eng.bollettini.interfacce.DrtbrpolServiceInterface;
import it.eng.bollettini.model.Drtbrpol;

@Component
public class DrtbrpolKafkaListener {

	private final Logger log = LoggerFactory.getLogger(DrtbrpolKafkaListener.class);

	@Autowired
	private DrtbrpolServiceInterface drtbrpolService;

	@KafkaListener(topics = "drtbrpol")
	public void insertDrtbrpol(Drtbrpol drtbrpol, Acknowledgment acknowledgment) {
		log.info("Ricevuto Kafka NDG " + drtbrpol.getNdg());
		drtbrpolService.insertRecord(drtbrpol);
		acknowledgment.acknowledge();
	}

}
