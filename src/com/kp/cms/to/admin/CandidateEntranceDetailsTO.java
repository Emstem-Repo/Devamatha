package com.kp.cms.to.admin;

import java.math.BigDecimal;

public class CandidateEntranceDetailsTO {
	private int id;
	private int entranceId;
	private int admApplnId;
	private String yearPassing;
	private String monthPassing;
	private String marksObtained;
	private String totalMarks;
	private String entranceRollNo;
	private String entranceName;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public int getAdmApplnId() {
		return admApplnId;
	}
	public void setAdmApplnId(int admApplnId) {
		this.admApplnId = admApplnId;
	}
	
	public int getEntranceId() {
		return entranceId;
	}
	public void setEntranceId(int entranceId) {
		this.entranceId = entranceId;
	}
	public String getEntranceRollNo() {
		return entranceRollNo;
	}
	public void setEntranceRollNo(String entranceRollNo) {
		this.entranceRollNo = entranceRollNo;
	}
	public String getYearPassing() {
		return yearPassing;
	}
	public void setYearPassing(String yearPassing) {
		this.yearPassing = yearPassing;
	}
	public String getMonthPassing() {
		return monthPassing;
	}
	public void setMonthPassing(String monthPassing) {
		this.monthPassing = monthPassing;
	}
	public String getMarksObtained() {
		return marksObtained;
	}
	public void setMarksObtained(String marksObtained) {
		this.marksObtained = marksObtained;
	}
	public String getTotalMarks() {
		return totalMarks;
	}
	public void setTotalMarks(String totalMarks) {
		this.totalMarks = totalMarks;
	}
	public String getEntranceName() {
		return entranceName;
	}
	public void setEntranceName(String entranceName) {
		this.entranceName = entranceName;
	}
	
	
	
}
