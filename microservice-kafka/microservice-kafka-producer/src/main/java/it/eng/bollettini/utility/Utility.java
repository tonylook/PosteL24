package it.eng.bollettini.utility;

import it.eng.bollettini.exception.ControlException;

public class Utility {
	//Controllo del giorno Feriale (Chiamata a Routine esterna Mockata)
	public boolean isFeriale(String data) {
		return true;
	}

	//Controllo del prefisso del numero di telefono
	public boolean ctrPre(String prefisso) throws ControlException{
		boolean swContinua=false;
		if (prefisso.trim().equals("+39")) swContinua=true;
		else throw new ControlException("Il prefisso inserito "+prefisso+" non è valido");
		return swContinua;
	}

	//Controllo validità del numero di telefono
	public boolean ctrNum(String numero) throws ControlException{
		boolean swContinua=false;
		if(numero.trim().length()<11) {
			if(numero.trim().substring(0, 1).equals("3")) swContinua=true;
			else throw new ControlException("Il numero inserito "+numero+" non è italiano");
		}else throw new ControlException("Il numero inserito "+numero+" ha più di 10 cifre");
		return swContinua;
	}
}
