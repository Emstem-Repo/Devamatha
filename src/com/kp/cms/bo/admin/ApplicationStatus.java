	package com.kp.cms.bo.admin;

import java.util.Date;
	
	public class ApplicationStatus implements java.io.Serializable{
	
		private int id;
		private String name;
		private String createdBy;;
		private String modifiedBy;
		private Date createdDate;
		private Date lastModifiedDate;
		private Boolean isActive;
		private String shortName;
		public ApplicationStatus() {
			super();
		}

		public ApplicationStatus(int id, String name, String createdBy,
				String modifiedBy, Date createdDate, Date lastModifiedDate,
				Boolean isActive,String shortName) {
			super();
			this.id = id;
			this.name = name;
			this.createdBy = createdBy;
			this.modifiedBy = modifiedBy;
			this.createdDate = createdDate;
			this.lastModifiedDate = lastModifiedDate;
			this.isActive = isActive;
			this.shortName = shortName;
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getCreatedBy() {
			return createdBy;
		}

		public void setCreatedBy(String createdBy) {
			this.createdBy = createdBy;
		}

		public String getModifiedBy() {
			return modifiedBy;
		}

		public void setModifiedBy(String modifiedBy) {
			this.modifiedBy = modifiedBy;
		}

		public Date getCreatedDate() {
			return createdDate;
		}

		public void setCreatedDate(Date createdDate) {
			this.createdDate = createdDate;
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

		public void setShortName(String shortName) {
			this.shortName = shortName;
		}

		public String getShortName() {
			return shortName;
		}
		
	}
