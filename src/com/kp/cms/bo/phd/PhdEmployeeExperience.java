package com.kp.cms.bo.phd;

import java.io.Serializable;
import java.util.Date;

public class PhdEmployeeExperience implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private PhdEmployee employee;
	private String tNameOfInstitution;
	private String tNameOfUniversity;
	private String tSubject;
	private String tYearsOfExpe;
	private String rNameOfInstitution;
	private String rNameOfTheUniversity;
	private String rSubject;
	private String rYearOfExper;
	private String pNameOfTitles;
	private String pJournalPubli;
	private String pyear;
	
	
	private Boolean teachingExperience;
	private Boolean researchExperience;
	private Boolean researchpublication;
	
	private Date createdDate;
	private String createdBy;
	private Date modifiedDate;
	private String modifiedBy;
	private Boolean isActive;
	
	
	public PhdEmployeeExperience(){
		
	}
	
	public PhdEmployeeExperience(int id,PhdEmployee employee,String tNameOfInstitution,String tNameOfUniversity, String tSubject, String tYearsOfExpe
	,String rNameOfInstitution,String rNameOfTheUniversity,String rSubject,String rYearOfExper,String pNameOfTitles,String pJournalPubli,String pyear,
	Boolean teachingExperience,Boolean researchExperience,Boolean researchpublication,Date createdDate,String createdBy,Date modifiedDate,String modifiedBy,Boolean isActive)
	  {
		super();
		this.id = id;
		this.employee = employee;
		this.tNameOfInstitution = tNameOfInstitution;
		this.tNameOfUniversity = tNameOfUniversity;
		this.tSubject = tSubject;
		this.tYearsOfExpe = tYearsOfExpe;
		this.rNameOfInstitution = rNameOfInstitution;
		this.rNameOfTheUniversity = rNameOfTheUniversity;
		this.rSubject = rSubject;
		this.rYearOfExper = rYearOfExper;
		this.pNameOfTitles = pNameOfTitles;
		this.pJournalPubli = pJournalPubli;
		this.pyear = pyear;
		this.teachingExperience = teachingExperience;
		this.researchExperience = researchExperience;
		this.researchpublication = researchpublication;
		
		this.createdDate = createdDate;
		this.createdBy = createdBy;
		this.modifiedDate = modifiedDate;
		this.modifiedBy = modifiedBy;
		this.isActive = isActive;
	}
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public PhdEmployee getEmployee() {
		return employee;
	}

	public void setEmployee(PhdEmployee employee) {
		this.employee = employee;
	}

	public String gettNameOfInstitution() {
		return tNameOfInstitution;
	}

	public void settNameOfInstitution(String tNameOfInstitution) {
		this.tNameOfInstitution = tNameOfInstitution;
	}

	public String gettNameOfUniversity() {
		return tNameOfUniversity;
	}

	public void settNameOfUniversity(String tNameOfUniversity) {
		this.tNameOfUniversity = tNameOfUniversity;
	}

	public String gettSubject() {
		return tSubject;
	}

	public void settSubject(String tSubject) {
		this.tSubject = tSubject;
	}

	public String gettYearsOfExpe() {
		return tYearsOfExpe;
	}

	public void settYearsOfExpe(String tYearsOfExpe) {
		this.tYearsOfExpe = tYearsOfExpe;
	}

	public String getrNameOfInstitution() {
		return rNameOfInstitution;
	}

	public void setrNameOfInstitution(String rNameOfInstitution) {
		this.rNameOfInstitution = rNameOfInstitution;
	}

	public String getrNameOfTheUniversity() {
		return rNameOfTheUniversity;
	}

	public void setrNameOfTheUniversity(String rNameOfTheUniversity) {
		this.rNameOfTheUniversity = rNameOfTheUniversity;
	}

	public String getrSubject() {
		return rSubject;
	}

	public void setrSubject(String rSubject) {
		this.rSubject = rSubject;
	}

	public String getrYearOfExper() {
		return rYearOfExper;
	}

	public void setrYearOfExper(String rYearOfExper) {
		this.rYearOfExper = rYearOfExper;
	}

	public String getpNameOfTitles() {
		return pNameOfTitles;
	}

	public void setpNameOfTitles(String pNameOfTitles) {
		this.pNameOfTitles = pNameOfTitles;
	}

	public String getpJournalPubli() {
		return pJournalPubli;
	}

	public void setpJournalPubli(String pJournalPubli) {
		this.pJournalPubli = pJournalPubli;
	}

	public String getPyear() {
		return pyear;
	}

	public void setPyear(String pyear) {
		this.pyear = pyear;
	}

	public Boolean getTeachingExperience() {
		return teachingExperience;
	}

	public void setTeachingExperience(Boolean teachingExperience) {
		this.teachingExperience = teachingExperience;
	}

	public Boolean getResearchExperience() {
		return researchExperience;
	}

	public void setResearchExperience(Boolean researchExperience) {
		this.researchExperience = researchExperience;
	}

	public Boolean getResearchpublication() {
		return researchpublication;
	}

	public void setResearchpublication(Boolean researchpublication) {
		this.researchpublication = researchpublication;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}



}
