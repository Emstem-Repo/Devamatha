package com.kp.cms.to.admission;
/**
 * 
 * 
 * TO Class for EligibilityCriteria BO
 * 
 */
public class EligibilityCriteriaCheckTO {
	private int subjectId;
	private double percentage;
	
	public double getPercentage() {
		return percentage;
	}
	public void setPercentage(double percentage) {
		this.percentage = percentage;
	}
	public int getSubjectId() {
		return subjectId;
	}
	public void setSubjectId(int subjectId) {
		this.subjectId = subjectId;
	}
	
}
