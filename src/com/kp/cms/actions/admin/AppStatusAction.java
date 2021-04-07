package com.kp.cms.actions.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.admin.AppStatusForm;
import com.kp.cms.handlers.admin.AppStatusHandler;
import com.kp.cms.to.admin.AppStatusTO;

public class AppStatusAction extends BaseDispatchAction {
	private static final Log log = LogFactory.getLog(AppStatusAction.class);
 /**
 * @param mapping
 * @param form
 * @param request
 * @param response
 * @return
 * @throws Exception
 */
public ActionForward initAppStatus(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {
	AppStatusForm appStatusForm = (AppStatusForm)form;
	setUserId(request, appStatusForm);
	appStatusForm.reset(mapping, request);
	try{
		String formName = mapping.getName();
		request.getSession().setAttribute(CMSConstants.FORMNAME, formName);
		setRequestedDateToForm(appStatusForm,request);
	}catch (Exception e) {
		log.error("error in initAppStatus...", e);
		if (e instanceof ApplicationException) {
			String msg = super.handleApplicationException(e);
			appStatusForm.setErrorMessage(msg);
			appStatusForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} else {
			throw e;
		}
	}
	return mapping.findForward(CMSConstants.APP_STATUS_ENTRY);
}
/**
 * @param appStatusForm
 * @param request
 * @throws Exception 
 */
private void setRequestedDateToForm(AppStatusForm appStatusForm,
		HttpServletRequest request) throws Exception {
	log.debug("inside setRequestedDateToForm: Action");
	List<AppStatusTO> appStatusList = AppStatusHandler.getInstance().getApplicationStatus();
	//request.setAttribute("appStatusList", appStatusList);
	appStatusForm.setAppStatusList(appStatusList);
	
}
/**
 * @param mapping
 * @param form
 * @param request
 * @param response
 * @return
 * @throws Exception
 */
public ActionForward addAppStatusEntry(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {
	AppStatusForm appStatusForm = (AppStatusForm)form;
	setUserId(request, appStatusForm);
	ActionMessages messages = new ActionMessages();
	ActionErrors errors=appStatusForm.validate(mapping, request);
	boolean isAdded = false;
	try{
		if(!errors.isEmpty()){
			saveErrors(request, errors);
			setRequestedDateToForm(appStatusForm,request);
			if(appStatusForm.getApplicationStatus().trim().isEmpty()){
				appStatusForm.setApplicationStatus(null);
			}
			return mapping.findForward(CMSConstants.APP_STATUS_ENTRY);
		}
		isAdded=AppStatusHandler.getInstance().addAppStatusEntry(appStatusForm,"Add");
		setRequestedDateToForm(appStatusForm,request);
	}catch (DuplicateException e1) {
		errors.add("error", new ActionError("knowledgepro.admin.ApplicationStatus.name.exists"));
		saveErrors(request, errors);
		setRequestedDateToForm(appStatusForm,request);
		return mapping.findForward(CMSConstants.APP_STATUS_ENTRY);
	} catch (ReActivateException e1) {
		errors.add("error", new ActionError("knowledgepro.admin.ApplicationStatus.addfailure.alreadyexist.reactivate"));
		saveErrors(request, errors);
		setRequestedDateToForm(appStatusForm,request);
		return mapping.findForward(CMSConstants.APP_STATUS_ENTRY);
	} catch (Exception e) {
		log.error("error in final submit of addAppStatusEntry page...", e);
		if (e instanceof BusinessException) {
			String msgKey = super.handleBusinessException(e);
			ActionMessage message = new ActionMessage(msgKey);
			messages.add("messages", message);
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} else if (e instanceof ApplicationException) {
			String msg = super.handleApplicationException(e);
			appStatusForm.setErrorMessage(msg);
			appStatusForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} else {
			throw e;
		}
	}
	if(isAdded){
		ActionMessage message = new ActionError("knowledgepro.admin.ApplicationStatus.addsuccess",appStatusForm.getApplicationStatus());
		messages.add("messages", message);
		saveMessages(request, messages);
		appStatusForm.reset(mapping, request);
	}else{
		errors.add("error", new ActionError("knowledgepro.admin.ApplicationStatus.addfailure",appStatusForm.getApplicationStatus()));
		saveErrors(request, errors);
	}
	return mapping.findForward(CMSConstants.APP_STATUS_ENTRY);
}
/**
 * @param mapping
 * @param form
 * @param request
 * @param response
 * @return
 * @throws Exception
 */
public ActionForward updateAppStatusEntry(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {
	AppStatusForm appStatusForm = (AppStatusForm)form;
	setUserId(request, appStatusForm);
	ActionMessages messages = new ActionMessages();
	ActionErrors errors=appStatusForm.validate(mapping, request);
	boolean isUpdated = false;
	try{
		if(!errors.isEmpty()){
			saveErrors(request, errors);
			setRequestedDateToForm(appStatusForm,request);
			if(appStatusForm.getApplicationStatus().trim().isEmpty()){
				appStatusForm.setApplicationStatus(null);
			}
			return mapping.findForward(CMSConstants.APP_STATUS_ENTRY);
		}
		isUpdated = AppStatusHandler.getInstance().addAppStatusEntry(appStatusForm, "Edit");
		setRequestedDateToForm(appStatusForm,request);
	}catch (DuplicateException e1) {
		errors.add("error", new ActionError("knowledgepro.admin.ApplicationStatus.name.exists"));
		saveErrors(request, errors);
		setRequestedDateToForm(appStatusForm,request);
		return mapping.findForward(CMSConstants.APP_STATUS_ENTRY);
	} catch (ReActivateException e1) {
		errors.add("error", new ActionError("knowledgepro.admin.ApplicationStatus.addfailure.alreadyexist.reactivate"));
		saveErrors(request, errors);
		setRequestedDateToForm(appStatusForm,request);
		return mapping.findForward(CMSConstants.APP_STATUS_ENTRY);
	}catch (Exception e) {
		log.error("error in update ApplicationStatus page...", e);
		if (e instanceof BusinessException) {
			String msgKey = super.handleBusinessException(e);
			ActionMessage message = new ActionMessage(msgKey);
			messages.add("messages", message);
			request.setAttribute("applicationStatus", "edit");
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} else if (e instanceof ApplicationException) {
			String msg = super.handleApplicationException(e);
			appStatusForm.setErrorMessage(msg);
			appStatusForm.setErrorStack(e.getMessage());
			request.setAttribute("applicationStatus", "edit");
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} else {
			throw e;
		}
	}
	if(isUpdated){
		ActionMessage actionMessage = new ActionMessage("knowledgepro.admin.ApplicationStatus.updatesuccess",appStatusForm.getApplicationStatus());
		messages.add("messages", actionMessage);
		saveMessages(request, messages);
		appStatusForm.reset(mapping, request);
	}else{
		errors.add("error", new ActionError("knowledgepro.admin.ApplicationStatus.updatefailure",appStatusForm.getApplicationStatus()));
		saveErrors(request, errors);
	}
	request.setAttribute("applicationStatus", "add");
	return mapping.findForward(CMSConstants.APP_STATUS_ENTRY);
}
/**
 * @param mapping
 * @param form
 * @param request
 * @param response
 * @return
 * @throws Exception
 */
public ActionForward deleteAppStatusEntry(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {
	AppStatusForm appStatusForm = (AppStatusForm)form;
	setUserId(request, appStatusForm);
	ActionErrors errors = new ActionErrors();
	ActionMessages messages = new ActionMessages();
	boolean isDeleted=false;
	try{
		if(appStatusForm.getId()!=0){
			int appStatusId=appStatusForm.getId();
			isDeleted = AppStatusHandler.getInstance().deleteAppStatusEntry(appStatusId,appStatusForm,false);
		}
	}catch (Exception e) {
		log.error("error in delete ApplicationStatus page...", e);
		if (e instanceof BusinessException) {
			String msgKey = super.handleBusinessException(e);
			ActionMessage message = new ActionMessage(msgKey);
			messages.add("messages", message);
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} else if (e instanceof ApplicationException) {
			String msg = super.handleApplicationException(e);
			appStatusForm.setErrorMessage(msg);
			appStatusForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} else {
			throw e;
		}
	}
	setRequestedDateToForm(appStatusForm,request);
	if(isDeleted){
		ActionMessage message = new ActionMessage("knowledgepro.admin.ApplicationStatus.deletesuccess",appStatusForm.getApplicationStatus());
		messages.add("messages",message);
		saveMessages(request, messages);
		appStatusForm.reset(mapping, request);
	}else{
		errors.add("error",new ActionError("knowledgepro.admin.ApplicationStatus.deletefailure",appStatusForm.getApplicationStatus()));
		saveErrors(request, errors);
	}
	return mapping.findForward(CMSConstants.APP_STATUS_ENTRY);
}
/**
 * @param mapping
 * @param form
 * @param request
 * @param response
 * @return
 * @throws Exception
 */
public ActionForward activateAppStatusEntry(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {
	AppStatusForm appStatusForm = (AppStatusForm)form;
	setUserId(request, appStatusForm);
	ActionErrors errors = new ActionErrors();
	ActionMessages messages = new ActionMessages();
	boolean isActivated=false;
	try{
		if(appStatusForm.getDupId()!=0){
			int appStatusId=appStatusForm.getDupId();
			isActivated=AppStatusHandler.getInstance().deleteAppStatusEntry(appStatusId,appStatusForm,true);
		}
	}catch (Exception e) {
		log.error("error in activateAppStatusEntry page...", e);
		if (e instanceof BusinessException) {
			String msgKey = super.handleBusinessException(e);
			ActionMessage message = new ActionMessage(msgKey);
			messages.add("messages", message);
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} else if (e instanceof ApplicationException) {
			String msg = super.handleApplicationException(e);
			appStatusForm.setErrorMessage(msg);
			appStatusForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} else {
			throw e;
		}
	}
	setRequestedDateToForm(appStatusForm,request);
	if(isActivated){
		ActionMessage message = new ActionMessage("knowledgepro.admin.ApplicationStatus.activate");
		messages.add("messages",message);
		saveMessages(request, messages);
	}
	else{
		errors.add("error", new ActionError("Knowledgepro.admin.ApplicationStatus.activate.failure"));
		saveErrors(request, errors);
	}
	log.debug("Leaving activateAppStatusEntry Action");
	return mapping.findForward(CMSConstants.APP_STATUS_ENTRY);
}
}
