package com.kp.cms.to.exam;

import java.io.Serializable;

/**
 * Jan 7, 2010 Created By 9Elements
 */
public class ExamExamEligibilitySetUpTO  implements Serializable,Comparable<ExamExamEligibilitySetUpTO>{
	private int id=0;
	private int classId=0;
	private int examTypeId=0;
	private String className="";
	private String examtypeName="";
	
	public ExamExamEligibilitySetUpTO(int id,int classId, String className,
			int examTypeId, String examtypeName) {
		super();
		this.id = id;
		this.classId = classId;
		this.className = className;
		this.examTypeId = examTypeId;
		this.examtypeName = examtypeName;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getExamtypeName() {
		return examtypeName;
	}
	public void setExamtypeName(String examtypeName) {
		this.examtypeName = examtypeName;
	}
	public int getClassId() {
		return classId;
	}
	public void setClassId(int classId) {
		this.classId = classId;
	}
	public int getExamTypeId() {
		return examTypeId;
	}
	public void setExamTypeId(int examTypeId) {
		this.examTypeId = examTypeId;
	}
	@Override
	public int compareTo(ExamExamEligibilitySetUpTO arg0) {
		if(arg0!=null && this!=null && arg0.getClassName()!=null
				 && this.getClassName()!=null){
			return this.getClassName().compareTo(arg0.getClassName());
		}
		else if(arg0!=null && this!=null && arg0.getExamtypeName()!=null
				 && this.getExamtypeName()!=null){
			return this.getExamtypeName().compareTo(arg0.getExamtypeName());
		}
		else
		return 0;
	}
	
}
