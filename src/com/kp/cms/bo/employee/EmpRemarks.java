package com.kp.cms.bo.employee;

import java.util.Date;

import com.kp.cms.bo.admin.Employee;

public class EmpRemarks {
	
	private int id;
	private Employee employee;
	private Date remarksDate;
	private String remarksDetails;
	private String remarksEnteredBy;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;
	private Boolean isActive;
	

	public EmpRemarks()
	{}
	public EmpRemarks(int id) {
		this.id = id;
	}
	
	public EmpRemarks(int id, Employee employee, Date remarksDate,
			String remarksDetails, String remarksEnteredBy, String createdBy,
			Date createdDate, String modifiedBy, Date lastModifiedDate,
			Boolean isActive) {
		super();
		this.id = id;
		this.employee = employee;
		this.remarksDate = remarksDate;
		this.remarksDetails = remarksDetails;
		this.remarksEnteredBy = remarksEnteredBy;
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
	public Date getRemarksDate() {
		return remarksDate;
	}
	public void setRemarksDate(Date remarksDate) {
		this.remarksDate = remarksDate;
	}
	public String getRemarksDetails() {
		return remarksDetails;
	}
	public void setRemarksDetails(String remarksDetails) {
		this.remarksDetails = remarksDetails;
	}
	public String getRemarksEnteredBy() {
		return remarksEnteredBy;
	}
	public void setRemarksEnteredBy(String remarksEnteredBy) {
		this.remarksEnteredBy = remarksEnteredBy;
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
		EmpRemarks remark=(EmpRemarks)obj;
		boolean date=false;
		boolean details=false;
		boolean enteredBy=false;
		if(this.remarksDetails==null && remark.getRemarksDetails()==null)
			details=true;
		else if(this.remarksDetails!=null && remark.getRemarksDetails()!=null){
			if(this.remarksDetails.equalsIgnoreCase(remark.getRemarksDetails()))
				details=true;
		}
		if(this.remarksEnteredBy==null && remark.getRemarksEnteredBy()==null)
			enteredBy=true;
		else if(this.remarksEnteredBy!=null && remark.getRemarksEnteredBy()!=null){
			if(this.remarksEnteredBy.equalsIgnoreCase(remark.getRemarksEnteredBy()))
				enteredBy=true;
		}
		if(this.remarksDate==null && remark.getRemarksDate()==null)
			date=true;
		else if(this.remarksDate!=null && remark.getRemarksDate()!=null){
			if(this.remarksDate.toString().equalsIgnoreCase(remark.getRemarksDate().toString()))
				date=true;
		}
		if(this.employee.getId()==remark.getEmployee().getId() && date && details && enteredBy){
			return true;
		}else
			return false;
	}
    
}
