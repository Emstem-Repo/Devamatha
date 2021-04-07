package com.kp.cms.helpers.attendance;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.Activity;
import com.kp.cms.bo.admin.AttendanceType;
import com.kp.cms.forms.attendance.ActivityForm;
import com.kp.cms.to.attendance.ActivityTO;
import com.kp.cms.to.attendance.AttendanceTypeTO;


public class ActivityHelper {
	private static final Log log = LogFactory.getLog(ActivityHelper.class);
	public static volatile ActivityHelper extraCurricularActivitiesHelper = null;
	   public static ActivityHelper getInstance() {
		      if(extraCurricularActivitiesHelper == null) {
		    	  extraCurricularActivitiesHelper = new ActivityHelper();
		    	  return extraCurricularActivitiesHelper;
		      }
		      return extraCurricularActivitiesHelper;
	   }
		/***
		 * 	Copying BO's to TO's
		 * @param activityList
		 * @return
		 */
		public List<ActivityTO> copyActivityBosToTos(List<Activity> activityList) {
			log.debug("inside copyActivityBosToTos");
			List<ActivityTO> activityTOList = new ArrayList<ActivityTO>();
			Iterator<Activity> i = activityList.iterator();
			Activity activity;
			ActivityTO activityTO;
			AttendanceTypeTO attendanceTypeTO;
			while (i.hasNext()) {
				activityTO = new ActivityTO();
				attendanceTypeTO = new AttendanceTypeTO();
				activity = (Activity) i.next();
				activityTO.setId(activity.getId());
				activityTO.setName(activity.getName());
				attendanceTypeTO.setId(activity.getAttendanceType().getId()!=0 ? activity.getAttendanceType().getId():null);
				attendanceTypeTO.setAttendanceTypeName(activity.getAttendanceType().getName()!=null ? activity.getAttendanceType().getName():null);
				activityTO.setAttendanceTypeTO(attendanceTypeTO);
				activityTOList.add(activityTO);
			}
			log.debug("leaving copyActivityBosToTos");
			return activityTOList;
		}
		/**
		 * creating activity BO from form
		 * @param activityForm
		 * @return
		 * @throws Exception
		 */
		public Activity populateActivityDataFromForm(ActivityForm activityForm) throws Exception{
			log.debug("inside populateActivityDataFromForm");
		    Activity activity = new Activity();
		    AttendanceType attendanceType = new AttendanceType();
		    if(activityForm.getId() != null && activityForm.getId().length() != 0) {
		    	activity.setId(Integer.parseInt(activityForm.getId()));
		      }
		    activity.setName(activityForm.getName().trim());
		    if(activityForm.getAttendanceTypeId() != null && activityForm.getAttendanceTypeId().length() != 0) {
		    	attendanceType.setId(Integer.parseInt(activityForm.getAttendanceTypeId()));
		     }
		     activity.setAttendanceType(attendanceType);
		     activity.setIsActive(true);
		     log.debug("leaving populateStatesDataFormForm");
		     return activity;
		}

		
}
