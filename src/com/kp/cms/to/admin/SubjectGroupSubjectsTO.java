package com.kp.cms.to.admin;

import java.io.Serializable;
import java.util.Date;

public class SubjectGroupSubjectsTO implements Serializable,Comparable<SubjectGroupSubjectsTO> {

	
	private int id;
	private String createdBy;;
	private SubjectTO subjectTo;
	private String modifiedBy;
	private SubjectGroupTO subjectGroupTo;
	private Date createdDate;
	private Date lastModifiedDate;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy)  {
		this.createdBy = createdBy;
	}
	public SubjectTO getSubjectTo() {
		return subjectTo;
	}
	public void setSubjectTo(SubjectTO subjectTo) {
		this.subjectTo = subjectTo;
	}
	public String getModifiedBy()  {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public SubjectGroupTO getSubjectGroupTo() {
		return subjectGroupTo;
	}
	public void setSubjectGroupTo(SubjectGroupTO subjectGroupTo) {
		this.subjectGroupTo = subjectGroupTo;
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
	@Override
	public int compareTo(SubjectGroupSubjectsTO arg0) {
		if(arg0!=null && this!=null && arg0.getSubjectTo()!=null 
				&& arg0.getSubjectTo().getName()!=null && this.getSubjectTo()!=null && this.getSubjectTo().getName()!=null){
			
				return this.getSubjectTo().getName().compareTo(arg0.getSubjectTo().getName());
		}else
		return 0;
	}
	
}
