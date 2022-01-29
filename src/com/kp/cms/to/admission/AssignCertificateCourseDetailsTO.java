package com.kp.cms.to.admission;

import java.io.Serializable;

public class AssignCertificateCourseDetailsTO implements Serializable,Comparable<AssignCertificateCourseDetailsTO> {
	
	private int id;
	private Boolean checked;
	private Boolean tempChecked;
	private int certificateCourseId;
	private String certificateCourseName;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Boolean getChecked() {
		return checked;
	}
	public void setChecked(Boolean checked) {
		this.checked = checked;
	}
	public Boolean getTempChecked() {
		return tempChecked;
	}
	public void setTempChecked(Boolean tempChecked) {
		this.tempChecked = tempChecked;
	}
	public int getCertificateCourseId() {
		return certificateCourseId;
	}
	public void setCertificateCourseId(int certificateCourseId) {
		this.certificateCourseId = certificateCourseId;
	}
	public String getCertificateCourseName() {
		return certificateCourseName;
	}
	public void setCertificateCourseName(String certificateCourseName) {
		this.certificateCourseName = certificateCourseName;
	}
	@Override
	public int compareTo(AssignCertificateCourseDetailsTO arg0) {
		if (arg0 != null    && this != null  ) {
			if(arg0.getCertificateCourseName() == null) {
				arg0.setCertificateCourseName("");
			}
			if(this.getCertificateCourseName() == null) {
				this.setCertificateCourseName("");
			}
			return this.getCertificateCourseName().toUpperCase().compareTo(arg0.getCertificateCourseName().toUpperCase());
		}
		return 0;
	}
	
}
