package com.kp.cms.to.admin;

import java.io.Serializable;
import java.util.Date;

public class PreferencesTO implements Serializable{
	private int prefId;
	private int prefCourseId;
	private int prefProgramId;
	private int courseId;
	private CourseTO courseTO;
	private CourseTO prefCourseTO;
	private String createdBy;;
	private String modifiedBy;
	private Date createdDate;
	private Date lastModifiedDate;	
	private String isActive;
	private String cDate;
	private String lDate;
	
	public PreferencesTO(){
		
	}
	public PreferencesTO(int prefId, int prefCourseId, int prefProgramId,
			int courseId, CourseTO courseTO, CourseTO prefCourseTO,
			String createdBy, String modifiedBy, Date createdDate,
			Date lastModifiedDate, String isActive, String cDate, String lDate) {
		super();
		this.prefId = prefId;
		this.prefCourseId = prefCourseId;
		this.prefProgramId = prefProgramId;
		this.courseId = courseId;
		this.courseTO = courseTO;
		this.prefCourseTO = prefCourseTO;
		this.createdBy = createdBy;
		this.modifiedBy = modifiedBy;
		this.createdDate = (Date)createdDate.clone();
		this.lastModifiedDate = (Date)lastModifiedDate.clone();
		this.isActive = isActive;
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
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
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
	public Date getCreatedDate() {
		return (Date)createdDate.clone();
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = (Date)createdDate.clone();
	}
	public Date getLastModifiedDate() {
		return (Date)lastModifiedDate.clone();
	}
	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = (Date)lastModifiedDate.clone();
	}
	public int getPrefId() {
		return prefId;
	}
	public void setPrefId(int prefId) {
		this.prefId = prefId;
	}
	public int getPrefCourseId() {
		return prefCourseId;
	}
	public void setPrefCourseId(int prefCourseId) {
		this.prefCourseId = prefCourseId;
	}
	public int getPrefProgramId() {
		return prefProgramId;
	}
	public void setPrefProgramId(int prefProgramId) {
		this.prefProgramId = prefProgramId;
	}
	public CourseTO getCourseTO() {
		return courseTO;
	}
	public void setCourseTO(CourseTO courseTO) {
		this.courseTO = courseTO;
	}
	public int getCourseId() {
		return courseId;
	}
	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}
	public CourseTO getPrefCourseTO() {
		return prefCourseTO;
	}
	public void setPrefCourseTO(CourseTO prefCourseTO) {
		this.prefCourseTO = prefCourseTO;
	}
	
	
}
