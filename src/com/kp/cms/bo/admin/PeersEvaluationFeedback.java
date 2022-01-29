package com.kp.cms.bo.admin;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class PeersEvaluationFeedback implements Serializable{
	private int id;
	private Users teacherId;
	private Department department;
	private PeerFeedbackSession sessionid;
	private Boolean cancel;
	private Set<PeersEvaluationFeedbackPeers> feedbackFaculty=new HashSet<PeersEvaluationFeedbackPeers>(0);
	private PeersEvaluationGroups peersGroups;
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
	public Users getTeacherId() {
		return teacherId;
	}
	public void setTeacherId(Users teacherId) {
		this.teacherId = teacherId;
	}
	public Department getDepartment() {
		return department;
	}
	public void setDepartment(Department department) {
		this.department = department;
	}
	public PeerFeedbackSession getSessionid() {
		return sessionid;
	}
	public void setSessionid(PeerFeedbackSession sessionid) {
		this.sessionid = sessionid;
	}
	public Boolean getCancel() {
		return cancel;
	}
	public void setCancel(Boolean cancel) {
		this.cancel = cancel;
	}
	public Set<PeersEvaluationFeedbackPeers> getFeedbackFaculty() {
		return feedbackFaculty;
	}
	public void setFeedbackFaculty(Set<PeersEvaluationFeedbackPeers> feedbackFaculty) {
		this.feedbackFaculty = feedbackFaculty;
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
	public PeersEvaluationGroups getPeersGroups() {
		return peersGroups;
	}
	public void setPeersGroups(PeersEvaluationGroups peersGroups) {
		this.peersGroups = peersGroups;
	}

}
