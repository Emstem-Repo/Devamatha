package com.kp.cms.bo.admin;

public class AssignCertificateCourseDetails implements java.io.Serializable{
	
	private int id;
	private AssignCertificateCourse assignCertificateCourse;
	private CertificateCourse certificateCourse;
	
	public AssignCertificateCourseDetails() {
		super();
		// TODO Auto-generated constructor stub
	}
	public AssignCertificateCourseDetails(int id,
			AssignCertificateCourse assignCertificateCourse,
			CertificateCourse certificateCourse) {
		super();
		this.id = id;
		this.assignCertificateCourse = assignCertificateCourse;
		this.certificateCourse = certificateCourse;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public AssignCertificateCourse getAssignCertificateCourse() {
		return assignCertificateCourse;
	}
	public void setAssignCertificateCourse(
			AssignCertificateCourse assignCertificateCourse) {
		this.assignCertificateCourse = assignCertificateCourse;
	}
	public CertificateCourse getCertificateCourse() {
		return certificateCourse;
	}
	public void setCertificateCourse(CertificateCourse certificateCourse) {
		this.certificateCourse = certificateCourse;
	}
	
	
}
