package com.kp.cms.to.exam;

import java.io.Serializable;

public class PublishSupplementaryImpApplicationTo implements Serializable,Comparable<PublishSupplementaryImpApplicationTo> {
	
	private int id;
	private String examName;
	private String startDate;
	private String endDate;
	private String className;
	
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
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(PublishSupplementaryImpApplicationTo arg0) {
		if(arg0 instanceof PublishSupplementaryImpApplicationTo && arg0.getExamName()!=null ){
			return this.getExamName().compareTo(arg0.examName);
		}else
			return 0;
	}
}
