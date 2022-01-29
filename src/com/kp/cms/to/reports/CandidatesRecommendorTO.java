package com.kp.cms.to.reports;

import java.io.Serializable;

public class CandidatesRecommendorTO implements Serializable {
	
	private int appno;
	private String recommendorName;
	private String studentName;

	public int getAppno() {
		return appno;
	}
	public void setAppno(int appno) {
		this.appno = appno;
	}
	public String getRecommendorName() {
		return recommendorName;
	}
	public void setRecommendorName(String recommendorName) {
		this.recommendorName = recommendorName;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	
}