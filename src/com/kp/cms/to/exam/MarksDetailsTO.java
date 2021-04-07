package com.kp.cms.to.exam;

import java.io.Serializable;

public class MarksDetailsTO implements Serializable {
	private int id;
	private String theoryMarks;
	private String practicalMarks;
	private int classId;
	private int marksId;
	private boolean theorySecured;
	private boolean practicalSecured;
	private String subjectId;
	private String previousPracticalMarks;
	private String prevoiusTheoryMarks;
	private String currentPracticalMarks;
	private String currentTheoryMarks;
	private String attendanceMark;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTheoryMarks() {
		return theoryMarks;
	}
	public void setTheoryMarks(String theoryMarks) {
		this.theoryMarks = theoryMarks;
	}
	public String getPracticalMarks() {
		return practicalMarks;
	}
	public void setPracticalMarks(String practicalMarks) {
		this.practicalMarks = practicalMarks;
	}
	public int getClassId() {
		return classId;
	}
	public void setClassId(int classId) {
		this.classId = classId;
	}
	public int getMarksId() {
		return marksId;
	}
	public void setMarksId(int marksId) {
		this.marksId = marksId;
	}
	public boolean isTheorySecured() {
		return theorySecured;
	}
	public void setTheorySecured(boolean theorySecured) {
		this.theorySecured = theorySecured;
	}
	public boolean isPracticalSecured() {
		return practicalSecured;
	}
	public void setPracticalSecured(boolean practicalSecured) {
		this.practicalSecured = practicalSecured;
	}
	public String getSubjectId() {
		return subjectId;
	}
	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}
	public String getPreviousPracticalMarks() {
		return previousPracticalMarks;
	}
	public void setPreviousPracticalMarks(String previousPracticalMarks) {
		this.previousPracticalMarks = previousPracticalMarks;
	}
	public String getPrevoiusTheoryMarks() {
		return prevoiusTheoryMarks;
	}
	public void setPrevoiusTheoryMarks(String prevoiusTheoryMarks) {
		this.prevoiusTheoryMarks = prevoiusTheoryMarks;
	}
	public String getCurrentPracticalMarks() {
		return currentPracticalMarks;
	}
	public void setCurrentPracticalMarks(String currentPracticalMarks) {
		this.currentPracticalMarks = currentPracticalMarks;
	}
	public String getCurrentTheoryMarks() {
		return currentTheoryMarks;
	}
	public void setCurrentTheoryMarks(String currentTheoryMarks) {
		this.currentTheoryMarks = currentTheoryMarks;
	}
	public String getAttendanceMark() {
		return attendanceMark;
	}
	public void setAttendanceMark(String attendanceMark) {
		this.attendanceMark = attendanceMark;
	}
	
	
}
