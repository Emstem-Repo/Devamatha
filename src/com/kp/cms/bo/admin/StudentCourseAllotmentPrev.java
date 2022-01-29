package com.kp.cms.bo.admin;

import java.util.Date;

// Generated Jan 22, 2009 3:35:47 PM by Hibernate Tools 3.2.0.b9

/**
 * CandidatePreference generated by hbm2java
 */
public class StudentCourseAllotmentPrev implements java.io.Serializable {

	private int id;
	private AdmAppln admAppln;
	
	private Double indexMark;
	private Course course;
	private String remark;
	private String createdBy;
	private String modifiedBy;
	private Date createdDate;
	private Date lastModifiedDate;
	private boolean isActive;
	private Double language1Marks;
	private Double language2Marks;
	private Double groupMarks;
	private Integer rank;
	private Integer prefNo;
	private boolean isAlloted;
	private boolean isAssigned;
	private boolean isCaste;
	private boolean isGeneral;
	private Integer allotmentNo;
	private boolean isCancel;
	private boolean isSatisfied;
	
	
	
	public boolean getIsSatisfied() {
		return isSatisfied;
	}



	public void setIsSatisfied(boolean isSatisfied) {
		this.isSatisfied = isSatisfied;
	}



	public boolean getIsCaste() {
		return isCaste;
	}



	public void setIsCaste(boolean isCaste) {
		this.isCaste = isCaste;
	}



	public boolean getIsGeneral() {
		return isGeneral;
	}



	public void setIsGeneral(boolean isGeneral) {
		this.isGeneral = isGeneral;
	}



	public boolean getIsAssigned() {
		return isAssigned;
	}



	public void setIsAssigned(boolean isAssigned) {
		this.isAssigned = isAssigned;
	}



	public StudentCourseAllotmentPrev() {
	}

	

	public boolean getIsAlloted() {
		return isAlloted;
	}



	public void setIsAlloted(boolean isAlloted) {
		this.isAlloted = isAlloted;
	}



	public Integer getPrefNo() {
		return prefNo;
	}



	public void setPrefNo(Integer prefNo) {
		this.prefNo = prefNo;
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


	public Double getIndexMark() {
		return indexMark;
	}

	public void setIndexMark(Double indexMark) {
		this.indexMark = indexMark;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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



	public Double getLanguage1Marks() {
		return language1Marks;
	}



	public void setLanguage1Marks(Double language1Marks) {
		this.language1Marks = language1Marks;
	}



	public Double getLanguage2Marks() {
		return language2Marks;
	}



	public void setLanguage2Marks(Double language2Marks) {
		this.language2Marks = language2Marks;
	}



	public Double getGroupMarks() {
		return groupMarks;
	}



	public void setGroupMarks(Double groupMarks) {
		this.groupMarks = groupMarks;
	}



	public Integer getAllotmentNo() {
		return allotmentNo;
	}



	public void setAllotmentNo(Integer allotmentNo) {
		this.allotmentNo = allotmentNo;
	}



	public boolean getIsCancel() {
		return isCancel;
	}



	public void setIsCancel(boolean isCancel) {
		this.isCancel = isCancel;
	}
	
	
	

}
