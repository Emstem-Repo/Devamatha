package com.kp.cms.bo.admin;

import java.util.Date;

public class ConvocationRegistration implements java.io.Serializable  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private int id;
	private boolean participatingConvocation;
	private boolean guestPassIsRequired;
	private String relationshipWithGuest;
	private int academicYear;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;
	private Boolean isActive;
	private Student student;
	private Integer guestPassCount;
	private ConvocationSession session;
	private boolean guestpassIssued;
	private String passNo1;
	private String passNo2;
	
	public ConvocationRegistration() {
	}
	

	public ConvocationRegistration(int id, boolean participatingConvocation,
			boolean guestPassIsRequired, String relationshipWithGuest,
			int academicYear, String createdBy, Date createdDate,
			String modifiedBy, Date lastModifiedDate, Boolean isActive,
			Student student,Integer guestPassCount,ConvocationSession session, boolean guestpassIssued,
			String passNo1,String passNo2) {
		super();
		this.id = id;
		this.participatingConvocation = participatingConvocation;
		this.guestPassIsRequired = guestPassIsRequired;
		this.relationshipWithGuest = relationshipWithGuest;
		this.academicYear = academicYear;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.lastModifiedDate = lastModifiedDate;
		this.isActive = isActive;
		this.student = student;
		this.guestPassCount=guestPassCount;
		this.session=session;
		this.guestpassIssued=guestpassIssued;
		this.passNo1=passNo1;
		this.passNo2=passNo2;
	}






	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public boolean isParticipatingConvocation() {
		return participatingConvocation;
	}
	public void setParticipatingConvocation(boolean participatingConvocation) {
		this.participatingConvocation = participatingConvocation;
	}
	public boolean isGuestPassIsRequired() {
		return guestPassIsRequired;
	}
	public void setGuestPassIsRequired(boolean guestPassIsRequired) {
		this.guestPassIsRequired = guestPassIsRequired;
	}
	public String getRelationshipWithGuest() {
		return relationshipWithGuest;
	}
	public void setRelationshipWithGuest(String relationshipWithGuest) {
		this.relationshipWithGuest = relationshipWithGuest;
	}

	public int getAcademicYear() {
		return academicYear;
	}


	public void setAcademicYear(int academicYear) {
		this.academicYear = academicYear;
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

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public ConvocationSession getSession() {
		return session;
	}


	public void setSession(ConvocationSession session) {
		this.session = session;
	}


	public Integer getGuestPassCount() {
		return guestPassCount;
	}


	public void setGuestPassCount(Integer guestPassCount) {
		this.guestPassCount = guestPassCount;
	}


	public boolean isGuestpassIssued() {
		return guestpassIssued;
	}


	public void setGuestpassIssued(boolean guestpassIssued) {
		this.guestpassIssued = guestpassIssued;
	}


	public String getPassNo1() {
		return passNo1;
	}


	public void setPassNo1(String passNo1) {
		this.passNo1 = passNo1;
	}


	public String getPassNo2() {
		return passNo2;
	}


	public void setPassNo2(String passNo2) {
		this.passNo2 = passNo2;
	}
	

}
