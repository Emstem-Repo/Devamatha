package com.kp.cms.to.exam;

import java.io.Serializable;

public class ModerationMarksEntryTo implements Serializable {
	
	private int studentId;
	private int subjectId;
	private int moderationId;
	private int classId;
	private int examId;
	
	private String subjectName;
	private String subjectCode;
	private String theoryMarks;
	private String practicalMarks;
	private String oldTheoryMarks;
	private String oldPracticalMarks;
	private String previousPracticalMarks;
	private String prevoiusTheoryMarks;
	private String currentPracticalMarks;
	private String currentTheoryMarks;
    private boolean revaluation;
    private boolean moderation;
    private String comments;
    private String theoryPractical;
    private String studentName;
    private String regNo;
    private boolean scrutiny;
    private boolean challengeValuation;
	

	public int getStudentId() {
		return studentId;
	}
	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}
	public int getSubjectId() {
		return subjectId;
	}
	public void setSubjectId(int subjectId) {
		this.subjectId = subjectId;
	}
	public String getSubjectName() {
		return subjectName;
	}
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
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
	
	public String getSubjectCode() {
		return subjectCode;
	}
	public void setSubjectCode(String subjectCode) {
		this.subjectCode = subjectCode;
	}
	public int getModerationId() {
		return moderationId;
	}
	public void setModerationId(int moderationId) {
		this.moderationId = moderationId;
	}
	public int getClassId() {
		return classId;
	}
	public void setClassId(int classId) {
		this.classId = classId;
	}
	public int getExamId() {
		return examId;
	}
	public void setExamId(int examId) {
		this.examId = examId;
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
	
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getComments() {
		return comments;
	}
	public boolean isRevaluation() {
		return revaluation;
	}
	public void setRevaluation(boolean revaluation) {
		this.revaluation = revaluation;
	}
	public boolean isModeration() {
		return moderation;
	}
	public void setModeration(boolean moderation) {
		this.moderation = moderation;
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
	public void setTheoryPractical(String theoryPractical) {
		this.theoryPractical = theoryPractical;
	}
	public String getTheoryPractical() {
		return theoryPractical;
	}
	public String getOldTheoryMarks() {
		return oldTheoryMarks;
	}
	public void setOldTheoryMarks(String oldTheoryMarks) {
		this.oldTheoryMarks = oldTheoryMarks;
	}
	public String getOldPracticalMarks() {
		return oldPracticalMarks;
	}
	public void setOldPracticalMarks(String oldPracticalMarks) {
		this.oldPracticalMarks = oldPracticalMarks;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public String getRegNo() {
		return regNo;
	}
	public void setRegNo(String regNo) {
		this.regNo = regNo;
	}
	public boolean getScrutiny() {
		return scrutiny;
	}
	public void setScrutiny(boolean scrutiny) {
		this.scrutiny = scrutiny;
	}
	public boolean getChallengeValuation() {
		return challengeValuation;
	}
	public void setChallengeValuation(boolean challengeValuation) {
		this.challengeValuation = challengeValuation;
	}
	
}