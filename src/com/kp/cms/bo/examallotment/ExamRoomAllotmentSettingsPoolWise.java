package com.kp.cms.bo.examallotment;

import java.io.Serializable;
import java.util.Date;

import com.kp.cms.bo.admin.Course;

public class ExamRoomAllotmentSettingsPoolWise implements Serializable{
	
	private int id;
	private ExamRoomAllotmentPool allotmentPool;
	private String midOrEndSem;
	private Course course;
	private Integer schemeNo;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;
	private Boolean isActive;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public ExamRoomAllotmentPool getAllotmentPool() {
		return allotmentPool;
	}
	public void setAllotmentPool(ExamRoomAllotmentPool allotmentPool) {
		this.allotmentPool = allotmentPool;
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
	public Integer getSchemeNo() {
		return schemeNo;
	}
	public void setSchemeNo(Integer schemeNo) {
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
