package com.kp.cms.to.inventory;

import java.io.Serializable;

public class InvCampusTo implements Serializable,Comparable<InvCampusTo>{

	private int id;
	private String campusName;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCampusName() {
		return campusName;
	}
	public void setCampusName(String campusName) {
		this.campusName = campusName;
	}
	@Override
	public int compareTo(InvCampusTo o) {
		 return getCampusName().compareTo(o.getCampusName());
	}
	
	
	
	
}
