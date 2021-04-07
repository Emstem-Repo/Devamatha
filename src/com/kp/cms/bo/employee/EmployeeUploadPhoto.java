package com.kp.cms.bo.employee;

import java.util.Date;

public class EmployeeUploadPhoto {
	private int id;
	private String fingerPrintId;
	private byte[] photo;
	private String modifiedBy;
	private Date lastModifiedDate;
	
	public EmployeeUploadPhoto() {
		super();
	}
	public EmployeeUploadPhoto(int id, String fingerPrintId, byte[] photo,
			String modifiedBy, Date lastModifiedDate) {
		super();
		this.id = id;
		this.fingerPrintId = fingerPrintId;
		this.photo = photo;
		this.modifiedBy = modifiedBy;
		this.lastModifiedDate = lastModifiedDate;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFingerPrintId() {
		return fingerPrintId;
	}
	public void setFingerPrintId(String fingerPrintId) {
		this.fingerPrintId = fingerPrintId;
	}
	public byte[] getPhoto() {
		return photo;
	}
	public void setPhoto(byte[] photo) {
		this.photo = photo;
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
	
}
