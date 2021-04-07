package com.kp.cms.to.timetable;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@SuppressWarnings("serial")
public class DepartmentTimeTablePeriodTo  implements Serializable,Comparable<DepartmentTimeTablePeriodTo>{
	
	private int id;
	private int periodId;
	private String periodName;
	List<TTSubjectBatchTo> subjectList;
	Map<Integer,String> timeTablePeriodTo;
	private String classNames;
	private String subjectNames;
	private String subjectCode;
	private String teacherName;
	private String week;
	private String subTypeId;
	private String subTypeName;
	private String empCode;
	private String termNo;	
	
	
	public Map<Integer, String> getTimeTablePeriodTo() {
		return timeTablePeriodTo;
	}
	public void setTimeTablePeriodTo(Map<Integer, String> timeTablePeriodTo) {
		this.timeTablePeriodTo = timeTablePeriodTo;
	}
	private int count;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getPeriodId() {
		return periodId;
	}
	public void setPeriodId(int periodId) {
		this.periodId = periodId;
	}
	
	public String getPeriodName() {
		return periodName;
	}
	public void setPeriodName(String periodName) {
		this.periodName = periodName;
	}
	public List<TTSubjectBatchTo> getSubjectList() {
		return subjectList;
	}
	public void setSubjectList(List<TTSubjectBatchTo> subjectList) {
		this.subjectList = subjectList;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getClassNames() {
		return classNames;
	}
	public void setClassNames(String classNames) {
		this.classNames = classNames;
	}
	public String getSubjectNames() {
		return subjectNames;
	}
	public void setSubjectNames(String subjectNames) {
		this.subjectNames = subjectNames;
	}
	public String getSubjectCode() {
		return subjectCode;
	}
	public void setSubjectCode(String subjectCode) {
		this.subjectCode = subjectCode;
	}
	
	public String getTeacherName() {
		return teacherName;
	}
	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}
	public String getWeek() {
		return week;
	}
	public void setWeek(String week) {
		this.week = week;
	}
	public String getSubTypeId() {
		return subTypeId;
	}
	public void setSubTypeId(String subTypeId) {
		this.subTypeId = subTypeId;
	}
	public String getSubTypeName() {
		return subTypeName;
	}
	public void setSubTypeName(String subTypeName) {
		this.subTypeName = subTypeName;
	}
	public String getEmpCode() {
		return empCode;
	}
	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}
	public String getTermNo() {
		return termNo;
	}
	public void setTermNo(String termNo) {
		this.termNo = termNo;
	}
	//@Override
	/*public int compareTo(DepartmentTimeTablePeriodTo arg0) {
		if(arg0!=null && this!=null){
			DateFormat dateFormat = new SimpleDateFormat("k:mm:ss");
			
			Date startTime;
			try {
				startTime = dateFormat.parse(this.getStartTime());
				Date startTime1 = dateFormat.parse(arg0.getStartTime());
				if(startTime.compareTo(startTime1) > 0)
					return 1;
				else if(startTime.compareTo(startTime1) < 0){
					return -1;
				}else
					return 0;
			} catch (ParseException e) {
			}
		}	
		return 0;
	}*/
	@Override
	public int compareTo(DepartmentTimeTablePeriodTo o) {
		 return this.getPeriodName().compareTo(o.getPeriodName());
	}
	

}