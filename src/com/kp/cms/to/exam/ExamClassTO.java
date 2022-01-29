package com.kp.cms.to.exam;

import java.io.Serializable;

public class ExamClassTO implements Serializable,Comparable<ExamClassTO>{
	private int courseId;
	private int courseSchemeId;
	private int schemeNo;
	private int academicYear;

	public ExamClassTO(int courseId, int courseSchemeId, int schemeNo) {
		super();
		this.courseId = courseId;
		this.courseSchemeId = courseSchemeId;
		this.schemeNo = schemeNo;
	}

	public ExamClassTO(int courseId, int courseSchemeId, int schemeNo,
			int academicYear) {
		super();
		this.courseId = courseId;
		this.courseSchemeId = courseSchemeId;
		this.schemeNo = schemeNo;
		this.academicYear = academicYear;
	}

	public int getCourseId() {
		return courseId;
	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}

	public int getCourseSchemeId() {
		return courseSchemeId;
	}

	public void setCourseSchemeId(int courseSchemeId) {
		this.courseSchemeId = courseSchemeId;
	}

	public int getSchemeNo() {
		return schemeNo;
	}

	public void setSchemeNo(int schemeNo) {
		this.schemeNo = schemeNo;
	}

	public void setAcademicYear(int academicYear) {
		this.academicYear = academicYear;
	}

	public int getAcademicYear() {
		return academicYear;
	}

	@Override
	public int compareTo(ExamClassTO arg0) {
		if(arg0!=null && this!=null && arg0.getCourseSchemeId()!=0
				 && this.getCourseSchemeId()!=0){
			if(this.getCourseSchemeId()>  arg0.getCourseSchemeId())
				return 1;
			else if(this.getCourseSchemeId() <  arg0.getCourseSchemeId())
				return -1;
			else
				return 0;
		}	
			return 0;
	}

}
