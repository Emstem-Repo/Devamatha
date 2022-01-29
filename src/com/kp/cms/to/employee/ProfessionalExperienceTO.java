package com.kp.cms.to.employee;

import java.io.Serializable;
import java.util.Map;

@SuppressWarnings("serial")
public class ProfessionalExperienceTO implements Serializable{
	private int id;
	private String currentlyWorking;
	private String teachingExperienceYear;
	private String teachingExperienceMonth;
	private String education;
	private String industryExperienceYear;
	private String industryExperienceMonth;
	private String qualificationLevel;
	private String totalExperienceYear;
	private String totalExperienceMonth;
	private String functionalArea;
	private String currentDesignation;
	private String currentOrganisation;
	private String currentSalaryLack;
	private String currentSalaryThosound;
	
	
	private String teachingExperience;
	private String industryExperience;
	private String totalExperience;
	private String currentSalary;
	
	private String previousOrganisation;
	private Map<Integer, String> educationMap;
	public String getCurrentlyWorking() {
		return currentlyWorking;
	}
	public void setCurrentlyWorking(String currentlyWorking) {
		this.currentlyWorking = currentlyWorking;
	}
	public String getTeachingExperienceYear() {
		return teachingExperienceYear;
	}
	public void setTeachingExperienceYear(String teachingExperienceYear) {
		this.teachingExperienceYear = teachingExperienceYear;
	}
	public String getTeachingExperienceMonth() {
		return teachingExperienceMonth;
	}
	public void setTeachingExperienceMonth(String teachingExperienceMonth) {
		this.teachingExperienceMonth = teachingExperienceMonth;
	}
	public String getEducation() {
		return education;
	}
	public void setEducation(String education) {
		this.education = education;
	}
	public String getIndustryExperienceYear() {
		return industryExperienceYear;
	}
	public void setIndustryExperienceYear(String industryExperienceYear) {
		this.industryExperienceYear = industryExperienceYear;
	}
	public String getIndustryExperienceMonth() {
		return industryExperienceMonth;
	}
	public void setIndustryExperienceMonth(String industryExperienceMonth) {
		this.industryExperienceMonth = industryExperienceMonth;
	}
	public String getQualificationLevel() {
		return qualificationLevel;
	}
	public void setQualificationLevel(String qualificationLevel) {
		this.qualificationLevel = qualificationLevel;
	}
	public String getTotalExperienceYear() {
		return totalExperienceYear;
	}
	public void setTotalExperienceYear(String totalExperienceYear) {
		this.totalExperienceYear = totalExperienceYear;
	}
	public String getTotalExperienceMonth() {
		return totalExperienceMonth;
	}
	public void setTotalExperienceMonth(String totalExperienceMonth) {
		this.totalExperienceMonth = totalExperienceMonth;
	}
	public String getFunctionalArea() {
		return functionalArea;
	}
	public void setFunctionalArea(String functionalArea) {
		this.functionalArea = functionalArea;
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
	public String getCurrentSalaryLack() {
		return currentSalaryLack;
	}
	public void setCurrentSalaryLack(String currentSalaryLack) {
		this.currentSalaryLack = currentSalaryLack;
	}
	public String getCurrentSalaryThosound() {
		return currentSalaryThosound;
	}
	public void setCurrentSalaryThosound(String currentSalaryThosound) {
		this.currentSalaryThosound = currentSalaryThosound;
	}
	public String getPreviousOrganisation() {
		return previousOrganisation;
	}
	public void setPreviousOrganisation(String previousOrganisation) {
		this.previousOrganisation = previousOrganisation;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getId() {
		return id;
	}
	public void setEducationMap(Map<Integer, String> educationMap) {
		this.educationMap = educationMap;
	}
	public Map<Integer, String> getEducationMap() {
		return educationMap;
	}
	public String getTeachingExperience() {
		return teachingExperience;
	}
	public void setTeachingExperience(String teachingExperience) {
		this.teachingExperience = teachingExperience;
	}
	public String getIndustryExperience() {
		return industryExperience;
	}
	public void setIndustryExperience(String industryExperience) {
		this.industryExperience = industryExperience;
	}
	public String getTotalExperience() {
		return totalExperience;
	}
	public void setTotalExperience(String totalExperience) {
		this.totalExperience = totalExperience;
	}
	public String getCurrentSalary() {
		return currentSalary;
	}
	public void setCurrentSalary(String currentSalary) {
		this.currentSalary = currentSalary;
	}
	

}
