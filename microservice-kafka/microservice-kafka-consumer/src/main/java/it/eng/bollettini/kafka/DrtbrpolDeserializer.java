package it.eng.bollettini.kafka;

import org.springframework.kafka.support.serializer.JsonDeserializer;
import it.eng.bollettini.model.Drtbrpol;

public class DrtbrpolDeserializer extends JsonDeserializer<Drtbrpol> {

	public DrtbrpolDeserializer() {
		super(Drtbrpol.class);
	}

}
