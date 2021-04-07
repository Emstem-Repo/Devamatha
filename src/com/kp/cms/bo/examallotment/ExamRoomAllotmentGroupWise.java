package com.kp.cms.bo.examallotment;

import java.io.Serializable;
import java.util.Date;

import com.kp.cms.bo.admin.Course;

public class ExamRoomAllotmentGroupWise implements Serializable {

	private int id;
	private String midOrEndSem;
	private Course course;
	private int schemeNo;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;
	private Boolean isActive;
	
	public ExamRoomAllotmentGroupWise() {
	}
	
    	
	
	public ExamRoomAllotmentGroupWise(int id, String midOrEndSem,
			Course course, int schemeNo, String createdBy, Date createdDate,
			String modifiedBy, Date lastModifiedDate, Boolean isActive) {
		super();
		this.id = id;
		this.midOrEndSem = midOrEndSem;
		this.course = course;
		this.schemeNo = schemeNo;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.lastModifiedDate = lastModifiedDate;
		this.isActive = isActive;
	}



	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getMidOrEndSem() {
		return midOrEndSem;
	}
	public void setMidOrEndSem(String midOrEndSem) {
		this.midOrEndSem = midOrEndSem;
	}
	public Course getCourse() {
		return course;
	}
	public void setCourse(Course course) {
		this.course = course;
	}
	public int getSchemeNo() {
		return schemeNo;
	}
	public void setSchemeNo(int schemeNo) {
		this.schemeNo = schemeNo;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
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
