package com.kp.cms.to.timetable;

import java.io.Serializable;

@SuppressWarnings("serial")
public class GenerateTimeTableTo extends TimeTableModuleTo implements
		Serializable {

	private String className;
	private String subjectName;
	private String status;
	private String noOfHours;
	private String endDate;

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getNoOfHours() {
		return noOfHours;
	}

	public void setNoOfHours(String noOfHours) {
		this.noOfHours = noOfHours;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

}
