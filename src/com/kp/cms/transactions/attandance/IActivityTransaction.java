package com.kp.cms.transactions.attandance;

import java.util.List;

import com.kp.cms.bo.admin.Activity;

public interface IActivityTransaction {
	public Activity isActivityNameDuplcated(Activity duplActivity) throws Exception;
	public boolean addActivity(Activity activity) throws Exception;	
	public boolean updateActivity(Activity activity) throws Exception;
	public boolean deleteActivity(int actId, Boolean activate, String userId) throws Exception ;	
	public List<Activity> getActivities() throws Exception;	
	
}
