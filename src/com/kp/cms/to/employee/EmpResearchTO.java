package com.kp.cms.to.employee;

public class EmpResearchTO {
	private int id;
	private String projectName;
	private String findingAgencyName;
	private String amount;
	private String fromDate1;
	private String toDate1;
	public String getFromDate1() {
		return fromDate1;
	}
	public void setFromDate1(String fromDate1) {
		this.fromDate1 = fromDate1;
	}
	public String getToDate1() {
		return toDate1;
	}
	public void setToDate1(String toDate1) {
		this.toDate1 = toDate1;
	}
	private String title;
	
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getFindingAgencyName() {
		return findingAgencyName;
	}
	public void setFindingAgencyName(String findingAgencyName) {
		this.findingAgencyName = findingAgencyName;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	
}
