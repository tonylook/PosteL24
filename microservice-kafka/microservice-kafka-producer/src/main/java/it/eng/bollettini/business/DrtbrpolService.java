package it.eng.bollettini.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import it.eng.bollettini.interfacce.DrtbrpolServiceInterface;
import it.eng.bollettini.model.Drtbrpol;

@Service
public class DrtbrpolService implements DrtbrpolServiceInterface{
	
	@Autowired
	private KafkaTemplate<String, Drtbrpol> kafkaTemplate;
	
	public void sendOnTopic(Drtbrpol drtbrpol) {
		kafkaTemplate.send("drtbrpol", drtbrpol.getNdg() + "created", drtbrpol);
	}
}
