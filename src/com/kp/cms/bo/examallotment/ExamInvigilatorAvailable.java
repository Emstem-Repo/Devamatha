package com.kp.cms.bo.examallotment;

import java.io.Serializable;
import java.util.Date;

import com.kp.cms.bo.admin.EmployeeWorkLocationBO;
import com.kp.cms.bo.admin.Users;
import com.kp.cms.bo.exam.ExamDefinition;

public class ExamInvigilatorAvailable implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int id;
	private ExamDefinition examId;
	private EmployeeWorkLocationBO workLocationId; 
	private Users teacherId;
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
	public ExamDefinition getExamId() {
		return examId;
	}
	public void setExamId(ExamDefinition examId) {
		this.examId = examId;
	}
	public Users getTeacherId() {
		return teacherId;
	}
	public void setTeacherId(Users teacherId) {
		this.teacherId = teacherId;
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
	public ExamInvigilatorAvailable(int id, ExamDefinition examId,
			Users teacherId, String createdBy, Date createdDate,
			String modifiedBy, Date lastModifiedDate, Boolean isActive, EmployeeWorkLocationBO workLocationId) {
		super();
		this.id = id;
		this.examId = examId;
		this.teacherId = teacherId;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.lastModifiedDate = lastModifiedDate;
		this.isActive = isActive;
		this.workLocationId= workLocationId;
	}
	public ExamInvigilatorAvailable() {
		super();
		// TODO Auto-generated constructor stub
	}
	public EmployeeWorkLocationBO getWorkLocationId() {
		return workLocationId;
	}
	public void setWorkLocationId(EmployeeWorkLocationBO workLocationId) {
		this.workLocationId = workLocationId;
	}
	
}
