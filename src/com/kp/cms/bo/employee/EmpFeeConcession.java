package com.kp.cms.bo.employee;

import java.util.Date;

import com.kp.cms.bo.admin.Employee;

public class EmpFeeConcession {

	private int id;
	private Employee employee;
	private Date feeConcessionDate;
	private String feeConcessionDetails;
	private String feeConcessionAmount;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;
	private Boolean isActive;
	
	public EmpFeeConcession()
	{}
	public EmpFeeConcession(int id) {
		this.id = id;
	}
	
	public EmpFeeConcession(int id, Employee employee, Date feeConcessionDate,
			String feeConcessionDetails, String feeConcessionAmount,
			String createdBy, Date createdDate, String modifiedBy,
			Date lastModifiedDate, Boolean isActive) {
		super();
		this.id = id;
		this.employee = employee;
		this.feeConcessionDate = feeConcessionDate;
		this.feeConcessionDetails = feeConcessionDetails;
		this.feeConcessionAmount = feeConcessionAmount;
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
	public Date getFeeConcessionDate() {
		return feeConcessionDate;
	}
	public void setFeeConcessionDate(Date feeConcessionDate) {
		this.feeConcessionDate = feeConcessionDate;
	}
	public String getFeeConcessionDetails() {
		return feeConcessionDetails;
	}
	public void setFeeConcessionDetails(String feeConcessionDetails) {
		this.feeConcessionDetails = feeConcessionDetails;
	}
	public String getFeeConcessionAmount() {
		return feeConcessionAmount;
	}
	public void setFeeConcessionAmount(String feeConcessionAmount) {
		this.feeConcessionAmount = feeConcessionAmount;
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
		EmpFeeConcession fee=(EmpFeeConcession)obj;
		boolean amount=false;
		boolean details=false;
		boolean date=false;
		if(this.feeConcessionAmount==null && fee.getFeeConcessionAmount()==null)
			amount=true;
		else if(this.feeConcessionAmount!=null && fee.getFeeConcessionAmount()!=null){
			if(this.feeConcessionAmount.equalsIgnoreCase(fee.getFeeConcessionAmount()))
				amount=true;
		}
		if(this.feeConcessionDetails==null && fee.getFeeConcessionDetails()==null)
			details=true;
		else if(this.feeConcessionDetails!=null && fee.getFeeConcessionDetails()!=null){
			if(this.feeConcessionDetails.equalsIgnoreCase(fee.getFeeConcessionDetails()))
				details=true;
		}
		if(this.feeConcessionDate==null && fee.getFeeConcessionDate()==null)
			date=true;
		else if(this.feeConcessionDate!=null && fee.getFeeConcessionDate()!=null){
			if(this.feeConcessionDate.toString().equalsIgnoreCase(fee.getFeeConcessionDate().toString()))
				date=true;
		}
		if(this.employee.getId()==fee.getEmployee().getId() && amount && details && date){
			return true;
		}else
			return false;
	}
	
}
