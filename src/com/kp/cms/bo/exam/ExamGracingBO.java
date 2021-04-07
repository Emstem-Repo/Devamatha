package com.kp.cms.bo.exam;

/**
 * Mar 1, 2010 Created By 9Elements Team
 */
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.kp.cms.bo.admin.Course;
import com.kp.cms.bo.admin.Student;

@SuppressWarnings("serial")
public class ExamGracingBO implements Serializable {

	private Integer id;
	private String createdBy;;
	private String modifiedBy;
	private Date createdDate;
	private Date lastModifiedDate;
	private boolean isActive;
	private Course course;
	private BigDecimal percentage;
	private Student student;
	private Integer semester;
	private String appliedYear;
	private String academicYear;
	private String remark;
	private boolean processed;
	private boolean onlyPass;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
	public void setIsActive(boolean isActive) {
		this.isActive = isActive;
	}
	public BigDecimal getPercentage() {
		return percentage;
	}
	public void setPercentage(BigDecimal percentage) {
		this.percentage = percentage;
	}
	public Student getStudent() {
		return student;
	}
	public void setStudent(Student student) {
		this.student = student;
	}
	public Integer getSemester() {
		return semester;
	}
	public void setSemester(Integer semester) {
		this.semester = semester;
	}
	public String getAcademicYear() {
		return academicYear;
	}
	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public void setCourse(Course course) {
		this.course = course;
	}
	public Course getCourse() {
		return course;
	}
	public void setProcessed(boolean processed) {
		this.processed = processed;
	}
	public boolean getProcessed() {
		return processed;
	}
	public void setAppliedYear(String appliedYear) {
		this.appliedYear = appliedYear;
	}
	public String getAppliedYear() {
		return appliedYear;
	}
	public void setOnlyPass(boolean onlyPass) {
		this.onlyPass = onlyPass;
	}
	public boolean getOnlyPass() {
		return onlyPass;
	}




}
