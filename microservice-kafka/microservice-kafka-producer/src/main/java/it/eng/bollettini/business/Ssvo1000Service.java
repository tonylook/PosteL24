package it.eng.bollettini.business;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import it.eng.bollettini.exception.ControlException;
import it.eng.bollettini.interfacce.Ssvo1000ServiceInterface;
import it.eng.bollettini.model.Drtbrpol;
import it.eng.bollettini.model.Ssvo1000In;
import it.eng.bollettini.model.Ssvo1000Out;
import it.eng.bollettini.utility.AnagrafeUtility;
import it.eng.bollettini.utility.Ssvo1000Utility;
import it.eng.bollettini.utility.Utility;

@Service
public class Ssvo1000Service implements Ssvo1000ServiceInterface{

	private final Logger log = LoggerFactory.getLogger(Ssvo1000Service.class);

	public Ssvo1000Out principale(Ssvo1000In ssvo1000in) {
		String data = ssvo1000in.getData();
		String pre = ssvo1000in.getPrefInt();
		String num = ssvo1000in.getNumCell();
		Ssvo1000Out response = new Ssvo1000Out();
		Utility utility = new Utility();
		Ssvo1000Utility ssvo1000Utility = new Ssvo1000Utility();
		AnagrafeUtility anagrafeUtility = new AnagrafeUtility();
		List<Drtbrpol> drtbrpol = new ArrayList<Drtbrpol>();
		if (utility.isFeriale(data)) { //Controllo se il giorno è lavorativo (mockato)
			try {
				if (utility.ctrPre(pre)) { //Effettuo controlli sulla validità del prefisso
					if (utility.ctrNum(num)) { //effettuo controlli sulla validità del numero
						drtbrpol = ssvo1000Utility.getDrtbrpol(num, "A"); //Ottengo i record da una query DB" (mockato)
						String statoCert;
						statoCert = ssvo1000Utility.ctrRich(drtbrpol); //Controllo se la richiesta è certificata
						Long ndg = drtbrpol.get(0).getNdg();
						String codFisc=anagrafeUtility.getCFAnagrafe(ndg).getCodFiscale();
						response.setCodFisc(codFisc);
						response.setStatoCert(statoCert);
						response.setRetCode("0");
					}
				}
			} catch (ControlException e) {
				log.error(e.getMessage());
				response.setDescErr(e.getMessage());
			}
		}
		return response;
	}
}
