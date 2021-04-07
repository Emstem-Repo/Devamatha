package com.kp.cms.to.inventory;

public class InvCounterTO {
	private int id;
	private String type;
	private String prefix;
	private int startNo;
	private int currentNo;
	
	public String getType() {
		return type;
	}
	public String getPrefix() {
		return prefix;
	}
	public int getStartNo() {
		return startNo;
	}
	public void setType(String type) {
		this.type = type;
	}
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	public void setStartNo(int startNo) {
		this.startNo = startNo;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCurrentNo() {
		return currentNo;
	}
	public void setCurrentNo(int currentNo) {
		this.currentNo = currentNo;
	}

}
