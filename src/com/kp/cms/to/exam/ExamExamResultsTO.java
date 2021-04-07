package com.kp.cms.to.exam;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ExamExamResultsTO implements Serializable,Comparable<ExamExamResultsTO>{
	private int id;
	private String examName;
	private String className;
	private String publishDate;
	private String publishOverallInternalComponentsOnly;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getExamName() {
		return examName;
	}
	public void setExamName(String examName) {
		this.examName = examName;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getPublishDate() {
		return publishDate;
	}
	public void setPublishDate(String publishDate) {
		this.publishDate = publishDate;
	}
	public String getPublishOverallInternalComponentsOnly() {
		return publishOverallInternalComponentsOnly;
	}
	public void setPublishOverallInternalComponentsOnly(
			String publishOverallInternalComponentsOnly) {
		this.publishOverallInternalComponentsOnly = publishOverallInternalComponentsOnly;
	}
	@Override
	public int compareTo(ExamExamResultsTO arg0) {
		if(arg0!=null && this!=null && arg0.getPublishDate()!=null
				 && this.getPublishDate()!=null){
			return this.getPublishDate().compareTo(arg0.getPublishDate());
		}else
		return 0;
	}

}
