package com.kp.cms.to.timetable;

import java.io.Serializable;
import java.util.Map;

@SuppressWarnings("serial")
public class DurationAllocationTo extends TimeTableModuleTo implements
		Serializable {

	private String subjectName;
	private int minimumLectureHours;
	private int maximumLectureHours;
	private int schemeNo;
	private int subjectId;
	private Map<Integer, String> schemeMap;

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public int getMinimumLectureHours() {
		return minimumLectureHours;
	}

	public void setMinimumLectureHours(int minimumLectureHours) {
		this.minimumLectureHours = minimumLectureHours;
	}

	public int getMaximumLectureHours() {
		return maximumLectureHours;
	}

	public void setMaximumLectureHours(int maximumLectureHours) {
		this.maximumLectureHours = maximumLectureHours;
	}

	public Map<Integer, String> getSchemeMap() {
		return schemeMap;
	}

	public void setSchemeMap(Map<Integer, String> schemeMap) {
		this.schemeMap = schemeMap;
	}

	public int getSchemeNo() {
		return schemeNo;
	}

	public void setSchemeNo(int schemeNo) {
		this.schemeNo = schemeNo;
	}

	public int getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(int subjectId) {
		this.subjectId = subjectId;
	}

}
