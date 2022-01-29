package com.kp.cms.to.alumni;

public class AlumniEducationDetailsTO {

	private int id;
	private String progarmTypeId;
	private String progarmId;
	private String courseId;
	private String batchFrom;
	private String batchTo;
	private String passOutYear;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getProgarmTypeId() {
		return progarmTypeId;
	}
	public void setProgarmTypeId(String progarmTypeId) {
		this.progarmTypeId = progarmTypeId;
	}
	public String getProgarmId() {
		return progarmId;
	}
	public void setProgarmId(String progarmId) {
		this.progarmId = progarmId;
	}
	public String getCourseId() {
		return courseId;
	}
	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
	public String getBatchFrom() {
		return batchFrom;
	}
	public void setBatchFrom(String batchFrom) {
		this.batchFrom = batchFrom;
	}
	public String getBatchTo() {
		return batchTo;
	}
	public void setBatchTo(String batchTo) {
		this.batchTo = batchTo;
	}
	public String getPassOutYear() {
		return passOutYear;
	}
	public void setPassOutYear(String passOutYear) {
		this.passOutYear = passOutYear;
	}
}
