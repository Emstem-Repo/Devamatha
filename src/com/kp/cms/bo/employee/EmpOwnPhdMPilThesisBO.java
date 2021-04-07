package com.kp.cms.bo.employee;

public class EmpOwnPhdMPilThesisBO {
	
	private int id;
	private String title;
	private String companyInstitution;
	private String nameGuide;
	private String place;
	private String monthYear;
	private String subject;
	private Boolean isOwnPhdMphilThesis;
	
	public EmpOwnPhdMPilThesisBO()
	{}
	
	public EmpOwnPhdMPilThesisBO(int id) {
		super();
		this.id = id;
	}
	public EmpOwnPhdMPilThesisBO(String title, String companyInstitution,
			String nameGuide, String place, String monthYear, String subject,
			Boolean isOwnPhdMphilThesis) {
		super();
		this.title = title;
		this.companyInstitution = companyInstitution;
		this.nameGuide = nameGuide;
		this.place = place;
		this.monthYear = monthYear;
		this.subject = subject;
		this.isOwnPhdMphilThesis = isOwnPhdMphilThesis; 
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getCompanyInstitution() {
		return companyInstitution;
	}
	public void setCompanyInstitution(String companyInstitution) {
		this.companyInstitution = companyInstitution;
	}
	public String getNameGuide() {
		return nameGuide;
	}
	public void setNameGuide(String nameGuide) {
		this.nameGuide = nameGuide;
	}
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	public String getMonthYear() {
		return monthYear;
	}
	public void setMonthYear(String monthYear) {
		this.monthYear = monthYear;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public Boolean getIsOwnPhdMphilThesis() {
		return isOwnPhdMphilThesis;
	}
	public void setIsOwnPhdMphilThesis(Boolean isOwnPhdMphilThesis) {
		this.isOwnPhdMphilThesis = isOwnPhdMphilThesis;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	

}
