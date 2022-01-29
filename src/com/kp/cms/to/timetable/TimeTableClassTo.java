package com.kp.cms.to.timetable;

import java.util.List;

public class TimeTableClassTo {
	
	private String week;
	private int position;
	private List<TimeTablePeriodTo> timeTablePeriodTos;
	
	public String getWeek() {
		return week;
	}
	public void setWeek(String week) {
		this.week = week;
	}
	public int getPosition() {
		return position;
	}
	public void setPosition(int position) {
		this.position = position;
	}
	public List<TimeTablePeriodTo> getTimeTablePeriodTos() {
		return timeTablePeriodTos;
	}
	public void setTimeTablePeriodTos(List<TimeTablePeriodTo> timeTablePeriodTos) {
		this.timeTablePeriodTos = timeTablePeriodTos;
	}
	
	
	
}
