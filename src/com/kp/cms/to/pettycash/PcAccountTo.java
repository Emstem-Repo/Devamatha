package com.kp.cms.to.pettycash;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.struts.upload.FormFile;

import com.kp.cms.bo.admin.PcAccountHead;

public class PcAccountTo {
	private int id;
	private String accountNo;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;
	private Boolean isActive;
	private Set<PcAccountHead> pcAccountHeads = new HashSet<PcAccountHead>(0);
	private Boolean isPhoto;
	private byte[] logo;
	private FormFile thefile;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
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
	public Set<PcAccountHead> getPcAccountHeads() {
		return pcAccountHeads;
	}
	public void setPcAccountHeads(Set<PcAccountHead> pcAccountHeads) {
		this.pcAccountHeads = pcAccountHeads;
	}
	public Boolean getIsPhoto() {
		return isPhoto;
	}
	public void setIsPhoto(Boolean isPhoto) {
		this.isPhoto = isPhoto;
	}
	public byte[] getLogo() {
		return logo;
	}
	public void setLogo(byte[] logo) {
		this.logo = logo;
	}
	public FormFile getThefile() {
		return thefile;
	}
	public void setThefile(FormFile thefile) {
		this.thefile = thefile;
	}

}
