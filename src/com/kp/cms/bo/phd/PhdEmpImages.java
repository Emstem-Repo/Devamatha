package com.kp.cms.bo.phd;

import java.io.Serializable;
import java.util.Date;

public class PhdEmpImages implements Serializable{
	
	private int id;
	private byte[] phdEmpPhoto;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;
	private PhdEmployee phdEmployeeId;
	
	public PhdEmpImages() {
		super();
		// TODO Auto-generated constructor stub
	}
	public PhdEmpImages(int id, byte[] phdEmpPhoto, String createdBy,
			Date createdDate, String modifiedBy, Date lastModifiedDate,
			PhdEmployee phdEmployeeId) {
		super();
		this.id = id;
		this.phdEmpPhoto = phdEmpPhoto;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.lastModifiedDate = lastModifiedDate;
		this.phdEmployeeId = phdEmployeeId;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public byte[] getPhdEmpPhoto() {
		return phdEmpPhoto;
	}
	public void setPhdEmpPhoto(byte[] phdEmpPhoto) {
		this.phdEmpPhoto = phdEmpPhoto;
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
	public PhdEmployee getPhdEmployeeId() {
		return phdEmployeeId;
	}
	public void setPhdEmployeeId(PhdEmployee phdEmployeeId) {
		this.phdEmployeeId = phdEmployeeId;
	}


}
