package com.kp.cms.actions.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.taglib.tiles.GetAttributeTag;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.bo.admin.Caste;
import com.kp.cms.bo.admin.ExtraCreditActivityType;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.admin.CasteForm;
import com.kp.cms.forms.admin.ExtraCreditActivityTypeForm;
import com.kp.cms.handlers.admin.CasteHandler;
import com.kp.cms.handlers.admin.ExtraCreditActivityTypeHandler;
import com.kp.cms.to.admin.CasteTO;
import com.kp.cms.to.admin.ExtraCreditActivityTypeTo;

public class ExtraCreditActivityTypeAction extends BaseDispatchAction {
	private static final Log log = LogFactory.getLog(ExtraCreditActivityTypeAction.class); 
	
	
	public ActionForward initGetActivityName(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response)throws Exception{
		ExtraCreditActivityTypeForm extraCreditActivityTypeForm = (ExtraCreditActivityTypeForm)form;
		HttpSession session = request.getSession();
		session.setAttribute("field", "ActivityType");
		try{
			
			List<ExtraCreditActivityTypeTo> activityList = ExtraCreditActivityTypeHandler.getInstance().getActivityTypeList();
			extraCreditActivityTypeForm.setActivityTypeList(activityList);			
		}catch(Exception e){
			log.error("Error occured in caste Entry Action", e);
			String msg = super.handleApplicationException(e);
			extraCreditActivityTypeForm.setErrorMessage(msg);
			extraCreditActivityTypeForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.ACTIVITY_TYPE_ENTRY);
	}
	
	
	public ActionForward submitMethod(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	         throws Exception{
		ExtraCreditActivityTypeForm extraCreditActivityTypeForm = (ExtraCreditActivityTypeForm)form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = extraCreditActivityTypeForm.validate(mapping, request);
		boolean isActivity=false;
		if(errors.isEmpty()){
			String activityName=extraCreditActivityTypeForm.getActivityName();
			try{
				setUserId(request, extraCreditActivityTypeForm);
				isActivity=ExtraCreditActivityTypeHandler.getInstance().getAddActivityName(extraCreditActivityTypeForm,request);
			}catch (Exception e) {
				if(e instanceof DuplicateException){
					errors.add("error", new ActionError("knowledgepro.admin.Activity.name.exists",activityName));
					saveErrors(request, errors);
					
					List<ExtraCreditActivityTypeTo> activityList = ExtraCreditActivityTypeHandler.getInstance().getActivityTypeList();
					extraCreditActivityTypeForm.setActivityTypeList(activityList);
					return mapping.findForward(CMSConstants.ACTIVITY_TYPE_ENTRY);	
				}
				if(e instanceof ReActivateException){
					errors.add("error", new ActionError("knowledgepro.admin.Activity.addfailure.alreadyexist.reactivate",activityName));
					saveErrors(request, errors);
					
					List<ExtraCreditActivityTypeTo> activityList = ExtraCreditActivityTypeHandler.getInstance().getActivityTypeList();
					extraCreditActivityTypeForm.setActivityTypeList(activityList);
					return mapping.findForward(CMSConstants.ACTIVITY_TYPE_ENTRY);		
				}
				log.error("Error occured in caste Entry Action", e);
				String msg = super.handleApplicationException(e);
				extraCreditActivityTypeForm.setErrorMessage(msg);
				extraCreditActivityTypeForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
				
			}
			if (isActivity) {
				ActionMessage message = new ActionMessage("knowledgepro.admin.Activity.addsuccess", activityName);
				messages.add("messages", message);
				saveMessages(request, messages);
				extraCreditActivityTypeForm.reset(mapping, request);
				
				List<ExtraCreditActivityTypeTo> activityList = ExtraCreditActivityTypeHandler.getInstance().getActivityTypeList();
				extraCreditActivityTypeForm.setActivityTypeList(activityList);
				extraCreditActivityTypeForm.reset(mapping, request);
			}else{
				// failed
				errors.add("error", new ActionError("knowledgepro.admin.Activity.addfailure",  activityName));
				saveErrors(request, errors);
				
				List<ExtraCreditActivityTypeTo> activityList = ExtraCreditActivityTypeHandler.getInstance().getActivityTypeList();
				extraCreditActivityTypeForm.setActivityTypeList(activityList);
			}
		} else {
			saveErrors(request, errors);
			List<ExtraCreditActivityTypeTo> activityList = ExtraCreditActivityTypeHandler.getInstance().getActivityTypeList();
			extraCreditActivityTypeForm.setActivityTypeList(activityList);
			return mapping.findForward(CMSConstants.ACTIVITY_TYPE_ENTRY);	
		}
		
		
		return mapping.findForward(CMSConstants.ACTIVITY_TYPE_ENTRY);
		}
	
	public ActionForward updateActivityName(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ExtraCreditActivityTypeForm extraCreditActivityTypeForm = (ExtraCreditActivityTypeForm)form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = extraCreditActivityTypeForm.validate(mapping, request);
		String name=extraCreditActivityTypeForm.getActivityName();
		boolean isActivityEdited = false;
		if (errors.isEmpty()) {
			try{
			setUserId(request, extraCreditActivityTypeForm);
			
			isActivityEdited=ExtraCreditActivityTypeHandler.getInstance().updateActivate(extraCreditActivityTypeForm,request);

			}catch (Exception e) {
				if(e instanceof DuplicateException){
					errors.add("error", new ActionError("knowledgepro.admin.Activity.name.exists",name));
					saveErrors(request, errors);
					List<ExtraCreditActivityTypeTo> activityList = ExtraCreditActivityTypeHandler.getInstance().getActivityTypeList();
					extraCreditActivityTypeForm.setActivityTypeList(activityList);
					request.setAttribute("operation", CMSConstants.EDIT_OPERATION);
					return mapping.findForward(CMSConstants.ACTIVITY_TYPE_ENTRY);
				}
				if(e instanceof ReActivateException){
					errors.add("error", new ActionError("knowledgepro.admin.Activity.addfailure.alreadyexist.reactivate",name));
					saveErrors(request, errors);
					List<ExtraCreditActivityTypeTo> activityList = ExtraCreditActivityTypeHandler.getInstance().getActivityTypeList();
					extraCreditActivityTypeForm.setActivityTypeList(activityList);
					request.setAttribute("operation", CMSConstants.EDIT_OPERATION);
					return mapping.findForward(CMSConstants.ACTIVITY_TYPE_ENTRY);
				}
				log.error("Error occured in caste Entry Action", e);
				String msg = super.handleApplicationException(e);
				extraCreditActivityTypeForm.setErrorMessage(msg);
				extraCreditActivityTypeForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
				
			}
		} else {
			saveErrors(request, errors);
			request.setAttribute("operation", "edit");
			List<ExtraCreditActivityTypeTo> activityList = ExtraCreditActivityTypeHandler.getInstance().getActivityTypeList();
			extraCreditActivityTypeForm.setActivityTypeList(activityList);
			return mapping.findForward(CMSConstants.ACTIVITY_TYPE_ENTRY);
		}
		
		
		if (isActivityEdited) {
			ActionMessage message = new ActionMessage("knowledgepro.admin.Activity.updatesuccess", extraCreditActivityTypeForm.getActivityName());
			messages.add("messages", message);
			saveMessages(request, messages);
			extraCreditActivityTypeForm.reset(mapping, request);
			List<ExtraCreditActivityTypeTo> activityList = ExtraCreditActivityTypeHandler.getInstance().getActivityTypeList();
			extraCreditActivityTypeForm.setActivityTypeList(activityList);
		}else{
			// failed
			errors.add("error", new ActionError("knowledgepro.admin.Activity.updatefailure",  extraCreditActivityTypeForm.getActivityName()));
			saveErrors(request, errors);
			List<ExtraCreditActivityTypeTo> activityList = ExtraCreditActivityTypeHandler.getInstance().getActivityTypeList();
			extraCreditActivityTypeForm.setActivityTypeList(activityList);
		}
		return mapping.findForward(CMSConstants.ACTIVITY_TYPE_ENTRY);
	}
	public ActionForward deleteActivity(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ExtraCreditActivityTypeForm extraCreditActivityTypeForm = (ExtraCreditActivityTypeForm)form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		int activeId =extraCreditActivityTypeForm.getActivityTypeId();
		String name=extraCreditActivityTypeForm.getActivityName();
		boolean isActivityDeleted=false;
		try{
			setUserId(request, extraCreditActivityTypeForm);
			isActivityDeleted = ExtraCreditActivityTypeHandler.getInstance().deleteActivity(activeId,extraCreditActivityTypeForm.getUserId());
		}catch (Exception e) {
			log.error("Error occured in caste Entry Action", e);
			String msg = super.handleApplicationException(e);
			extraCreditActivityTypeForm.setErrorMessage(msg);
			extraCreditActivityTypeForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
			
		}
		
		if (isActivityDeleted) {
			ActionMessage message = new ActionMessage("knowledgepro.admin.Activity.deletesuccess", extraCreditActivityTypeForm.getActivityName());
			messages.add("messages", message);
			saveMessages(request, messages);
			extraCreditActivityTypeForm.reset(mapping, request);
			List<ExtraCreditActivityTypeTo> activityList = ExtraCreditActivityTypeHandler.getInstance().getActivityTypeList();
			extraCreditActivityTypeForm.setActivityTypeList(activityList);
		}else{
			errors.add("error", new ActionError("knowledgepro.admin.Activity.deletefailure",extraCreditActivityTypeForm.getActivityName()));
			saveErrors(request, errors);
			List<ExtraCreditActivityTypeTo> activityList = ExtraCreditActivityTypeHandler.getInstance().getActivityTypeList();
			extraCreditActivityTypeForm.setActivityTypeList(activityList);
		}
		return mapping.findForward(CMSConstants.ACTIVITY_TYPE_ENTRY);
	}
	public ActionForward reActivateActivity(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ExtraCreditActivityTypeForm extraCreditActivityTypeForm = (ExtraCreditActivityTypeForm)form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		ExtraCreditActivityType type=(ExtraCreditActivityType)request.getSession().getAttribute("Activity");
		boolean isActivityReActivate=false;
		try{
			setUserId(request, extraCreditActivityTypeForm);
			isActivityReActivate = ExtraCreditActivityTypeHandler.getInstance().reActiveActivity(type,extraCreditActivityTypeForm.getUserId());
		}catch (Exception e) {
			log.error("Error occured in caste Entry Action", e);
			String msg = super.handleApplicationException(e);
			extraCreditActivityTypeForm.setErrorMessage(msg);
			extraCreditActivityTypeForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
			
		}
		
		if (isActivityReActivate) {
			ActionMessage message = new ActionMessage("knowledgepro.admin.Activity.activate");
			messages.add("messages", message);
			saveMessages(request, messages);
			extraCreditActivityTypeForm.reset(mapping, request);
			List<ExtraCreditActivityTypeTo> activityList = ExtraCreditActivityTypeHandler.getInstance().getActivityTypeList();
			extraCreditActivityTypeForm.setActivityTypeList(activityList);
		}else{
			errors.add("error", new ActionError("knowledgepro.admin.Activity.activatefailure"));
			saveErrors(request, errors);
			
			List<ExtraCreditActivityTypeTo> activityList = ExtraCreditActivityTypeHandler.getInstance().getActivityTypeList();
			extraCreditActivityTypeForm.setActivityTypeList(activityList);
		}
		request.getSession().removeAttribute("Caste");
		return mapping.findForward(CMSConstants.ACTIVITY_TYPE_ENTRY);
		
	}

}

