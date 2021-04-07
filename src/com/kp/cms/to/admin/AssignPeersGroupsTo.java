package com.kp.cms.to.admin;

public class AssignPeersGroupsTo {
	private int id;
	private String departmentId;
	private String[] employeeIds;
	private int groupId;
	private String departmentName;
	private String groupName;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}
	public String[] getEmployeeIds() {
		return employeeIds;
	}
	public void setEmployeeIds(String[] employeeIds) {
		this.employeeIds = employeeIds;
	}
	public int getGroupId() {
		return groupId;
	}
	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	
}
