package com.kp.cms.bo.admin;

import java.util.Date;

public class EmpApplyLeaveDates {
	private int id;
	private EmpApplyLeave empApplyLeave;
	private Date date;
	
	public EmpApplyLeaveDates(){
		
	}
	
	public EmpApplyLeaveDates(int id, EmpApplyLeave empApplyLeave,
			Date date) {
		super();
		this.id = id;
		this.empApplyLeave = empApplyLeave;
		this.date = date;
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
	public EmpApplyLeave getEmpApplyLeave() {
		return empApplyLeave;
	}
	public void setEmpApplyLeave(EmpApplyLeave empApplyLeave) {
		this.empApplyLeave = empApplyLeave;
	}
	
	
}
