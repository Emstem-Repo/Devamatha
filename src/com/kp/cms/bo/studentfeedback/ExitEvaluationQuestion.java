package com.kp.cms.bo.studentfeedback;

import java.util.Date;

import com.kp.cms.to.exam.ExamEvaluatorTypeMarksTo;


public class ExitEvaluationQuestion {
	
	private int id;
	private String createdBy;;
	private String modifiedBy;
	private ExitEvaluationTopic exitEvalTopic;
	private String question;
	private Date createdDate;
	private Date lastModifiedDate;
	private Boolean isActive;
	private Integer order;
	public int getId() {
		return id;
	}
	public void setId(int id) {
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
	public ExitEvaluationTopic getExitEvalTopic() {
		return exitEvalTopic;
	}
	public void setExitEvalTopic(ExitEvaluationTopic exitEvalTopic) {
		this.exitEvalTopic = exitEvalTopic;
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
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public Integer getOrder() {
		return order;
	}
	public void setOrder(Integer order) {
		this.order = order;
	}
	
	

}
