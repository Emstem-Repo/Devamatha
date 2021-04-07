package com.kp.cms.to.exam;

import java.io.Serializable;

/**
 * @author user
 *
 */
public class RevaluationExamFeesTo implements Serializable,Comparable<RevaluationExamFeesTo> {
	
	private int id;
	private String courseName;
	private double applicationFees;
	private double marksCopyFees;
	private double revaluationFees;
	private double scrutinyFees;
	private String academicYear;
	private double onlineSevriceFees;
	private double challengeValuationFees;

	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	
	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(RevaluationExamFeesTo arg0) {
		if(arg0 instanceof RevaluationExamFeesTo && arg0.getCourseName()!=null ){
			return this.courseName.compareTo(arg0.getCourseName());
		}else
			return 0;
	}
	public double getApplicationFees() {
		return applicationFees;
	}
	public void setApplicationFees(double applicationFees) {
		this.applicationFees = applicationFees;
	}
	
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public String getAcademicYear() {
		return academicYear;
	}
	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}
	public double getMarksCopyFees() {
		return marksCopyFees;
	}
	public void setMarksCopyFees(double marksCopyFees) {
		this.marksCopyFees = marksCopyFees;
	}
	public double getRevaluationFees() {
		return revaluationFees;
	}
	public void setRevaluationFees(double revaluationFees) {
		this.revaluationFees = revaluationFees;
	}
	public double getScrutinyFees() {
		return scrutinyFees;
	}
	public void setScrutinyFees(double scrutinyFees) {
		this.scrutinyFees = scrutinyFees;
	}
	public double getOnlineSevriceFees() {
		return onlineSevriceFees;
	}
	public void setOnlineSevriceFees(double onlineSevriceFees) {
		this.onlineSevriceFees = onlineSevriceFees;
	}
	public double getChallengeValuationFees() {
		return challengeValuationFees;
	}
	public void setChallengeValuationFees(double challengeValuationFees) {
		this.challengeValuationFees = challengeValuationFees;
	}
	
}
