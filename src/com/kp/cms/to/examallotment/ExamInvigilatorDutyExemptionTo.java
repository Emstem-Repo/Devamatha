package com.kp.cms.to.examallotment;

public class ExamInvigilatorDutyExemptionTo {
	private String name;
	private int id;
	private String checked;
	private String workLocation;
	private String department;
	private String exemptionName;
	private int workLocationId;
	private int userId;
	private String flag;


	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getWorkLocationId() {
		return workLocationId;
	}
	public void setWorkLocationId(int workLocationId) {
		this.workLocationId = workLocationId;
	}
	public String getWorkLocation() {
		return workLocation;
	}
	public void setWorkLocation(String workLocation) {
		this.workLocation = workLocation;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	
	public String getExemptionName() {
		return exemptionName;
	}
	public void setExemptionName(String exemptionName) {
		this.exemptionName = exemptionName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getChecked() {
		return checked;
	}
	public void setChecked(String checked) {
		this.checked = checked;
	}


}
