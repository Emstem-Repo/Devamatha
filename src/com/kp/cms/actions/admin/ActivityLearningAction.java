package com.kp.cms.actions.admin;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.bo.admin.ActivityLearning;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.admin.ActivityLearningForm;
import com.kp.cms.handlers.admin.ActivityLearningHandler;
import com.kp.cms.handlers.admin.ExtraCreditActivityTypeHandler;
import com.kp.cms.to.admin.ActivityLearningTO;
import com.kp.cms.to.admin.ExtraCreditActivityTypeTo;



public class ActivityLearningAction extends BaseDispatchAction{
	private static final Logger log=Logger.getLogger(ActivityLearningAction.class);

	
	
	public ActionForward initActivityLearningMethod(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response) throws Exception{
		log.info(" call of init method of activityLearningAction class.");
		ActivityLearningForm activityLearningForm = (ActivityLearningForm)form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = new ActionErrors();
		errors= activityLearningForm.validate(mapping, request);
		if(errors.isEmpty()){
			getActivityLearningList(activityLearningForm);
		}else{
			errors.add(messages);
			saveErrors(request, errors);
		}
		return mapping.findForward(CMSConstants.ACTIVITY_LEARNING);
	}

	public ActionForward afterSubmitMethod(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	 										throws Exception{
		ActivityLearningForm activityLearningForm = (ActivityLearningForm)form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors =activityLearningForm.validate(mapping, request);
		setUserId(request, activityLearningForm);
		try{
			validateData(request,errors,activityLearningForm);
			
			if(errors !=null && errors.isEmpty()){
				boolean isDuplicate = ActivityLearningHandler.getInstance().checkDuplicate(activityLearningForm);
				if(!isDuplicate){
					boolean isAdded = ActivityLearningHandler.getInstance().addDetails(activityLearningForm);
					if(isAdded){
						ActionMessage message = new ActionError("knowledgepro.admin.activityLearning.success");
						messages.add("messages", message);
						saveMessages(request, messages);
						//activityLearningForm.reset(mapping, request);
						getActivityLearningList(activityLearningForm);
					}else{
						activityLearningForm.reset(mapping, request);
						errors.add("error", new ActionError("knowledgepro.admin.activityLearning.failure"));
						saveErrors(request, errors);
					}
				}else{
					if(activityLearningForm.getErrorMessage() != null && !activityLearningForm.getErrorMessage().isEmpty()){
						errors .add( "error", new ActionError("knowledgepro.admin.activityLearning.already.exists.course",activityLearningForm.getErrorMessage()));
						saveErrors(request, errors);
					}else{
						errors .add( "error", new ActionError("knowledgepro.admin.activityLearning.already.exists"));
						saveErrors(request, errors);
					}
				}
			}else{
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.ACTIVITY_LEARNING);
			}
		}catch (Exception exception) {
			if(exception instanceof ReActivateException){
				errors.add("eror", new ActionError("knowledgepro.admin.activityLearning.reactive"));
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.ACTIVITY_LEARNING);
			}
			String msg = super.handleApplicationException(exception);
			activityLearningForm.setErrorMessage(msg);
			activityLearningForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
				
		}
		return mapping.findForward(CMSConstants.ACTIVITY_LEARNING);
	}

	private void validateData(HttpServletRequest request, ActionErrors errors,
			ActivityLearningForm activityLearningForm) {
		
		if(activityLearningForm.getCourseIds() == null || activityLearningForm.getCourseIds().length==0){
			errors .add( "errors", new ActionError( "knowledgepro.admin.activityLearning.course.name"));
		}
		if(activityLearningForm.getMaxmark() == null || activityLearningForm.getMaxmark().isEmpty()){
			errors.add("errors",new ActionError("knowledgepro.admin.activityLearning.max.mark") );
		}
		if(activityLearningForm.getMinmark() == null || activityLearningForm.getMinmark().isEmpty()){
			errors.add("errors",new ActionError("knowledgepro.admin.activityLearning.min.mark") );
		}
		if(activityLearningForm.getExtraCreditActivityType()==null || activityLearningForm.getExtraCreditActivityType().isEmpty()){
			errors.add("errors", new ActionError("knowledgepro.admin.activityLearning.activity.name.required"));
		}
		
 	}

	private void getActivityLearningList(ActivityLearningForm activityLearningForm)throws Exception {
		log.info("call of getFeeHeadingDetails method in FeeHeadingsAction class.");
		Map<Integer, String> courseMap = ActivityLearningHandler.getInstance().getCourseList();
		activityLearningForm.setCourseMap(courseMap);
		List<ExtraCreditActivityTypeTo> activityLearningToList = ActivityLearningHandler.getInstance().getactivityLearning();
		activityLearningForm.setList(activityLearningToList);
		List<ActivityLearningTO> learningTOs=ActivityLearningHandler.getInstance().getCourseAndActivityList();
		activityLearningForm.setActivityLearningToList(learningTOs);
		
	}
	
	public ActionForward editDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ActivityLearningForm activityLearningForm = (ActivityLearningForm)form;
		setUserId(request, activityLearningForm);
		try{
			ActivityLearningHandler.getInstance().setEditDetailsToForm(activityLearningForm);
			request.setAttribute("learningOperation","edit");
		}catch (Exception e) {
			String msg = super.handleApplicationException(e);
			activityLearningForm.setErrorMessage(msg);
			activityLearningForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.ACTIVITY_LEARNING);
	}
	
	
	
	public ActionForward updateDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ActivityLearningForm activityLearningForm = (ActivityLearningForm)form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = activityLearningForm.validate(mapping, request);
		boolean isActivityEdited=false;
		if(errors.isEmpty()){
			try{
				setUserId(request, activityLearningForm);
				isActivityEdited=ActivityLearningHandler.getInstance().updateActivity(activityLearningForm,request);
			}catch (Exception e) {
				if(e instanceof DuplicateException){
					errors.add("error", new ActionError("knowledgepro.admin.Activity.name.exists",activityLearningForm.getActivityLearningId()));
					saveErrors(request, errors);
					List<ActivityLearningTO> learningTOs=ActivityLearningHandler.getInstance().getCourseAndActivityList();
					activityLearningForm.setActivityLearningToList(learningTOs);
					request.setAttribute("operation", CMSConstants.EDIT_OPERATION);
					return mapping.findForward(CMSConstants.ACTIVITY_LEARNING);
				}
				if(e instanceof ReActivateException){
					errors.add("error", new ActionError("knowledgepro.admin.activityLearning.reactive"));
					saveErrors(request, errors);
					List<ActivityLearningTO> learningTOs=ActivityLearningHandler.getInstance().getCourseAndActivityList();
					activityLearningForm.setActivityLearningToList(learningTOs);
					request.setAttribute("operation", CMSConstants.EDIT_OPERATION);
					return mapping.findForward(CMSConstants.ACTIVITY_LEARNING);
				}
				String msg = super.handleApplicationException(e);
				activityLearningForm.setErrorMessage(msg);
				activityLearningForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
		}	
		
		}else {
			saveErrors(request, errors);
			request.setAttribute("operation", "edit");
			List<ActivityLearningTO> learningTOs=ActivityLearningHandler.getInstance().getCourseAndActivityList();
			activityLearningForm.setActivityLearningToList(learningTOs);
			return mapping.findForward(CMSConstants.ACTIVITY_LEARNING);
		}
		if (isActivityEdited) {
			ActionMessage message = new ActionMessage("knowledgepro.admin.activityLearning.updatesuccess");
			messages.add("messages", message);
			saveMessages(request, messages);
			activityLearningForm.reset(mapping, request);
			List<ActivityLearningTO> learningTOs=ActivityLearningHandler.getInstance().getCourseAndActivityList();
			activityLearningForm.setActivityLearningToList(learningTOs);
		}else{
			// failed
			errors.add("error", new ActionError("knowledgepro.admin.activityLearning.updatefailure"));
			saveErrors(request, errors);
			List<ActivityLearningTO> learningTOs=ActivityLearningHandler.getInstance().getCourseAndActivityList();
			activityLearningForm.setActivityLearningToList(learningTOs);
		}
		return mapping.findForward(CMSConstants.ACTIVITY_LEARNING);
	}
	
	
	public ActionForward deleteDetails(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
										throws Exception{
		ActivityLearningForm activityLearningForm = (ActivityLearningForm)form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = activityLearningForm.validate(mapping, request);
		boolean isActivityDeleted=false;
		try{
			setUserId(request, activityLearningForm);
			isActivityDeleted = ActivityLearningHandler.getInstance().deleteActivity(activityLearningForm);
		}catch (Exception e) {
			log.error("Error occured in caste Entry Action", e);
			String msg = super.handleApplicationException(e);
			activityLearningForm.setErrorMessage(msg);
			activityLearningForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		if(isActivityDeleted){
			ActionMessage message = new ActionMessage("knowledgepro.admin.activityLearning.deletesuccess");
			messages.add("messages", message);
			saveMessages(request, messages);
			activityLearningForm.reset(mapping, request);
			List<ActivityLearningTO> learningTOs=ActivityLearningHandler.getInstance().getCourseAndActivityList();
			activityLearningForm.setActivityLearningToList(learningTOs);
		}else{
			errors.add("error", new ActionError("knowledgepro.admin.activityLearning.deletefailure"));
			saveErrors(request, errors);
			List<ActivityLearningTO> learningTOs=ActivityLearningHandler.getInstance().getCourseAndActivityList();
			activityLearningForm.setActivityLearningToList(learningTOs);
		}
		
		return mapping.findForward(CMSConstants.ACTIVITY_LEARNING);
	}
	
	
	public ActionForward reActivateActivity(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
											throws Exception{
		
		ActivityLearningForm activityLearningForm = (ActivityLearningForm)form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = activityLearningForm.validate(mapping, request);
		ActivityLearning type=(ActivityLearning)request.getSession().getAttribute("Activity");
		boolean isActivityReActivate=false;
		try{
			setUserId(request, activityLearningForm);
			int reactId=activityLearningForm.getReActiveId();
			isActivityReActivate=ActivityLearningHandler.getInstance().reActiveLearning(reactId,true,activityLearningForm);
		}catch (Exception e) {
			log.error("Error occured in caste Entry Action", e);
			String msg = super.handleApplicationException(e);
			activityLearningForm.setErrorMessage(msg);
			activityLearningForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		if(isActivityReActivate){
			ActionMessage message = new ActionMessage("knowledgepro.admin.Activity.activate");
			messages.add("messages", message);
			saveMessages(request, messages);
			activityLearningForm.reset(mapping, request);
			List<ActivityLearningTO> learningTOs=ActivityLearningHandler.getInstance().getCourseAndActivityList();
			activityLearningForm.setActivityLearningToList(learningTOs);
		}else{
			errors.add("error", new ActionError("knowledgepro.admin.Activity.activatefailure"));
			saveErrors(request, errors);
			List<ActivityLearningTO> learningTOs=ActivityLearningHandler.getInstance().getCourseAndActivityList();
			activityLearningForm.setActivityLearningToList(learningTOs);
		}	
		return mapping.findForward(CMSConstants.ACTIVITY_LEARNING);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
