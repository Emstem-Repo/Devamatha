package com.kp.cms.transactions.admin;

import java.util.List;
import java.util.Map;

import com.kp.cms.bo.admin.ActivityLearning;
import com.kp.cms.bo.admin.ExtraCreditActivityType;
import com.kp.cms.forms.admin.ActivityLearningForm;

public interface IActivityLearningTransaction {
	

	List<ExtraCreditActivityType> getActivities()throws Exception;

	Map<Integer, String> getCourseMap() throws Exception;

	boolean checkDuplicate(ActivityLearningForm activityLearningForm)throws Exception;

	ActivityLearning isDupEntry(ActivityLearning activityLearning)throws Exception;

	

	List<ActivityLearning> getCourseAndActivityList()throws Exception;

	ActivityLearning getList(ActivityLearningForm activityLearningForm)throws Exception;

	boolean updateActivity(ActivityLearning activityLearning)throws Exception;

	public ActivityLearning isActivityDuplicated(ActivityLearning learning1);

	boolean deleteActivity(ActivityLearningForm activityLearningForm)throws Exception;

	boolean reactive(int reactId, boolean activate,
			ActivityLearningForm activityLearningForm)throws Exception;

	List<ActivityLearning> addDetails(ActivityLearning activityLearning)throws Exception;
	

}
