package com.kp.cms.bo.admin;

import java.io.Serializable;

public class FeesClassFee implements Serializable {
	private int id;
	private String classes;
	private Boolean gia;
	private int fees;
	private int maintFee;
	private Boolean outKarFe;
	private int selFnFee;
	private Boolean applForNri;
	private Boolean aplForAdad;
	private int slfnSpFee;
	private int cetFees;
	private int scstbtFee;
	private int sscstMaint;
	private int nriFees;
	private int nriMFees;
	private int forgnFees;
	private int forgnMFees;
	private int insSpFees;
	private int academicYear;
	public FeesClassFee() {
		
	}
	public FeesClassFee(int id, String classes, Boolean gia, int fees,
			int maintFee, Boolean outKarFe, int selFnFee, Boolean applForNri,
			Boolean aplForAdad, int slfnSpFee, int cetFees, int scstbtFee,
			int sscstMaint, int nriFees, int nriMFees, int forgnFees,
			int forgnMFees, int insSpFees, int academicYear) {
		super();
		this.id = id;
		this.classes = classes;
		this.gia = gia;
		this.fees = fees;
		this.maintFee = maintFee;
		this.outKarFe = outKarFe;
		this.selFnFee = selFnFee;
		this.applForNri = applForNri;
		this.aplForAdad = aplForAdad;
		this.slfnSpFee = slfnSpFee;
		this.cetFees = cetFees;
		this.scstbtFee = scstbtFee;
		this.sscstMaint = sscstMaint;
		this.nriFees = nriFees;
		this.nriMFees = nriMFees;
		this.forgnFees = forgnFees;
		this.forgnMFees = forgnMFees;
		this.insSpFees = insSpFees;
		this.academicYear = academicYear;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getClasses() {
		return classes;
	}
	public void setClasses(String classes) {
		this.classes = classes;
	}
	public Boolean getGia() {
		return gia;
	}
	public void setGia(Boolean gia) {
		this.gia = gia;
	}
	public int getFees() {
		return fees;
	}
	public void setFees(int fees) {
		this.fees = fees;
	}
	public int getMaintFee() {
		return maintFee;
	}
	public void setMaintFee(int maintFee) {
		this.maintFee = maintFee;
	}
	public Boolean getOutKarFe() {
		return outKarFe;
	}
	public void setOutKarFe(Boolean outKarFe) {
		this.outKarFe = outKarFe;
	}
	public int getSelFnFee() {
		return selFnFee;
	}
	public void setSelFnFee(int selFnFee) {
		this.selFnFee = selFnFee;
	}
	public Boolean getApplForNri() {
		return applForNri;
	}
	public void setApplForNri(Boolean applForNri) {
		this.applForNri = applForNri;
	}
	public Boolean getAplForAdad() {
		return aplForAdad;
	}
	public void setAplForAdad(Boolean aplForAdad) {
		this.aplForAdad = aplForAdad;
	}
	public int getSlfnSpFee() {
		return slfnSpFee;
	}
	public void setSlfnSpFee(int slfnSpFee) {
		this.slfnSpFee = slfnSpFee;
	}
	public int getCetFees() {
		return cetFees;
	}
	public void setCetFees(int cetFees) {
		this.cetFees = cetFees;
	}
	public int getScstbtFee() {
		return scstbtFee;
	}
	public void setScstbtFee(int scstbtFee) {
		this.scstbtFee = scstbtFee;
	}
	public int getSscstMaint() {
		return sscstMaint;
	}
	public void setSscstMaint(int sscstMaint) {
		this.sscstMaint = sscstMaint;
	}
	public int getNriFees() {
		return nriFees;
	}
	public void setNriFees(int nriFees) {
		this.nriFees = nriFees;
	}
	public int getNriMFees() {
		return nriMFees;
	}
	public void setNriMFees(int nriMFees) {
		this.nriMFees = nriMFees;
	}
	public int getForgnFees() {
		return forgnFees;
	}
	public void setForgnFees(int forgnFees) {
		this.forgnFees = forgnFees;
	}
	public int getForgnMFees() {
		return forgnMFees;
	}
	public void setForgnMFees(int forgnMFees) {
		this.forgnMFees = forgnMFees;
	}
	public int getInsSpFees() {
		return insSpFees;
	}
	public void setInsSpFees(int insSpFees) {
		this.insSpFees = insSpFees;
	}
	public int getAcademicYear() {
		return academicYear;
	}
	public void setAcademicYear(int academicYear) {
		this.academicYear = academicYear;
	}
	
}
