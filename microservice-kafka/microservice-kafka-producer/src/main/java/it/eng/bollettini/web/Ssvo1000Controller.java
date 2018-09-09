package it.eng.bollettini.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import it.eng.bollettini.business.Ssvo1000Service;
import it.eng.bollettini.interfacce.Ssvo1000ServiceInterface;
import it.eng.bollettini.model.Ssvo1000In;
import it.eng.bollettini.model.Ssvo1000Out;

@RestController
public class Ssvo1000Controller {
	
	@Autowired
	private final Ssvo1000ServiceInterface ssvo1000Service;
	
	public Ssvo1000Controller(Ssvo1000Service ssvo1000Service) {
		this.ssvo1000Service = ssvo1000Service;
	}
	
	@PostMapping("/ssvo1000")
	public Ssvo1000Out ssvo1000(@RequestBody Ssvo1000In parametri) {
		return ssvo1000Service.principale(parametri);
	}
}
