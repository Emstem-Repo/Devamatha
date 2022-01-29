package com.kp.cms.to.timetable;

import java.io.Serializable;
import java.util.ArrayList;

public class SubjectWiseTimeTableViewTo  implements Serializable{
	private String dayName;
	private ArrayList<String> subMapList;

	public SubjectWiseTimeTableViewTo(String dayName, ArrayList<String> subMapList) {
		this.dayName = dayName;
		this.subMapList = subMapList;
	}

	public String getDayName() {
		return dayName;
	}

	public void setDayName(String dayName) {
		this.dayName = dayName;
	}

	public ArrayList<String> getSubMapList() {
		return subMapList;
	}

	public void setSubMapList(ArrayList<String> subMapList) {
		this.subMapList = subMapList;
	}
}

