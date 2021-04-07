package com.kp.cms.bo.examallotment;

import java.io.Serializable;
import java.util.Date;

import com.kp.cms.bo.admin.Course;

public class ExamRoomAllotmentCycleDetails implements Serializable{
	private int id;
	private ExamRoomAllotmentCycle examRoomAllotmentCycle;
	private Course course;
	private String schemeNo;
	private String createdBy;
	private String modifiedBy;
	private Date createdDate;
	private Date lastModifiedDate;
	private Boolean isActive;
	
	public ExamRoomAllotmentCycleDetails() {
		super();
	}
	
	public ExamRoomAllotmentCycleDetails(int id, Course course,ExamRoomAllotmentCycle examRoomAllotmentCycle,
			String schemeNo, String createdBy, String modifiedBy,
			Date createdDate, Date lastModifiedDate, Boolean isActive) {
		super();
		this.id = id;
		this.examRoomAllotmentCycle = examRoomAllotmentCycle;
		this.course = course;
		this.schemeNo = schemeNo;
		this.createdBy = createdBy;
		this.modifiedBy = modifiedBy;
		this.createdDate = createdDate;
		this.lastModifiedDate = lastModifiedDate;
		this.isActive = isActive;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Course getCourse() {
		return course;
	}
	public void setCourse(Course course) {
		this.course = course;
	}
	public String getSchemeNo() {
		return schemeNo;
	}
	public void setSchemeNo(String schemeNo) {
		this.schemeNo = schemeNo;
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
	public Boolean getIsActive() {
		return isActive;
	}
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public ExamRoomAllotmentCycle getExamRoomAllotmentCycle() {
		return examRoomAllotmentCycle;
	}

	public void setExamRoomAllotmentCycle(
			ExamRoomAllotmentCycle examRoomAllotmentCycle) {
		this.examRoomAllotmentCycle = examRoomAllotmentCycle;
	}

}
