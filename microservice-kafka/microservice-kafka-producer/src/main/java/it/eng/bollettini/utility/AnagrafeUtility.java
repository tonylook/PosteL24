package it.eng.bollettini.utility;

import it.eng.bollettini.model.ACS108CX;

public class AnagrafeUtility {
	//Reperimento Codice Fiscale tramite NDG (Chiamata ad Anagrafe Mockata)
	public ACS108CX getCFAnagrafe(Long ndg) {
		ACS108CX anagrafe = new ACS108CX();
		anagrafe.setCodFiscale("RSSMRA75L01H501A");
		return anagrafe;
	}
	
	//Reperimento Natura Giuridica tramite Codice Fiscale (Chiamata ad Anagrafe Mockata)
	public ACS108CX getNatGiuridicaAnagrafe(String codFisc) {
		ACS108CX anagrafe = new ACS108CX();
		anagrafe.setNatGiuridica("PF");
		anagrafe.setrNdg(Long.parseLong("978563254128"));
		return anagrafe;
	}
}
