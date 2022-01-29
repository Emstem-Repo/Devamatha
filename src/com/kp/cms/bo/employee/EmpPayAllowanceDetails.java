package com.kp.cms.bo.employee;
import java.io.Serializable;
import java.util.Date;

import com.kp.cms.bo.admin.EmpAllowance;
import com.kp.cms.bo.admin.Employee;
import com.kp.cms.bo.admin.PayScaleBO;

public class EmpPayAllowanceDetails implements Serializable{
	
	public EmpPayAllowanceDetails() {
		super();
	}

	public EmpPayAllowanceDetails(int id) {
		super();
		this.id = id;
	}
	private static final long serialVersionUID = 1L;
	private int id;
	private String allowanceValue;
	private Boolean isActive;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;
	private Employee employee;
	private EmpAllowance empAllowance;
	
	
	
	
	public EmpPayAllowanceDetails(int id, String allowanceValue, Boolean isActive,
			String createdBy, Date createdDate, String modifiedBy,
			Date modifiedDate,Employee employee, EmpAllowance empAllowance) {
		super();
		this.id = id;
		this.allowanceValue = allowanceValue;
		this.isActive = isActive;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
		this.employee=employee;
		this.empAllowance=empAllowance;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getAllowanceValue() {
		return allowanceValue;
	}

	public void setAllowanceValue(String allowanceValue) {
		this.allowanceValue = allowanceValue;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public EmpAllowance getEmpAllowance() {
		return empAllowance;
	}

	public void setEmpAllowance(EmpAllowance empAllowance) {
		this.empAllowance = empAllowance;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	@Override
	public boolean equals(Object obj) {
		EmpPayAllowanceDetails allow=(EmpPayAllowanceDetails)obj;
		boolean allowance=false;
		boolean value=false;
		if(this.empAllowance==null && allow.getEmpAllowance()==null)
			allowance=true;
		else if(this.empAllowance!=null && allow.getEmpAllowance()!=null){
			if(this.empAllowance.getId()==allow.getEmpAllowance().getId())
				allowance=true;
		}
		if(this.allowanceValue==null && allow.getAllowanceValue()==null)
			value=true;
		else if(this.allowanceValue!=null && allow.getAllowanceValue()!=null){
			if(this.allowanceValue.equalsIgnoreCase(allow.getAllowanceValue()))
				value=true;
		}
		if(this.employee.getId()==allow.getEmployee().getId() && allowance && value){
			return true;
		}else
			return false;
	}

}
