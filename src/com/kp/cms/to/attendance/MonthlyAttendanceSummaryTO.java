package com.kp.cms.to.attendance;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.kp.cms.bo.admin.Student;

public class MonthlyAttendanceSummaryTO implements Serializable,Comparable<MonthlyAttendanceSummaryTO> {


	private String regNo;
	private String rollNo;
	private String studentName;
	private String aggrigatePercentage;
	private int classId;
	private String className;
	private List<SubjectSummaryTO> subjectSummaryList = new ArrayList<SubjectSummaryTO>();
	private LinkedHashMap<Integer,SubjectSummaryTO> summaryMap = new LinkedHashMap<Integer,SubjectSummaryTO>();
	private List<String> subjectCodeNameList = new ArrayList<String>();
	private Student student ;
	private String secondLanguage;
	
	private List<ActivitySummaryTO> activitySummaryTOlist = new ArrayList<ActivitySummaryTO>();
	private Map<Integer,ActivitySummaryTO> activityMap = new HashMap<Integer,ActivitySummaryTO>();
	
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public String getRegNo() {
		return regNo;
	}
	public String getRollNo() {
		return rollNo;
	}
	public List<SubjectSummaryTO> getSubjectSummaryList() {
		return subjectSummaryList;
	}
	
	public List<ActivitySummaryTO> getActivitySummaryTOlist() {
		return activitySummaryTOlist;
	}
	public Map<Integer, ActivitySummaryTO> getActivityMap() {
		return activityMap;
	}
	public void setRegNo(String regNo) {
		this.regNo = regNo;
	}
	public void setRollNo(String rollNo) {
		this.rollNo = rollNo;
	}
	public void setSubjectSummaryList(List<SubjectSummaryTO> subjectSummaryList) {
		this.subjectSummaryList = subjectSummaryList;
	}
	
	public void setActivitySummaryTOlist(
			List<ActivitySummaryTO> activitySummaryTOlist) {
		this.activitySummaryTOlist = activitySummaryTOlist;
	}
	public void setActivityMap(Map<Integer, ActivitySummaryTO> activityMap) {
		this.activityMap = activityMap;
	}
	public String getAggrigatePercentage() {
		return aggrigatePercentage;
	}
	public void setAggrigatePercentage(String aggrigatePercentage) {
		this.aggrigatePercentage = aggrigatePercentage;
	}
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
	public List<String> getSubjectCodeNameList() {
		return subjectCodeNameList;
	}
	public void setSubjectCodeNameList(List<String> subjectCodeNameList) {
		this.subjectCodeNameList = subjectCodeNameList;
	}
	public LinkedHashMap<Integer, SubjectSummaryTO> getSummaryMap() {
		return summaryMap;
	}
	public void setSummaryMap(LinkedHashMap<Integer, SubjectSummaryTO> summaryMap) {
		this.summaryMap = summaryMap;
	}
	public Student getStudent() {
		return student;
	}
	public void setStudent(Student student) {
		this.student = student;
	}
	public String getSecondLanguage() {
		return secondLanguage;
	}
	public void setSecondLanguage(String secondLanguage) {
		this.secondLanguage = secondLanguage;
	}
	@Override
	public int compareTo(MonthlyAttendanceSummaryTO attendanceSummaryTO1) {
		if (attendanceSummaryTO1 != null    && this != null  ) {
			if(attendanceSummaryTO1.getRegNo() == null) {
				attendanceSummaryTO1.setRegNo("");
			}
			if(this.getRegNo() == null) {
				this.setRegNo("");
			}
			return this.getRegNo().compareTo(attendanceSummaryTO1.getRegNo());
		}
		return 0;
	}
}