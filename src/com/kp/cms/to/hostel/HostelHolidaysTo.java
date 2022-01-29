package com.kp.cms.to.hostel;

import java.util.Date;

import com.kp.cms.bo.admin.Course;
import com.kp.cms.bo.admin.Program;

public class HostelHolidaysTo {
	private int id;
	private Program programId;
	private Course courseId;
	private String holidaysFrom;
	private String holidaysTo;
	private String holidaysFromSession;
	private String holidaysToSession;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;
	private Boolean isActive;
	private String programName;
	private String courseName;
	private String holidaysOrVaction;
	private Date date;
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getHolidaysOrVaction() {
		return holidaysOrVaction;
	}
	public void setHolidaysOrVaction(String holidaysOrVaction) {
		this.holidaysOrVaction = holidaysOrVaction;
	}
	public String getProgramName() {
		return programName;
	}
	public void setProgramName(String programName) {
		this.programName = programName;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Program getProgramId() {
		return programId;
	}
	public void setProgramId(Program programId) {
		this.programId = programId;
	}
	public Course getCourseId() {
		return courseId;
	}
	public void setCourseId(Course courseId) {
		this.courseId = courseId;
	}
	
	
	public String getHolidaysFrom() {
		return holidaysFrom;
	}
	public void setHolidaysFrom(String holidaysFrom) {
		this.holidaysFrom = holidaysFrom;
	}
	public String getHolidaysTo() {
		return holidaysTo;
	}
	public void setHolidaysTo(String holidaysTo) {
		this.holidaysTo = holidaysTo;
	}
	public String getHolidaysFromSession() {
		return holidaysFromSession;
	}
	public void setHolidaysFromSession(String holidaysFromSession) {
		this.holidaysFromSession = holidaysFromSession;
	}
	public String getHolidaysToSession() {
		return holidaysToSession;
	}
	public void setHolidaysToSession(String holidaysToSession) {
		this.holidaysToSession = holidaysToSession;
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
