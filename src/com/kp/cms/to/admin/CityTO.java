package com.kp.cms.to.admin;


public class CityTO {

	private int id;
	private StateTO stateTo;
	private String name;
	

	
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public StateTO getStateTo() {
		return stateTo;
	}

	public void setStateTo(StateTO stateTo) {
		this.stateTo = stateTo;
	}
}
