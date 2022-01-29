package com.kp.cms.bo.exam;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.kp.cms.bo.admin.Classes;
import com.kp.cms.bo.admin.Student;

public class ExamMidsemExemption implements java.io.Serializable{
	
	private int id;
	private ExamDefinitionBO examId;
	private Student studentId;
	private Classes classId;
	private String reason;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;
	private Boolean isActive;
	private Set<ExamMidsemExemptionDetails> examMidsemExemptionDetails=new HashSet<ExamMidsemExemptionDetails>(0);
	
	public ExamMidsemExemption(){
	}
	
	public ExamMidsemExemption(int id, ExamDefinitionBO examId, Student studentId, Classes classId, 
			String reason, String createdBy, Date createdDate, String modifiedBy, Date lastModifiedDate, Boolean isActive, 
			Set<ExamMidsemExemptionDetails> examMidsemExemptionDetails){
		super();
		this.id = id;
		this.examId=examId;
		this.studentId=studentId;
		this.classId=classId;
		this.reason=reason;
		this.createdBy=createdBy;
		this.createdDate=createdDate;
		this.modifiedBy=modifiedBy;
		this.lastModifiedDate=lastModifiedDate;
		this.isActive=isActive;
		this.examMidsemExemptionDetails=examMidsemExemptionDetails;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ExamDefinitionBO getExamId() {
		return examId;
	}

	public void setExamId(ExamDefinitionBO examId) {
		this.examId = examId;
	}

	public Student getStudentId() {
		return studentId;
	}

	public void setStudentId(Student studentId) {
		this.studentId = studentId;
	}

	public Classes getClassId() {
		return classId;
	}

	public void setClassId(Classes classId) {
		this.classId = classId;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
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

	public Set<ExamMidsemExemptionDetails> getExamMidsemExemptionDetails() {
		return examMidsemExemptionDetails;
	}

	public void setExamMidsemExemptionDetails(
			Set<ExamMidsemExemptionDetails> examMidsemExemptionDetails) {
		this.examMidsemExemptionDetails = examMidsemExemptionDetails;
	}
	
	

}
