package com.kp.cms.to.inventory;

public class InvCompanyTO implements Comparable<InvCompanyTO>{
	private int id;
	private String companyName;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	@Override
	public int compareTo(InvCompanyTO o) {
		return getCompanyName().compareTo(o.getCompanyName());
	}

}
