package com.kp.cms.to.timetable;

import java.util.List;

public class DepartentTimeTableClassTo {
	
	private String week;
	private int position;
	private List<DepartmentTimeTablePeriodTo> departmentTimeTablePeriodTos;
	
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
	public List<DepartmentTimeTablePeriodTo> getDepartmentTimeTablePeriodTos() {
		return departmentTimeTablePeriodTos;
	}
	public void setDepartmentTimeTablePeriodTos(List<DepartmentTimeTablePeriodTo> departmentTimeTablePeriodTos) {
		this.departmentTimeTablePeriodTos = departmentTimeTablePeriodTos;
	}
	
	
	
	
}
