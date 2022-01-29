package com.kp.cms.bo.admin;

// Generated Feb 12, 2009 11:40:28 AM by Hibernate Tools 3.2.0.b9

import java.util.Date;

/**
 * StudentVehicleDetails generated by hbm2java
 */
public class StudentVehicleDetails implements java.io.Serializable {

	private int id;
	private AdmAppln admAppln;
	private String vehicleType;
	private String vehicleNo;
	private Integer createdBy;
	private Date createdDate;
	private Integer modifiedBy;
	private Date lastModifiedDate;

	public StudentVehicleDetails() {
	}

	public StudentVehicleDetails(int id) {
		this.id = id;
	}

	public StudentVehicleDetails(int id, AdmAppln admAppln, String vehicleType,
			String vehicleNo, Integer createdBy, Date createdDate,
			Integer modifiedBy, Date lastModifiedDate) {
		this.id = id;
		this.admAppln = admAppln;
		this.vehicleType = vehicleType;
		this.vehicleNo = vehicleNo;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.lastModifiedDate = lastModifiedDate;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public AdmAppln getAdmAppln() {
		return this.admAppln;
	}

	public void setAdmAppln(AdmAppln admAppln) {
		this.admAppln = admAppln;
	}

	public String getVehicleType() {
		return this.vehicleType;
	}

	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}

	public String getVehicleNo() {
		return this.vehicleNo;
	}

	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

	public Integer getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Integer getModifiedBy() {
		return this.modifiedBy;
	}

	public void setModifiedBy(Integer modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getLastModifiedDate() {
		return this.lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

}
