package com.kp.cms.bo.exam;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import com.kp.cms.bo.admin.Subject;
import com.kp.cms.bo.admin.Users;

public class ExamValidationDetails implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private ExamDefinition exam;
	private Subject subject;
	private Users users;
	private String otherEmployee;
	private String createdBy;
	private String modifiedBy;
	private Date createdDate;
	private Date lastModifiedDate;
	private Boolean isActive;
	private Set<ExamValuationAnswerScript> answerScripts;
	private String isValuator;
	private ExamValuators examValuators;
	
	public ExamValidationDetails() {
	}
	
	
	public ExamValidationDetails(int id, ExamDefinition exam,
			Subject subject, Users users, String otherEmployee,
			String createdBy, String modifiedBy, Date createdDate,
			Date lastModifiedDate, Boolean isActive,Set<ExamValuationAnswerScript> answerScripts,String isValuator,ExamValuators examValuators) {
		this.id = id;
		this.exam = exam;
		this.subject = subject;
		this.users = users;
		this.otherEmployee = otherEmployee;
		this.createdBy = createdBy;
		this.modifiedBy = modifiedBy;
		this.createdDate = createdDate;
		this.lastModifiedDate = lastModifiedDate;
		this.isActive = isActive;
		this.answerScripts=answerScripts;
		this.isValuator = isValuator;
		this.examValuators=examValuators;
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


	public Users getUsers() {
		return users;
	}


	public void setUsers(Users users) {
		this.users = users;
	}


	public String getOtherEmployee() {
		return otherEmployee;
	}

	public void setOtherEmployee(String otherEmployee) {
		this.otherEmployee = otherEmployee;
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


	public Set<ExamValuationAnswerScript> getAnswerScripts() {
		return answerScripts;
	}


	public void setAnswerScripts(Set<ExamValuationAnswerScript> answerScripts) {
		this.answerScripts = answerScripts;
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

	
}
