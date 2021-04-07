package com.kp.cms.to.phd;

import java.io.Serializable;

public class PhdSettingTO implements Serializable{
	/**
	 * Dec 23, 2009
	 * Created By 9Elements Team
	 */
	
	private int id;
	private String reminderMailBefore;
	private String dueMailAfter;
	private int maxGuidesAssign;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getReminderMailBefore() {
		return reminderMailBefore;
	}
	public void setReminderMailBefore(String reminderMailBefore) {
		this.reminderMailBefore = reminderMailBefore;
	}
	public String getDueMailAfter() {
		return dueMailAfter;
	}
	public void setDueMailAfter(String dueMailAfter) {
		this.dueMailAfter = dueMailAfter;
	}
	public int getMaxGuidesAssign() {
		return maxGuidesAssign;
	}
	public void setMaxGuidesAssign(int maxGuidesAssign) {
		this.maxGuidesAssign = maxGuidesAssign;
	}
	

}
