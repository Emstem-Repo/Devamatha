package com.kp.cms.to.exam;

import java.util.List;

public class SupplementaryApplicationClassTo {

	private int classId;
	private String className;
	private List<ExamSupplementaryImpApplicationSubjectTO> toList;
	private String registerNo;
	private String rollNo;
	private String courseName;
	private String semNo;
	private String studentName;
	private boolean supplementary;
	private boolean improvement;
	private boolean isRevaluation;
	private boolean isChallengeValuation;
	private boolean isScrutiny;
	
	public int getClassId() {
		return classId;
	}
	public void setClassId(int classId) {
		this.classId = classId;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public List<ExamSupplementaryImpApplicationSubjectTO> getToList() {
		return toList;
	}
	public void setToList(List<ExamSupplementaryImpApplicationSubjectTO> toList) {
		this.toList = toList;
	}
	public String getRegisterNo() {
		return registerNo;
	}
	public void setRegisterNo(String registerNo) {
		this.registerNo = registerNo;
	}
	public String getRollNo() {
		return rollNo;
	}
	public void setRollNo(String rollNo) {
		this.rollNo = rollNo;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public String getSemNo() {
		return semNo;
	}
	public void setSemNo(String semNo) {
		this.semNo = semNo;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public boolean isSupplementary() {
		return supplementary;
	}
	public void setSupplementary(boolean supplementary) {
		this.supplementary = supplementary;
	}
	public boolean isImprovement() {
		return improvement;
	}
	public void setImprovement(boolean improvement) {
		this.improvement = improvement;
	}
	@Override
	public boolean equals(Object obj) {

	    if(obj instanceof SupplementaryApplicationClassTo)
	    {
	    	SupplementaryApplicationClassTo temp = (SupplementaryApplicationClassTo) obj;
	        if(this.classId== temp.getClassId())
	            return true;
	    }
	    return false;
	}
	public boolean isRevaluation() {
		return isRevaluation;
	}
	public void setRevaluation(boolean isRevaluation) {
		this.isRevaluation = isRevaluation;
	}
	public boolean isScrutiny() {
		return isScrutiny;
	}
	public void setScrutiny(boolean isScrutiny) {
		this.isScrutiny = isScrutiny;
	}
	public boolean getIsChallengeValuation() {
		return isChallengeValuation;
	}
	public void setIsChallengeValuation(boolean isChallengeValuation) {
		this.isChallengeValuation = isChallengeValuation;
	}
	
	
}