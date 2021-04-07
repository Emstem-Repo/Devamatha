package com.kp.cms.handlers.attendance;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.Activity;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.attendance.ActivityForm;
import com.kp.cms.helpers.attendance.ActivityHelper;
import com.kp.cms.to.attendance.ActivityTO;
import com.kp.cms.transactions.attandance.IActivityTransaction;
import com.kp.cms.transactionsimpl.attendance.ActivityTransactionImpl;


public class ActivityHandler {
	private static Log log = LogFactory.getLog(ActivityHandler.class);
	public static volatile ActivityHandler extraCurricularActivitiesHandler = null;

	public static ActivityHandler getInstance() {
		if (extraCurricularActivitiesHandler == null) {
			extraCurricularActivitiesHandler = new ActivityHandler();
			return extraCurricularActivitiesHandler;
		}
		return extraCurricularActivitiesHandler;
	}
	/**
	 * using for UI display
	 * @return activityToList
	 * @throws Exception
	 */
	public List<ActivityTO> getActivity() throws Exception {
		log.debug("inside getActivity");
		IActivityTransaction iActivityTransaction = ActivityTransactionImpl.getInstance();
		List<Activity> activityList = iActivityTransaction.getActivities();
		List<ActivityTO> activityToList = ActivityHelper.getInstance().copyActivityBosToTos(activityList); 
		log.debug("leaving getActivity");
		return activityToList;
	}
	
	/**
	 * add method
	 * @param activityForm
	 * @return
	 * @throws Exception
	 */
	public boolean addActivity(ActivityForm activityForm) throws Exception {
		log.debug("inside addActivity");
		IActivityTransaction iActivityTransaction = ActivityTransactionImpl.getInstance();
		boolean isAdded = false;

		Activity duplActivity = ActivityHelper.getInstance().populateActivityDataFromForm(activityForm); 

		duplActivity = iActivityTransaction.isActivityNameDuplcated(duplActivity);   //duplicate checking
		if (duplActivity  != null && duplActivity.getIsActive() == true) {
			throw new DuplicateException();
		} else if (duplActivity  != null && duplActivity .getIsActive() == false) {
			activityForm.setDuplActId(duplActivity.getId());
			throw new ReActivateException();
		}
		Activity activity = ActivityHelper.getInstance().populateActivityDataFromForm(activityForm); 
		
		activity.setCreatedBy(activityForm.getUserId());
		activity.setModifiedBy(activityForm.getUserId());
		activity.setCreatedDate(new Date());
		activity.setLastModifiedDate(new Date());
		isAdded = iActivityTransaction.addActivity(activity) ;
		log.debug("leaving addActivity");
		return isAdded;
	}
	
	/**
	 * update method
	 * @param activityForm
	 * @return
	 * @throws Exception
	 */
	public boolean updateActivity(ActivityForm activityForm) throws Exception {
		log.debug("inside updateActivity");
		IActivityTransaction iActivityTransaction = ActivityTransactionImpl.getInstance();
		boolean isUpdate = false;
		//original variables are used for update. checking duplicate when data changes in edit
		Boolean originalNotChanged = false;

		String actName = "";
		String origActName = "";

		actName = activityForm.getName().trim();
		origActName = activityForm.getOrigName().trim();

		int attTypeId = 0;
		if ((activityForm.getAttendanceTypeId() != null) || (!activityForm.getAttendanceTypeId().isEmpty())){
			attTypeId = Integer.parseInt(activityForm.getAttendanceTypeId());
		}
		int origAttTypeId = activityForm.getEditAttId(); 
		
		
		if (actName.trim().equalsIgnoreCase(origActName.trim()) && attTypeId == origAttTypeId) {
			originalNotChanged = true;
		}
		//duplication checking
		if (!originalNotChanged) {
			Activity duplActivity = ActivityHelper.getInstance().populateActivityDataFromForm(activityForm); 
			duplActivity = iActivityTransaction.isActivityNameDuplcated(duplActivity); 
			if (duplActivity != null && duplActivity.getIsActive() == true) {
				throw new DuplicateException();
			} else if (duplActivity != null && duplActivity.getIsActive() == false) {
				activityForm.setDuplActId(duplActivity.getId());
				throw new ReActivateException();
			}
		}
		Activity activity = ActivityHelper.getInstance().populateActivityDataFromForm(activityForm) ;

		activity.setModifiedBy(activityForm.getUserId());
		activity.setLastModifiedDate(new Date());
		isUpdate = iActivityTransaction.updateActivity(activity); 
		log.debug("leaving updateActivity");
		return isUpdate;
	}
	/**
	 * delete method
	 * @param id
	 * @param activate
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public boolean deleteActivity(int id, Boolean activate, String userId) throws Exception {
		log.debug("inside deleteActivity");
		IActivityTransaction iActivityTransaction = ActivityTransactionImpl.getInstance();
		boolean result = iActivityTransaction.deleteActivity(id, activate, userId) ;
		log.debug("leaving deleteActivity");
		return result;
	}
	
	
}
