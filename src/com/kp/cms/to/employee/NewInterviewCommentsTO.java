package com.kp.cms.to.employee;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.kp.cms.bo.admin.EmpOnlineResume;
import com.kp.cms.bo.employee.InterviewRatingFactor;

public class NewInterviewCommentsTO {
	private int id;
	private int empOnlineResumeId;
	private Integer noOfInternalInterviewers;
	private Integer noOfExternalInterviewers;
	private String comments;
	private String nameOfInternalInterviewer1;
	private String nameOfInternalInterviewer2;
	private String nameOfInternalInterviewer3;
	private String nameOfExternalInterviewer1;
	private String nameOfExternalInterviewer2;
	private String nameOfExternalInterviewer3;
	private List<NewInterviewCommentsDetailsTo> newInterviewCommentsList;
	private String name;
	private String email;
	
	private String applicationNo;
	private String department;
	private int age;
	private Date dateOfBirth;
	private String gender;
	// code Added
	private String nameOfInternalInterviewer4;
	private String nameOfInternalInterviewer5;
	private String nameOfInternalInterviewer6;
	private String nameOfExternalInterviewer4;
	private String nameOfExternalInterviewer5;
	private String nameOfExternalInterviewer6;
	private String joiningTime;
	private String tempDeptId;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	
	public Integer getNoOfInternalInterviewers() {
		return noOfInternalInterviewers;
	}
	public void setNoOfInternalInterviewers(Integer noOfInternalInterviewers) {
		this.noOfInternalInterviewers = noOfInternalInterviewers;
	}
	public Integer getNoOfExternalInterviewers() {
		return noOfExternalInterviewers;
	}
	public void setNoOfExternalInterviewers(Integer noOfExternalInterviewers) {
		this.noOfExternalInterviewers = noOfExternalInterviewers;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getNameOfInternalInterviewer1() {
		return nameOfInternalInterviewer1;
	}
	public void setNameOfInternalInterviewer1(String nameOfInternalInterviewer1) {
		this.nameOfInternalInterviewer1 = nameOfInternalInterviewer1;
	}
	public String getNameOfInternalInterviewer2() {
		return nameOfInternalInterviewer2;
	}
	public void setNameOfInternalInterviewer2(String nameOfInternalInterviewer2) {
		this.nameOfInternalInterviewer2 = nameOfInternalInterviewer2;
	}
	public String getNameOfInternalInterviewer3() {
		return nameOfInternalInterviewer3;
	}
	public void setNameOfInternalInterviewer3(String nameOfInternalInterviewer3) {
		this.nameOfInternalInterviewer3 = nameOfInternalInterviewer3;
	}
	public String getNameOfExternalInterviewer1() {
		return nameOfExternalInterviewer1;
	}
	public void setNameOfExternalInterviewer1(String nameOfExternalInterviewer1) {
		this.nameOfExternalInterviewer1 = nameOfExternalInterviewer1;
	}
	public String getNameOfExternalInterviewer2() {
		return nameOfExternalInterviewer2;
	}
	public void setNameOfExternalInterviewer2(String nameOfExternalInterviewer2) {
		this.nameOfExternalInterviewer2 = nameOfExternalInterviewer2;
	}
	public String getNameOfExternalInterviewer3() {
		return nameOfExternalInterviewer3;
	}
	public void setNameOfExternalInterviewer3(String nameOfExternalInterviewer3) {
		this.nameOfExternalInterviewer3 = nameOfExternalInterviewer3;
	}
	
	public void setApplicationNo(String applicationNo) {
		this.applicationNo = applicationNo;
	}
	public String getApplicationNo() {
		return applicationNo;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getDepartment() {
		return department;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public Date getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public void setNewInterviewCommentsList(List<NewInterviewCommentsDetailsTo> newInterviewCommentsList) {
		this.newInterviewCommentsList = newInterviewCommentsList;
	}
	public List<NewInterviewCommentsDetailsTo> getNewInterviewCommentsList() {
		return newInterviewCommentsList;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getId() {
		return id;
	}
	public void setEmpOnlineResumeId(int empOnlineResumeId) {
		this.empOnlineResumeId = empOnlineResumeId;
	}
	public int getEmpOnlineResumeId() {
		return empOnlineResumeId;
	}
	public String getNameOfInternalInterviewer4() {
		return nameOfInternalInterviewer4;
	}
	public void setNameOfInternalInterviewer4(String nameOfInternalInterviewer4) {
		this.nameOfInternalInterviewer4 = nameOfInternalInterviewer4;
	}
	public String getNameOfInternalInterviewer5() {
		return nameOfInternalInterviewer5;
	}
	public void setNameOfInternalInterviewer5(String nameOfInternalInterviewer5) {
		this.nameOfInternalInterviewer5 = nameOfInternalInterviewer5;
	}
	public String getNameOfInternalInterviewer6() {
		return nameOfInternalInterviewer6;
	}
	public void setNameOfInternalInterviewer6(String nameOfInternalInterviewer6) {
		this.nameOfInternalInterviewer6 = nameOfInternalInterviewer6;
	}
	public String getNameOfExternalInterviewer4() {
		return nameOfExternalInterviewer4;
	}
	public void setNameOfExternalInterviewer4(String nameOfExternalInterviewer4) {
		this.nameOfExternalInterviewer4 = nameOfExternalInterviewer4;
	}
	public String getNameOfExternalInterviewer5() {
		return nameOfExternalInterviewer5;
	}
	public void setNameOfExternalInterviewer5(String nameOfExternalInterviewer5) {
		this.nameOfExternalInterviewer5 = nameOfExternalInterviewer5;
	}
	public String getNameOfExternalInterviewer6() {
		return nameOfExternalInterviewer6;
	}
	public void setNameOfExternalInterviewer6(String nameOfExternalInterviewer6) {
		this.nameOfExternalInterviewer6 = nameOfExternalInterviewer6;
	}
	public void setJoiningTime(String joiningTime) {
		this.joiningTime = joiningTime;
	}
	public String getJoiningTime() {
		return joiningTime;
	}
	public String getTempDeptId() {
		return tempDeptId;
	}
	public void setTempDeptId(String tempDeptId) {
		this.tempDeptId = tempDeptId;
	}
}
