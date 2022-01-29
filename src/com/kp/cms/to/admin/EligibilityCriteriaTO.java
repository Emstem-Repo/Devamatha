package com.kp.cms.to.admin;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class EligibilityCriteriaTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3451912036200363176L;

	private int id;

	private CourseTO courseTO;
	private BigDecimal totalPercentage;
	private BigDecimal percentageWithoutLanguage;
	private List<EligibleSubjectsTO> eligibleSubjectsTOList;
	private Boolean isActive;
	private int year;
	
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;
	private String eligibleTotalPercentage;
	private String eligibleWithoutLanguage;
	private String active;
	private String eligibleYear;
	private String cDate;
	private String lDate;
	
	public EligibilityCriteriaTO(){
		
	}
	public EligibilityCriteriaTO(int id, CourseTO courseTO,
			BigDecimal totalPercentage, BigDecimal percentageWithoutLanguage,
			List<EligibleSubjectsTO> eligibleSubjectsTOList, Boolean isActive,
			int year, String createdBy, Date createdDate, String modifiedBy,
			Date lastModifiedDate, String eligibleTotalPercentage,
			String eligibleWithoutLanguage, String active, String eligibleYear,
			String cDate, String lDate) {
		super();
		this.id = id;
		this.courseTO = courseTO;
		this.totalPercentage = totalPercentage;
		this.percentageWithoutLanguage = percentageWithoutLanguage;
		this.eligibleSubjectsTOList = eligibleSubjectsTOList;
		this.isActive = isActive;
		this.year = year;
		this.createdBy = createdBy;
		this.createdDate = (Date)createdDate.clone();
		this.modifiedBy = modifiedBy;
		this.lastModifiedDate = (Date)lastModifiedDate.clone();
		this.eligibleTotalPercentage = eligibleTotalPercentage;
		this.eligibleWithoutLanguage = eligibleWithoutLanguage;
		this.active = active;
		this.eligibleYear = eligibleYear;
		this.cDate = cDate;
		this.lDate = lDate;
	}
	public String getCDate() {
		return cDate;
	}
	public void setCDate(String date) {
		cDate = date;
	}
	public String getLDate() {
		return lDate;
	}
	public void setLDate(String date) {
		lDate = date;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public CourseTO getCourseTO() {
		return courseTO;
	}
	public BigDecimal getTotalPercentage() {
		return totalPercentage;
	}
	public BigDecimal getPercentageWithoutLanguage() {
		return percentageWithoutLanguage;
	}
	public void setCourseTO(CourseTO courseTO) {
		this.courseTO = courseTO;
	}
	public void setTotalPercentage(BigDecimal totalPercentage) {
		this.totalPercentage = totalPercentage;
	}
	public void setPercentageWithoutLanguage(BigDecimal percentageWithoutLanguage) {
		this.percentageWithoutLanguage = percentageWithoutLanguage;
	}
	public List<EligibleSubjectsTO> getEligibleSubjectsTOList() {
		return eligibleSubjectsTOList;
	}
	public void setEligibleSubjectsTOList(
			List<EligibleSubjectsTO> eligibleSubjectsTOList) {
		this.eligibleSubjectsTOList = eligibleSubjectsTOList;
	}
	public Boolean getIsActive() {
		return isActive;
	}
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public Date getCreatedDate() {
		return (Date)createdDate.clone();
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = (Date)createdDate.clone();
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public Date getLastModifiedDate() {
		return (Date)lastModifiedDate.clone();
	}
	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = (Date)lastModifiedDate.clone();
	}
	public String getEligibleTotalPercentage() {
		return eligibleTotalPercentage;
	}
	public void setEligibleTotalPercentage(String eligibleTotalPercentage) {
		this.eligibleTotalPercentage = eligibleTotalPercentage;
	}
	public String getEligibleWithoutLanguage() {
		return eligibleWithoutLanguage;
	}
	public void setEligibleWithoutLanguage(String eligibleWithoutLanguage) {
		this.eligibleWithoutLanguage = eligibleWithoutLanguage;
	}
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
	public String getEligibleYear() {
		return eligibleYear;
	}
	public void setEligibleYear(String eligibleYear) {
		this.eligibleYear = eligibleYear;
	}
	
}