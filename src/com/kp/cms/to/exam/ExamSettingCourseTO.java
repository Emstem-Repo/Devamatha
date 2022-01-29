package com.kp.cms.to.exam;

import java.io.Serializable;
import java.util.List;

public class ExamSettingCourseTO implements Serializable,Comparable<ExamSettingCourseTO> {
	private int id;
	private int courseID;
	private List<DisplayValueTO> revaluationList;
	private String improvement;
	private String supplementaryForFailedSubject;

	private String minReqAttendanceWithoutFine;
	private String minReqAttendanceWithFine;

	private String passCriteria;
	private String programCourse;
	private String programType;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCourseID() {
		return courseID;
	}

	public void setCourseID(int courseID) {
		this.courseID = courseID;
	}

	public List<DisplayValueTO> getRevaluationList() {
		return revaluationList;
	}

	public void setRevaluationList(List<DisplayValueTO> revaluationList) {
		this.revaluationList = revaluationList;
	}

	public String getImprovement() {
		return improvement;
	}

	public void setImprovement(String improvement) {
		this.improvement = improvement;
	}

	public String getSupplementaryForFailedSubject() {
		return supplementaryForFailedSubject;
	}

	public void setSupplementaryForFailedSubject(
			String supplementaryForFailedSubject) {
		this.supplementaryForFailedSubject = supplementaryForFailedSubject;
	}

	public String getMinReqAttendanceWithoutFine() {
		return minReqAttendanceWithoutFine;
	}

	public void setMinReqAttendanceWithoutFine(
			String minReqAttendanceWithoutFine) {
		this.minReqAttendanceWithoutFine = minReqAttendanceWithoutFine;
	}

	public String getMinReqAttendanceWithFine() {
		return minReqAttendanceWithFine;
	}

	public void setMinReqAttendanceWithFine(String minReqAttendanceWithFine) {
		this.minReqAttendanceWithFine = minReqAttendanceWithFine;
	}

	public String getProgramCourse() {
		return programCourse;
	}

	public void setProgramCourse(String programCourse) {
		this.programCourse = programCourse;
	}

	public String getProgramType() {
		return programType;
	}

	public void setProgramType(String programType) {
		this.programType = programType;
	}

	public void setPassCriteria(String passCriteria) {
		this.passCriteria = passCriteria;
	}

	public String getPassCriteria() {
		return passCriteria;
	}

	@Override
	public int compareTo(ExamSettingCourseTO arg0) {
		if(arg0!=null && this!=null && arg0.getProgramCourse()!=null
				 && this.getProgramCourse()!=null){
			return this.getProgramCourse().compareTo(arg0.getProgramCourse());
		}else
		return 0;
	}

}
