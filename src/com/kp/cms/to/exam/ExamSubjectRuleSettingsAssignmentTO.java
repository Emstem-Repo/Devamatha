package com.kp.cms.to.exam;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ExamSubjectRuleSettingsAssignmentTO implements Serializable,Comparable<ExamSubjectRuleSettingsAssignmentTO>
{
	private String id;
	private int assignmentTypeId;
	private String name;
	private String minimumAssignMarks;
	private String maximumAssignMarks;
	private String isTheoryPractical;
	public ExamSubjectRuleSettingsAssignmentTO() {
		super();
	}
	public ExamSubjectRuleSettingsAssignmentTO(String id, String maximumMarks,
			String minimumMarks, String name,String isTheoryPractical,int assignmentTypeId) {
		super();
		this.id = id;
		this.minimumAssignMarks=minimumMarks;
		this.maximumAssignMarks=maximumMarks;
		this.name = name;
		this.isTheoryPractical = isTheoryPractical;
		this.assignmentTypeId=assignmentTypeId;
	}
	public ExamSubjectRuleSettingsAssignmentTO(int assignmentTypeId, String name) {
		super();
		this.assignmentTypeId = assignmentTypeId;
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setMinimumAssignMarks(String minimumAssignMarks) {
		this.minimumAssignMarks = minimumAssignMarks;
	}
	public String getMinimumAssignMarks() {
		return minimumAssignMarks;
	}
	public void setMaximumAssignMarks(String maximumAssignMarks) {
		this.maximumAssignMarks = maximumAssignMarks;
	}
	public String getMaximumAssignMarks() {
		return maximumAssignMarks;
	}
	public void setIsTheoryPractical(String isTheoryPractical) {
		this.isTheoryPractical = isTheoryPractical;
	}
	public String getIsTheoryPractical() {
		return isTheoryPractical;
	}
	public void setAssignmentTypeId(int assignmentTypeId) {
		this.assignmentTypeId = assignmentTypeId;
	}
	public int getAssignmentTypeId() {
		return assignmentTypeId;
	}
	@Override
	public int compareTo(ExamSubjectRuleSettingsAssignmentTO arg0) {
		if(arg0!=null && this!=null && arg0.getName()!=null
				 && this.getName()!=null){
			return this.getName().compareTo(arg0.getName());
		}		
		else
		return 0;
	}
	
	
	

}
