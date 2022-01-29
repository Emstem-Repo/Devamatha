package com.kp.cms.bo.exam;

import java.io.Serializable;
import java.util.Date;

import com.kp.cms.bo.admin.ProgramType;

public class ConsolidateMarksCardSiNo implements Serializable {
	private int id;
	private String startNo;
	private String currentNo;
	private Boolean isActive;
	private Date createdDate;
	private Integer academicYear;
	private ProgramType programType;
	
	
	public ConsolidateMarksCardSiNo(){
		
	}
	
	public ConsolidateMarksCardSiNo(int id, String startNo, String currentNo,
			Boolean isActive, Date createdDate) {
		super();
		this.id = id;
		this.startNo = startNo;
		this.currentNo = currentNo;
		this.isActive = isActive;
		this.createdDate = createdDate;
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
	public Integer getAcademicYear() {
		return academicYear;
	}

	public void setAcademicYear(Integer academicYear) {
		this.academicYear = academicYear;
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

	public ProgramType getProgramType() {
		return programType;
	}

	public void setProgramType(ProgramType programType) {
		this.programType = programType;
	}
	
	
	
	
}
