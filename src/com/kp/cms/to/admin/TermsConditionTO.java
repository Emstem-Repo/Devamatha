package com.kp.cms.to.admin;

import java.io.Serializable;
import java.util.List;


/**
 * Manages Transaction related to Terms & Condition
 * 
 * @author kshirod.k
 */
public class TermsConditionTO implements Serializable {

	private int programType;
	private CourseTO courseTo;
	private String description;
	private int id;
	private int year;
	private int endYear;
	private String academicYear;
	private String createdBy;;
	private String modifiedBy;
	private String createdDate;
	private String lastModifiedDate;
	
	private String termsConditionYear;
	private String isActive;
	private List<TermsConditionChecklistTO> checkListTos; 
	
	public int getProgramType() {
		return programType;
	}

	public void setProgramType(int programType) {
		this.programType = programType;
	}

	public CourseTO getCourseTo() {
		return courseTo;
	}

	public void setCourseTo(CourseTO courseTo) {
		this.courseTo = courseTo;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getEndYear() {
		return endYear;
	}

	public void setEndYear(int endYear) {
		this.endYear = endYear;
	}

	public String getTermsConditionYear() {
		return termsConditionYear;
	}

	public void setTermsConditionYear(String termsConditionYear) {
		this.termsConditionYear = termsConditionYear;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public String getAcademicYear() {
		return academicYear;
	}

	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public String getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public void setLastModifiedDate(String lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public List<TermsConditionChecklistTO> getCheckListTos() {
		return checkListTos;
	}

	public void setCheckListTos(List<TermsConditionChecklistTO> checkListTos) {
		this.checkListTos = checkListTos;
	}

	

}