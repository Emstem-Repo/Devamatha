package com.kp.cms.bo.admin;

import java.io.Serializable;
import java.util.Date;

public class PeersEvaluationOpenSession implements Serializable{
	private int id;
	private Department departmentId;
	private PeerFeedbackSession peerFeedbackSession;
	private Date startDate;
	private Date endDate;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;
	private Boolean isActive;
	public PeersEvaluationOpenSession() {
		super();
	}
	public PeersEvaluationOpenSession(int id, Department departmentId,
			PeerFeedbackSession peerFeedbackSession, Date startDate,
			Date endDate, String createdBy, Date createdDate,
			String modifiedBy, Date lastModifiedDate, Boolean isActive) {
		super();
		this.id = id;
		this.departmentId = departmentId;
		this.peerFeedbackSession = peerFeedbackSession;
		this.startDate = startDate;
		this.endDate = endDate;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.lastModifiedDate = lastModifiedDate;
		this.isActive = isActive;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Department getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(Department departmentId) {
		this.departmentId = departmentId;
	}
	public PeerFeedbackSession getPeerFeedbackSession() {
		return peerFeedbackSession;
	}
	public void setPeerFeedbackSession(PeerFeedbackSession peerFeedbackSession) {
		this.peerFeedbackSession = peerFeedbackSession;
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
	
}
