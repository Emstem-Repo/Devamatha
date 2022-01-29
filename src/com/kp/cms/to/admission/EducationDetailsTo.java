package com.kp.cms.to.admission;
/**
 * 
 * 
 * TO Class for EdnQualifications BO
 * 
 */
public class EducationDetailsTo {
	private String instituteName;
	private String passYear;
	private int obtainedMark;
	private int totalmark;
	private String status;
	public String getInstituteName() {
		return instituteName;
	}
	public void setInstituteName(String instituteName) {
		this.instituteName = instituteName;
	}
	public String getPassYear() {
		return passYear;
	}
	public void setPassYear(String passYear) {
		this.passYear = passYear;
	}
	public int getObtainedMark() {
		return obtainedMark;
	}
	public void setObtainedMark(int obtainedMark) {
		this.obtainedMark = obtainedMark;
	}
	public int getTotalmark() {
		return totalmark;
	}
	public void setTotalmark(int totalmark) {
		this.totalmark = totalmark;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}
