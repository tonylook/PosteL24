package it.eng.bollettini.utility;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import it.eng.bollettini.exception.ControlException;
import it.eng.bollettini.model.Drtbrpol;

public class Ssvo1000Utility {
	//Reperimento richieste per numCell e statRich (Chiamata al DB2 Mockata)
	public List<Drtbrpol> getDrtbrpol(String numCell, String statRich) {
		List<Drtbrpol> risposta = new ArrayList<Drtbrpol>();
		Drtbrpol record = new Drtbrpol();
		record.setNdg(Long.parseLong("786532195824"));
		record.setStatRich("A");
		record.setStatSvbuo("C");
		risposta.add(record);
		return risposta;
	}

	//Verifica certificazione richiesta
	public String ctrRich(List<Drtbrpol> drtbrpol) throws ControlException {
		int contaRec=0;
		String statoCert = null;
		for (Iterator<Drtbrpol> iterator = drtbrpol.iterator(); iterator.hasNext();) {
			Drtbrpol drtbrpol2 = (Drtbrpol) iterator.next();
			if (drtbrpol2.getStatSvbuo().equals("C") && drtbrpol2.getStatRich().equals("A"))
				contaRec++;
		}
		if (drtbrpol.isEmpty()) {
			statoCert = "A";
			throw new ControlException("servizio RPOL disattivato");
		}
		if (((!drtbrpol.isEmpty()) && contaRec == 0) || (drtbrpol.size() > 1)) {
			statoCert = "N";
			throw new ControlException("servizio RPOL non richiesto");		
		}
		if (contaRec == 1) {
			statoCert = "C";
		}
		return statoCert;
	}
}
