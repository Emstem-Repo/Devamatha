package com.kp.cms.to.admin;

public class ActivityLearningTO {
	
	private String activityName;
	private int activityId;
	private ExtraCreditActivityTypeTo extraCreditActivityTypeTo;
	private int appliedYear;
	private String[] courseIds;
	private String courseNames;
	private String minMark;
	private String maxMark;
	

	
	public String getActivityName() {
		return activityName;
	}
	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}
	public int getActivityId() {
		return activityId;
	}
	public void setActivityId(int activityId) {
		this.activityId = activityId;
	}
	public ExtraCreditActivityTypeTo getExtraCreditActivityTypeTo() {
		return extraCreditActivityTypeTo;
	}
	public void setExtraCreditActivityTypeTo(
			ExtraCreditActivityTypeTo extraCreditActivityTypeTo) {
		this.extraCreditActivityTypeTo = extraCreditActivityTypeTo;
	}
	public int getAppliedYear() {
		return appliedYear;
	}
	public void setAppliedYear(int appliedYear) {
		this.appliedYear = appliedYear;
	}
	public String[] getCourseIds() {
		return courseIds;
	}
	public void setCourseIds(String[] courseIds) {
		this.courseIds = courseIds;
	}
	public String getCourseNames() {
		return courseNames;
	}
	public void setCourseNames(String courseNames) {
		this.courseNames = courseNames;
	}
	public String getMinMark() {
		return minMark;
	}
	public void setMinMark(String minMark) {
		this.minMark = minMark;
	}
	public String getMaxMark() {
		return maxMark;
	}
	public void setMaxMark(String maxMark) {
		this.maxMark = maxMark;
	}
	
	
	
	
	
	
	

}
