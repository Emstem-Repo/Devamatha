package com.kp.cms.bo.admin;

// Generated Sep 25, 2009 3:08:41 PM by Hibernate Tools 3.2.0.b9

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.kp.cms.bo.employee.EmployeeInfoBO;

/**
 * EmpPreviousOrg generated by hbm2java
 */
public class EmpPreviousOrg implements java.io.Serializable {

	private int id;
	private EmpFunctionalArea empFunctionalArea;
	private EmpQualificationLevel empQualificationLevel;
	private EmpEducationMaster empEducationMaster;
	private EmpOnlineResume empOnlineResume;
	private EmployeeInfoBO employeeInfoBO;
	
	private Boolean isCurrentlyWorking;
	private Integer teachingExpMonths;
	private Integer teachingExpYears;
	private Integer industryExpMonths;
	private Integer industryExpYears;
	
	private Integer totalExpMonths;
	private Integer totalExpYears;
	private String currentDesignation;
	private String currentOrganisation;
	
	private Integer currentSalaryLakhs;
	private Integer currentSalaryThousands;
	
	private String previousOrgName;
	
	
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;
	private Boolean isActive;
	

	

	

	public EmpPreviousOrg(int id, EmpFunctionalArea empFunctionalArea,
			EmpQualificationLevel empQualificationLevel,
			EmpEducationMaster empEducationMaster, Boolean isCurrentlyWorking,
			Integer teachingExpMonths, Integer teachingExpYears,
			Integer industryExpMonths, Integer industryExpYears,
			Integer totalExpMonths, Integer totalExpYears,
			String currentDesignation, String currentOrganisation,
			Integer currentSalaryLakhs, Integer currentSalaryThousands,
			String previousOrgName, String createdBy, Date createdDate,
			String modifiedBy, Date lastModifiedDate, Boolean isActive) {
		super();
		this.id = id;
		this.empFunctionalArea = empFunctionalArea;
		this.empQualificationLevel = empQualificationLevel;
		this.empEducationMaster = empEducationMaster;
		this.isCurrentlyWorking = isCurrentlyWorking;
		this.teachingExpMonths = teachingExpMonths;
		this.teachingExpYears = teachingExpYears;
		this.industryExpMonths = industryExpMonths;
		this.industryExpYears = industryExpYears;
		this.totalExpMonths = totalExpMonths;
		this.totalExpYears = totalExpYears;
		this.currentDesignation = currentDesignation;
		this.currentOrganisation = currentOrganisation;
		this.currentSalaryLakhs = currentSalaryLakhs;
		this.setCurrentSalaryThousands(currentSalaryThousands);
		this.previousOrgName = previousOrgName;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.lastModifiedDate = lastModifiedDate;
		this.isActive = isActive;
	}

	public EmpPreviousOrg() {
		
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public EmpFunctionalArea getEmpFunctionalArea() {
		return this.empFunctionalArea;
	}

	public void setEmpFunctionalArea(EmpFunctionalArea empFunctionalArea) {
		this.empFunctionalArea = empFunctionalArea;
	}

	public String getPreviousOrgName() {
		return this.previousOrgName;
	}

	public void setPreviousOrgName(String previousOrgName) {
		this.previousOrgName = previousOrgName;
	}

	

	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getModifiedBy() {
		return this.modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getLastModifiedDate() {
		return this.lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public Boolean getIsActive() {
		return this.isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public EmpQualificationLevel getEmpQualificationLevel() {
		return empQualificationLevel;
	}

	public void setEmpQualificationLevel(EmpQualificationLevel empQualificationLevel) {
		this.empQualificationLevel = empQualificationLevel;
	}

	public EmpEducationMaster getEmpEducationMaster() {
		return empEducationMaster;
	}

	public void setEmpEducationMaster(EmpEducationMaster empEducationMaster) {
		this.empEducationMaster = empEducationMaster;
	}

	public Boolean getIsCurrentlyWorking() {
		return isCurrentlyWorking;
	}

	public void setIsCurrentlyWorking(Boolean isCurrentlyWorking) {
		this.isCurrentlyWorking = isCurrentlyWorking;
	}

	public Integer getTeachingExpMonths() {
		return teachingExpMonths;
	}

	public void setTeachingExpMonths(Integer teachingExpMonths) {
		this.teachingExpMonths = teachingExpMonths;
	}

	public Integer getTeachingExpYears() {
		return teachingExpYears;
	}

	public void setTeachingExpYears(Integer teachingExpYears) {
		this.teachingExpYears = teachingExpYears;
	}

	public Integer getIndustryExpMonths() {
		return industryExpMonths;
	}

	public void setIndustryExpMonths(Integer industryExpMonths) {
		this.industryExpMonths = industryExpMonths;
	}

	public Integer getIndustryExpYears() {
		return industryExpYears;
	}

	public void setIndustryExpYears(Integer industryExpYears) {
		this.industryExpYears = industryExpYears;
	}

	public Integer getTotalExpMonths() {
		return totalExpMonths;
	}

	public void setTotalExpMonths(Integer totalExpMonths) {
		this.totalExpMonths = totalExpMonths;
	}

	public Integer getTotalExpYears() {
		return totalExpYears;
	}

	public void setTotalExpYears(Integer totalExpYears) {
		this.totalExpYears = totalExpYears;
	}

	public String getCurrentDesignation() {
		return currentDesignation;
	}

	public void setCurrentDesignation(String currentDesignation) {
		this.currentDesignation = currentDesignation;
	}

	public String getCurrentOrganisation() {
		return currentOrganisation;
	}

	public void setCurrentOrganisation(String currentOrganisation) {
		this.currentOrganisation = currentOrganisation;
	}

	

	public void setCurrentSalaryLakhs(Integer currentSalaryLakhs) {
		this.currentSalaryLakhs = currentSalaryLakhs;
	}

	public Integer getCurrentSalaryLakhs() {
		return currentSalaryLakhs;
	}

	public void setCurrentSalaryThousands(Integer currentSalaryThousands) {
		this.currentSalaryThousands = currentSalaryThousands;
	}

	public Integer getCurrentSalaryThousands() {
		return currentSalaryThousands;
	}

	public void setEmpOnlineResume(EmpOnlineResume empOnlineResume) {
		this.empOnlineResume = empOnlineResume;
	}

	public EmpOnlineResume getEmpOnlineResume() {
		return empOnlineResume;
	}

	public EmployeeInfoBO getEmployeeInfoBO() {
		return employeeInfoBO;
	}

	public void setEmployeeInfoBO(EmployeeInfoBO employeeInfoBO) {
		this.employeeInfoBO = employeeInfoBO;
	}

	

	

	

}