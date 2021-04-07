package com.kp.cms.to.employee;

public class EmpLeaveTypeTo {
	
	private String id;
	private String leaveType;
	private String isLeave;
	private String isExemption;
	private String code;
	private String continuousdays;
	private String canapplyonline;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getLeaveType() {
		return leaveType;
	}
	public String getIsLeave() {
		return isLeave;
	}
	public String getIsExemption() {
		return isExemption;
	}
	public void setLeaveType(String leaveType) {
		this.leaveType = leaveType;
	}
	public void setIsLeave(String isLeave) {
		this.isLeave = isLeave;
	}
	public void setIsExemption(String isExemption) {
		this.isExemption = isExemption;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getContinuousdays() {
		return continuousdays;
	}
	public void setContinuousdays(String continuousdays) {
		this.continuousdays = continuousdays;
	}
	public String getCanapplyonline() {
		return canapplyonline;
	}
	public void setCanapplyonline(String canapplyonline) {
		this.canapplyonline = canapplyonline;
	}

}
