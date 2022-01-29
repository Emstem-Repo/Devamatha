package com.kp.cms.to.admission;

import java.util.Date;

import com.kp.cms.to.admin.CourseTO;

public class CopyClassesTO {
	
	private int id;
	private Integer year;
	private Integer termNo;
	private CourseTO courseTo;
	private String className;
	private String sectionName;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;
	private String isActive;
	private String cDate;
	private String lDate;
	private boolean classesSelId;

	private Integer courseGroupCodeId;
	
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
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the year
	 */
	public Integer getYear() {
		return year;
	}
	/**
	 * @param year the year to set
	 */
	public void setYear(Integer year) {
		this.year = year;
	}
	/**
	 * @return the termNo
	 */
	public Integer getTermNo() {
		return termNo;
	}
	/**
	 * @param termNo the termNo to set
	 */
	public void setTermNo(Integer termNo) {
		this.termNo = termNo;
	}
	/**
	 * @return the courseTo
	 */
	public CourseTO getCourseTo() {
		return courseTo;
	}
	/**
	 * @param courseTo the courseTo to set
	 */
	public void setCourseTo(CourseTO courseTo) {
		this.courseTo = courseTo;
	}
	/**
	 * @return the className
	 */
	public String getClassName() {
		return className;
	}
	/**
	 * @param className the className to set
	 */
	public void setClassName(String className) {
		this.className = className;
	}
	/**
	 * @return the sectionName
	 */
	public String getSectionName() {
		return sectionName;
	}
	/**
	 * @param sectionName the sectionName to set
	 */
	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}
	public void setCourseGroupCodeId(Integer courseGroupCodeId) {
		this.courseGroupCodeId = courseGroupCodeId;
	}
	public Integer getCourseGroupCodeId() {
		return courseGroupCodeId;
	}
	public boolean getClassesSelId() {
		return classesSelId;
	}
	public void setClassesSelId(boolean classesSelId) {
		this.classesSelId = classesSelId;
	}

	

}
