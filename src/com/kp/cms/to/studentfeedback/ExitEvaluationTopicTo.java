package com.kp.cms.to.studentfeedback;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ExitEvaluationTopicTo implements Serializable {
	private int id;
	private String name;
	private String createdBy;;
	private String modifiedBy;
	private Date createdDate;
	private Date lastModifiedDate;
	private String isActive;
	private List<ExitEvaluationQuestionTo> exitEvalTopicToList=new ArrayList<ExitEvaluationQuestionTo>();
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	public List<ExitEvaluationQuestionTo> getExitEvalTopicToList() {
		return exitEvalTopicToList;
	}
	public void setExitEvalTopicToList(
			List<ExitEvaluationQuestionTo> exitEvalTopicToList) {
		this.exitEvalTopicToList = exitEvalTopicToList;
	}
	
	
	

}
