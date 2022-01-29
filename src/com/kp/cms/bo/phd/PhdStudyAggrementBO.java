package com.kp.cms.bo.phd;

/**
 * Dec 29, 2009
 * Created By 9Elements Team
 */
import java.io.Serializable;
import java.util.Date;

import com.kp.cms.bo.admin.Course;
import com.kp.cms.bo.admin.Student;

public class PhdStudyAggrementBO implements Serializable {
    
	private int id;
	private Student studentId;
	private Course courseId;
	private PhdGuideDetailsBO guideId;
	private PhdGuideDetailsBO coGuideId;
	private Date signedOn;
	private Boolean isActive;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;

	public PhdStudyAggrementBO() {
	}

	public PhdStudyAggrementBO(int id,Student studentId,Course courseId,PhdGuideDetailsBO guideId,PhdGuideDetailsBO coGuideId,
			Date signedOn,Boolean isActive,String createdBy,String modifiedBy,Date createdDate,Date lastModifiedDate) 
	{
		
		this.id = id;
		this.studentId=studentId;
		this.courseId=courseId;
		this.guideId=guideId;
		this.coGuideId=coGuideId;
		this.signedOn=signedOn;
		this.isActive = isActive;
		this.createdBy = createdBy;
		this.modifiedBy = modifiedBy;
		this.createdDate = createdDate;
		this.lastModifiedDate = lastModifiedDate;
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

	public PhdGuideDetailsBO getGuideId() {
		return guideId;
	}

	public void setGuideId(PhdGuideDetailsBO guideId) {
		this.guideId = guideId;
	}

	public PhdGuideDetailsBO getCoGuideId() {
		return coGuideId;
	}

	public void setCoGuideId(PhdGuideDetailsBO coGuideId) {
		this.coGuideId = coGuideId;
	}

	public Date getSignedOn() {
		return signedOn;
	}

	public void setSignedOn(Date signedOn) {
		this.signedOn = signedOn;
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
