package it.eng.bollettini.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import it.eng.bollettini.business.DrtbrpolService;
import it.eng.bollettini.interfacce.DrtbrpolServiceInterface;
import it.eng.bollettini.model.Drtbrpol;

@RestController
public class DrtbrpolController {

	private final Logger LOG = LoggerFactory.getLogger(DrtbrpolController.class);

	@Autowired
	private final DrtbrpolServiceInterface drtbrpolService;

	public DrtbrpolController(DrtbrpolService drtbrpolService) {
		this.drtbrpolService = drtbrpolService;
	}
	
	@PostMapping("/create")
	public void addNewDrtbrpol(@RequestBody Drtbrpol drtbrpol) {
		LOG.info("Scrivo sul topic");
		drtbrpolService.sendOnTopic(drtbrpol);
	}
	
	@GetMapping("/get")
	@ResponseBody
	public Drtbrpol getDrtbrpol() {
		Drtbrpol oggetto = new Drtbrpol();
		oggetto.setAutorAt("autorAt"); oggetto.setAutorDt("autorDt"); oggetto.setAutorVr("autorVr"); oggetto.setCellNumer("cellNumer");
		oggetto.setCellPrefi("cellPrefi"); oggetto.setCoperAt("coperAt"); oggetto.setCoperDt("coperDt");
		LOG.info("Visualizzo");
		return oggetto;
	}
}
