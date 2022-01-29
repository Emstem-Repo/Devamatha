package com.kp.cms.bo.admin;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class PeersEvaluationFeedbackPeers implements Serializable{
	private int id;
	private PeersEvaluationFeedback peersEvaluationFeedback;
	private Users peerId;
	private Set<PeersEvaluationFeedbackAnswers> evaluationFeedbackAnswers=new HashSet<PeersEvaluationFeedbackAnswers>(0);
	private String remarks;
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
	public PeersEvaluationFeedback getPeersEvaluationFeedback() {
		return peersEvaluationFeedback;
	}
	public void setPeersEvaluationFeedback(
			PeersEvaluationFeedback peersEvaluationFeedback) {
		this.peersEvaluationFeedback = peersEvaluationFeedback;
	}
	public Users getPeerId() {
		return peerId;
	}
	public void setPeerId(Users peerId) {
		this.peerId = peerId;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
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
	public Set<PeersEvaluationFeedbackAnswers> getEvaluationFeedbackAnswers() {
		return evaluationFeedbackAnswers;
	}
	public void setEvaluationFeedbackAnswers(
			Set<PeersEvaluationFeedbackAnswers> evaluationFeedbackAnswers) {
		this.evaluationFeedbackAnswers = evaluationFeedbackAnswers;
	}
	
}
