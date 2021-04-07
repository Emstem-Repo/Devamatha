package com.kp.cms.helpers.admin;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.kp.cms.bo.admin.ActivityLearning;
import com.kp.cms.bo.admin.Course;
import com.kp.cms.bo.admin.ExtraCreditActivityType;
import com.kp.cms.forms.admin.ActivityLearningForm;
import com.kp.cms.to.admin.ActivityLearningTO;
import com.kp.cms.to.admin.ExtraCreditActivityTypeTo;

public class ActivityLearningHelper {

	public static List<ExtraCreditActivityTypeTo> convertBotoTos(
			List<ExtraCreditActivityType> activities)throws Exception {
		List<ExtraCreditActivityTypeTo> activityLearnings = new ArrayList<ExtraCreditActivityTypeTo>();
		if(activities!=null){
			Iterator<ExtraCreditActivityType> iterator = activities.listIterator();
			while(iterator.hasNext()){
				ExtraCreditActivityType learning = (ExtraCreditActivityType)iterator.next();
				ExtraCreditActivityTypeTo learningTO = new ExtraCreditActivityTypeTo();
				learningTO.setActivityName(learning.getName());
				learningTO.setActivityTypeId(learning.getId());
				activityLearnings.add(learningTO);
			}
		}
		
		return activityLearnings;
	}

	public static List<ActivityLearningTO> convertBotoTosCourse(
			List<ActivityLearning> courseAndActivityList) {
		List<ActivityLearningTO> activityLearningTOs = new ArrayList<ActivityLearningTO>();
		if(courseAndActivityList!=null){
			Iterator<ActivityLearning> iterator = courseAndActivityList.listIterator();
			while(iterator.hasNext()){
				ActivityLearning activityLearning =(ActivityLearning)iterator.next();
				ActivityLearningTO to = new ActivityLearningTO();
				to.setAppliedYear(activityLearning.getAppliedYear());
				to.setActivityId(activityLearning.getId());
				to.setActivityName(activityLearning.getExtraCreditActivityType().getName());
				to.setCourseNames(activityLearning.getCourse().getName());
				to.setMinMark(activityLearning.getMinMark());
				to.setMaxMark(activityLearning.getMaxMark());
				activityLearningTOs.add(to);
			}
		}
		return activityLearningTOs;
	}

	public static ActivityLearning converttoTOname(
			ActivityLearningForm activityLearningForm, String mode) {
		ActivityLearning activityLearning = new ActivityLearning();
		ExtraCreditActivityType type = new ExtraCreditActivityType();
		Course course = new Course();
		type.setId(Integer.parseInt(activityLearningForm.getExtraCreditActivityType()));
		activityLearning.setExtraCreditActivityType(type);
		activityLearning.setId(activityLearningForm.getActivityLearningId());
		activityLearning.setAppliedYear(Integer.parseInt(activityLearningForm.getYear()));
		activityLearning.setMinMark(activityLearningForm.getMinmark());
		activityLearning.setMaxMark(activityLearningForm.getMaxmark());
		activityLearning.setIsActive(new Boolean(true));
		activityLearning.setCreditInfo(activityLearningForm.getCreditInfo());
		course.setId(Integer.parseInt(activityLearningForm.getCourseId()));
		activityLearning.setCourse(course);
		if(mode.equals("Update")){
			activityLearning.setModifiedBy(activityLearningForm.getUserId());
			activityLearning.setLastModifiedDate(new Date());
		}
		return activityLearning;
	}
}
