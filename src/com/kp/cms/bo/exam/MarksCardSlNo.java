package com.kp.cms.bo.exam;

import java.io.Serializable;
import java.util.Date;

import com.kp.cms.bo.admin.ProgramType;

@SuppressWarnings("serial")
public class MarksCardSlNo implements Serializable 
{
	private int id;
	private int academicYear;
	private ProgramType programType;
	private int scheme;
	private int startNo;
	private Date createdDate;
	private String createdBy;
	private int currentNo;
	private boolean isActive;
	
	public MarksCardSlNo(int id, 
						 int academicYear, 
						 ProgramType programType,
						 int scheme, 
						 String prefix, 
						 int startNo, 
						 Date createdDate,
						 String createdBy, 
						 int currentNo, 
						 boolean isActive) {
		super();
		this.id = id;
		this.academicYear = academicYear;
		this.programType = programType;
		this.scheme = scheme;
		this.startNo = startNo;
		this.createdDate = createdDate;
		this.createdBy = createdBy;
		this.currentNo = currentNo;
		this.isActive = isActive;
	}
	public MarksCardSlNo() {
		
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getAcademicYear() {
		return academicYear;
	}
	public void setAcademicYear(int academicYear) {
		this.academicYear = academicYear;
	}
	public ProgramType getProgramType() {
		return programType;
	}
	public void setProgramType(ProgramType programType) {
		this.programType = programType;
	}
	public int getScheme() {
		return scheme;
	}
	public void setScheme(int scheme) {
		this.scheme = scheme;
	}
	public int getStartNo() {
		return startNo;
	}
	public void setStartNo(int startNo) {
		this.startNo = startNo;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public int getCurrentNo() {
		return currentNo;
	}
	public void setCurrentNo(int currentNo) {
		this.currentNo = currentNo;
	}
	public boolean getIsActive() {
		return isActive;
	}
	public void setIsActive(boolean isActive) {
		this.isActive = isActive;
	}
}
