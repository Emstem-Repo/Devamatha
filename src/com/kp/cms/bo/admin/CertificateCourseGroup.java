package com.kp.cms.bo.admin;

import java.io.Serializable;

public class CertificateCourseGroup implements Serializable {
	private int id;
	private CertificateCourse certificateCourse;
	private CCGroup groups;
	private Integer maxIntake;
	

	public CertificateCourseGroup() {
		super();
	}


	public CertificateCourseGroup(int id, CertificateCourse certificateCourse,
			CCGroup groups, Integer maxIntake) {
		this.id = id;
		this.certificateCourse = certificateCourse;
		this.groups = groups;
		this.maxIntake = maxIntake;
	}
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public CertificateCourse getCertificateCourse() {
		return certificateCourse;
	}
	public void setCertificateCourse(CertificateCourse certificateCourse) {
		this.certificateCourse = certificateCourse;
	}
	public CCGroup getGroups() {
		return groups;
	}
	public void setGroups(CCGroup group) {
		this.groups = group;
	}
	public Integer getMaxIntake() {
		return maxIntake;
	}
	public void setMaxIntake(Integer maxIntake) {
		this.maxIntake = maxIntake;
	}
}