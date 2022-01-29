package com.kp.cms.to.admin;

import java.util.Date;
import java.util.List;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.exam.ExamMarksEntryDetailsTO;

public class ProgressCardTo extends BaseActionForm{
	
	private int id;
	private int term;
	private int order;
	private String termName;
	private List<ProgressSubjectTO> subjectList;
	private String academicYear;
	private String appliedYear;
	private String firstInternalTotal;
	private String secondInternalTotal;
	private String firstInternalMaxTotal;
	private String secondInternalMaxTotal;
	private String eseAwardedTotal;
	private String eseMaxTotal;
	private String attTotalprssesnt;
	private String attTotalHeld;
	private String studentName;
	private String classNo;
	private String programeme;
	private Double totalAttPercentage;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getTerm() {
		return term;
	}
	public void setTerm(int term) {
		this.term = term;
	}
	public int getOrder() {
		return order;
	}
	public void setOrder(int order) {
		this.order = order;
	}
	public String getTermName() {
		return termName;
	}
	public void setTermName(String termName) {
		this.termName = termName;
	}
	public List<ProgressSubjectTO> getSubjectList() {
		return subjectList;
	}
	public void setSubjectList(List<ProgressSubjectTO> subjectList) {
		this.subjectList = subjectList;
	}
	public String getAcademicYear() {
		return academicYear;
	}
	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}
	public String getAppliedYear() {
		return appliedYear;
	}
	public void setAppliedYear(String appliedYear) {
		this.appliedYear = appliedYear;
	}
	public String getFirstInternalTotal() {
		return firstInternalTotal;
	}
	public void setFirstInternalTotal(String firstInternalTotal) {
		this.firstInternalTotal = firstInternalTotal;
	}
	
	public String getFirstInternalMaxTotal() {
		return firstInternalMaxTotal;
	}
	public void setFirstInternalMaxTotal(String firstInternalMaxTotal) {
		this.firstInternalMaxTotal = firstInternalMaxTotal;
	}
	
	public String getEseAwardedTotal() {
		return eseAwardedTotal;
	}
	public void setEseAwardedTotal(String eseAwardedTotal) {
		this.eseAwardedTotal = eseAwardedTotal;
	}
	public String getEseMaxTotal() {
		return eseMaxTotal;
	}
	public void setEseMaxTotal(String eseMaxTotal) {
		this.eseMaxTotal = eseMaxTotal;
	}
	public String getAttTotalprssesnt() {
		return attTotalprssesnt;
	}
	public void setAttTotalprssesnt(String attTotalprssesnt) {
		this.attTotalprssesnt = attTotalprssesnt;
	}
	public String getAttTotalHeld() {
		return attTotalHeld;
	}
	public void setAttTotalHeld(String attTotalHeld) {
		this.attTotalHeld = attTotalHeld;
	}
	public String getSecondInternalMaxTotal() {
		return secondInternalMaxTotal;
	}
	public void setSecondInternalMaxTotal(String secondInternalMaxTotal) {
		this.secondInternalMaxTotal = secondInternalMaxTotal;
	}
	public String getSecondInternalTotal() {
		return secondInternalTotal;
	}
	public void setSecondInternalTotal(String secondInternalTotal) {
		this.secondInternalTotal = secondInternalTotal;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public String getClassNo() {
		return classNo;
	}
	public void setClassNo(String classNo) {
		this.classNo = classNo;
	}
	public String getProgrameme() {
		return programeme;
	}
	public void setProgrameme(String programeme) {
		this.programeme = programeme;
	}
	public Double getTotalAttPercentage() {
		return totalAttPercentage;
	}
	public void setTotalAttPercentage(Double totalAttPercentage) {
		this.totalAttPercentage = totalAttPercentage;
	}
		
		
		
		
}