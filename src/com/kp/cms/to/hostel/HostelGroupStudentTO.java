package com.kp.cms.to.hostel;

public class HostelGroupStudentTO {
	private int id;
	private int hlGroupId;
	private int appFormId;
	private String name;
	private boolean selected;
	private boolean dummySelected;
	
	public int getId() {
		return id;
	}
	public int getHlGroupId() {
		return hlGroupId;
	}
	public int getAppFormId() {
		return appFormId;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setHlGroupId(int hlGroupId) {
		this.hlGroupId = hlGroupId;
	}
	public void setAppFormId(int appFormId) {
		this.appFormId = appFormId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isSelected() {
		return selected;
	}
	public boolean isDummySelected() {
		return dummySelected;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	public void setDummySelected(boolean dummySelected) {
		this.dummySelected = dummySelected;
	}
	
	

}
