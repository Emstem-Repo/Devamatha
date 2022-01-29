package com.kp.cms.bo.hostel;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.kp.cms.bo.admin.HlHostel;

public class HostelExemptionBo {
	private int id;
	private HlHostel hostelId;
	private String reason;
	private String fromSession;
	private String toSession;
	private Date fromDate;
	private Date toDate;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;
	private Boolean isActive;
	private Set<HostelExemptionDetailsBo> hostelExemptionDetailsBo=new HashSet<HostelExemptionDetailsBo>();
	public HlHostel getHostelId() {
		return hostelId;
	}
	public void setHostelId(HlHostel hostelId) {
		this.hostelId = hostelId;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getFromSession() {
		return fromSession;
	}
	public void setFromSession(String fromSession) {
		this.fromSession = fromSession;
	}
	public String getToSession() {
		return toSession;
	}
	public void setToSession(String toSession) {
		this.toSession = toSession;
	}
	public Date getFromDate() {
		return fromDate;
	}
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}
	public Date getToDate() {
		return toDate;
	}
	public void setToDate(Date toDate) {
		this.toDate = toDate;
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
	public Set<HostelExemptionDetailsBo> getHostelExemptionDetailsBo() {
		return hostelExemptionDetailsBo;
	}
	public void setHostelExemptionDetailsBo(
			Set<HostelExemptionDetailsBo> hostelExemptionDetailsBo) {
		this.hostelExemptionDetailsBo = hostelExemptionDetailsBo;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	

}
