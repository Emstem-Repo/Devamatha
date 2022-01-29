package com.kp.cms.to.exam;

import java.io.Serializable;

/**
 * @author user
 *
 */
public class SupplementaryFeesTo implements Serializable,Comparable<SupplementaryFeesTo> {
	
	private int id;
	private String programName;
	private double theoryFees;
	private double practicalFees;
	private double paperFees;
	private double registrationFees;
	private double cvCampFees;
	private double marksListFees;
	private double applicationFees;
	private String sectionName;
	private String className;
	private String academicYear;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getProgramName() {
		return programName;
	}
	public void setProgramName(String programName) {
		this.programName = programName;
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
	
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	
	public double getPaperFees() {
		return paperFees;
	}
	public void setPaperFees(double paperFees) {
		this.paperFees = paperFees;
	}
	public double getRegistrationFees() {
		return registrationFees;
	}
	public void setRegistrationFees(double registrationFees) {
		this.registrationFees = registrationFees;
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
	public double getApplicationFees() {
		return applicationFees;
	}
	public void setApplicationFees(double applicationFees) {
		this.applicationFees = applicationFees;
	}
	public String getSectionName() {
		return sectionName;
	}
	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}
	public String getAcademicYear() {
		return academicYear;
	}
	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}
	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(SupplementaryFeesTo arg0) {
		if(arg0 instanceof SupplementaryFeesTo && arg0.getProgramName()!=null ){
			return this.getProgramName().compareTo(arg0.programName);
		}else
			return 0;
	}
}
