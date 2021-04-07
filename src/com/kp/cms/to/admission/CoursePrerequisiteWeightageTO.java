package com.kp.cms.to.admission;

import java.math.BigDecimal;

public class CoursePrerequisiteWeightageTO {
	
	private int coursePrerequisiteid;
	
	private String prerequisiteName;
	
	private int weightageid;
	
	private String weightagePercentage;	
	
	private BigDecimal weightageAdjustedMarks;
	
	private int candidatesPrerequisiteId;

	public String getPrerequisiteName() {
		return prerequisiteName;
	}

	public void setPrerequisiteName(String prerequisiteName) {
		this.prerequisiteName = prerequisiteName;
	}

	public int getWeightageid() {
		return weightageid;
	}

	public void setWeightageid(int weightageid) {
		this.weightageid = weightageid;
	}

	public String getWeightagePercentage() {
		return weightagePercentage;
	}

	public void setWeightagePercentage(String weightagePercentage) {
		this.weightagePercentage = weightagePercentage;
	}

	public int getCoursePrerequisiteid() {
		return coursePrerequisiteid;
	}

	public void setCoursePrerequisiteid(int coursePrerequisiteid) {
		this.coursePrerequisiteid = coursePrerequisiteid;
	}

	public BigDecimal getWeightageAdjustedMarks() {
		return weightageAdjustedMarks;
	}

	public void setWeightageAdjustedMarks(BigDecimal weightageAdjustedMarks) {
		this.weightageAdjustedMarks = weightageAdjustedMarks;
	}

	public int getCandidatesPrerequisiteId() {
		return candidatesPrerequisiteId;
	}

	public void setCandidatesPrerequisiteId(int candidatesPrerequisiteId) {
		this.candidatesPrerequisiteId = candidatesPrerequisiteId;
	}

}
