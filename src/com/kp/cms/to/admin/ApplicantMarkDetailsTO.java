package com.kp.cms.to.admin;

import java.io.Serializable;


public class ApplicantMarkDetailsTO  implements Serializable,Comparable<ApplicantMarkDetailsTO>{
	private int id;
	private int ednQualificationId;
	private int semesterNo;
	private String semesterName;
	private String maxMarks;
	private String marksObtained;
	private int percentage;
	private boolean lastExam;
	private int totalMark;
	private int obtainedMark;
	private boolean populated;
	private String maxMarks_languagewise;
	private String marksObtained_languagewise;
	private String totalMark_languagewise;
	private String totalObtainedMark_languagewise_;
	
	public String getMaxMarks_languagewise() {
		return maxMarks_languagewise;
	}
	public void setMaxMarks_languagewise(String maxMarks_languagewise) {
		this.maxMarks_languagewise = maxMarks_languagewise;
	}
	public String getMarksObtained_languagewise() {
		return marksObtained_languagewise;
	}
	public void setMarksObtained_languagewise(String marksObtained_languagewise) {
		this.marksObtained_languagewise = marksObtained_languagewise;
	}
	public String getTotalMark_languagewise() {
		return totalMark_languagewise;
	}
	public void setTotalMark_languagewise(String totalMark_languagewise) {
		this.totalMark_languagewise = totalMark_languagewise;
	}
	public String getTotalObtainedMark_languagewise_() {
		return totalObtainedMark_languagewise_;
	}
	public void setTotalObtainedMark_languagewise_(
			String totalObtainedMark_languagewise_) {
		this.totalObtainedMark_languagewise_ = totalObtainedMark_languagewise_;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getEdnQualificationId() {
		return ednQualificationId;
	}
	public void setEdnQualificationId(int ednQualificationId) {
		this.ednQualificationId = ednQualificationId;
	}
	public int getSemesterNo() {
		return semesterNo;
	}
	public void setSemesterNo(int semesterNo) {
		this.semesterNo = semesterNo;
	}
	public String getSemesterName() {
		return semesterName;
	}
	public void setSemesterName(String semesterName) {
		this.semesterName = semesterName;
	}
	public String getMaxMarks() {
		return maxMarks;
	}
	public void setMaxMarks(String maxMarks) {
		this.maxMarks = maxMarks;
	}
	public String getMarksObtained() {
		return marksObtained;
	}
	public void setMarksObtained(String marksObtained) {
		this.marksObtained = marksObtained;
	}
	public int getPercentage() {
		return percentage;
	}
	public void setPercentage(int percentage) {
		this.percentage = percentage;
	}
	
	public int getTotalMark() {
		return totalMark;
	}
	public void setTotalMark(int totalMark) {
		this.totalMark = totalMark;
	}
	public int getObtainedMark() {
		return obtainedMark;
	}
	public void setObtainedMark(int obtainedMark) {
		this.obtainedMark = obtainedMark;
	}
	public boolean isLastExam() {
		return lastExam;
	}
	public void setLastExam(boolean lastExam) {
		this.lastExam = lastExam;
	}
	public boolean isPopulated() {
		return populated;
	}
	public void setPopulated(boolean populated) {
		this.populated = populated;
	}
	@Override
	public int compareTo(ApplicantMarkDetailsTO arg0) {
		if(arg0!=null && this!=null){
			if(this.getSemesterNo() > arg0.getSemesterNo())
				return 1;
			else if(this.getSemesterNo() < arg0.getSemesterNo())
				return -1;
			else
				return 0;
		}
		return 0;
	}
	
}
