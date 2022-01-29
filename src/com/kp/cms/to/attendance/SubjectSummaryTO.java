package com.kp.cms.to.attendance;

import java.io.Serializable;

import com.kp.cms.bo.admin.Attendance;
import com.kp.cms.bo.admin.AttendanceStudent;

public class SubjectSummaryTO  implements Serializable,Comparable<SubjectSummaryTO>{
	
	private int subId;
	private String subjectName;
	private String classesHeld;
	private String classesAttended;
	private String percentage;
	private String subjectCode;
	private AttendanceStudent attendanceStudent;
	private Attendance attendance;
	private double held;
	private double attended;
	private String aggregatePercentage = "";
	private String classesAttendedWithLeave;
	private String classesWithLeave;
	private String practicalClassesHeld;
	private String practicalClassesAttended;
	private String practicalClassesWithLeaveAttended;
	private String practicalPercentage;
	private Boolean isPractical;
	private Boolean isTheory;
	public double getHeld() {
		return held;
	}
	public double getAttended() {
		return attended;
	}
	public void setHeld(double held) {
		this.held = held;
	}
	public void setAttended(double attended) {
		this.attended = attended;
	}
	
	
	public String getSubjectName() {
		return subjectName;
	}
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
	public String getClassesHeld() {
		return classesHeld;
	}
	public void setClassesHeld(String classesHeld) {
		this.classesHeld = classesHeld;
	}
	public String getClassesAttended() {
		return classesAttended;
	}
	public void setClassesAttended(String classesAttended) {
		this.classesAttended = classesAttended;
	}
	public String getPercentage() {
		return percentage;
	}
	public void setPercentage(String percentage) {
		this.percentage = percentage;
	}
	public int getSubId() {
		return subId;
	}
	public void setSubId(int subId) {
		this.subId = subId;
	}
	public String getSubjectCode() {
		return subjectCode;
	}
	public void setSubjectCode(String subjectCode) {
		this.subjectCode = subjectCode;
	}
	public AttendanceStudent getAttendanceStudent() {
		return attendanceStudent;
	}
	public Attendance getAttendance() {
		return attendance;
	}
	public void setAttendanceStudent(AttendanceStudent attendanceStudent) {
		this.attendanceStudent = attendanceStudent;
	}
	public void setAttendance(Attendance attendance) {
		this.attendance = attendance;
	}
	public String getAggregatePercentage() {
		return aggregatePercentage;
	}
	public void setAggregatePercentage(String aggregatePercentage) {
		this.aggregatePercentage = aggregatePercentage;
	}
	public String getClassesAttendedWithLeave() {
		return classesAttendedWithLeave;
	}
	public void setClassesAttendedWithLeave(String classesAttendedWithLeave) {
		this.classesAttendedWithLeave = classesAttendedWithLeave;
	}
	public String getClassesWithLeave() {
		return classesWithLeave;
	}
	public void setClassesWithLeave(String classesWithLeave) {
		this.classesWithLeave = classesWithLeave;
	}
	public String getPracticalClassesHeld() {
		return practicalClassesHeld;
	}
	public void setPracticalClassesHeld(String practicalClassesHeld) {
		this.practicalClassesHeld = practicalClassesHeld;
	}
	public String getPracticalClassesAttended() {
		return practicalClassesAttended;
	}
	public void setPracticalClassesAttended(String practicalClassesAttended) {
		this.practicalClassesAttended = practicalClassesAttended;
	}
	public String getPracticalClassesWithLeaveAttended() {
		return practicalClassesWithLeaveAttended;
	}
	public void setPracticalClassesWithLeaveAttended(
			String practicalClassesWithLeaveAttended) {
		this.practicalClassesWithLeaveAttended = practicalClassesWithLeaveAttended;
	}
	public String getPracticalPercentage() {
		return practicalPercentage;
	}
	public void setPracticalPercentage(String practicalPercentage) {
		this.practicalPercentage = practicalPercentage;
	}
	public Boolean getIsPractical() {
		return isPractical;
	}
	public void setIsPractical(Boolean isPractical) {
		this.isPractical = isPractical;
	}
	public Boolean getIsTheory() {
		return isTheory;
	}
	public void setIsTheory(Boolean isTheory) {
		this.isTheory = isTheory;
	}
	@Override
	public int compareTo(SubjectSummaryTO arg0) {
		if(arg0!=null && this!=null){
			if(this.getSubId()>arg0.getSubId())
				return 1;
			else if(this.getSubId() < arg0.getSubId())
				return -1;
			else
				return 0;
		}
		return 0;
	}	
	
}
