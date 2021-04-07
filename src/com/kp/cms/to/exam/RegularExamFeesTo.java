package com.kp.cms.to.exam;

import java.io.Serializable;

/**
 * @author user
 *
 */
public class RegularExamFeesTo implements Serializable,Comparable<RegularExamFeesTo> {
	
	private int id;
	private String courseName;
	private double theoryFees;
	private double practicalFees;
	private double applicationFees;
	private double cvCampFees;
	private double marksListFees;
	private double onlineServiceChargeFees;
	private String academicYear;
	private String sectionName;
	private String className;
	private double examFees;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public double getTheoryFees() {
		return theoryFees;
	}
	public void setTheoryFees(double theoryFees) {
		this.theoryFees = theoryFees;
	}
	public double getPracticalFees() {
		return practicalFees;
	}
	public void setPracticalFees(double practicalFees) {
		this.practicalFees = practicalFees;
	}
	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(RegularExamFeesTo arg0) {
		if(arg0 instanceof RegularExamFeesTo && arg0.getCourseName()!=null ){
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
	public double getCvCampFees() {
		return cvCampFees;
	}
	public void setCvCampFees(double cvCampFees) {
		this.cvCampFees = cvCampFees;
	}
	public double getMarksListFees() {
		return marksListFees;
	}
	public void setMarksListFees(double marksListFees) {
		this.marksListFees = marksListFees;
	}
	public double getOnlineServiceChargeFees() {
		return onlineServiceChargeFees;
	}
	public void setOnlineServiceChargeFees(double onlineServiceChargeFees) {
		this.onlineServiceChargeFees = onlineServiceChargeFees;
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
	public String getSectionName() {
		return sectionName;
	}
	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public double getExamFees() {
		return examFees;
	}
	public void setExamFees(double examFees) {
		this.examFees = examFees;
	}
	
}
