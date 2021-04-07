package com.kp.cms.to.hostel;


public class ApplicationFormTO {
	private int id;
	private String name;
	private boolean dummySelected;
	private boolean selected;

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

	public boolean isDummySelected() {
		return dummySelected;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setDummySelected(boolean dummySelected) {
		this.dummySelected = dummySelected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	
	
}
