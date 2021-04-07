package com.kp.cms.to.exam;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ExamGracingTo {
	private Integer id;
	private String createdBy;;
	private String modifiedBy;
	private Date createdDate;
	private Date lastModifiedDate;
	private boolean isActive;
	private Integer courseId;
	private BigDecimal percentage;
	private Integer studentId;
	private Integer semester;
	private String academicYear;
	private String appliedYear;
	private String remark;
	private boolean processed;
	private boolean onlyPass;
	private Map<Integer, Integer> suppplyIds = new HashMap<Integer, Integer>();
	
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
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	public int getCourseId() {
		return courseId;
	}
	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}
	public BigDecimal getPercentage() {
		return percentage;
	}
	public void setPercentage(BigDecimal percentage) {
		this.percentage = percentage;
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
	
	public void setStudentId(Integer studentId) {
		this.studentId = studentId;
	}
	public Integer getStudentId() {
		return studentId;
	}
	public void setProcessed(boolean processed) {
		this.processed = processed;
	}
	public boolean isProcessed() {
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
	public void setSuppplyIds(Map<Integer, Integer> suppplyIds) {
		this.suppplyIds = suppplyIds;
	}
	public Map<Integer, Integer> getSuppplyIds() {
		return suppplyIds;
	}

	
}
