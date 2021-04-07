package com.kp.cms.bo.hostel;

import java.util.Date;

import com.kp.cms.bo.admin.HlAdmissionBo;

public class HostelExemptionDetailsBo {
	private int id;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;
	private Boolean isActive;
	private HlAdmissionBo hlAdmissionBo;
	private HostelExemptionBo hostelExemptionBo;
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
	public HlAdmissionBo getHlAdmissionBo() {
		return hlAdmissionBo;
	}
	public void setHlAdmissionBo(HlAdmissionBo hlAdmissionBo) {
		this.hlAdmissionBo = hlAdmissionBo;
	}
	public HostelExemptionBo getHostelExemptionBo() {
		return hostelExemptionBo;
	}
	public void setHostelExemptionBo(HostelExemptionBo hostelExemptionBo) {
		this.hostelExemptionBo = hostelExemptionBo;
	}

}
