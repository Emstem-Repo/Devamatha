package com.kp.cms.transactions.admin;

import java.io.Serializable;
import java.util.List;

import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admin.StudentLogin;
import com.kp.cms.to.timetable.TimeTablePeriodTo;

public class StudentLoginTO implements Serializable{
	private int studentLoginId;
	private String originalPassword;
	private StudentLogin originalBO;
	private Student originalStudent;
	private String studentUsername;
	private String parentUsername;
	private String parentPassword;
	private String studentName;
	
	private String studentMail;
	private String parentMail;
	
	
	private String week;
	private int position;
	private List<TimeTablePeriodTo> timeTablePeriodTos;
	
	private String showWeek;
	
	
	
	public int getStudentLoginId() {
		return studentLoginId;
	}
	public void setStudentLoginId(int studentLoginId) {
		this.studentLoginId = studentLoginId;
	}
	public String getOriginalPassword() {
		return originalPassword;
	}
	public void setOriginalPassword(String originalPassword) {
		this.originalPassword = originalPassword;
	}
	public StudentLogin getOriginalBO() {
		return originalBO;
	}
	public void setOriginalBO(StudentLogin originalBO) {
		this.originalBO = originalBO;
	}
	public Student getOriginalStudent() {
		return originalStudent;
	}
	public void setOriginalStudent(Student originalStudent) {
		this.originalStudent = originalStudent;
	}
	public String getStudentUsername() {
		return studentUsername;
	}
	public void setStudentUsername(String studentUsername) {
		this.studentUsername = studentUsername;
	}
	public String getParentUsername() {
		return parentUsername;
	}
	public void setParentUsername(String parentUsername) {
		this.parentUsername = parentUsername;
	}
	public String getParentPassword() {
		return parentPassword;
	}
	public void setParentPassword(String parentPassword) {
		this.parentPassword = parentPassword;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public String getStudentMail() {
		return studentMail;
	}
	public void setStudentMail(String studentMail) {
		this.studentMail = studentMail;
	}
	public String getParentMail() {
		return parentMail;
	}
	public void setParentMail(String parentMail) {
		this.parentMail = parentMail;
	}
	public String getWeek() {
		return week;
	}
	public void setWeek(String week) {
		this.week = week;
	}
	public int getPosition() {
		return position;
	}
	public void setPosition(int position) {
		this.position = position;
	}
	public List<TimeTablePeriodTo> getTimeTablePeriodTos() {
		return timeTablePeriodTos;
	}
	public void setTimeTablePeriodTos(List<TimeTablePeriodTo> timeTablePeriodTos) {
		this.timeTablePeriodTos = timeTablePeriodTos;
	}
	public String getShowWeek() {
		return showWeek;
	}
	public void setShowWeek(String showWeek) {
		this.showWeek = showWeek;
	}
	
}
