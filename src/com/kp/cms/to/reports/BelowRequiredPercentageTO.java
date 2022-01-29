package com.kp.cms.to.reports;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.kp.cms.to.attendance.ActivitySummaryTO;
import com.kp.cms.to.attendance.SubjectSummaryTO;

public class BelowRequiredPercentageTO {

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
	private Map<String, SubjectSummaryTO> subMap;
	
	private List subjectList;
	private List subjectcount;
	private int studentId;
	
	
	private Map<String, SubjectSummaryTO> subjectMap;
	
	public Map<String, SubjectSummaryTO> getSubjectMap() {
		return subjectMap;
	}
	public void setSubjectMap(Map<String, SubjectSummaryTO> subjectMap) {
		this.subjectMap = subjectMap;
	}
	private List<SubjectSummaryTO> subjectCodeList;

	private List<SubjectSummaryTO> subjectSummaryList = new ArrayList<SubjectSummaryTO>();
	private Map<Integer,SubjectSummaryTO> summaryMap = new HashMap<Integer,SubjectSummaryTO>();
	
	private List<ActivitySummaryTO> activitySummaryTOlist = new ArrayList<ActivitySummaryTO>();
	private Map<Integer,ActivitySummaryTO> activityMap = new HashMap<Integer,ActivitySummaryTO>();

	
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
	public Map<Integer, SubjectSummaryTO> getSummaryMap() {
		return summaryMap;
	}
	public void setSummaryMap(Map<Integer, SubjectSummaryTO> summaryMap) {
		this.summaryMap = summaryMap;
	}
	public String getRollNo() {
		return rollNo;
	}
	public void setRollNo(String rollNo) {
		this.rollNo = rollNo;
	}
	public int getStudentId() {
		return studentId;
	}
	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}
	public List<SubjectSummaryTO> getSubjectCodeList() {
		return subjectCodeList;
	}
	public void setSubjectCodeList(List<SubjectSummaryTO> subjectCodeList) {
		this.subjectCodeList = subjectCodeList;
	}
	public Map<String, SubjectSummaryTO> getSubMap() {
		return subMap;
	}
	public void setSubMap(Map<String, SubjectSummaryTO> subMap) {
		this.subMap = subMap;
	}
}
