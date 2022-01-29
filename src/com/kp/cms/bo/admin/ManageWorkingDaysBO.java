package com.kp.cms.bo.admin;

import java.util.Date;
import com.kp.cms.bo.exam.ExamGenBO;

@SuppressWarnings("serial")
public class ManageWorkingDaysBO extends ExamGenBO {

	private String startTime;
	private String endTime;
	private String breakStartTime;
	private String breakEndTime;
	private String createdBy;
	private String modifiedBy;
	private Date createdDate;
	private Date lastModifiedDate;
	private AcademicYear academicYearId;
	private AcademicYear academicYear;
	private ProgramType programTypeId;
	private Course courseId;

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getBreakStartTime() {
		return breakStartTime;
	}

	public void setBreakStartTime(String breakStartTime) {
		this.breakStartTime = breakStartTime;
	}

	public String getBreakEndTime() {
		return breakEndTime;
	}

	public void setBreakEndTime(String breakEndTime) {
		this.breakEndTime = breakEndTime;
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

	public AcademicYear getAcademicYearId() {
		return academicYearId;
	}

	public void setAcademicYearId(AcademicYear academicYearId) {
		this.academicYearId = academicYearId;
	}

	public AcademicYear getAcademicYear() {
		return academicYear;
	}

	public void setAcademicYear(AcademicYear academicYear) {
		this.academicYear = academicYear;
	}

	public ProgramType getProgramTypeId() {
		return programTypeId;
	}

	public void setProgramTypeId(ProgramType programTypeId) {
		this.programTypeId = programTypeId;
	}

	public Course getCourseId() {
		return courseId;
	}

	public void setCourseId(Course courseId) {
		this.courseId = courseId;
	}

}
