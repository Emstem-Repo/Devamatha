package com.kp.cms.forms.studentfeedback;

import java.util.List;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.studentfeedback.EvaStudentFeedBackQuestionTo;

public class EvaluateFacultyFeedbackForm extends BaseActionForm{
	private String academicYear;
	private String teacherId;
	private List<EvaStudentFeedBackQuestionTo> questions;
	private double overallScore;
	private double classAverage;

	public String getAcademicYear() {
		return academicYear;
	}

	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}
	public void resetFeilds()
	{
		super.clear();
		this.academicYear=null;
	}

	public String getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(String teacherId) {
		this.teacherId = teacherId;
	}

	public List<EvaStudentFeedBackQuestionTo> getQuestions() {
		return questions;
	}

	public void setQuestions(List<EvaStudentFeedBackQuestionTo> questions) {
		this.questions = questions;
	}

	public double getOverallScore() {
		return overallScore;
	}

	public void setOverallScore(double overallScore) {
		this.overallScore = overallScore;
	}

	public double getClassAverage() {
		return classAverage;
	}

	public void setClassAverage(double classAverage) {
		this.classAverage = classAverage;
	}
	
}
