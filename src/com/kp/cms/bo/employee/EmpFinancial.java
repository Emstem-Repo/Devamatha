package com.kp.cms.bo.employee;

import java.util.Date;

import com.kp.cms.bo.admin.Employee;

public class EmpFinancial {

	private int id;
	private Employee employee;
	private Date financialDate;
	private String financialDetails;
	private String financialAmount;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;
	private Boolean isActive;
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
	public Date getFinancialDate() {
		return financialDate;
	}
	public void setFinancialDate(Date financialDate) {
		this.financialDate = financialDate;
	}
	public String getFinancialDetails() {
		return financialDetails;
	}
	public void setFinancialDetails(String financialDetails) {
		this.financialDetails = financialDetails;
	}
	public String getFinancialAmount() {
		return financialAmount;
	}
	public void setFinancialAmount(String financialAmount) {
		this.financialAmount = financialAmount;
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
		EmpFinancial financial=(EmpFinancial)obj;
		boolean amount=false;
		boolean date=false;
		boolean details=false;
		if(this.financialAmount==null && financial.getFinancialAmount()==null)
			amount=true;
		else if(this.financialAmount!=null && financial.getFinancialAmount()!=null){
			if(this.financialAmount.equalsIgnoreCase(financial.getFinancialAmount()))
				amount=true;
		}
		if(this.financialDetails==null && financial.getFinancialDetails()==null)
			details=true;
		else if(this.financialDetails!=null && financial.getFinancialDetails()!=null){
			if(this.financialDetails.equalsIgnoreCase(financial.getFinancialDetails()))
				details=true;
		}
		if(this.financialDate==null && financial.getFinancialDate()==null)
			date=true;
		else if(this.financialDate!=null && financial.getFinancialDate()!=null){
			if(this.financialDate.toString().equalsIgnoreCase(financial.getFinancialDate().toString()))
				date=true;
		}
		if(this.employee.getId()==financial.getEmployee().getId() && amount && date && details){
			return true;
		}else
			return false;
	}
	
}
