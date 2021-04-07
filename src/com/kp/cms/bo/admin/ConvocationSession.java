package com.kp.cms.bo.admin;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;


public class ConvocationSession implements java.io.Serializable  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private int id;
	private Date date;
	private String amPM;
	private Integer maxGuestAllowed;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;
	private Boolean isActive;
	private BigDecimal passAmount;
	private Set<ConvocationCourse> courses;
	
	public ConvocationSession() {
	}

	public ConvocationSession(int id, Date date, String amPM,
			Integer maxGuestAllowed, String createdBy, Date createdDate,
			String modifiedBy, Date lastModifiedDate, Boolean isActive,
			BigDecimal passAmount, Set<ConvocationCourse> courses) {
		super();
		this.id = id;
		this.date = date;
		this.amPM = amPM;
		this.maxGuestAllowed = maxGuestAllowed;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.lastModifiedDate = lastModifiedDate;
		this.isActive = isActive;
		this.passAmount = passAmount;
		this.courses = courses;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getAmPM() {
		return amPM;
	}

	public void setAmPM(String amPM) {
		this.amPM = amPM;
	}

	public Integer getMaxGuestAllowed() {
		return maxGuestAllowed;
	}

	public void setMaxGuestAllowed(Integer maxGuestAllowed) {
		this.maxGuestAllowed = maxGuestAllowed;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
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

	public BigDecimal getPassAmount() {
		return passAmount;
	}

	public void setPassAmount(BigDecimal passAmount) {
		this.passAmount = passAmount;
	}

	public Set<ConvocationCourse> getCourses() {
		return courses;
	}

	public void setCourses(Set<ConvocationCourse> courses) {
		this.courses = courses;
	}

	
}
	