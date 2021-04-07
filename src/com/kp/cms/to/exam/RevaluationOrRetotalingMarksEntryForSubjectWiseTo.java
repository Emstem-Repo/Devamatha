package com.kp.cms.to.exam;

import java.io.Serializable;

public class RevaluationOrRetotalingMarksEntryForSubjectWiseTo implements Serializable,Comparable<RevaluationOrRetotalingMarksEntryForSubjectWiseTo>{
	private int id;
	private String registerNo;
	private String className;
	private int studentId;
	private int classId;
	private int courseId;
	private int examRevaluationAppId;
	private int examRevaluationDetailsId;
	private String theoryMarks;
	private int year;
	private int schemeNo;
	private boolean marksValidationCompleted;
	private String marks;
	private String marks1;
	private String marks2;
	private String thirdEvlMarks;

	public String getRegisterNo() {
		return registerNo;
	}
	public void setRegisterNo(String registerNo) {
		this.registerNo = registerNo;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public int getStudentId() {
		return studentId;
	}
	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}
	public int getClassId() {
		return classId;
	}
	public void setClassId(int classId) {
		this.classId = classId;
	}
	public int getCourseId() {
		return courseId;
	}
	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}
	public int getExamRevaluationAppId() {
		return examRevaluationAppId;
	}
	public void setExamRevaluationAppId(int examRevaluationAppId) {
		this.examRevaluationAppId = examRevaluationAppId;
	}
	public int getExamRevaluationDetailsId() {
		return examRevaluationDetailsId;
	}
	public void setExamRevaluationDetailsId(int examRevaluationDetailsId) {
		this.examRevaluationDetailsId = examRevaluationDetailsId;
	}
	public int compareTo(RevaluationOrRetotalingMarksEntryForSubjectWiseTo arg0) {
		if(arg0!=null && this!=null && arg0.getRegisterNo()!=null
				 && this.getRegisterNo()!=null){
			return this.getRegisterNo().compareTo(arg0.getRegisterNo());
		}else
		return 0;
	}
	public String getTheoryMarks() {
		return theoryMarks;
	}
	public void setTheoryMarks(String theoryMarks) {
		this.theoryMarks = theoryMarks;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public int getSchemeNo() {
		return schemeNo;
	}
	public void setSchemeNo(int schemeNo) {
		this.schemeNo = schemeNo;
	}
	public boolean isMarksValidationCompleted() {
		return marksValidationCompleted;
	}
	public void setMarksValidationCompleted(boolean marksValidationCompleted) {
		this.marksValidationCompleted = marksValidationCompleted;
	}
	public String getMarks() {
		return marks;
	}
	public void setMarks(String marks) {
		this.marks = marks;
	}
	public String getMarks1() {
		return marks1;
	}
	public void setMarks1(String marks1) {
		this.marks1 = marks1;
	}
	public String getMarks2() {
		return marks2;
	}
	public void setMarks2(String marks2) {
		this.marks2 = marks2;
	}
	public String getThirdEvlMarks() {
		return thirdEvlMarks;
	}
	public void setThirdEvlMarks(String thirdEvlMarks) {
		this.thirdEvlMarks = thirdEvlMarks;
	}
}
