package com.kp.cms.bo.exam;

import java.io.Serializable;
import java.util.Date;

import com.kp.cms.bo.admin.Subject;

public class ExamRevaluationAppDetails implements Serializable {
	private int id;
	private ExamRevaluationApplication examRevApp;
	private Subject subject;
	private String status;
	private String type;
	private Boolean isActive;
	private Date createdDate;
	private Date lastModifiedDate;
	private String createdBy;
	private String modifiedBy;
	private String marks;
	private String mark1;
	private String mark2;
	private boolean thirdEvaluation;
	private Boolean isUpdated;
	private String thirdEvlMarks;
	//new
	private String mark3;
	
	public ExamRevaluationAppDetails() {
		super();
	}
	
	public ExamRevaluationAppDetails(int id,
			ExamRevaluationApplication examRevApp, Subject subject,
			String status, String type, Boolean isActive, Date createdDate,
			Date lastModifiedDate, String createdBy, String modifiedBy) {
		super();
		this.id = id;
		this.examRevApp = examRevApp;
		this.subject = subject;
		this.status = status;
		this.type = type;
		this.isActive = isActive;
		this.createdDate = createdDate;
		this.lastModifiedDate = lastModifiedDate;
		this.createdBy = createdBy;
		this.modifiedBy = modifiedBy;
	}



	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public ExamRevaluationApplication getExamRevApp() {
		return examRevApp;
	}
	public void setExamRevApp(ExamRevaluationApplication examRevApp) {
		this.examRevApp = examRevApp;
	}
	public Subject getSubject() {
		return subject;
	}
	public void setSubject(Subject subject) {
		this.subject = subject;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Boolean getIsActive() {
		return isActive;
	}
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
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

	public String getMarks() {
		return marks;
	}

	public void setMarks(String marks) {
		this.marks = marks;
	}

	public String getMark1() {
		return mark1;
	}

	public void setMark1(String mark1) {
		this.mark1 = mark1;
	}

	public String getMark2() {
		return mark2;
	}

	public void setMark2(String mark2) {
		this.mark2 = mark2;
	}

	public boolean isThirdEvaluation() {
		return thirdEvaluation;
	}

	public void setThirdEvaluation(boolean thirdEvaluation) {
		this.thirdEvaluation = thirdEvaluation;
	}

	public Boolean getIsUpdated() {
		return isUpdated;
	}

	public void setIsUpdated(Boolean isUpdated) {
		this.isUpdated = isUpdated;
	}

	public String getThirdEvlMarks() {
		return thirdEvlMarks;
	}

	public void setThirdEvlMarks(String thirdEvlMarks) {
		this.thirdEvlMarks = thirdEvlMarks;
	}

	public String getMark3() {
		return mark3;
	}

	public void setMark3(String mark3) {
		this.mark3 = mark3;
	}

}