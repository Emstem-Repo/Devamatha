package com.kp.cms.to.admission;

import java.io.Serializable;
import java.util.List;

public class PrerequisiteYearMonthTO implements Serializable{
	
	 private int id;
	    private String year;
	    private String month;
	    private String isActive;
	    private String createdBy;
	    private String modifiedBy;
	    private String createdDate;
	    private String lastModifiedDate;
	    private String method;
	    private List<PrerequisiteYearMonthTO> yearList;
	    
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public String getYear() {
			return year;
		}
		public void setYear(String year) {
			this.year = year;
		}
		public String getMonth() {
			return month;
		}
		public void setMonth(String month) {
			this.month = month;
		}
		public String getIsActive() {
			return isActive;
		}
		public void setIsActive(String isActive) {
			this.isActive = isActive;
		}
		public String getMethod() {
			return method;
		}
		public void setMethod(String method) {
			this.method = method;
		}
		public List<PrerequisiteYearMonthTO> getYearList() {
			return yearList;
		}
		public void setYearList(List<PrerequisiteYearMonthTO> yearList) {
			this.yearList = yearList;
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
		public String getCreatedDate() {
			return createdDate;
		}
		public void setCreatedDate(String createdDate) {
			this.createdDate = createdDate;
		}
		public String getLastModifiedDate() {
			return lastModifiedDate;
		}
		public void setLastModifiedDate(String lastModifiedDate) {
			this.lastModifiedDate = lastModifiedDate;
		}

}
