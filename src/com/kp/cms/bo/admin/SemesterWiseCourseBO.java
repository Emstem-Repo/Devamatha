package com.kp.cms.bo.admin;

import java.io.Serializable;
import java.util.Date;

public class SemesterWiseCourseBO implements Serializable{
	private int id;
	private Course course;
	private Boolean isSemesterWise;
	private String createdBy;;
	private String modifiedBy;
	private Date createdDate;
	private Date lastModifiedDate;
	private Boolean isActive;
	
	public SemesterWiseCourseBO(){
		
	}
	
	public SemesterWiseCourseBO(int id){
		this.id = id;
	}
	
	public SemesterWiseCourseBO(int id,Course course,Boolean isSemesterWise,String createdBy,String modifiedBy,
			Date createdDate,Date lastModifiedDate,Boolean isActive){
		this.id = id;
		this.course = course;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.lastModifiedDate = lastModifiedDate;
		this.isSemesterWise = isSemesterWise;
		this.isActive = isActive;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public Boolean getIsSemesterWise() {
		return isSemesterWise;
	}

	public void setIsSemesterWise(Boolean isSemesterWise) {
		this.isSemesterWise = isSemesterWise;
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
	
	
}
