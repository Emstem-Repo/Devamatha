package com.kp.cms.to.exam;

import java.io.Serializable;
import java.util.ArrayList;

public class ExamCourseSchemeDetailsTO implements Serializable,Comparable<ExamCourseSchemeDetailsTO>{
	/**
	 * Dec 24, 2009 Created By 9Elements Team
	 */
	private int courseId;
	private String course;
	private int schemeId;
	private String schemeName;
	private String schemeCount;
	private int programId;
	private ArrayList<ExamSchemeTO> listDisplay;

	public String getCourse() {
		return course;
	}

	public void setCourse(String course) {
		this.course = course;
	}

	

	public ArrayList<ExamSchemeTO> getListDisplay() {
		return listDisplay;
	}

	public void setListDisplay(ArrayList<ExamSchemeTO> listDisplay) {
		this.listDisplay = listDisplay;
	}

	public void setSchemeCount(String schemeCount) {
		this.schemeCount = schemeCount;
	}

	public String getSchemeCount() {
		return schemeCount;
	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}

	public int getCourseId() {
		return courseId;
	}

	public void setSchemeId(int schemeId) {
		this.schemeId = schemeId;
	}

	public int getSchemeId() {
		return schemeId;
	}

	public void setSchemeName(String schemeName) {
		this.schemeName = schemeName;
	}

	public String getSchemeName() {
		return schemeName;
	}

	public void setProgramId(int programId) {
		this.programId = programId;
	}

	public int getProgramId() {
		return programId;
	}

	@Override
	public int compareTo(ExamCourseSchemeDetailsTO arg0) {
		if(arg0!=null && this!=null && arg0.getCourse()!=null && this.getCourse()!=null){
			return (this.getCourse()).compareTo(arg0.getCourse());
		}else
		return 0;
	}

}
