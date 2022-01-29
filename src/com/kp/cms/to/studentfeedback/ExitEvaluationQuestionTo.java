package com.kp.cms.to.studentfeedback;

import java.io.Serializable;

public class ExitEvaluationQuestionTo implements Serializable {
	
	private int id;
	private String question;
	private ExitEvaluationTopicTo exitEvalTopicTo;
	private String createdBy;
	private String modifiedBy;
	private String createdDate;
	private String lastModifiedDate;
	private String isActive;
	private String checked;
	private String remark;
	private String order;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public ExitEvaluationTopicTo getExitEvalTopicTo() {
		return exitEvalTopicTo;
	}
	public void setExitEvalTopicTo(ExitEvaluationTopicTo exitEvalTopicTo) {
		this.exitEvalTopicTo = exitEvalTopicTo;
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
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public String getLastModifiedDate() {
		return lastModifiedDate;
	}
	public void setLastModifiedDate(String lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public String getChecked() {
		return checked;
	}
	public void setChecked(String checked) {
		this.checked = checked;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	
	

}
