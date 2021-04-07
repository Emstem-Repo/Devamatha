package com.kp.cms.to.attendance;

import java.util.LinkedList;

import com.kp.cms.bo.admin.Student;

public class HourseHeldTO {

	
	private int day;
	
	private String hourseHeld;
	
	private int attenndenceId;
	
	private Student student;
	
	LinkedList<HoursHeld> hoursHeldList;
	
	private int tabIndex;

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public String getHourseHeld() {
		return hourseHeld;
	}

	public void setHourseHeld(String hourseHeld) {
		this.hourseHeld = hourseHeld;
	}

	public int getAttenndenceId() {
		return attenndenceId;
	}

	public void setAttenndenceId(int attenndenceId) {
		this.attenndenceId = attenndenceId;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public LinkedList<HoursHeld> getHoursHeldList() {
		return hoursHeldList;
	}

	public void setHoursHeldList(LinkedList<HoursHeld> hoursHeldList) {
		this.hoursHeldList = hoursHeldList;
	}

	public int getTabIndex() {
		return tabIndex;
	}

	public void setTabIndex(int tabIndex) {
		this.tabIndex = tabIndex;
	}
	

}
