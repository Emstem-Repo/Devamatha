package com.kp.cms.handlers.admin;

import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.runner.Request;

import com.kp.cms.bo.admin.ActivityLearning;
import com.kp.cms.bo.admin.Course;
import com.kp.cms.bo.admin.ExtraCreditActivityType;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.admin.ActivityLearningForm;
import com.kp.cms.helpers.admin.ActivityLearningHelper;
import com.kp.cms.helpers.admin.ExtraCreditActivityTypeHelper;
import com.kp.cms.to.admin.ActivityLearningTO;
import com.kp.cms.to.admin.ExtraCreditActivityTypeTo;
import com.kp.cms.transactions.admin.IActivityLearningTransaction;
import com.kp.cms.transactions.admin.IExtraCreditActivityTypeTransaction;
import com.kp.cms.transactionsimpl.admin.ActivityLearningImpl;
import com.kp.cms.transactionsimpl.admin.ExtraCreditActivityTypeImpl;
import com.kp.cms.transactionsimpl.attendance.ActivityTransactionImpl;


public class ActivityLearningHandler {
	
	public static ActivityLearningHandler activityLearningHandler=null;
	
	private static final Log log = LogFactory.getLog(ActivityLearningHandler.class);
	
	public static ActivityLearningHandler getInstance(){
		if(activityLearningHandler==null){
			activityLearningHandler = new ActivityLearningHandler();
			return activityLearningHandler;
		}
		return activityLearningHandler;
	}

	IActivityLearningTransaction transaction = ActivityLearningImpl.getInstance();
	
	public List<ExtraCreditActivityTypeTo> getactivityLearning() throws Exception {
		
		log.info("call of getactivityLearning method in ActivityHandler class.");
		IActivityLearningTransaction transaction = ActivityLearningImpl.getInstance();
		List<ExtraCreditActivityTypeTo> list = ActivityLearningHelper.convertBotoTos(transaction.getActivities());
		
		return list;
	}

	public Map<Integer, String> getCourseList()throws Exception {
		return transaction.getCourseMap();
	}

	public boolean checkDuplicate(ActivityLearningForm activityLearningForm)throws Exception {

		
		return transaction.checkDuplicate(activityLearningForm);
	}

	public boolean addDetails(ActivityLearningForm activityLearningForm)throws Exception {
		
		ActivityLearning activityLearning = new ActivityLearning();
		ActivityLearning activityLearning1 = new ActivityLearning();
		ExtraCreditActivityType extraCreditActivityType = new ExtraCreditActivityType();
		Set<ActivityLearning> courses = new HashSet<ActivityLearning>();
		List<ActivityLearning> list=null;
		for (int i = 0; i < activityLearningForm.getCourseIds().length; i++) {
			if(activityLearningForm.getCourseIds()[i] != null && !activityLearningForm.getCourseIds()[i].trim().isEmpty()){
				Course course = new Course();
				course.setId(Integer.parseInt(activityLearningForm.getCourseIds()[i]));
				activityLearning.setCourse(course);
				extraCreditActivityType.setId(Integer.parseInt(activityLearningForm.getExtraCreditActivityType()));
				activityLearning.setAppliedYear(Integer.parseInt(activityLearningForm.getYear()));
				activityLearning.setMinMark(activityLearningForm.getMinmark());
				activityLearning.setMaxMark(activityLearningForm.getMaxmark());
				activityLearning.setExtraCreditActivityType(extraCreditActivityType);
				activityLearning.setCreatedBy(activityLearningForm.getUserId());
				activityLearning.setCreatedDate(new Date());
				activityLearning.setIsActive(true);
				activityLearning.setCreditInfo(activityLearningForm.getCreditInfo());
				courses.add(activityLearning);
				activityLearning1 = transaction.isDupEntry(activityLearning);
				if(activityLearning1!=null){
					activityLearningForm.setReActiveId(activityLearning1.getId());
					throw new ReActivateException();
				}
				list= transaction.addDetails(activityLearning);
		    }
		}
		if(list!=null){
			return false;
		}
		return true;
	}

	public List<ActivityLearningTO> getCourseAndActivityList()throws Exception {
		List<ActivityLearningTO> list1 = ActivityLearningHelper.convertBotoTosCourse(transaction.getCourseAndActivityList());
		return list1;
	}

	public void setEditDetailsToForm(ActivityLearningForm activityLearningForm)throws Exception {
		ActivityLearning activityLearning = transaction.getList(activityLearningForm);
		if(activityLearning!=null){
			activityLearningForm.setMinmark(activityLearning.getMinMark());
			activityLearningForm.setMaxmark(activityLearning.getMaxMark());
			activityLearningForm.setYear(String.valueOf(activityLearning.getAppliedYear()));
			activityLearningForm.setExtraCreditActivityType(String.valueOf(activityLearning.getExtraCreditActivityType().getId()));
			activityLearningForm.setCreditInfo(activityLearning.getCreditInfo());
			if(activityLearning.getCourse() != null){
				activityLearningForm.setCourseId(String.valueOf(activityLearning.getCourse().getId()));
			}
		}
		
		
		
		
	}

	public boolean updateActivity(ActivityLearningForm activityLearningForm,HttpServletRequest request)
				throws Exception{
	
		IActivityLearningTransaction transaction = new  ActivityLearningImpl();
		ActivityLearning learning = ActivityLearningHelper.converttoTOname(activityLearningForm,"Update");
		ActivityLearning learning1 = ActivityLearningHelper.converttoTOname(activityLearningForm,"Update");
		learning1= transaction.isActivityDuplicated(learning1);
		boolean isActivityEdit=false;
		
		if(learning1!=null && learning1.getIsActive()){
			throw new DuplicateException();
		}
		if(learning1 !=null && !learning1.getIsActive()){
			request.getSession().setAttribute("Activity",learning1);
			activityLearningForm.setReActiveId(learning1.getId());
			throw new ReActivateException();
		}else if(transaction!=null){
			isActivityEdit=transaction.updateActivity(learning);
		}
		
		return isActivityEdit;
	
	
	}

	public boolean deleteActivity(ActivityLearningForm activityLearningForm)throws Exception {
		boolean isActivityDelete  = false;
		if(transaction!=null){
			isActivityDelete=transaction.deleteActivity(activityLearningForm);
		}
		
		return isActivityDelete;
	}

	

	public boolean reActiveLearning(int reactId, boolean activate,
			ActivityLearningForm activityLearningForm)throws Exception {
		boolean deleted = transaction.reactive(reactId,activate,activityLearningForm);

		return deleted;
	}
	
	

}
