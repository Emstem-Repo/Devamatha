package com.kp.cms.bo.phd;

import java.io.Serializable;
import java.util.Date;

import com.kp.cms.bo.admin.Batch;
import com.kp.cms.bo.admin.Course;
import com.kp.cms.bo.admin.Student;

public class PhdCompletionDetailsBO implements Serializable {
	
	private int id;
	private Student studentId;
	private Course courseId;
	private String batch;
	private String title;
	private String discipline;
	private Date vivaVoice;
	private Boolean isActive;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;
	
	public PhdCompletionDetailsBO() {
		
	}
	
	public PhdCompletionDetailsBO(int id,Student studentId,Course courseId,
			String batch,String title,String discipline,Date vivaVoice,Boolean isActive,
			String createdBy,String modifiedBy,Date createdDate,Date lastModifiedDate) 
	{
		this.id = id;
		this.studentId=studentId;
		this.courseId=courseId;
		this.batch=batch;
		this.title=title;
		this.discipline=discipline;
		this.vivaVoice=vivaVoice;
		this.isActive = isActive;
		this.createdBy = createdBy;
		this.modifiedBy = modifiedBy;
		this.createdDate = createdDate;
		this.lastModifiedDate = lastModifiedDate;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDiscipline() {
		return discipline;
	}

	public void setDiscipline(String discipline) {
		this.discipline = discipline;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Student getStudentId() {
		return studentId;
	}

	public void setStudentId(Student studentId) {
		this.studentId = studentId;
	}

	public Course getCourseId() {
		return courseId;
	}

	public void setCourseId(Course courseId) {
		this.courseId = courseId;
	}

	public String getBatch() {
		return batch;
	}

	public void setBatch(String batch) {
		this.batch = batch;
	}

	public Date getVivaVoice() {
		return vivaVoice;
	}

	public void setVivaVoice(Date vivaVoice) {
		this.vivaVoice = vivaVoice;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
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
	
}
