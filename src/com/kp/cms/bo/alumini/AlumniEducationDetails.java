package com.kp.cms.bo.alumini;

import java.util.Date;

import com.kp.cms.bo.admin.Course;

public class AlumniEducationDetails {

	private int id;
	private AlumniRegistrationDetails alumniRegistrationDetails;
	private Course course;
	private int batchFrom;
	private int batchTo;
	private int passOutYear;
	private Date createdDate;
	public AlumniEducationDetails() {
		super();
	}
	public AlumniEducationDetails(int id,
			AlumniRegistrationDetails alumniRegistrationDetails, Course course,
			int batchFrom, int batchTo, int passOutYear, Date createdDate) {
		super();
		this.id = id;
		this.alumniRegistrationDetails = alumniRegistrationDetails;
		this.course = course;
		this.batchFrom = batchFrom;
		this.batchTo = batchTo;
		this.passOutYear = passOutYear;
		this.createdDate = createdDate;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public AlumniRegistrationDetails getAlumniRegistrationDetails() {
		return alumniRegistrationDetails;
	}
	public void setAlumniRegistrationDetails(
			AlumniRegistrationDetails alumniRegistrationDetails) {
		this.alumniRegistrationDetails = alumniRegistrationDetails;
	}
	public Course getCourse() {
		return course;
	}
	public void setCourse(Course course) {
		this.course = course;
	}
	public int getBatchFrom() {
		return batchFrom;
	}
	public void setBatchFrom(int batchFrom) {
		this.batchFrom = batchFrom;
	}
	public int getBatchTo() {
		return batchTo;
	}
	public void setBatchTo(int batchTo) {
		this.batchTo = batchTo;
	}
	public int getPassOutYear() {
		return passOutYear;
	}
	public void setPassOutYear(int passOutYear) {
		this.passOutYear = passOutYear;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
}
