package com.kp.cms.to.exam;

import java.io.Serializable;
import java.util.Date;

public class ExamDefinitionTO implements Serializable,Comparable<ExamDefinitionTO>{
	/**
	 * Dec 23, 2009
	 * Created By 9Elements Team
	 */
	
	private int id;
	private String programType;
	private String program;
	private String examName;
	private String examType;
	private String month;
	private String year;
	private String academicYear;
	private String examCode;
	private String isCurrent;
	private String internalExamNameList;
	private String examDefId_progId;
	private Date examMonthyear;
	private String startDate;
	private String endDate;
	private String theoryPractical;
	private String displayExamName;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getProgramType() {
		return programType;
	}
	public void setProgramType(String programType) {
		this.programType = programType;
	}
	public String getProgram() {
		return program;
	}
	public void setProgram(String program) {
		this.program = program;
	}
	public String getExamName() {
		return examName;
	}
	public void setExamName(String examName) {
		this.examName = examName;
	}
	public String getExamType() {
		return examType;
	}
	public void setExamType(String examType) {
		this.examType = examType;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getAcademicYear() {
		return academicYear;
	}
	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}
	public String getExamCode() {
		return examCode;
	}
	public void setExamCode(String examCode) {
		this.examCode = examCode;
	}
	public String getIsCurrent() {
		return isCurrent;
	}
	public void setIsCurrent(String isCurrent) {
		this.isCurrent = isCurrent;
	}
	public String getInternalExamNameList() {
		return internalExamNameList;
	}
	public void setInternalExamNameList(String internalExamNameList) {
		this.internalExamNameList = internalExamNameList;
	}
	public void setExamDefId_progId(String examDefId_progId) {
		this.examDefId_progId = examDefId_progId;
	}
	public String getExamDefId_progId() {
		return examDefId_progId;
	}
	public Date getExamMonthyear() {
		return examMonthyear;
	}
	public void setExamMonthyear(Date examMonthyear) {
		this.examMonthyear = examMonthyear;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getTheoryPractical() {
		return theoryPractical;
	}
	public void setTheoryPractical(String theoryPractical) {
		this.theoryPractical = theoryPractical;
	}
	@Override
	public int compareTo(ExamDefinitionTO arg0) {
		if(arg0!=null && this!=null && arg0.getProgramType()!=null 
				 && this.getProgramType()!=null){
			return this.getProgramType().compareTo(arg0.getProgramType());
		}else
			return 0;
	}
	public String getDisplayExamName() {
		return displayExamName;
	}
	public void setDisplayExamName(String displayExamName) {
		this.displayExamName = displayExamName;
	}
}
