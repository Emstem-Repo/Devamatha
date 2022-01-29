package com.kp.cms.to.reports;

import java.io.Serializable;
import java.util.List;

public class LeaveReportTO implements Serializable {
	
	private String studentName;
	
	private String registerNo;
	
	private String rollNo;
	
	private List<String> classesNameList;
	
	private List<String> startPeriodList;
	
	private List<String> endPeriodList;
	
	private List<String> startDateList;
	
	private List<String> endDateList;

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
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

	public List<String> getClassesNameList() {
		return classesNameList;
	}

	public void setClassesNameList(List<String> classesNameList) {
		this.classesNameList = classesNameList;
	}

	public List<String> getStartPeriodList() {
		return startPeriodList;
	}

	public void setStartPeriodList(List<String> startPeriodList) {
		this.startPeriodList = startPeriodList;
	}

	public List<String> getEndPeriodList() {
		return endPeriodList;
	}

	public void setEndPeriodList(List<String> endPeriodList) {
		this.endPeriodList = endPeriodList;
	}

	public List<String> getStartDateList() {
		return startDateList;
	}

	public void setStartDateList(List<String> startDateList) {
		this.startDateList = startDateList;
	}

	public List<String> getEndDateList() {
		return endDateList;
	}

	public void setEndDateList(List<String> endDateList) {
		this.endDateList = endDateList;
	}
	
	
	
//	private StudentTO studentTO;
//	private ClassesTO classesTO;
//	private StudentLeaveTO studentLeaveTO;
//	
//	public StudentLeaveTO getStudentLeaveTO() {
//		return studentLeaveTO;
//	}
//	public void setStudentLeaveTO(StudentLeaveTO studentLeaveTO) {
//		this.studentLeaveTO = studentLeaveTO;
//	}
//	public ClassesTO getClassesTO() {
//		return classesTO;
//	}
//	public void setClassesTO(ClassesTO classesTO) {
//		this.classesTO = classesTO;
//	}
//	public StudentTO getStudentTO() {
//		return studentTO;
//	}
//	public void setStudentTO(StudentTO studentTO) {
//		this.studentTO = studentTO;
//	}
		
}