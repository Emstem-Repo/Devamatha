package com.kp.cms.to.exam;

import java.io.Serializable;
import java.util.List;

import com.kp.cms.to.admin.ApplicantLateralDetailsTO;

public class ConsolidateMarksCardTO implements Serializable{
	private static final long serialVersionUID = 1L;
	private String name;
	private String registerNo;
	private List<String> regNos;
	private String courseName;
	private String yearOfStudy;
	private String result;
	private String totalMaxMarks;
	private String totalMarksAwarded;
	private String totalCredits;
	List<MarksCardTO> toList;
	private String gradePointAvg;
	private String yearOfPassing;
	private String grade;
	private String semNo;
	private String creditPoint;
	private String sgpa;
	
	
	public String getCreditPoint() {
		return creditPoint;
	}
	public void setCreditPoint(String creditPoint) {
		this.creditPoint = creditPoint;
	}
	public String getSgpa() {
		return sgpa;
	}
	public void setSgpa(String sgpa) {
		this.sgpa = sgpa;
	}
	public String getSemNo() {
		return semNo;
	}
	public void setSemNo(String semNo) {
		this.semNo = semNo;
	}
	public String getYearOfPassing() {
		return yearOfPassing;
	}
	public void setYearOfPassing(String yearOfPassing) {
		this.yearOfPassing = yearOfPassing;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRegisterNo() {
		return registerNo;
	}
	public void setRegisterNo(String registerNo) {
		this.registerNo = registerNo;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public String getYearOfStudy() {
		return yearOfStudy;
	}
	public void setYearOfStudy(String yearOfStudy) {
		this.yearOfStudy = yearOfStudy;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getTotalMaxMarks() {
		return totalMaxMarks;
	}
	public void setTotalMaxMarks(String totalMaxMarks) {
		this.totalMaxMarks = totalMaxMarks;
	}
	public String getTotalMarksAwarded() {
		return totalMarksAwarded;
	}
	public void setTotalMarksAwarded(String totalMarksAwarded) {
		this.totalMarksAwarded = totalMarksAwarded;
	}
	public String getTotalCredits() {
		return totalCredits;
	}
	public void setTotalCredits(String totalCredits) {
		this.totalCredits = totalCredits;
	}
	public List<MarksCardTO> getToList() {
		return toList;
	}
	public void setToList(List<MarksCardTO> toList) {
		this.toList = toList;
	}
	public String getGradePointAvg() {
		return gradePointAvg;
	}
	public void setGradePointAvg(String gradePointAvg) {
		this.gradePointAvg = gradePointAvg;
	}
	public List<String> getRegNos() {
		return regNos;
	}
	public void setRegNos(List<String> regNos) {
		this.regNos = regNos;
	}
	

}