package com.kp.cms.to.admin;

import java.util.Date;

public class CoreComplimentaryPapersTo 
{
	private int corecompId;
	private CourseTO courseto;
	private String coreSubject;
	private String coreSubject2;
	private String coreSubject3;
	private String complementary1Subject;
	private String complementary2Subject;
	private String complementary3Subject;
	private String openSubject;
	private String createdBy;;
	private String modifiedBy;
	private Date createdDate;
	private Date lastModifiedDate;
	private String isActive;
	private String cDate;
	private String lDate;	
	
	public CoreComplimentaryPapersTo() {

	}
	public CoreComplimentaryPapersTo(int corecompId, CourseTO courseto,
			String coreSubject, String complementary1Subject,
			String complementary2Subject, String createdBy, String modifiedBy,
			Date createdDate, Date lastModifiedDate, String isActive,
			String cDate, String lDate) {
		super();
		this.corecompId = corecompId;
		this.courseto = courseto;
		this.coreSubject = coreSubject;
		this.complementary1Subject = complementary1Subject;
		this.complementary2Subject = complementary2Subject;
		this.createdBy = createdBy;
		this.modifiedBy = modifiedBy;
		this.createdDate = createdDate;
		this.lastModifiedDate = lastModifiedDate;
		this.isActive = isActive;
		this.cDate = cDate;
		this.lDate = lDate;
	}
	public int getCorecompId() {
		return corecompId;
	}
	public void setCorecompId(int corecompId) {
		this.corecompId = corecompId;
	}
	public CourseTO getCourseto() {
		return courseto;
	}
	public void setCourseto(CourseTO courseto) {
		this.courseto = courseto;
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
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	public String getcDate() {
		return cDate;
	}
	public void setcDate(String cDate) {
		this.cDate = cDate;
	}
	public String getlDate() {
		return lDate;
	}
	public void setlDate(String lDate) {
		this.lDate = lDate;
	}
}
