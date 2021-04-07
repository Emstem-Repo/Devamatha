package com.kp.cms.bo.employee;

public class EmpPhdMPhilThesisGuidedBO {
	
	private int id;
	private String title;
	private String subject;
	private String companyInstitution;
	private String place;
	private String monthYear;
	private String nameStudent;
	private Boolean isPhdMPhilThesisGuided;
	
	
	
	public EmpPhdMPhilThesisGuidedBO(int id) {
		super();
		this.id = id;
	}
	public EmpPhdMPhilThesisGuidedBO() {
		super();
	}
	public EmpPhdMPhilThesisGuidedBO(String title, String subject,
			String companyInstitution, String place, String monthYear,
			String nameStudent, Boolean isPhdMPhilThesisGuided) {
		super();
		this.title = title;
		this.subject = subject;
		this.companyInstitution = companyInstitution;
		this.place = place;
		this.monthYear = monthYear;
		this.nameStudent = nameStudent;
		this.isPhdMPhilThesisGuided = isPhdMPhilThesisGuided;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getCompanyInstitution() {
		return companyInstitution;
	}
	public void setCompanyInstitution(String companyInstitution) {
		this.companyInstitution = companyInstitution;
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
	public String getNameStudent() {
		return nameStudent;
	}
	public void setNameStudent(String nameStudent) {
		this.nameStudent = nameStudent;
	}
	public Boolean getIsPhdMPhilThesisGuided() {
		return isPhdMPhilThesisGuided;
	}
	public void setIsPhdMPhilThesisGuided(Boolean isPhdMPhilThesisGuided) {
		this.isPhdMPhilThesisGuided = isPhdMPhilThesisGuided;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	

}
