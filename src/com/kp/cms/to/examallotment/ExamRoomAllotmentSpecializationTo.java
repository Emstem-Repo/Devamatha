package com.kp.cms.to.examallotment;

import java.io.Serializable;
import java.util.List;

public class ExamRoomAllotmentSpecializationTo implements Serializable{
	private int id;
	private String midEndSem;
	private int schemeNo;
	private List<Integer> courseIds;
	private List<String> courseNames;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getMidEndSem() {
		return midEndSem;
	}
	public void setMidEndSem(String midEndSem) {
		this.midEndSem = midEndSem;
	}
	public int getSchemeNo() {
		return schemeNo;
	}
	public void setSchemeNo(int schemeNo) {
		this.schemeNo = schemeNo;
	}
	public List<Integer> getCourseIds() {
		return courseIds;
	}
	public void setCourseIds(List<Integer> courseIds) {
		this.courseIds = courseIds;
	}
	public List<String> getCourseNames() {
		return courseNames;
	}
	public void setCourseNames(List<String> courseNames) {
		this.courseNames = courseNames;
	}
	
}
