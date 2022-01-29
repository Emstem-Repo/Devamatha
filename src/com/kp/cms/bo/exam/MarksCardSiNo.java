package com.kp.cms.bo.exam;

import java.io.Serializable;
import java.util.Date;

import com.kp.cms.bo.admin.Course;

public class MarksCardSiNo implements Serializable {
	private int id;
	private String startNo;
	private String currentNo;
	private Boolean isActive;
	private Date createdDate;
	private Integer academicYear;
	private Integer semister;
	private String prefix;
	private Course courseId;
	private String createdBy;
	
	public MarksCardSiNo(){
		
	}
	
	public MarksCardSiNo(int id, String startNo, String currentNo,
			Boolean isActive, Date createdDate, Integer academicYear,
			Integer semister, String prefix, Course courseId, String createdBy) {
		super();
		this.id = id;
		this.startNo = startNo;
		this.currentNo = currentNo;
		this.isActive = isActive;
		this.createdDate = createdDate;
		this.academicYear = academicYear;
		this.semister = semister;
		this.prefix = prefix;
		this.courseId = courseId;
		this.createdBy = createdBy;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getStartNo() {
		return startNo;
	}
	public void setStartNo(String startNo) {
		this.startNo = startNo;
	}
	public String getCurrentNo() {
		return currentNo;
	}
	public void setCurrentNo(String currentNo) {
		this.currentNo = currentNo;
	}
	public Boolean getIsActive() {
		return isActive;
	}
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Integer getAcademicYear() {
		return academicYear;
	}
	public void setAcademicYear(Integer academicYear) {
		this.academicYear = academicYear;
	}
	public Integer getSemister() {
		return semister;
	}
	public void setSemister(Integer semister) {
		this.semister = semister;
	}
	public String getPrefix() {
		return prefix;
	}
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	public Course getCourseId() {
		return courseId;
	}
	public void setCourseId(Course courseId) {
		this.courseId = courseId;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	
}
