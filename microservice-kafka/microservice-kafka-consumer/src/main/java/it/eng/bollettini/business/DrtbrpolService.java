package it.eng.bollettini.business;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.eng.bollettini.interfacce.DrtbrpolServiceInterface;
import it.eng.bollettini.model.Drtbrpol;
import it.eng.bollettini.repository.DrtbrpolRepository;

@Service
public class DrtbrpolService implements DrtbrpolServiceInterface {

	private final Logger log = LoggerFactory.getLogger(DrtbrpolService.class);
	
	@Autowired
	private DrtbrpolRepository drtbrpolRepository;
	
	public void insertRecord(Drtbrpol drtbrpol) {
		if (drtbrpolRepository.existsById(drtbrpol.getNdg())) {
			log.info("L'NDG {} esiste già", drtbrpol.getNdg());
		} else {
			drtbrpolRepository.save(drtbrpol);
		}
	}
}
