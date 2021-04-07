package com.kp.cms.bo.exam;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import com.kp.cms.bo.admin.Subject;
import com.kp.cms.bo.admin.Users;

public class ExamValuationScheduleDetails {
	
	private int id;
	private ExamDefinition exam;
	private Subject subject;
	private Users users;
	private String createdBy;
	private String modifiedBy;
	private Date createdDate;
	private Date lastModifiedDate;
	private Boolean isActive;
	private String isValuator;
	private ExamValuators examValuators;
	private Date boardValuationDate;
	private Date valuationFrom;
	private Date valuationTo;

	
	public ExamValuationScheduleDetails() {
	}
	
	public ExamValuationScheduleDetails(int id, ExamDefinition exam, Subject subject, Users users, String createdBy, 
			String modifiedBy, Date createdDate, Date lastModifiedDate, Boolean isActive, 
			String isValuator, ExamValuators examValuators, Date boardValuationDate, 
			Date valuationFrom, Date valuationTo) {
		this.id = id;
		this.exam = exam;
		this.subject = subject;
		this.users = users;
		this.createdBy = createdBy;
		this.modifiedBy = modifiedBy;
		this.createdDate = createdDate;
		this.lastModifiedDate = lastModifiedDate;
		this.isActive = isActive;
		this.isValuator = isValuator;
		this.examValuators=examValuators;
		this.boardValuationDate = boardValuationDate;
		this.valuationFrom = valuationFrom;
		this.valuationTo = valuationTo;
	}

	public Users getUsers() {
		return users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ExamDefinition getExam() {
		return exam;
	}

	public void setExam(ExamDefinition exam) {
		this.exam = exam;
	}

	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
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

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public String getIsValuator() {
		return isValuator;
	}

	public void setIsValuator(String isValuator) {
		this.isValuator = isValuator;
	}

	public ExamValuators getExamValuators() {
		return examValuators;
	}

	public void setExamValuators(ExamValuators examValuators) {
		this.examValuators = examValuators;
	}

	public Date getBoardValuationDate() {
		return boardValuationDate;
	}

	public void setBoardValuationDate(Date boardValuationDate) {
		this.boardValuationDate = boardValuationDate;
	}

	public Date getValuationFrom() {
		return valuationFrom;
	}

	public void setValuationFrom(Date valuationFrom) {
		this.valuationFrom = valuationFrom;
	}

	public Date getValuationTo() {
		return valuationTo;
	}

	public void setValuationTo(Date valuationTo) {
		this.valuationTo = valuationTo;
	}
	
}