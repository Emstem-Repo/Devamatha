
package com.kp.cms.to.exam;

import java.util.ArrayList;

public class ExamDefListTO {

	private ArrayList<Integer> courseIdList;
	private ArrayList<Integer> schemeIdList;
	private ArrayList<Integer> programIdList;
	private ArrayList<Integer> courseSchemeIdList;
	public ArrayList<Integer> getCourseIdList() {
		return courseIdList;
	}
	public void setCourseIdList(ArrayList<Integer> courseIdList) {
		this.courseIdList = courseIdList;
	}
	public ArrayList<Integer> getSchemeIdList() {
		return schemeIdList;
	}
	public void setSchemeIdList(ArrayList<Integer> schemeIdList) {
		this.schemeIdList = schemeIdList;
	}
	public ArrayList<Integer> getProgramIdList() {
		return programIdList;
	}
	public void setProgramIdList(ArrayList<Integer> programIdList) {
		this.programIdList = programIdList;
	}
	public ArrayList<Integer> getCourseSchemeIdList() {
		return courseSchemeIdList;
	}
	public void setCourseSchemeIdList(ArrayList<Integer> courseSchemeIdList) {
		this.courseSchemeIdList = courseSchemeIdList;
	}
	
}
