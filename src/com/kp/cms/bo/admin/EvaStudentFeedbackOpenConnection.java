package com.kp.cms.bo.admin;

import java.io.Serializable;
import java.util.Date;

import com.kp.cms.bo.exam.ExamSpecializationBO;

public class EvaStudentFeedbackOpenConnection implements Serializable{
	private int id;
	private Classes classesId;
	private EvaluationStudentFeedbackSession feedbackSession;
	private Date startDate;
	private Date endDate;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;
	private Boolean isActive;
	// newly added by sudhir
	private ExamSpecializationBO examSpecializationBO;
	private boolean isExitEval;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Classes getClassesId() {
		return classesId;
	}
	public void setClassesId(Classes classesId) {
		this.classesId = classesId;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
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
	public EvaStudentFeedbackOpenConnection() {
		super();
	}
	public EvaStudentFeedbackOpenConnection(int id, Classes classesId,
			Date startDate, Date endDate, String createdBy, Date createdDate,
			String modifiedBy, Date lastModifiedDate, Boolean isActive) {
		super();
		this.id = id;
		this.classesId = classesId;
		this.startDate = startDate;
		this.endDate = endDate;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.lastModifiedDate = lastModifiedDate;
		this.isActive = isActive;
	}
	public EvaluationStudentFeedbackSession getFeedbackSession() {
		return feedbackSession;
	}
	public void setFeedbackSession(EvaluationStudentFeedbackSession feedbackSession) {
		this.feedbackSession = feedbackSession;
	}
	public ExamSpecializationBO getExamSpecializationBO() {
		return examSpecializationBO;
	}
	public void setExamSpecializationBO(ExamSpecializationBO examSpecializationBO) {
		this.examSpecializationBO = examSpecializationBO;
	}
	public boolean getIsExitEval() {
		return isExitEval;
	}
	public void setIsExitEval(boolean isExitEval) {
		this.isExitEval = isExitEval;
	}
	
	
}
