package com.kp.cms.bo.serviceLearning;

import java.util.Date;

import com.kp.cms.bo.admin.ExtraCreditActivityType;

public class ProgrammeEntryBo {
	
	private int id;
	private String programmeName;
	private String inchargeName;
	private String programmeCode;
	private Date startDate;
	private Date endDate;
	private DepartmentNameEntry departmentNameEntry;
	private String createdBy;;
	private String modifiedBy;
	private Date createdDate;
	private Date lastModifiedDate;
	private Boolean isActive;
	private String maxHrs;
	
	private ExtraCreditActivityType extraCreditActivityType;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getProgrammeName() {
		return programmeName;
	}
	public void setProgrammeName(String programmeName) {
		this.programmeName = programmeName;
	}
	public String getInchargeName() {
		return inchargeName;
	}
	public void setInchargeName(String inchargeName) {
		this.inchargeName = inchargeName;
	}
	public String getProgrammeCode() {
		return programmeCode;
	}
	public void setProgrammeCode(String programmeCode) {
		this.programmeCode = programmeCode;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public DepartmentNameEntry getDepartmentNameEntry() {
		return departmentNameEntry;
	}
	public void setDepartmentNameEntry(DepartmentNameEntry departmentNameEntry) {
		this.departmentNameEntry = departmentNameEntry;
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
	public String getMaxHrs() {
		return maxHrs;
	}
	public void setMaxHrs(String maxHrs) {
		this.maxHrs = maxHrs;
	}
	public ExtraCreditActivityType getExtraCreditActivityType() {
		return extraCreditActivityType;
	}
	public void setExtraCreditActivityType(
			ExtraCreditActivityType extraCreditActivityType) {
		this.extraCreditActivityType = extraCreditActivityType;
	}

	
	
}
