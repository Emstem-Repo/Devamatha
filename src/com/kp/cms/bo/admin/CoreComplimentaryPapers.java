package com.kp.cms.bo.admin;

import java.io.Serializable;
import java.util.Date;

public class CoreComplimentaryPapers implements Serializable 
{
	private int id;
	private String coreSubject;
	private String coreSubject2;
	private String coreSubject3;
	private String complementary1Subject;
	private String complementary2Subject;
	private String complementary3Subject;
	private String openSubject;
	private Course course;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;
	private Boolean isActive;
	
	public CoreComplimentaryPapers() {
		super();
	}
	public CoreComplimentaryPapers(int id, String coreSubject,
			String coreSubject2, String coreSubject3,
			String complementary1Subject, String complementary2Subject,
			String complementary3Subject, String openSubject, Course course,
			String createdBy, Date createdDate, String modifiedBy,
			Date lastModifiedDate, Boolean isActive) {
		super();
		this.id = id;
		this.coreSubject = coreSubject;
		this.coreSubject2 = coreSubject2;
		this.coreSubject3 = coreSubject3;
		this.complementary1Subject = complementary1Subject;
		this.complementary2Subject = complementary2Subject;
		this.complementary3Subject = complementary3Subject;
		this.openSubject = openSubject;
		this.course = course;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.lastModifiedDate = lastModifiedDate;
		this.isActive = isActive;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCoreSubject() {
		return coreSubject;
	}
	public void setCoreSubject(String coreSubject) {
		this.coreSubject = coreSubject;
	}
	public String getCoreSubject2() {
		return coreSubject2;
	}
	public void setCoreSubject2(String coreSubject2) {
		this.coreSubject2 = coreSubject2;
	}
	public String getCoreSubject3() {
		return coreSubject3;
	}
	public void setCoreSubject3(String coreSubject3) {
		this.coreSubject3 = coreSubject3;
	}
	public String getComplementary1Subject() {
		return complementary1Subject;
	}
	public void setComplementary1Subject(String complementary1Subject) {
		this.complementary1Subject = complementary1Subject;
	}
	public String getComplementary2Subject() {
		return complementary2Subject;
	}
	public void setComplementary2Subject(String complementary2Subject) {
		this.complementary2Subject = complementary2Subject;
	}
	public String getComplementary3Subject() {
		return complementary3Subject;
	}
	public void setComplementary3Subject(String complementary3Subject) {
		this.complementary3Subject = complementary3Subject;
	}
	public String getOpenSubject() {
		return openSubject;
	}
	public void setOpenSubject(String openSubject) {
		this.openSubject = openSubject;
	}
	public Course getCourse() {
		return course;
	}
	public void setCourse(Course course) {
		this.course = course;
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
