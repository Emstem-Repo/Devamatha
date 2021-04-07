package com.kp.cms.to.exam;

public class ExaminerDutiesTO {
	private int invDutyTypeId;
	private int employeeId;
	private String display;
	
	
	
	public ExaminerDutiesTO(int invDutyTypeId, int employeeId, String display) {
		super();
		this.invDutyTypeId = invDutyTypeId;
		this.employeeId = employeeId;
		this.display = display;
	}
	
	public void setInvDutyTypeId(int invDutyTypeId) {
		this.invDutyTypeId = invDutyTypeId;
	}
	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}
	public void setDisplay(String display) {
		this.display = display;
	}
	public int getInvDutyTypeId() {
		return invDutyTypeId;
	}
	public int getEmployeeId() {
		return employeeId;
	}
	public String getDisplay() {
		return display;
	}
	
	

}
