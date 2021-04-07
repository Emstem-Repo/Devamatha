package com.kp.cms.to.exam;

import java.io.Serializable;
import java.util.List;

public class NewConsolidatedMarksCardTo implements Serializable {
	
	private int classId;
	private List<Integer> subjectIds;
	private int schemeNo;
	
	public int getClassId() {
		return classId;
	}
	public void setClassId(int classId) {
		this.classId = classId;
	}
	public List<Integer> getSubjectIds() {
		return subjectIds;
	}
	public void setSubjectIds(List<Integer> subjectIds) {
		this.subjectIds = subjectIds;
	}
	public int getSchemeNo() {
		return schemeNo;
	}
	public void setSchemeNo(int schemeNo) {
		this.schemeNo = schemeNo;
	}
	
}
