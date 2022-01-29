package com.kp.cms.to.exam;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ExamSubjectGroupDetailsTO implements Serializable {
	
	private Integer schemeNo;
	private String subjectGroup;
	
	
	
	public ExamSubjectGroupDetailsTO(Integer schemeNo, String subjectGroup) {
		super();
		this.schemeNo = schemeNo;
		this.subjectGroup = subjectGroup;
	}
	public Integer getSchemeNo() {
		return schemeNo;
	}
	public void setSchemeNo(Integer schemeNo) {
		this.schemeNo = schemeNo;
	}
	public void setSubjectGroup(String subjectGroup) {
		this.subjectGroup = subjectGroup;
	}
	public String getSubjectGroup() {
		return subjectGroup;
	}
	
	
	
	
}
