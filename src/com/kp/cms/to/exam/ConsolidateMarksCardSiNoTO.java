package com.kp.cms.to.exam;

import java.io.Serializable;
import java.util.Date;

import com.kp.cms.bo.admin.ProgramType;

public class ConsolidateMarksCardSiNoTO implements Serializable {
	private int id;
	private String startNo;
	private String currentNo;
	private Boolean isActive;
	private Date createdDate;
	private Integer academicYear;
	private String programTypeName;
	private Integer programTypeId;
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
	public String getProgramTypeName() {
		return programTypeName;
	}
	public void setProgramTypeName(String programTypeName) {
		this.programTypeName = programTypeName;
	}
	public Integer getProgramTypeId() {
		return programTypeId;
	}
	public void setProgramTypeId(Integer programTypeId) {
		this.programTypeId = programTypeId;
	}
	
	
	
	
	
}
