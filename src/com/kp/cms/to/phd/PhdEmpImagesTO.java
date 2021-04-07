package com.kp.cms.to.phd;

import java.util.Date;

import org.apache.struts.upload.FormFile;

import com.kp.cms.bo.phd.PhdEmployee;

public class PhdEmpImagesTO {
	private int id;
	private PhdEmployee phdEmployee;
	private FormFile phdEmpPhoto;
	private byte[] photoBytes;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public PhdEmployee getPhdEmployee() {
		return phdEmployee;
	}
	public void setPhdEmployee(PhdEmployee phdEmployee) {
		this.phdEmployee = phdEmployee;
	}
	public FormFile getPhdEmpPhoto() {
		return phdEmpPhoto;
	}
	public void setPhdEmpPhoto(FormFile phdEmpPhoto) {
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
	public byte[] getPhotoBytes() {
		return photoBytes;
	}
	public void setPhotoBytes(byte[] photoBytes) {
		this.photoBytes = photoBytes;
	}
	
}
