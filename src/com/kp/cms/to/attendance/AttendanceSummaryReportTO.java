package com.kp.cms.to.attendance;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.kp.cms.bo.admin.Student;

public class AttendanceSummaryReportTO implements Serializable,Comparable<AttendanceSummaryReportTO> {
	private String subject;
	private String subjectCode;
	private String subjectId;
	private String regNo;
	private String rollNo;
	private String aggregate;
	private boolean isSecondLang;
	private int classHeld;
	private int classAttended;
	private int percentage;
	private String studentName;
	private int classId;
	private String className;
	private List subjectList;
	private List subjectcount;
	private String aggrigatePercentage;
	private boolean leaveStatus;
	public boolean isLeaveStatus() {
		return leaveStatus;
	}
	public void setLeaveStatus(boolean leaveStatus) {
		this.leaveStatus = leaveStatus;
	}
	private List<String> subjectCodeNameList = new ArrayList<String>();
	
	
	private List<SubjectSummaryTO> subjectSummaryList = new ArrayList<SubjectSummaryTO>();
//	private Map<Integer,SubjectSummaryTO> summaryMap = new HashMap<Integer,SubjectSummaryTO>();
	private LinkedHashMap<Integer,SubjectSummaryTO> summaryMap = new LinkedHashMap<Integer,SubjectSummaryTO>();
	private List<ActivitySummaryTO> activitySummaryTOlist = new ArrayList<ActivitySummaryTO>();
	private Map<Integer,ActivitySummaryTO> activityMap = new HashMap<Integer,ActivitySummaryTO>();

	private Student student;
	private String secondLanguage;
	public List<ActivitySummaryTO> getActivitySummaryTOlist() {
		return activitySummaryTOlist;
	}
	public void setActivitySummaryTOlist(
			List<ActivitySummaryTO> activitySummaryTOlist) {
		this.activitySummaryTOlist = activitySummaryTOlist;
	}
	public Map<Integer, ActivitySummaryTO> getActivityMap() {
		return activityMap;
	}
	public void setActivityMap(Map<Integer, ActivitySummaryTO> activityMap) {
		this.activityMap = activityMap;
	}
	public List getSubjectcount() {
		return subjectcount;
	}
	public void setSubjectcount(List subjectcount) {
		this.subjectcount = subjectcount;
	}
	public List getSubjectList() {
		return subjectList;
	}
	public void setSubjectList(List subjectList) {
		this.subjectList = subjectList;
	}
	public String getSubjectId() {
		return subjectId;
	}
	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}
	public String getSubjectCode() {
		return subjectCode;
	}
	public void setSubjectCode(String subjectCode) {
		this.subjectCode = subjectCode;
	}
	public String getSecondLang() {
		return secondLang;
	}
	public void setSecondLang(String secondLang) {
		this.secondLang = secondLang;
	}
	private String secondLang;
	
	
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getRegNo() {
		return regNo;
	}
	public void setRegNo(String regNo) {
		this.regNo = regNo;
	}
	public String getAggregate() {
		return aggregate;
	}
	public void setAggregate(String aggregate) {
		this.aggregate = aggregate;
	}
	public boolean isSecondLang() {
		return isSecondLang;
	}
	public void setSecondLang(boolean isSecondLang) {
		this.isSecondLang = isSecondLang;
	}
	
	public int getClassHeld() {
		return classHeld;
	}
	public void setClassHeld(int classHeld) {
		this.classHeld = classHeld;
	}
	public int getClassAttended() {
		return classAttended;
	}
	public void setClassAttended(int classAttended) {
		this.classAttended = classAttended;
	}
	public int getPercentage() {
		return percentage;
	}
	public void setPercentage(int percentage) {
		this.percentage = percentage;
	}
	public List<SubjectSummaryTO> getSubjectSummaryList() {
		return subjectSummaryList;
	}
	public void setSubjectSummaryList(List<SubjectSummaryTO> subjectSummaryList) {
		this.subjectSummaryList = subjectSummaryList;
	}
	public LinkedHashMap<Integer, SubjectSummaryTO> getSummaryMap() {
		return summaryMap;
	}
	public void setSummaryMap(LinkedHashMap<Integer, SubjectSummaryTO> summaryMap) {
		this.summaryMap = summaryMap;
	}
	public String getRollNo() {
		return rollNo;
	}
	public void setRollNo(String rollNo) {
		this.rollNo = rollNo;
	}
	public int getClassId() {
		return classId;
	}
	public void setClassId(int classId) {
		this.classId = classId;
	}
	public String getAggrigatePercentage() {
		return aggrigatePercentage;
	}
	public void setAggrigatePercentage(String aggrigatePercentage) {
		this.aggrigatePercentage = aggrigatePercentage;
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
	public Student getStudent() {
		return student;
	}
	public String getSecondLanguage() {
		return secondLanguage;
	}
	public void setStudent(Student student) {
		this.student = student;
	}
	public void setSecondLanguage(String secondLanguage) {
		this.secondLanguage = secondLanguage;
	}
	@Override
	public int compareTo(AttendanceSummaryReportTO attendanceTo1) {
		if (attendanceTo1 != null) {
			if(attendanceTo1.getRegNo() == null) {
				attendanceTo1.setRegNo("");
			}
			if(this.getRegNo() == null) {
				this.setRegNo("");
			}
			return this.getRegNo().compareTo(attendanceTo1.getRegNo());
		}
		return 0;
	}
}
