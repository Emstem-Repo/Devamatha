package com.kp.cms.bo.admin;

import java.util.Date;

public class NewsEventsContactDetails {
	
		private int id;
		private NewsEventsDetails newsEventsId;
		private String name;
		private String contactNo;
		private String email;
		private String remarks;
		private String createdBy;
		private Date createdDate;
		private String modifiedBy;
		private Date lastModifiedDate;
		private Boolean isActive;
		
		
		public NewsEventsContactDetails() {
			super();
			// TODO Auto-generated constructor stub
		}
		public NewsEventsContactDetails(NewsEventsDetails newsEventsId, String name,
				String contactNo, String email, String remarks, String createdBy,
				Date createdDate, String modifiedBy, Date lastModifiedDate,
				Boolean isActive) {
			super();
			this.newsEventsId = newsEventsId;
			this.name = name;
			this.contactNo = contactNo;
			this.email = email;
			this.remarks = remarks;
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
		public String getRemarks() {
			return remarks;
		}
		public void setRemarks(String remarks) {
			this.remarks = remarks;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}

	}



