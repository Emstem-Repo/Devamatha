package com.kp.cms.bo.hostel;

import java.util.Date;

import com.kp.cms.bo.admin.HlAdmissionBo;
import com.kp.cms.bo.admin.HlFacility;

public class HlStudentFacilityAllotted implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private HlFacility hlFacilityId;
	private HlAdmissionBo hlAdmissionId;
	private String description;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;
	private Boolean isActive;
	
	
	public HlStudentFacilityAllotted(int id) {
		super();
		this.id = id;
	}
	public HlStudentFacilityAllotted() {
		super();
		// TODO Auto-generated constructor stub
	}
	public HlStudentFacilityAllotted(int id, HlFacility hlFacilityId,
			HlAdmissionBo hlAdmissionId, String description, String createdBy,
			Date createdDate, String modifiedBy, Date lastModifiedDate,
			Boolean isActive) {
		super();
		this.id = id;
		this.hlFacilityId = hlFacilityId;
		this.hlAdmissionId = hlAdmissionId;
		this.description = description;
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
	public HlFacility getHlFacilityId() {
		return hlFacilityId;
	}
	public void setHlFacilityId(HlFacility hlFacilityId) {
		this.hlFacilityId = hlFacilityId;
	}
	public HlAdmissionBo getHlAdmissionId() {
		return hlAdmissionId;
	}
	public void setHlAdmissionId(HlAdmissionBo hlAdmissionId) {
		this.hlAdmissionId = hlAdmissionId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
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
