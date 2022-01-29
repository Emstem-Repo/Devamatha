package com.kp.cms.to.admin;

import java.util.HashSet;
import java.util.Set;

public class CategoryTO {

	private int id;
	private String name;
	private String description;
	private Set weightageDefinitions = new HashSet(0);
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Set getWeightageDefinitions() {
		return weightageDefinitions;
	}
	public void setWeightageDefinitions(Set weightageDefinitions) {
		this.weightageDefinitions = weightageDefinitions;
	}

	
	
}
