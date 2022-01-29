package com.kp.cms.to.admin;

import java.io.Serializable;
import java.util.Date;

public class TermsConditionChecklistTO implements Serializable,Comparable<TermsConditionChecklistTO>{
	private int id;
	private int courseId;
	private boolean mandatory;
	private String checklistDescription;
	private boolean isActive;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;
	private boolean checked;
	private boolean tempChecked;
	private CourseTO courseTO;
	private int year;
	private int endYear;
	private String isMandatory;	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	
	public int getCourseId() {
		return courseId;
	}
	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}
	public boolean isMandatory() {
		return mandatory;
	}
	public void setMandatory(boolean mandatory) {
		this.mandatory = mandatory;
	}
	public String getChecklistDescription() {
		return checklistDescription;
	}
	public void setChecklistDescription(String checklistDescription) {
		this.checklistDescription = checklistDescription;
	}
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
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
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	public boolean isTempChecked() {
		return tempChecked;
	}
	public void setTempChecked(boolean tempChecked) {
		this.tempChecked = tempChecked;
	}
	public CourseTO getCourseTO() {
		return courseTO;
	}
	public int getYear() {
		return year;
	}
	public int getEndYear() {
		return endYear;
	}
	public String getIsMandatory() {
		return isMandatory;
	}
	public void setCourseTO(CourseTO courseTO) {
		this.courseTO = courseTO;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public void setEndYear(int endYear) {
		this.endYear = endYear;
	}
	public void setIsMandatory(String isMandatory) {
		this.isMandatory = isMandatory;
	}
	@Override
	public int compareTo(TermsConditionChecklistTO arg0) {
		if(arg0!=null){
			if (arg0.getId() != 0 && this.getId()!=0){
			if(this.getId() > arg0.getId())
				return 1;
			else if(this.getId() < arg0.getId()){
				return -1;
			}else
				return 0;
		    }
		}
		return 0;
	}
	
	
}
