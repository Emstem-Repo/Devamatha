package com.kp.cms.to.exam;

import java.io.Serializable;
import java.util.Date;

import com.kp.cms.bo.admin.Course;

public class MarksCardSiNoTO implements Serializable {
	
	private int id;
	private String startNo;
	private String currentNo;
	private Boolean isActive;
	private Date createdDate;
	private Integer academicYear;
	private Integer semister;
	private String prefix;
	private int programTypeId;
	private String programTypeName;
	
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
	public int getProgramTypeId() {
		return programTypeId;
	}
	public void setProgramTypeId(int programTypeId) {
		this.programTypeId = programTypeId;
	}
	public String getProgramTypeName() {
		return programTypeName;
	}
	public void setProgramTypeName(String programTypeName) {
		this.programTypeName = programTypeName;
	}
}
