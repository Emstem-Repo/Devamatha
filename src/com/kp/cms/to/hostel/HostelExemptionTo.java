package com.kp.cms.to.hostel;

public class HostelExemptionTo {
	private int hlAdmitionId;
	private String registerNo;
	private String name;
	private String block;
	private String unit;
	private String courseName;
	private String checked1;
	private boolean selected;
	
	public String getRegisterNo() {
		return registerNo;
	}
	public void setRegisterNo(String registerNo) {
		this.registerNo = registerNo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getBlock() {
		return block;
	}
	public void setBlock(String block) {
		this.block = block;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public String getChecked1() {
		return checked1;
	}
	public void setChecked1(String checked1) {
		this.checked1 = checked1;
	}
	public int getHlAdmitionId() {
		return hlAdmitionId;
	}
	public void setHlAdmitionId(int hlAdmitionId) {
		this.hlAdmitionId = hlAdmitionId;
	}
	public boolean isSelected() {
		return selected;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
	}

}
