package com.kp.cms.to.employee;

import java.util.Set;

public class HolidaysDatesTO {
	
	private int datesId;
	private Set<Integer> datesIdSet;
	private String date;
	private int holidaysId;
	
	public int getDatesId() {
		return datesId;
	}
	public void setDatesId(int datesId) {
		this.datesId = datesId;
	}
	public Set<Integer> getDatesIdSet() {
		return datesIdSet;
	}
	public void setDatesIdSet(Set<Integer> datesIdSet) {
		this.datesIdSet = datesIdSet;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public int getHolidaysId() {
		return holidaysId;
	}
	public void setHolidaysId(int holidaysId) {
		this.holidaysId = holidaysId;
	}
	
	
}
