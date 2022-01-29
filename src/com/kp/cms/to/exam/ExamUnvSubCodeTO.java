package com.kp.cms.to.exam;

import java.io.Serializable;

public class ExamUnvSubCodeTO implements Serializable,Comparable<ExamUnvSubCodeTO> {

	private int id;
	private String subjectName;
	private String subjectCode;
	private String universitySubjectCode;

	public ExamUnvSubCodeTO(int id, String subjectCode, String subjectName,
			String universitySubjectCode) {
		this.id = id;
		this.subjectCode = subjectCode;
		this.subjectName = subjectName;
		this.universitySubjectCode = universitySubjectCode;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public String getSubjectCode() {
		return subjectCode;
	}

	public void setSubjectCode(String subjectCode) {
		this.subjectCode = subjectCode;
	}

	public String getUniversitySubjectCode() {
		return universitySubjectCode;
	}

	public void setUniversitySubjectCode(String universitySubjectCode) {
		this.universitySubjectCode = universitySubjectCode;
	}

	@Override
	public int compareTo(ExamUnvSubCodeTO arg0) {
		if(arg0!=null && this!=null && arg0.getSubjectName()!=null
				 && this.getSubjectName()!=null){
				return this.getSubjectName().compareTo(arg0.getSubjectName());
		}else
		return 0;
	}

}
