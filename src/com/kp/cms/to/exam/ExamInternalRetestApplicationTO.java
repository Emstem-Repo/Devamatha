package com.kp.cms.to.exam;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Jan 25, 2010 Created By 9Elements
 */
@SuppressWarnings("serial")
public class ExamInternalRetestApplicationTO implements Serializable{
	
	private Integer studentId ;
	private String regNumber;
	private String rollNumber;
	private String studentName;
	private Integer classId;
	private String className;
	private Integer chance;
	private String examName;
	private String academicYear;
	private String examId;
	private Integer id;
	private ArrayList<ExamInternalRetestApplicationSubjectsTO> subjectList;
	
	public ExamInternalRetestApplicationTO(String academicYear, String examId,
			Integer studentId, Integer classId, Integer chance,
			ArrayList<ExamInternalRetestApplicationSubjectsTO> subjectList) {
		super();
		this.academicYear = academicYear;
		this.examId = examId;
		this.studentId = studentId;
		this.classId = classId;
		this.chance = chance;
		this.subjectList = subjectList;
	}
	
	public Integer getStudentId() {
		return studentId;
	}
	public void setStudentId(Integer studentId) {
		this.studentId = studentId;
	}
	public String getRegNumber() {
		return regNumber;
	}
	public void setRegNumber(String regNumber) {
		this.regNumber = regNumber;
	}
	public String getRollNumber() {
		return rollNumber;
	}
	public void setRollNumber(String rollNumber) {
		this.rollNumber = rollNumber;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public Integer getClassId() {
		return classId;
	}
	public void setClassId(Integer classId) {
		this.classId = classId;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public ExamInternalRetestApplicationTO()
	{
		super();
	}
	public void setSubjectList(ArrayList<ExamInternalRetestApplicationSubjectsTO> subjectList) {
		this.subjectList = subjectList;
	}
	public ArrayList<ExamInternalRetestApplicationSubjectsTO> getSubjectList() {
		return subjectList;
	}
	public void setChance(Integer chance) {
		this.chance = chance;
	}
	public Integer getChance() {
		return chance;
	}
	public void setExamName(String examName) {
		this.examName = examName;
	}
	public String getExamName() {
		return examName;
	}
	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}
	public String getAcademicYear() {
		return academicYear;
	}
	public String getExamId() {
		return examId;
	}
	public void setExamId(String examId) {
		this.examId = examId;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
}
