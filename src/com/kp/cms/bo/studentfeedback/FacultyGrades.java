package com.kp.cms.bo.studentfeedback;

import java.util.Date;
import java.math.BigDecimal;

public class FacultyGrades implements java.io.Serializable {
	
	private int id;
	private String createdBy;
	private String modifiedBy;
	private String grade;
	private Date createdDate;
	private Date lastModifiedDate;
	private Boolean isActive;
	private BigDecimal scaleFrom;
	private BigDecimal scaleTo;
	
	public FacultyGrades(){
	}
	
	public FacultyGrades(int id, String createdBy, String modifiedBy,
			String grade, Date createdDate, Date lastModifiedDate,
			Boolean isActive, BigDecimal scaleFrom, BigDecimal scaleTo) {
		
		this.id = id;
		this.createdBy = createdBy;
		this.modifiedBy = modifiedBy;
		this.grade = grade;
		this.createdDate = createdDate;
		this.lastModifiedDate = lastModifiedDate;
		this.isActive = isActive;
		this.scaleFrom = scaleFrom;
		this.scaleTo = scaleTo;

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public BigDecimal getScaleFrom() {
		return scaleFrom;
	}

	public void setScaleFrom(BigDecimal scaleFrom) {
		this.scaleFrom = scaleFrom;
	}

	public BigDecimal getScaleTo() {
		return scaleTo;
	}

	public void setScaleTo(BigDecimal scaleTo) {
		this.scaleTo = scaleTo;
	}

}
