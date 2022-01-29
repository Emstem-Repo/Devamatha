package com.kp.cms.bo.employee;

import java.io.Serializable;
import java.util.Date;

import com.kp.cms.bo.admin.EmpOnlineResume;

public class EmpOnlinePreviousExperience implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String empOrganization;
	private String empDesignation;
	private int expYears;
	private int expMonths;
	private boolean teachingExperience;
	private boolean industryExperience;
	private boolean active;
	private Date createdDate;
	private String createdBy;
	private Date modifiedDate;
	private String modifiedBy;
	
	private Date toDate;
	private Date fromDate;
	private EmpOnlineResume empOnlineResume;
	private String grossSalary;
	
	public EmpOnlinePreviousExperience(){
		
	}
	
	/**
	 * @param id
	 * @param empOrganization
	 * @param empDesignation
	 * @param expYears
	 * @param expMonths
	 * @param teachingExperience
	 * @param industryExperience
	 * @param active
	 * @param createdDate
	 * @param createdBy
	 * @param modifiedDate
	 * @param modifiedBy
	 * @param empOnlineResume
	 */
	public EmpOnlinePreviousExperience(int id, String empOrganization,
			String empDesignation, int expYears, int expMonths,
			boolean teachingExperience, boolean industryExperience,
			boolean active, Date createdDate, String createdBy,
			Date modifiedDate, String modifiedBy,
			EmpOnlineResume empOnlineResume, String grossSalary, Date toDate, Date fromDate) {
		super();
		this.id = id;
		this.grossSalary= grossSalary;
		this.toDate=toDate;
		this.fromDate= fromDate;
		this.empOrganization = empOrganization;
		this.empDesignation = empDesignation;
		this.expYears = expYears;
		this.expMonths = expMonths;
		this.teachingExperience = teachingExperience;
		this.industryExperience = industryExperience;
		this.active = active;
		this.createdDate = createdDate;
		this.createdBy = createdBy;
		this.modifiedDate = modifiedDate;
		this.modifiedBy = modifiedBy;
		this.empOnlineResume = empOnlineResume;
	}

	public int getId() {
		return id;
	}

	public String getEmpOrganization() {
		return empOrganization;
	}

	public String getEmpDesignation() {
		return empDesignation;
	}

	public int getExpYears() {
		return expYears;
	}

	public int getExpMonths() {
		return expMonths;
	}

	public boolean isTeachingExperience() {
		return teachingExperience;
	}

	public boolean isIndustryExperience() {
		return industryExperience;
	}

	public boolean isActive() {
		return active;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public EmpOnlineResume getEmpOnlineResume() {
		return empOnlineResume;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setEmpOrganization(String empOrganization) {
		this.empOrganization = empOrganization;
	}

	public void setEmpDesignation(String empDesignation) {
		this.empDesignation = empDesignation;
	}

	public void setExpYears(int expYears) {
		this.expYears = expYears;
	}

	public void setExpMonths(int expMonths) {
		this.expMonths = expMonths;
	}

	public void setTeachingExperience(boolean teachingExperience) {
		this.teachingExperience = teachingExperience;
	}

	public void setIndustryExperience(boolean industryExperience) {
		this.industryExperience = industryExperience;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public void setEmpOnlineResume(EmpOnlineResume empOnlineResume) {
		this.empOnlineResume = empOnlineResume;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public String getGrossSalary() {
		return grossSalary;
	}

	public void setGrossSalary(String grossSalary) {
		this.grossSalary = grossSalary;
	}

	

	
}
