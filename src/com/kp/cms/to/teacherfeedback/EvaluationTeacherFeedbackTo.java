package com.kp.cms.to.teacherfeedback;

import java.io.Serializable;
import java.util.List;


public class EvaluationTeacherFeedbackTo {
	
	private int studentId;
	private String studentName;
	private String rollNo;
	private String registerNo;
	private int applicationNo;
	private Boolean done;
	private List<Integer> totalStudents;
	private int studentNo;
	private String remarks;
	List<EvaTeacherFeedBackQuestionTo> questionTosList;
	private String indexPoints;
	private String internal;
	private String average;
	private String pointsScoreByTeacher;
	private String weightedPoints;
	
	public int getStudentId() {
		return studentId;
	}
	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public String getRollNo() {
		return rollNo;
	}
	public void setRollNo(String rollNo) {
		this.rollNo = rollNo;
	}
	public String getRegisterNo() {
		return registerNo;
	}
	public void setRegisterNo(String registerNo) {
		this.registerNo = registerNo;
	}
	public int getApplicationNo() {
		return applicationNo;
	}
	public void setApplicationNo(int applicationNo) {
		this.applicationNo = applicationNo;
	}
	public Boolean getDone() {
		return done;
	}
	public void setDone(Boolean done) {
		this.done = done;
	}
	public List<Integer> getTotalStudents() {
		return totalStudents;
	}
	public void setTotalStudents(List<Integer> totalStudents) {
		this.totalStudents = totalStudents;
	}
	public int getStudentNo() {
		return studentNo;
	}
	public void setStudentNo(int studentNo) {
		this.studentNo = studentNo;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public List<EvaTeacherFeedBackQuestionTo> getQuestionTosList() {
		return questionTosList;
	}
	public void setQuestionTosList(
			List<EvaTeacherFeedBackQuestionTo> questionTosList) {
		this.questionTosList = questionTosList;
	}
	public String getIndexPoints() {
		return indexPoints;
	}
	public void setIndexPoints(String indexPoints) {
		this.indexPoints = indexPoints;
	}
	public String getInternal() {
		return internal;
	}
	public void setInternal(String internal) {
		this.internal = internal;
	}
	public String getAverage() {
		return average;
	}
	public void setAverage(String average) {
		this.average = average;
	}
	public String getPointsScoreByTeacher() {
		return pointsScoreByTeacher;
	}
	public void setPointsScoreByTeacher(String pointsScoreByTeacher) {
		this.pointsScoreByTeacher = pointsScoreByTeacher;
	}
	public String getWeightedPoints() {
		return weightedPoints;
	}
	public void setWeightedPoints(String weightedPoints) {
		this.weightedPoints = weightedPoints;
	}

	
}
