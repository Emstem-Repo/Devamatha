package com.kp.cms.to.exam;

import java.io.Serializable;

public class ExamSubjectTO implements Serializable,Comparable<ExamSubjectTO> {

	private int id;
	private String subjectName;
	private String subjectCode;
	private String value;
	private boolean dummyValue;

	public ExamSubjectTO(int id, String subjectName, String subjectCode) {
		super();
		this.id = id;
		this.subjectCode = subjectCode;
		this.subjectName = subjectName;
	}

	public ExamSubjectTO(int id) {
		super();
		this.id = id;
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

	public ExamSubjectTO() {
		super();
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public void setDummyValue(boolean dummyValue) {
		this.dummyValue = dummyValue;
	}

	public boolean getDummyValue() {
		return dummyValue;
	}

	@Override
	public int compareTo(ExamSubjectTO arg0) {
		if(arg0!=null && this!=null && arg0.getSubjectName()!=null
				 && this.getSubjectName()!=null){
			return this.getSubjectName().compareTo(arg0.getSubjectName());
		}		
		else
		return 0;
	}

}
