package com.kp.cms.bo.admin;

public class AdditionalFees {
	private int id;
	private String feesCode;
	private String feesName;
	private String accGia;
	private String accNgia;
	private int amount;
	private String classes;
	private Boolean loadToHelp;
	private int academicYear;
	public AdditionalFees() {
		
	}
	public AdditionalFees(int id, String feesCode, String feesName,
			String accGia, String accNgia, int amount, String classes,
			Boolean loadToHelp, int academicYear) {
		super();
		this.id = id;
		this.feesCode = feesCode;
		this.feesName = feesName;
		this.accGia = accGia;
		this.accNgia = accNgia;
		this.amount = amount;
		this.classes = classes;
		this.loadToHelp = loadToHelp;
		this.academicYear = academicYear;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFeesCode() {
		return feesCode;
	}
	public void setFeesCode(String feesCode) {
		this.feesCode = feesCode;
	}
	public String getFeesName() {
		return feesName;
	}
	public void setFeesName(String feesName) {
		this.feesName = feesName;
	}
	public String getAccGia() {
		return accGia;
	}
	public void setAccGia(String accGia) {
		this.accGia = accGia;
	}
	public String getAccNgia() {
		return accNgia;
	}
	public void setAccNgia(String accNgia) {
		this.accNgia = accNgia;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public String getClasses() {
		return classes;
	}
	public void setClasses(String classes) {
		this.classes = classes;
	}
	public Boolean getLoadToHelp() {
		return loadToHelp;
	}
	public void setLoadToHelp(Boolean loadToHelp) {
		this.loadToHelp = loadToHelp;
	}
	public int getAcademicYear() {
		return academicYear;
	}
	public void setAcademicYear(int academicYear) {
		this.academicYear = academicYear;
	}
	
}
