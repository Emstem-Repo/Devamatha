package com.kp.cms.to.reports;

import java.io.Serializable;
import com.kp.cms.to.admin.CourseTO;

public class StudentsListReportTO implements Serializable{
	
	private CourseTO courseTO;
	private String maxIntake;
	private String noOfMale;
	private String noOfFemale;
	private String total;
	private String semister;
	
	public String getSemister() {
		return semister;
	}
	public void setSemister(String semister) {
		this.semister = semister;
	}
	public CourseTO getCourseTO() {
		return courseTO;
	}
	public void setCourseTO(CourseTO courseTO) {
		this.courseTO = courseTO;
	}
	public String getMaxIntake() {
		return maxIntake;
	}
	public void setMaxIntake(String maxIntake) {
		this.maxIntake = maxIntake;
	}
	public String getNoOfMale() {
		return noOfMale;
	}
	public void setNoOfMale(String noOfMale) {
		this.noOfMale = noOfMale;
	}
	public String getNoOfFemale() {
		return noOfFemale;
	}
	public void setNoOfFemale(String noOfFemale) {
		this.noOfFemale = noOfFemale;
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
}