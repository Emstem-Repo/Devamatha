package com.kp.cms.bo.employee;

import java.util.Date;

import com.kp.cms.bo.admin.Employee;

public class EmpIncentives {
	
	private int id;
	private Employee employee;
	private Date incentivesDate;
	private String incentivesDetails;
	private String incentivesAmount;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;
	private Boolean isActive;
	
	public EmpIncentives() {
	}

	public EmpIncentives(int id) {
		this.id = id;
	}

	
	public EmpIncentives(int id, Employee employee, Date incentivesDate,
			String incentivesDetails, String incentivesAmount,
			String createdBy, Date createdDate, String modifiedBy,
			Date lastModifiedDate, Boolean isActive) {
		super();
		this.id = id;
		this.employee = employee;
		this.incentivesDate = incentivesDate;
		this.incentivesDetails = incentivesDetails;
		this.incentivesAmount = incentivesAmount;
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
	public Employee getEmployee() {
		return employee;
	}
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	public Date getIncentivesDate() {
		return incentivesDate;
	}
	public void setIncentivesDate(Date incentivesDate) {
		this.incentivesDate = incentivesDate;
	}
	public String getIncentivesDetails() {
		return incentivesDetails;
	}
	public void setIncentivesDetails(String incentivesDetails) {
		this.incentivesDetails = incentivesDetails;
	}
	public String getIncentivesAmount() {
		return incentivesAmount;
	}
	public void setIncentivesAmount(String incentivesAmount) {
		this.incentivesAmount = incentivesAmount;
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

	@Override
	public boolean equals(Object obj) {
		EmpIncentives incent=(EmpIncentives)obj;
		boolean amount=false;
		boolean date=false;
		boolean details=false;
		if(this.incentivesAmount==null && incent.getIncentivesAmount()==null)
			amount=true;
		else if(this.incentivesAmount!=null && incent.getIncentivesAmount()!=null){
			if(this.incentivesAmount.equalsIgnoreCase(incent.getIncentivesAmount()))
				amount=true;
		}
		if(this.incentivesDetails==null && incent.getIncentivesDetails()==null)
			details=true;
		else if(this.incentivesDetails!=null && incent.getIncentivesDetails()!=null){
			if(this.incentivesDetails.equalsIgnoreCase(incent.getIncentivesDetails()))
				details=true;
		}
		if(this.incentivesDate==null && incent.getIncentivesDate()==null)
			date=true;
		else if(this.incentivesDate!=null && incent.getIncentivesDate()!=null){
			if(this.incentivesDate.toString().equalsIgnoreCase(incent.getIncentivesDate().toString()))
				date=true;
		}
		if(this.employee.getId()==incent.getEmployee().getId() && amount && date && details){
			return true;
		}else
			return false;
	}

}
