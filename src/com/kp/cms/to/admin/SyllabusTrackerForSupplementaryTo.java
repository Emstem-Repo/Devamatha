package com.kp.cms.to.admin;

import com.kp.cms.to.exam.RevaluationMarksUpdateTo;

public class SyllabusTrackerForSupplementaryTo implements Comparable<SyllabusTrackerForSupplementaryTo>{
	private int semNo;
	private String paperCode;
	private String paperName;
	private String numOfChancesLeft;
	private String admitedYear;
	private int courseId;
	private Boolean isChancesAvailable;
	private String joiningYear;
	private String registerNo; 
	private String examFirstAttemptedYear;
	
	public int getSemNo() {
		return semNo;
	}
	public void setSemNo(int semNo) {
		this.semNo = semNo;
	}
	public String getPaperCode() {
		return paperCode;
	}
	public void setPaperCode(String paperCode) {
		this.paperCode = paperCode;
	}
	public String getPaperName() {
		return paperName;
	}
	public void setPaperName(String paperName) {
		this.paperName = paperName;
	}
	public String getNumOfChancesLeft() {
		return numOfChancesLeft;
	}
	public void setNumOfChancesLeft(String numOfChancesLeft) {
		this.numOfChancesLeft = numOfChancesLeft;
	}
	public String getAdmitedYear() {
		return admitedYear;
	}
	public void setAdmitedYear(String admitedYear) {
		this.admitedYear = admitedYear;
	}
	public int getCourseId() {
		return courseId;
	}
	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}
	public Boolean getIsChancesAvailable() {
		return isChancesAvailable;
	}
	public void setIsChancesAvailable(Boolean isChancesAvailable) {
		this.isChancesAvailable = isChancesAvailable;
	}
	public String getJoiningYear() {
		return joiningYear;
	}
	public void setJoiningYear(String joiningYear) {
		this.joiningYear = joiningYear;
	}
	public String getRegisterNo() {
		return registerNo;
	}
	public void setRegisterNo(String registerNo) {
		this.registerNo = registerNo;
	}
	
	public int compareTo(SyllabusTrackerForSupplementaryTo arg0) {
		if(arg0 instanceof SyllabusTrackerForSupplementaryTo && this.getRegisterNo()!=null &&  arg0.getRegisterNo()!=null ){
			return this.getRegisterNo().compareTo(arg0.registerNo);
	}else
		return 0;
}
	public String getExamFirstAttemptedYear() {
		return examFirstAttemptedYear;
	}
	public void setExamFirstAttemptedYear(String examFirstAttemptedYear) {
		this.examFirstAttemptedYear = examFirstAttemptedYear;
	}
}
