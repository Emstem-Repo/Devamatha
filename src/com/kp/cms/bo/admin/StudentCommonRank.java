package com.kp.cms.bo.admin;

import java.util.Date;

// Generated Jan 22, 2009 3:35:47 PM by Hibernate Tools 3.2.0.b9

/**
 * CandidatePreference generated by hbm2java
 */
public class StudentCommonRank implements java.io.Serializable {

	private int id;
	private AdmAppln admAppln;
	private Course course;
	private String createdBy;
	private String modifiedBy;
	private Date createdDate;
	private Date lastModifiedDate;
	private boolean isActive;
	private Double percentage;
	private Double groupMarks;
	private Integer rank;
	private Double totalMark;
	private Double groupPercentage;
	
	




	public StudentCommonRank() {
	}

	

	public Integer getRank() {
		return rank;
	}



	public void setRank(Integer rank) {
		this.rank = rank;
	}



	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public AdmAppln getAdmAppln() {
		return this.admAppln;
	}

	public void setAdmAppln(AdmAppln admAppln) {
		this.admAppln = admAppln;
	}


	
	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
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



	public boolean getIsActive() {
		return isActive;
	}



	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}



	public Double getGroupMarks() {
		return groupMarks;
	}



	public void setGroupMarks(Double groupMarks) {
		this.groupMarks = groupMarks;
	}



	public Double getTotalMark() {
		return totalMark;
	}



	public void setTotalMark(Double totalMark) {
		this.totalMark = totalMark;
	}



	public Double getPercentage() {
		return percentage;
	}



	public void setPercentage(Double percentage) {
		this.percentage = percentage;
	}



	public Double getGroupPercentage() {
		return groupPercentage;
	}



	public void setGroupPercentage(Double groupPercentage) {
		this.groupPercentage = groupPercentage;
	}



		
	

}
