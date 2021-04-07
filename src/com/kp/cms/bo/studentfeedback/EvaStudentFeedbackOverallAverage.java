package com.kp.cms.bo.studentfeedback;

import java.util.HashSet;
import java.util.Set;

import com.kp.cms.bo.admin.Subject;
import com.kp.cms.bo.admin.Users;

public class EvaStudentFeedbackOverallAverage {
	private int id;
	private EvaStudentFeedbackAverage evaStudentFeedbackAverage;
	private Users teacher;
	private Subject subject;
	private double overallAverage;
	private Set<EvaStudentFeedbackScore> scores = new HashSet<EvaStudentFeedbackScore>();

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public EvaStudentFeedbackAverage getEvaStudentFeedbackAverage() {
		return evaStudentFeedbackAverage;
	}
	public void setEvaStudentFeedbackAverage(
			EvaStudentFeedbackAverage evaStudentFeedbackAverage) {
		this.evaStudentFeedbackAverage = evaStudentFeedbackAverage;
	}
	public Users getTeacher() {
		return teacher;
	}
	public void setTeacher(Users teacher) {
		this.teacher = teacher;
	}
	public Subject getSubject() {
		return subject;
	}
	public void setSubject(Subject subject) {
		this.subject = subject;
	}
	public double getOverallAverage() {
		return overallAverage;
	}
	public void setOverallAverage(double overallAverage) {
		this.overallAverage = overallAverage;
	}
	public Set<EvaStudentFeedbackScore> getScores() {
		return scores;
	}
	public void setScores(Set<EvaStudentFeedbackScore> scores) {
		this.scores = scores;
	}	
	
}
