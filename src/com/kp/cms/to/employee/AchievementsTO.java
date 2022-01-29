package com.kp.cms.to.employee;

import java.io.Serializable;

@SuppressWarnings("serial")
public class AchievementsTO implements Serializable{
	private int id;
	private String achievements;
	private String details;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getAchievements() {
		return achievements;
	}
	public void setAchievements(String achievements) {
		this.achievements = achievements;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}

}
