package it.eng.bollettini.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Drtbrpol {

	@Id
	private Long ndg;
	private Long rapport;
	private Long dataAtt;
	private Long oraAtt;
	private String termAtt;
	private String coperAt;
	private String autorAt;
	private String dipeAtt;
	private String statRich;
	private String statSvinq;
	private String statSvbuo;
	private Long dtCeSvbuo;
	private String cellPrefi;
	private String cellNumer;
	private Long dataDtt;
	private Long oraDtt;
	private String termDtt;
	private String coperDt;
	private String autorDt;
	private String dipeaDt;
	private Long dataVar;
	private Long oraVar;
	private String termVar;
	private String coperVr;
	private String autorVr;
	private String dipeaVr;
	
	public Long getNdg() {
		return ndg;
	}
	public Long getRapport() {
		return rapport;
	}
	public Long getDataAtt() {
		return dataAtt;
	}
	public Long getOraAtt() {
		return oraAtt;
	}
	public String getTermAtt() {
		return termAtt;
	}
	public String getCoperAt() {
		return coperAt;
	}
	public String getAutorAt() {
		return autorAt;
	}
	public String getDipeAtt() {
		return dipeAtt;
	}
	public String getStatRich() {
		return statRich;
	}
	public String getStatSvinq() {
		return statSvinq;
	}
	public String getStatSvbuo() {
		return statSvbuo;
	}
	public Long getDtCeSvbuo() {
		return dtCeSvbuo;
	}
	public String getCellPrefi() {
		return cellPrefi;
	}
	public String getCellNumer() {
		return cellNumer;
	}
	public Long getDataDtt() {
		return dataDtt;
	}
	public Long getOraDtt() {
		return oraDtt;
	}
	public String getTermDtt() {
		return termDtt;
	}
	public String getCoperDt() {
		return coperDt;
	}
	public String getAutorDt() {
		return autorDt;
	}
	public String getDipeaDt() {
		return dipeaDt;
	}
	public Long getDataVar() {
		return dataVar;
	}
	public Long getOraVar() {
		return oraVar;
	}
	public String getTermVar() {
		return termVar;
	}
	public String getCoperVr() {
		return coperVr;
	}
	public String getAutorVr() {
		return autorVr;
	}
	public String getDipeaVr() {
		return dipeaVr;
	}
	public void setNdg(Long ndg) {
		this.ndg = ndg;
	}
	public void setRapport(Long rapport) {
		this.rapport = rapport;
	}
	public void setDataAtt(Long dataAtt) {
		this.dataAtt = dataAtt;
	}
	public void setOraAtt(Long oraAtt) {
		this.oraAtt = oraAtt;
	}
	public void setTermAtt(String termAtt) {
		this.termAtt = termAtt;
	}
	public void setCoperAt(String coperAt) {
		this.coperAt = coperAt;
	}
	public void setAutorAt(String autorAt) {
		this.autorAt = autorAt;
	}
	public void setDipeAtt(String dipeAtt) {
		this.dipeAtt = dipeAtt;
	}
	public void setStatRich(String statRich) {
		this.statRich = statRich;
	}
	public void setStatSvinq(String statSvinq) {
		this.statSvinq = statSvinq;
	}
	public void setStatSvbuo(String statSvbuo) {
		this.statSvbuo = statSvbuo;
	}
	public void setDtCeSvbuo(Long dtCeSvbuo) {
		this.dtCeSvbuo = dtCeSvbuo;
	}
	public void setCellPrefi(String cellPrefi) {
		this.cellPrefi = cellPrefi;
	}
	public void setCellNumer(String cellNumer) {
		this.cellNumer = cellNumer;
	}
	public void setDataDtt(Long dataDtt) {
		this.dataDtt = dataDtt;
	}
	public void setOraDtt(Long oraDtt) {
		this.oraDtt = oraDtt;
	}
	public void setTermDtt(String termDtt) {
		this.termDtt = termDtt;
	}
	public void setCoperDt(String coperDt) {
		this.coperDt = coperDt;
	}
	public void setAutorDt(String autorDt) {
		this.autorDt = autorDt;
	}
	public void setDipeaDt(String dipeaDt) {
		this.dipeaDt = dipeaDt;
	}
	public void setDataVar(Long dataVar) {
		this.dataVar = dataVar;
	}
	public void setOraVar(Long oraVar) {
		this.oraVar = oraVar;
	}
	public void setTermVar(String termVar) {
		this.termVar = termVar;
	}
	public void setCoperVr(String coperVr) {
		this.coperVr = coperVr;
	}
	public void setAutorVr(String autorVr) {
		this.autorVr = autorVr;
	}
	public void setDipeaVr(String dipeaVr) {
		this.dipeaVr = dipeaVr;
	}
	
}
