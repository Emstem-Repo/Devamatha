package com.kp.cms.to.exam;

import java.io.Serializable;

/**
 * Jan 7, 2010 Created By 9Elements
 */
public class ExamUpdateExcludeWithheldTO implements Serializable{
	private int id;
	private int studentId;
	private String regNumber;
	private String rollNumber;
	private String studentName;
	private String exclude="";
	private String withheld="";
	private int excludeId;
	private int withheldId;
	
	private String examName;
	private int courseId;
	private String courseName;
	private int schemeNo;
	private String name;
	
	
	public ExamUpdateExcludeWithheldTO() {
		super();
	}
	public ExamUpdateExcludeWithheldTO(int id, int studentId, String studentName, String regNumber,
			String rollNumber, String exclude,String withheld) {
		super();
		this.id = id;
		this.studentName = studentName;
		this.regNumber = regNumber;
		this.rollNumber = rollNumber;
		this.studentId = studentId;
		this.exclude=exclude;
		this.withheld=withheld;
	}
	
	public ExamUpdateExcludeWithheldTO(int courseId, String courseName,
			String examName, int id, String name, String regNumber,
			String rollNumber, int schemeNo, int studentId) {
		super();
		this.courseId = courseId;
		this.courseName = courseName;
		this.examName = examName;
		this.id = id;
		this.name = name;
		this.regNumber = regNumber;
		this.rollNumber = rollNumber;
		this.schemeNo = schemeNo;
		this.studentId = studentId;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getStudentId() {
		return studentId;
	}
	public void setStudentId(int studentId) {
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
	public String getExamName() {
		return examName;
	}
	public void setExamName(String examName) {
		this.examName = examName;
	}
	public int getCourseId() {
		return courseId;
	}
	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public int getSchemeNo() {
		return schemeNo;
	}
	public void setSchemeNo(int schemeNo) {
		this.schemeNo = schemeNo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getExclude() {
		return exclude;
	}
	public void setExclude(String exclude) {
		this.exclude = exclude;
	}
	public String getWithheld() {
		return withheld;
	}
	public void setWithheld(String withheld) {
		this.withheld = withheld;
	}
	public int getExcludeId() {
		return excludeId;
	}
	public void setExcludeId(int excludeId) {
		this.excludeId = excludeId;
	}
	public int getWithheldId() {
		return withheldId;
	}
	public void setWithheldId(int withheldId) {
		this.withheldId = withheldId;
	}
}
