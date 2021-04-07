package com.kp.cms.to.exam;

import java.util.List;


public class SupplementaryAppExamTo {
	
	private int examId;
	private String examName;
	private List<SupplementaryApplicationClassTo> examList;
	private String examDate;
	private String extendedDate;
	
	public int getExamId() {
		return examId;
	}
	public void setExamId(int examId) {
		this.examId = examId;
	}
	public List<SupplementaryApplicationClassTo> getExamList() {
		return examList;
	}
	public void setExamList(List<SupplementaryApplicationClassTo> examList) {
		this.examList = examList;
	}
	public String getExamName() {
		return examName;
	}
	public void setExamName(String examName) {
		this.examName = examName;
	}
	public String getExamDate() {
		return examDate;
	}
	public void setExamDate(String examDate) {
		this.examDate = examDate;
	}
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof SupplementaryAppExamTo){
			SupplementaryAppExamTo temp=(SupplementaryAppExamTo) obj;
			if(temp.getExamId()==this.examId)
				return true;
		}
			
		return false;
	}
	public String getExtendedDate() {
		return extendedDate;
	}
	public void setExtendedDate(String extendedDate) {
		this.extendedDate = extendedDate;
	}
}