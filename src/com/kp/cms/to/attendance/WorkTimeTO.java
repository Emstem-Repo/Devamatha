package com.kp.cms.to.attendance;

public class WorkTimeTO {
	private int id;
	private String name;
	private String inTimeFromHr;
	private String inTimeFromMins;
	private String inTimeToHrs;
	private String inTimeToMins;
	private String outTimeFromHrs;
	private String outTimeFromMins;
	private String outTimeToHrs;
	private String outTimeToMins;
	private boolean checked;
	private boolean tempChecked;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getInTimeFromHr() {
		return inTimeFromHr;
	}
	public void setInTimeFromHr(String inTimeFromHr) {
		this.inTimeFromHr = inTimeFromHr;
	}
	public String getInTimeFromMins() {
		return inTimeFromMins;
	}
	public void setInTimeFromMins(String inTimeFromMins) {
		this.inTimeFromMins = inTimeFromMins;
	}
	public String getInTimeToHrs() {
		return inTimeToHrs;
	}
	public void setInTimeToHrs(String inTimeToHrs) {
		this.inTimeToHrs = inTimeToHrs;
	}
	public String getInTimeToMins() {
		return inTimeToMins;
	}
	public void setInTimeToMins(String inTimeToMins) {
		this.inTimeToMins = inTimeToMins;
	}
	public String getOutTimeFromHrs() {
		return outTimeFromHrs;
	}
	public void setOutTimeFromHrs(String outTimeFromHrs) {
		this.outTimeFromHrs = outTimeFromHrs;
	}
	public String getOutTimeFromMins() {
		return outTimeFromMins;
	}
	public void setOutTimeFromMins(String outTimeFromMins) {
		this.outTimeFromMins = outTimeFromMins;
	}
	public String getOutTimeToHrs() {
		return outTimeToHrs;
	}
	public void setOutTimeToHrs(String outTimeToHrs) {
		this.outTimeToHrs = outTimeToHrs;
	}
	public String getOutTimeToMins() {
		return outTimeToMins;
	}
	public void setOutTimeToMins(String outTimeToMins) {
		this.outTimeToMins = outTimeToMins;
	}
	public boolean getTempChecked() {
		return tempChecked;
	}
	public void setTempChecked(boolean tempChecked) {
		this.tempChecked = tempChecked;
	}
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
}
