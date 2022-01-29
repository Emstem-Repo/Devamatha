package com.kp.cms.to.exam;

import java.io.Serializable;

public class ExamSubjectRuleSettingsSubjectTO implements Serializable,Comparable<ExamSubjectRuleSettingsSubjectTO>
{
	private Integer id;
	private Integer subid;
	private String subjectName;
	private String schemeName;
	private String courseName;
	public ExamSubjectRuleSettingsSubjectTO(Integer id, String subjectName,
			String schemeName, String courseName, Integer subid) {
		super();
		this.id = id;
		this.subjectName = subjectName;
		this.schemeName = schemeName;
		this.courseName = courseName;
		this.subid = subid;
	}
	public ExamSubjectRuleSettingsSubjectTO() {
		super();
	}
	public ExamSubjectRuleSettingsSubjectTO(Integer subid, String subjectName) {
		super();
		this.subid = subid;
		this.subjectName = subjectName;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getSubid() {
		return subid;
	}
	public void setSubid(Integer subid) {
		this.subid = subid;
	}
	public String getSubjectName() {
		return subjectName;
	}
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
	public String getSchemeName() {
		return schemeName;
	}
	public void setSchemeName(String schemeName) {
		this.schemeName = schemeName;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	@Override
	public int compareTo(ExamSubjectRuleSettingsSubjectTO arg0) {
		if(arg0!=null && this!=null && arg0.getSubjectName()!=null
				 && this.getSubjectName()!=null){
			return this.getSubjectName().compareTo(arg0.getSubjectName());
		}		
		else
			return 0;
	}
	

}
