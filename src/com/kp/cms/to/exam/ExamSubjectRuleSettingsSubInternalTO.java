package com.kp.cms.to.exam;

import java.io.Serializable;

public class ExamSubjectRuleSettingsSubInternalTO implements Serializable,Comparable<ExamSubjectRuleSettingsSubInternalTO>
{
	private String id;
	private String count_marks;
	private String type;
	private String minimumMarks;
	private String maximumMarks;
	private String entryMaximumMarks;
	private String isTheoryPractical;
	private String totalMinimummumMarks;
	private String totalMaximumMarks;
	private String totalentryMaximumMarks;
	private String selectTheBest;
	private String internalExamTypeId;
	public ExamSubjectRuleSettingsSubInternalTO() {
		super();
	}
	public ExamSubjectRuleSettingsSubInternalTO(String internalExamTypeId, String type) {
		this.internalExamTypeId = internalExamTypeId;
		this.type = type;
	}
	public ExamSubjectRuleSettingsSubInternalTO(String id,
			String entryMaximumMarks, String maximumMarks, String minimumMarks,
			String type,String isTheoryPractical,String selectTheBest,String internalExamTypeId) {
		super();
		this.id = id;
		this.entryMaximumMarks=entryMaximumMarks;
		this.maximumMarks = maximumMarks;
		this.minimumMarks = minimumMarks;
		this.type = type;
		this.isTheoryPractical = isTheoryPractical;
		this.selectTheBest = selectTheBest;
		this.internalExamTypeId=internalExamTypeId;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getMinimumMarks() {
		return minimumMarks;
	}
	public void setMinimumMarks(String minimumMarks) {
		this.minimumMarks = minimumMarks;
	}
	public String getMaximumMarks() {
		return maximumMarks;
	}
	public void setMaximumMarks(String maximumMarks) {
		this.maximumMarks = maximumMarks;
	}
	public void setEntryMaximumMarks(String entryMaximumMarks) {
		this.entryMaximumMarks = entryMaximumMarks;
	}
	public String getEntryMaximumMarks() {
		return entryMaximumMarks;
	}
	public void setIsTheoryPractical(String isTheoryPractical) {
		this.isTheoryPractical = isTheoryPractical;
	}
	public String getIsTheoryPractical() {
		return isTheoryPractical;
	}
	public void setCount_marks(String count_marks) {
		this.count_marks = count_marks;
	}
	public String getCount_marks() {
		return count_marks;
	}
	public void setTotalMinimummumMarks(String totalMinimummumMarks) {
		this.totalMinimummumMarks = totalMinimummumMarks;
	}
	public String getTotalMinimummumMarks() {
		return totalMinimummumMarks;
	}
	public void setTotalMaximumMarks(String totalMaximumMarks) {
		this.totalMaximumMarks = totalMaximumMarks;
	}
	public String getTotalMaximumMarks() {
		return totalMaximumMarks;
	}
	public void setTotalentryMaximumMarks(String totalentryMaximumMarks) {
		this.totalentryMaximumMarks = totalentryMaximumMarks;
	}
	public String getTotalentryMaximumMarks() {
		return totalentryMaximumMarks;
	}
	public void setSelectTheBest(String selectTheBest) {
		this.selectTheBest = selectTheBest;
	}
	public String getSelectTheBest() {
		return selectTheBest;
	}
	public void setInternalExamTypeId(String internalExamTypeId) {
		this.internalExamTypeId = internalExamTypeId;
	}
	public String getInternalExamTypeId() {
		return internalExamTypeId;
	}
	@Override
	public int compareTo(ExamSubjectRuleSettingsSubInternalTO arg0) {
		if(arg0!=null && this!=null && arg0.getType()!=null
				 && this.getType()!=null){
			return this.getType().compareTo(arg0.getType());
		}		
		else
		return 0;
	}
}
