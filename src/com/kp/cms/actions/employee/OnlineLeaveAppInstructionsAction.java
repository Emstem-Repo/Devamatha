package com.kp.cms.actions.employee;

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
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.admin.NewsEventsForm;
import com.kp.cms.forms.employee.OnlineLeaveAppInstructionForm;
import com.kp.cms.handlers.admin.NewsEventsHandler;
import com.kp.cms.handlers.employee.OnlineLeaveAppInstructionHandler;
import com.kp.cms.to.employee.OnlineLeaveAppInstructionTO;

public class OnlineLeaveAppInstructionsAction extends BaseDispatchAction{
	private static final Log log = LogFactory.getLog(OnlineLeaveAppInstructionsAction.class);
/**
 * @param mapping
 * @param form
 * @param request
 * @param response
 * @return
 * @throws Exception
 */
public ActionForward initLeaveAppInstruction(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {
	log.info("call of initLeaveAppInstruction in OnlineLeaveAppInstructionsAction class. ");
	OnlineLeaveAppInstructionForm appInstructionForm = (OnlineLeaveAppInstructionForm)form;
	try {
		appInstructionForm.reset(mapping, request);
		//call of setLeaveAppInstructions method.
		setLeaveAppInstructions(appInstructionForm);
	} catch (BusinessException businessException) {
		String msgKey = super.handleBusinessException(businessException);
		appInstructionForm.setErrorMessage(msgKey);
		appInstructionForm.setErrorStack(businessException.getMessage());
		return mapping.findForward(CMSConstants.ERROR_PAGE);
	} catch (Exception exception) {
		String msg = super.handleApplicationException(exception);
		appInstructionForm.setErrorMessage(msg);
		appInstructionForm.setErrorStack(exception.getMessage());
		return mapping.findForward(CMSConstants.ERROR_PAGE);
	}	
	log.info("end of initLeaveAppInstruction in OnlineLeaveAppInstructionsAction class. ");
	return mapping.findForward(CMSConstants.LEAVE_APPLICATION_INSTR);
}
/**
 * @param appInstructionForm
 * @throws Exception
 */
private void setLeaveAppInstructions( OnlineLeaveAppInstructionForm appInstructionForm)throws Exception {
	List<OnlineLeaveAppInstructionTO> onlineLeaveAppTO= OnlineLeaveAppInstructionHandler.getInstance().getOnlineLeaveInstructions();
	appInstructionForm.setLeaveInstructionsTo(onlineLeaveAppTO);
	}
/**
 * @param mapping
 * @param form
 * @param request
 * @param response
 * @return
 * @throws Exception
 */
public ActionForward addLeaveAppInstructions(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {
	log.info("call of addLeaveAppInstructions in OnlineLeaveAppInstructionsAction class. ");
	OnlineLeaveAppInstructionForm appInstructionForm = (OnlineLeaveAppInstructionForm)form;
	ActionMessages messages = new ActionMessages();
	ActionErrors errors = appInstructionForm.validate(mapping, request);
	//call of setUserId method and setting to form.
	setUserId(request, appInstructionForm);
	validateDescription(appInstructionForm,errors);
	try {
		if(errors.isEmpty()){
			String descMessage = appInstructionForm.getDescription(); 
			boolean isExist = OnlineLeaveAppInstructionHandler.getInstance().checkDuplicateLeaveInstructions(descMessage);
			if(!isExist){
			boolean isAdded = OnlineLeaveAppInstructionHandler.getInstance().addOnlineLeaveInstructions(appInstructionForm);
			if(isAdded){
				//if adding is success.
				ActionMessage message = new ActionMessage("knowledgepro.admin.leave.instructions.AddedSuccess");
				messages.add("messages", message);
				saveMessages(request, messages);
				appInstructionForm.reset(mapping, request);	
			}
			if(!isAdded){
				//if adding is failure.
				ActionMessage message = new ActionMessage("knowledgepro.admin.leave.instructions.AddedFailure");
				messages.add("messages", message);
				saveMessages(request, messages);
				appInstructionForm.reset(mapping, request);	
			}
			}else{
				errors.add("knowledgepro.admin.leave.instructions.Exist", new ActionError("knowledgepro.admin.leave.instructions.Exist"));
				saveErrors(request, errors);
			}
		}else{
			errors.add(messages);
			saveErrors(request, errors);
		}
	}catch (BusinessException businessException) {
		String msgKey = super.handleBusinessException(businessException);
		ActionMessage message = new ActionMessage(msgKey);
		messages.add(CMSConstants.MESSAGES, message);
		return mapping.findForward(CMSConstants.ERROR_PAGE);
	} catch (Exception exception) {	
		String msg = super.handleApplicationException(exception);
		appInstructionForm.setErrorMessage(msg);
		appInstructionForm.setErrorStack(exception.getMessage());
		return mapping.findForward(CMSConstants.ERROR_PAGE);
	}
	//call of setLeaveAppInstructions method.
	setLeaveAppInstructions(appInstructionForm);
	return mapping.findForward(CMSConstants.LEAVE_APPLICATION_INSTR);
}
/**
 * @param appInstructionForm
 */
private void validateDescription( OnlineLeaveAppInstructionForm appInstructionForm,ActionErrors errors) {
	if(appInstructionForm.getDescription()!=null && !appInstructionForm.getDescription().isEmpty() && appInstructionForm.getDescription().length()>2000){
		errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.onlineleaveins.description.length"));
	}
	
}
/**
 * @param mapping
 * @param form
 * @param request
 * @param response
 * @return
 * @throws Exception
 */
public ActionForward editLeaveAppInstructions(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {
	OnlineLeaveAppInstructionForm appInstructionForm = (OnlineLeaveAppInstructionForm)form;
	ActionMessages messages = new ActionMessages();
	try{
		OnlineLeaveAppInstructionHandler.getInstance().editLeaveAppInstructions(appInstructionForm);
		request.setAttribute("leave", "edit");
	}catch (BusinessException businessException) {
		log.info("Exception editMenus");
		String msgKey = super.handleBusinessException(businessException);
		ActionMessage message = new ActionMessage(msgKey);
		messages.add(CMSConstants.MESSAGES, message);
		return mapping.findForward(CMSConstants.ERROR_PAGE);
	} catch (Exception exception) {	
		String msg = super.handleApplicationException(exception);
		appInstructionForm.setErrorMessage(msg);
		appInstructionForm.setErrorStack(exception.getMessage());
		return mapping.findForward(CMSConstants.ERROR_PAGE);
	}
	//call of setLeaveAppInstructions method.
	setLeaveAppInstructions(appInstructionForm);
	return mapping.findForward(CMSConstants.LEAVE_APPLICATION_INSTR);
}
/**
 * @param mapping
 * @param form
 * @param request
 * @param response
 * @return
 * @throws Exception
 */
public ActionForward deleteLeaveAppInstructions(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {
	OnlineLeaveAppInstructionForm appInstructionForm = (OnlineLeaveAppInstructionForm)form;
	ActionMessages messages = new ActionMessages();
	ActionErrors errors = appInstructionForm.validate(mapping, request);
	//call of setUserId method and setting to form.
	setUserId(request, appInstructionForm);
	int leaveAppInsId= appInstructionForm.getOnlineLeaveAppId();
	try{
		if(errors != null && errors.isEmpty()){
			boolean isDeleted = OnlineLeaveAppInstructionHandler.getInstance().deleteLeaveAppIns(leaveAppInsId);
			if(isDeleted){
				//if delete is success.
				ActionMessage message = new ActionMessage("knowledgepro.admin.leave.instructions.DeleteSuccess");
				messages.add("messages", message);
				saveMessages(request, messages);
				appInstructionForm.reset(mapping, request);	
			}if(!isDeleted){
				//if delete is failure.
				ActionMessage message = new ActionMessage("knowledgepro.admin.leave.instructions.DeleteFailure");
				messages.add("messages", message);
				saveMessages(request, messages);
				appInstructionForm.reset(mapping, request);	
			}
		}else{
			errors.add(messages);
			saveErrors(request, errors);
		}
		
	}catch (BusinessException businessException) {
		log.info("Exception addInterviewDefinition");
		String msgKey = super.handleBusinessException(businessException);
		ActionMessage message = new ActionMessage(msgKey);
		messages.add(CMSConstants.MESSAGES, message);
		return mapping.findForward(CMSConstants.ERROR_PAGE);
	} catch (Exception exception) {	
		String msg = super.handleApplicationException(exception);
		appInstructionForm.setErrorMessage(msg);
		appInstructionForm.setErrorStack(exception.getMessage());
		return mapping.findForward(CMSConstants.ERROR_PAGE);
	}
	setLeaveAppInstructions(appInstructionForm);
	return mapping.findForward(CMSConstants.LEAVE_APPLICATION_INSTR);
}
/**
 * @param mapping
 * @param form
 * @param request
 * @param response
 * @return
 * @throws Exception
 */
public ActionForward updateLeaveAppInstructions(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {
	log.info("call of addLeaveAppInstructions in OnlineLeaveAppInstructionsAction class. ");
	OnlineLeaveAppInstructionForm appInstructionForm = (OnlineLeaveAppInstructionForm)form;
	ActionMessages messages = new ActionMessages();
	ActionErrors errors = appInstructionForm.validate(mapping, request);
	//call of setUserId method and setting to form.
	setUserId(request, appInstructionForm);
	try {
		if(errors != null && errors.isEmpty()){
			boolean isUpdated = OnlineLeaveAppInstructionHandler.getInstance().updateOnlineLeaveInstructions(appInstructionForm);
			if(isUpdated){
				//if adding is success.
				ActionMessage message = new ActionMessage("knowledgepro.admin.leave.instructions.UpdatedSuccess");
				messages.add("messages", message);
				saveMessages(request, messages);
				appInstructionForm.reset(mapping, request);	
			}
			if(!isUpdated){
				//if adding is failure.
				ActionMessage message = new ActionMessage("knowledgepro.admin.leave.instructions.UpdatedFailure");
				messages.add("messages", message);
				saveMessages(request, messages);
				appInstructionForm.reset(mapping, request);	
			}
		}else{
			errors.add(messages);
			request.setAttribute("leave", "edit");
			saveErrors(request, errors);
		}
	}catch (BusinessException businessException) {
		String msgKey = super.handleBusinessException(businessException);
		ActionMessage message = new ActionMessage(msgKey);
		messages.add(CMSConstants.MESSAGES, message);
		request.setAttribute("leave", "edit");
		return mapping.findForward(CMSConstants.ERROR_PAGE);
	} catch (Exception exception) {	
		String msg = super.handleApplicationException(exception);
		appInstructionForm.setErrorMessage(msg);
		appInstructionForm.setErrorStack(exception.getMessage());
		request.setAttribute("leave", "edit");
		return mapping.findForward(CMSConstants.ERROR_PAGE);
	}
	//call of setLeaveAppInstructions method.
	setLeaveAppInstructions(appInstructionForm);
	return mapping.findForward(CMSConstants.LEAVE_APPLICATION_INSTR);
}
}
