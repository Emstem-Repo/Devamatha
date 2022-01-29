package com.kp.cms.to.admin;

import java.io.Serializable;
import java.util.Date;



public class EmpOnlinePreviousExperienceTO implements Serializable {
	
	
	private int id;
	private String empOrganization;
	private String empDesignation;
	private String totalTeachingExperience;
	
	// to print Total Experience in online resume print
	
	private String totalExp;
	private String qualificationLevel;
	private String subjectArea;
	private String jobType;
	private String empStatus;
	private String expectedSalary;
	private String eligibilityTest;
	
	// to print Industry Experience in online resume print
	
	private String organization;
	private String designation;
	private String experience;
	
	//code added by sudhir
	private String teachingExpYears;
	private String teachingExpMonths;
	private String industryExpYears;
	private String industryExpMonths;
	private String fromDate;
	private String toDate;
	
	//
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getEmpOrganization() {
		return empOrganization;
	}
	public void setEmpOrganization(String empOrganization) {
		this.empOrganization = empOrganization;
	}
	public String getEmpDesignation() {
		return empDesignation;
	}
	public void setEmpDesignation(String empDesignation) {
		this.empDesignation = empDesignation;
	}
	public String getTotalTeachingExperience() {
		return totalTeachingExperience;
	}
	public void setTotalTeachingExperience(String totalTeachingExperience) {
		this.totalTeachingExperience = totalTeachingExperience;
	}
	public String getTotalExp() {
		return totalExp;
	}
	public void setTotalExp(String totalExp) {
		this.totalExp = totalExp;
	}
	public String getQualificationLevel() {
		return qualificationLevel;
	}
	public void setQualificationLevel(String qualificationLevel) {
		this.qualificationLevel = qualificationLevel;
	}
	public String getSubjectArea() {
		return subjectArea;
	}
	public void setSubjectArea(String subjectArea) {
		this.subjectArea = subjectArea;
	}
	public String getJobType() {
		return jobType;
	}
	public void setJobType(String jobType) {
		this.jobType = jobType;
	}
	public String getEmpStatus() {
		return empStatus;
	}
	public void setEmpStatus(String empStatus) {
		this.empStatus = empStatus;
	}
	public String getExpectedSalary() {
		return expectedSalary;
	}
	public void setExpectedSalary(String expectedSalary) {
		this.expectedSalary = expectedSalary;
	}
	public String getEligibilityTest() {
		return eligibilityTest;
	}
	public void setEligibilityTest(String eligibilityTest) {
		this.eligibilityTest = eligibilityTest;
	}
	public String getOrganization() {
		return organization;
	}
	public void setOrganization(String organization) {
		this.organization = organization;
	}
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	public String getExperience() {
		return experience;
	}
	public void setExperience(String experience) {
		this.experience = experience;
	}
	public void setTeachingExpYears(String teachingExpYears) {
		this.teachingExpYears = teachingExpYears;
	}
	public String getTeachingExpYears() {
		return teachingExpYears;
	}
	public void setTeachingExpMonths(String teachingExpMonths) {
		this.teachingExpMonths = teachingExpMonths;
	}
	public String getTeachingExpMonths() {
		return teachingExpMonths;
	}
	public void setIndustryExpYears(String industryExpYears) {
		this.industryExpYears = industryExpYears;
	}
	public String getIndustryExpYears() {
		return industryExpYears;
	}
	public void setIndustryExpMonths(String industryExpMonths) {
		this.industryExpMonths = industryExpMonths;
	}
	public String getIndustryExpMonths() {
		return industryExpMonths;
	}
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	
	
}
