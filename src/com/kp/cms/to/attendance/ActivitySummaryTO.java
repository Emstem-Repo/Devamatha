package com.kp.cms.to.attendance;

public class ActivitySummaryTO {

	private int activityId;
	private String activityName;
	private String activityHeld;
	private String activityAttended;
	private String activityPercentage;
	private String activityWithLeaveAttended;
	public int getActivityId() {
		return activityId;
	}
	public void setActivityId(int activityId) {
		this.activityId = activityId;
	}
	public String getActivityName() {
		return activityName;
	}
	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}
	public String getActivityHeld() {
		return activityHeld;
	}
	public void setActivityHeld(String activityHeld) {
		this.activityHeld = activityHeld;
	}
	public String getActivityAttended() {
		return activityAttended;
	}
	public void setActivityAttended(String activityAttended) {
		this.activityAttended = activityAttended;
	}
	public String getActivityPercentage() {
		return activityPercentage;
	}
	public void setActivityPercentage(String activityPercentage) {
		this.activityPercentage = activityPercentage;
	}
	public String getActivityWithLeaveAttended() {
		return activityWithLeaveAttended;
	}
	public void setActivityWithLeaveAttended(String activityWithLeaveAttended) {
		this.activityWithLeaveAttended = activityWithLeaveAttended;
	}

	
}
