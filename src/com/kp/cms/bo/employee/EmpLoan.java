package com.kp.cms.bo.employee;

import java.util.Date;
import com.kp.cms.bo.admin.Employee;

public class EmpLoan implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private Employee employee;
	private Date loanDate;
	private String loanDetails;
	private String loanAmount;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;
	private Boolean isActive;
	
	
	public EmpLoan() {
	}

	public EmpLoan(int id) {
		this.id = id;
	}

	public EmpLoan(int id, String loanAmount, Date loanDate,
			String loanDetails, String createdBy, Date createdDate,Employee employee,
			String modifiedBy, Date lastModifiedDate, Boolean isActive) {
		this.id = id;
		this.employee = employee;
		this.loanDate = loanDate;
		this.loanDetails = loanDetails;
		this.loanAmount = loanAmount;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.lastModifiedDate = lastModifiedDate;
		this.isActive = isActive;
		
		
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getModifiedBy() {
		return this.modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getLastModifiedDate() {
		return this.lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public Boolean getIsActive() {
		return this.isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Date getLoanDate() {
		return loanDate;
	}

	public void setLoanDate(Date loanDate) {
		this.loanDate = loanDate;
	}

	public String getLoanDetails() {
		return loanDetails;
	}

	public void setLoanDetails(String loanDetails) {
		this.loanDetails = loanDetails;
	}

	public String getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(String loanAmount) {
		this.loanAmount = loanAmount;
	}

	@Override
	public boolean equals(Object obj) {
		EmpLoan loan=(EmpLoan)obj;
		boolean amount=false;
		boolean details=false;
		boolean date=false;
		if(this.loanAmount==null && loan.getLoanAmount()==null)
			amount=true;
		else if(this.loanAmount!=null && loan.getLoanAmount()!=null){
        	if(this.loanAmount.equalsIgnoreCase(loan.getLoanAmount()))
        	amount=true;
        }
		if(this.loanDetails==null && loan.getLoanDetails()==null)
			details=true;
		else if(this.loanDetails!=null && loan.getLoanDetails()!=null){
        	if(this.loanDetails.equalsIgnoreCase(loan.getLoanDetails()))
        	details=true;
        }
		if(this.loanDate==null && loan.getLoanDate()==null)
			date=true;
		else if(this.loanDate!=null && loan.getLoanDate()!=null){
        	if(this.loanDate.toString().equalsIgnoreCase(loan.getLoanDate().toString()))
        	date=true;
        }
		if(this.employee.getId()==loan.getEmployee().getId() && amount && details && date){
			return true;
		}else
			return false;
	}

}
