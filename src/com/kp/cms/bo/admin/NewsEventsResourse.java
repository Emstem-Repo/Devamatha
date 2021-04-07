package com.kp.cms.bo.admin;

import java.util.Date;

public class NewsEventsResourse {
	
	private int id;
	private NewsEventsDetails newsEventsId;
	private String resourseName;
	private String contactNo;
	private String email;
	private String otherInfo;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;
	private Boolean isActive;
	
	
	public NewsEventsResourse() {
		super();
		// TODO Auto-generated constructor stub
	}
	public NewsEventsResourse(NewsEventsDetails newsEventsId, String resourseName,
			String contactNo, String email, String otherInfo, String createdBy,
			Date createdDate, String modifiedBy, Date lastModifiedDate,
			Boolean isActive) {
		super();
		this.newsEventsId = newsEventsId;
		this.resourseName = resourseName;
		this.contactNo = contactNo;
		this.email = email;
		this.otherInfo = otherInfo;
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
	
	public String getResourseName() {
		return resourseName;
	}
	public void setResourseName(String resourseName) {
		this.resourseName = resourseName;
	}
	public String getContactNo() {
		return contactNo;
	}
	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getOtherInfo() {
		return otherInfo;
	}
	public void setOtherInfo(String otherInfo) {
		this.otherInfo = otherInfo;
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
	public NewsEventsDetails getNewsEventsId() {
		return newsEventsId;
	}
	public void setNewsEventsId(NewsEventsDetails newsEventsId) {
		this.newsEventsId = newsEventsId;
	}

}
