package com.kp.cms.to.admin;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.kp.cms.bo.admin.Course;
import com.kp.cms.bo.admin.InterviewResult;
import com.kp.cms.bo.admin.PersonalData;
import com.kp.cms.bo.admin.Student;


public class AdmApplnTO {

	private int id;
	private String createdBy;;
	private String modifiedBy;
	private PersonalData personalData;
	private Course course;
	private String applnNo;
	private String challanRefNo;
	private String journalNo;
	private Date createdDate;
	private Date lastModifiedDate;
	private Date date;
	private BigDecimal amount;
	private Boolean isSelected;
	private Boolean isInterviewSelected;
	private Set<Student> students = new HashSet<Student>(0);
	private Set<InterviewResult> interviewResults = new HashSet<InterviewResult>(
			0);
	private String admittedThroughId;
	private String subjectGroupId;
	private String cancelDate;
	private Date courseChangeDate;
	private Boolean isWaiting;
	private Boolean notSelected;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy)  {
		this.createdBy = createdBy;
	}
	public String getModifiedBy()  {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public PersonalData getPersonalData() {
		return personalData;
	}
	public void setPersonalData(PersonalData personalData) {
		this.personalData = personalData;
	}
	public Course getCourse() {
		return course;
	}
	public void setCourse(Course course) {
		this.course = course;
	}
	public String getApplnNo() {
		return applnNo;
	}
	public void setApplnNo(String applnNo) {
		this.applnNo = applnNo;
	}
	public String getChallanRefNo() {
		return challanRefNo;
	}
	public void setChallanRefNo(String challanRefNo) {
		this.challanRefNo = challanRefNo;
	}
	public String getJournalNo() {
		return journalNo;
	}
	public void setJournalNo(String journalNo) {
		this.journalNo = journalNo;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}
	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public Boolean getIsSelected() {
		return isSelected;
	}
	public void setIsSelected(Boolean isSelected) {
		this.isSelected = isSelected;
	}
	public Boolean getIsInterviewSelected() {
		return isInterviewSelected;
	}
	public void setIsInterviewSelected(Boolean isInterviewSelected) {
		this.isInterviewSelected = isInterviewSelected;
	}
	public Set<Student> getStudents() {
		return students;
	}
	public void setStudents(Set<Student> students) {
		this.students = students;
	}
	public Set<InterviewResult> getInterviewResults() {
		return interviewResults;
	}
	public void setInterviewResults(Set<InterviewResult> interviewResults) {
		this.interviewResults = interviewResults;
	}
	public String getAdmittedThroughId() {
		return admittedThroughId;
	}
	public void setAdmittedThroughId(String admittedThroughId) {
		this.admittedThroughId = admittedThroughId;
	}
	public String getSubjectGroupId() {
		return subjectGroupId;
	}
	public void setSubjectGroupId(String subjectGroupId) {
		this.subjectGroupId = subjectGroupId;
	}
	public String getCancelDate() {
		return cancelDate;
	}
	public void setCancelDate(String cancelDate) {
		this.cancelDate = cancelDate;
	}
	public Date getCourseChangeDate() {
		return courseChangeDate;
	}
	public void setCourseChangeDate(Date courseChangeDate) {
		this.courseChangeDate = courseChangeDate;
	}
	public Boolean getIsWaiting() {
		return isWaiting;
	}
	public void setIsWaiting(Boolean isWaiting) {
		this.isWaiting = isWaiting;
	}
	public Boolean getNotSelected() {
		return notSelected;
	}
	public void setNotSelected(Boolean notSelected) {
		this.notSelected = notSelected;
	}

	
}
