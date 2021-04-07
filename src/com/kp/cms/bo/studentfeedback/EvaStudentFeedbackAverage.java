package com.kp.cms.bo.studentfeedback;

import java.util.HashSet;
import java.util.Set;

public class EvaStudentFeedbackAverage {
	private int id;
	private int classId;
	private int year;
	private double classAverage;
	private Set<EvaStudentFeedbackOverallAverage> overallAverages = new HashSet<EvaStudentFeedbackOverallAverage>();
	private int semester;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}	
	public int getClassId() {
		return classId;
	}
	public void setClassId(int classId) {
		this.classId = classId;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public double getClassAverage() {
		return classAverage;
	}
	public void setClassAverage(double classAverage) {
		this.classAverage = classAverage;
	}
	public Set<EvaStudentFeedbackOverallAverage> getOverallAverages() {
		return overallAverages;
	}
	public void setOverallAverages(
			Set<EvaStudentFeedbackOverallAverage> overallAverages) {
		this.overallAverages = overallAverages;
	}
	public int getSemester() {
		return semester;
	}
	public void setSemester(int semester) {
		this.semester = semester;
	}
	
}
