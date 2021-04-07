package com.kp.cms.to.admission;

import java.io.Serializable;
import java.util.Date;

public class CCGroupTo implements Serializable, Comparable<CCGroupTo> {
	
	private int id;
	private String name;
	private String startDate;
	private String endDate;
	private String maxInTake;
	private String totalMaxInTake;
	private boolean display=false;
	private boolean checkBoxDisplay;
	
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
	
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getMaxInTake() {
		return maxInTake;
	}
	public void setMaxInTake(String maxInTake) {
		this.maxInTake = maxInTake;
	}
	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(CCGroupTo arg0) {
		if(arg0 instanceof CCGroupTo && arg0.getName()!=null ){
				return this.getName().compareTo(arg0.name);
		}else
			return 0;
	}
	public String getTotalMaxInTake() {
		return totalMaxInTake;
	}
	public void setTotalMaxInTake(String totalMaxInTake) {
		this.totalMaxInTake = totalMaxInTake;
	}
	public boolean isDisplay() {
		return display;
	}
	public void setDisplay(boolean display) {
		this.display = display;
	}
	public boolean isCheckBoxDisplay() {
		return checkBoxDisplay;
	}
	public void setCheckBoxDisplay(boolean checkBoxDisplay) {
		this.checkBoxDisplay = checkBoxDisplay;
	}
}
