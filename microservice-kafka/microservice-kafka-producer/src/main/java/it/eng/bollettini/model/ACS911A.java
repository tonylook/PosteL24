package it.eng.bollettini.model;

import java.util.List;

class ACS911A {
	private Long banca;
	private Long ndg;
	private String servizio;
	private String categoria;
	private String filiale;
	private Long numero;
	private String serInput;
	private String catInput;
	private String filInput;
	private Long flgChiamata;
	private Long ndgRif;
	private String anagRif;
	private String fgiurRif;
	private List <ACS911ElemRap> elemRap;
	private String retCode;
	private Long codAbi;
	
	public Long getBanca() {
		return banca;
	}
	public void setBanca(Long banca) {
		this.banca = banca;
	}
	public Long getNdg() {
		return ndg;
	}
	public void setNdg(Long ndg) {
		this.ndg = ndg;
	}
	public String getServizio() {
		return servizio;
	}
	public void setServizio(String servizio) {
		this.servizio = servizio;
	}
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	public String getFiliale() {
		return filiale;
	}
	public void setFiliale(String filiale) {
		this.filiale = filiale;
	}
	public Long getNumero() {
		return numero;
	}
	public void setNumero(Long numero) {
		this.numero = numero;
	}
	public String getSerInput() {
		return serInput;
	}
	public void setSerInput(String serInput) {
		this.serInput = serInput;
	}
	public String getCatInput() {
		return catInput;
	}
	public void setCatInput(String catInput) {
		this.catInput = catInput;
	}
	public String getFilInput() {
		return filInput;
	}
	public void setFilInput(String filInput) {
		this.filInput = filInput;
	}
	public Long getFlgChiamata() {
		return flgChiamata;
	}
	public void setFlgChiamata(Long flgChiamata) {
		this.flgChiamata = flgChiamata;
	}
	public Long getNdgRif() {
		return ndgRif;
	}
	public void setNdgRif(Long ndgRif) {
		this.ndgRif = ndgRif;
	}
	public String getAnagRif() {
		return anagRif;
	}
	public void setAnagRif(String anagRif) {
		this.anagRif = anagRif;
	}
	public String getFgiurRif() {
		return fgiurRif;
	}
	public void setFgiurRif(String fgiurRif) {
		this.fgiurRif = fgiurRif;
	}
	public List<ACS911ElemRap> getElemRap() {
		return elemRap;
	}
	public void setElemRap(List<ACS911ElemRap> elemRap) {
		this.elemRap = elemRap;
	}
	public String getRetCode() {
		return retCode;
	}
	public void setRetCode(String retCode) {
		this.retCode = retCode;
	}
	public Long getCodAbi() {
		return codAbi;
	}
	public void setCodAbi(Long codAbi) {
		this.codAbi = codAbi;
	}
}
