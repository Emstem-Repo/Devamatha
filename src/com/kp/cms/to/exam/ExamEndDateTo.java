package com.kp.cms.to.exam;

import java.io.Serializable;

public class ExamEndDateTo implements Serializable,Comparable<ExamEndDateTo> {
	
	private int id;
	private String examName;
	private String endDate;
	
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
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(ExamEndDateTo arg0) {
		if(arg0 instanceof ExamEndDateTo && arg0.getExamName()!=null ){
			return this.getExamName().compareTo(arg0.examName);
		}else
			return 0;
	}

}
