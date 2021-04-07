package com.kp.cms.bo.admin;

import java.util.Date;

public class EmpExceptionDetailsDates {
	
	private int id;
	private EmpExceptionDetailsBO empExceptionDetailsBO;
	private Date date;
	private Boolean isActive; 
	
	public EmpExceptionDetailsDates(){
		
	}
	
	public EmpExceptionDetailsDates(int id, EmpExceptionDetailsBO empExceptionDetailsBO,
			Date date,Boolean isActive) {
		super();
		this.id = id;
		this.empExceptionDetailsBO = empExceptionDetailsBO;
		this.date = date;
		this.isActive = isActive;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}

	public EmpExceptionDetailsBO getEmpExceptionDetailsBO() {
		return empExceptionDetailsBO;
	}

	public void setEmpExceptionDetailsBO(EmpExceptionDetailsBO empExceptionDetailsBO) {
		this.empExceptionDetailsBO = empExceptionDetailsBO;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
}
