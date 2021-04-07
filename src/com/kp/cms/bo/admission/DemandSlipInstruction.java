package com.kp.cms.bo.admission;

import java.util.Date;

import com.kp.cms.bo.admin.Course;

public class DemandSlipInstruction implements java.io.Serializable{
	
	private int id;
	private int schemeNo;
	private Course course;
	private String instruction;
	private Boolean isActive;
	private String createdBy;
	private String modifiedBy;
	private Date createdDate;
	private Date lastModifiedDate;
	
	public DemandSlipInstruction(){
		
	}
	
	public DemandSlipInstruction(int id,int schemeNo,Course course,String instruction,Boolean isActive,
			String createdBy,String modifiedBy,Date createdDate,Date lastModifiedDate){
		this.id=id;
		this.course=course;
		this.createdBy=createdBy;
		this.createdDate=createdDate;
		this.instruction=instruction;
		this.isActive=isActive;
		this.lastModifiedDate=lastModifiedDate;
		this.modifiedBy=modifiedBy;
		this.schemeNo=schemeNo;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getSchemeNo() {
		return schemeNo;
	}

	public void setSchemeNo(int schemeNo) {
		this.schemeNo = schemeNo;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public String getInstruction() {
		return instruction;
	}

	public void setInstruction(String instruction) {
		this.instruction = instruction;
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
}
