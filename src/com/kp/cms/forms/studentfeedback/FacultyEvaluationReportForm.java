package com.kp.cms.forms.studentfeedback;

import java.util.List;
import java.util.Map;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.studentfeedback.EvaStudentFeedBackQuestionTo;
import com.kp.cms.to.studentfeedback.FacultyEvaluationReportTo;

public class FacultyEvaluationReportForm extends BaseActionForm{
	private String academicYear;
	private Map<Integer,String> subjectMap;
	private FacultyEvaluationReportTo to;
	private List<EvaStudentFeedBackQuestionTo> questions;
	private double overallScore;
	private double classAverage;
	private double deptAverage;
	private boolean dataExist;
	private Map<Integer, String> remarks;
	
	public String getAcademicYear() {
		return academicYear;
	}
	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}
	public Map<Integer, String> getSubjectMap() {
		return subjectMap;
	}
	public void setSubjectMap(Map<Integer, String> subjectMap) {
		this.subjectMap = subjectMap;
	}
	public void resetFeilds()
	{
		super.clear();
		this.academicYear=null;
		this.subjectMap=null;
	}
	public void setTo(FacultyEvaluationReportTo to) {
		this.to = to;
	}
	public FacultyEvaluationReportTo getTo() {
		return to;
	}
	public List<EvaStudentFeedBackQuestionTo> getQuestions() {
		return questions;
	}
	public void setQuestions(List<EvaStudentFeedBackQuestionTo> queTo) {
		this.questions = queTo;
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
	public double getDeptAverage() {
		return deptAverage;
	}
	public void setDeptAverage(double deptAverage) {
		this.deptAverage = deptAverage;
	}
	public boolean isDataExist() {
		return dataExist;
	}
	public void setDataExist(boolean dataExist) {
		this.dataExist = dataExist;
	}
	public Map<Integer, String> getRemarks() {
		return remarks;
	}
	public void setRemarks(Map<Integer, String> remarks) {
		this.remarks = remarks;
	}
	
}
