package com.kp.cms.bo.examallotment;

import java.io.Serializable;
import java.util.Date;
import com.kp.cms.bo.admin.Users;
import com.kp.cms.bo.exam.ExamDefinition;

public class ExamInviligatorExemptionDatewise implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private ExamDefinition examId;
	private Users teacherId;
	private Date date;
	private String session;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;
	private Boolean isActive;
	private ExaminationSessions examinationSessions;
	
	public ExamInviligatorExemptionDatewise(int id, ExamDefinition examId,
			Users teacherId, Date date, String session, String createdBy,
			Date createdDate, String modifiedBy, Date lastModifiedDate,
			Boolean isActive, ExaminationSessions examinationSessions) {
		super();
		this.id = id;
		this.examId = examId;
		this.teacherId = teacherId;
		this.date = date;
		this.session = session;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.lastModifiedDate = lastModifiedDate;
		this.isActive = isActive;
		this.examinationSessions = examinationSessions;
	}

	public ExamInviligatorExemptionDatewise() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public String getSession() {
		return session;
	}
	public void setSession(String session) {
		this.session = session;
	}
	public ExaminationSessions getExaminationSessions() {
		return examinationSessions;
	}
	public void setExaminationSessions(ExaminationSessions examinationSessions) {
		this.examinationSessions = examinationSessions;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public ExamDefinition getExamId() {
		return examId;
	}
	public void setExamId(ExamDefinition examId) {
		this.examId = examId;
	}
	public Users getTeacherId() {
		return teacherId;
	}
	public void setTeacherId(Users teacherId) {
		this.teacherId = teacherId;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
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
}
