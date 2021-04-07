package com.kp.cms.actions.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.kp.cms.forms.admin.StudentFeedbackInstructionsForm;
import com.kp.cms.handlers.admin.StudentFeedbackInstructionsHandler;
import com.kp.cms.to.admin.StudentFeedbackInstructionsTO;

public class StudentFeedbackInstructionsAction extends BaseDispatchAction {
 /**
 * @param mapping
 * @param form
 * @param request
 * @param response
 * @return
 * @throws Exception
 */
public ActionForward initStuFeedbackInstructions(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {
	StudentFeedbackInstructionsForm stuFeedbackInstructionsForm = (StudentFeedbackInstructionsForm)form;
	stuFeedbackInstructionsForm.reset(mapping, request);
	setStuFeedbackInstructions(stuFeedbackInstructionsForm);
	return mapping.findForward(CMSConstants.INIT_STU_FEEDBACK_INST);
}
/**
 * @param stuFeedbackInstructionsForm
 * @throws Exception
 */
private void setStuFeedbackInstructions( StudentFeedbackInstructionsForm stuFeedbackInstructionsForm) throws Exception{
	List<StudentFeedbackInstructionsTO> instructionsList = StudentFeedbackInstructionsHandler.getInstance().getInstructions();
		stuFeedbackInstructionsForm.setStuFeedbackInsToList(instructionsList);
}
/**
 * @param mapping
 * @param form
 * @param request
 * @param response
 * @return
 * @throws Exception
 */
public ActionForward addStuFeedbackInstructions(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
	StudentFeedbackInstructionsForm stuFeedbackInstructionsForm = (StudentFeedbackInstructionsForm)form;
	ActionMessages messages = new ActionMessages();
	ActionErrors errors = stuFeedbackInstructionsForm.validate(mapping, request);
	//call of setUserId method and setting to form.
	setUserId(request, stuFeedbackInstructionsForm);
	validateDescription(stuFeedbackInstructionsForm,errors);
	try{
		if(errors!=null && errors.isEmpty()){
			boolean isExist = StudentFeedbackInstructionsHandler.getInstance().checkDuplicateInstructions(stuFeedbackInstructionsForm);
			if(!isExist){
				boolean isAdded= StudentFeedbackInstructionsHandler.getInstance().addStuFeedbackInstructions(stuFeedbackInstructionsForm,"add");
				if(isAdded){
					//if adding is success.
					ActionMessage message = new ActionMessage("knowledgepro.admin.stufeedback.instructions.AddedSuccess");
					messages.add("messages", message);
					saveMessages(request, messages);
					stuFeedbackInstructionsForm.reset(mapping, request);	
				}else{
					//if adding is failure.
					ActionMessage message = new ActionMessage("knowledgepro.admin.stufeedback.instructions.AddedFailure");
					messages.add("messages", message);
					saveMessages(request, messages);
					stuFeedbackInstructionsForm.reset(mapping, request);	
				}
			}else{
				errors.add("CMSConstants.ERROR", new ActionError("knowledgepro.admin.stufeedback.instructions.Exist"));
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
		stuFeedbackInstructionsForm.setErrorMessage(msg);
		stuFeedbackInstructionsForm.setErrorStack(exception.getMessage());
		return mapping.findForward(CMSConstants.ERROR_PAGE);
	}
	//call of setLeaveAppInstructions method.
	setStuFeedbackInstructions(stuFeedbackInstructionsForm);
	return mapping.findForward(CMSConstants.INIT_STU_FEEDBACK_INST);
	}
/**
 * @param stuFeedbackInstructionsForm
 * @param errors
 * @throws Exception
 */
private void validateDescription( StudentFeedbackInstructionsForm stuFeedbackInstructionsForm, ActionErrors errors)throws Exception {
	if(stuFeedbackInstructionsForm.getDescription()!=null && !stuFeedbackInstructionsForm.getDescription().isEmpty() && stuFeedbackInstructionsForm.getDescription().length()>2000){
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
public ActionForward editStuFeedbackInstructions(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
	StudentFeedbackInstructionsForm stuFeedbackInstructionsForm = (StudentFeedbackInstructionsForm)form;
	ActionMessages messages = new ActionMessages();
	setUserId(request, stuFeedbackInstructionsForm);
	try{
		StudentFeedbackInstructionsHandler.getInstance().editFeedbackInstructions(stuFeedbackInstructionsForm);
		request.setAttribute("instructions", "edit");
	}catch (BusinessException businessException) {
		String msgKey = super.handleBusinessException(businessException);
		ActionMessage message = new ActionMessage(msgKey);
		messages.add(CMSConstants.MESSAGES, message);
		return mapping.findForward(CMSConstants.ERROR_PAGE);
	} catch (Exception exception) {	
		String msg = super.handleApplicationException(exception);
		stuFeedbackInstructionsForm.setErrorMessage(msg);
		stuFeedbackInstructionsForm.setErrorStack(exception.getMessage());
		return mapping.findForward(CMSConstants.ERROR_PAGE);
	}
	setStuFeedbackInstructions(stuFeedbackInstructionsForm);
	return mapping.findForward(CMSConstants.INIT_STU_FEEDBACK_INST);
	}
/**
 * @param mapping
 * @param form
 * @param request
 * @param response
 * @return
 * @throws Exception
 */
public ActionForward deleteStuFeedbackInstructions(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
	StudentFeedbackInstructionsForm stuFeedbackInstructionsForm = (StudentFeedbackInstructionsForm)form;
	ActionMessages messages = new ActionMessages();
	setUserId(request, stuFeedbackInstructionsForm);
	ActionErrors errors = stuFeedbackInstructionsForm.validate(mapping, request);
	try{
		if(errors.isEmpty()){
			boolean isDeleted = StudentFeedbackInstructionsHandler.getInstance().deleteInstructions(stuFeedbackInstructionsForm);
			if(isDeleted){
				//if delete is success.
				ActionMessage message = new ActionMessage("knowledgepro.admin.stufeedback.instructions.DeleteSuccess");
				messages.add("messages", message);
				saveMessages(request, messages);
				stuFeedbackInstructionsForm.reset(mapping, request);	
			}else{
				//if delete is failure.
				ActionMessage message = new ActionMessage("knowledgepro.admin.stufeedback.instructions.DeleteFailure");
				messages.add("messages", message);
				saveMessages(request, messages);
				stuFeedbackInstructionsForm.reset(mapping, request);	
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
		stuFeedbackInstructionsForm.setErrorMessage(msg);
		stuFeedbackInstructionsForm.setErrorStack(exception.getMessage());
		return mapping.findForward(CMSConstants.ERROR_PAGE);
	}
	setStuFeedbackInstructions(stuFeedbackInstructionsForm);
	return mapping.findForward(CMSConstants.INIT_STU_FEEDBACK_INST);
	}
/**
 * @param mapping
 * @param form
 * @param request
 * @param response
 * @return
 * @throws Exception
 */
public ActionForward updateStuFeedbackInstructions(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
	StudentFeedbackInstructionsForm stuFeedbackInstructionsForm = (StudentFeedbackInstructionsForm)form;
	ActionMessages messages = new ActionMessages();
	ActionErrors errors = stuFeedbackInstructionsForm.validate(mapping, request);
	//call of setUserId method and setting to form.
	setUserId(request, stuFeedbackInstructionsForm);
	validateDescription(stuFeedbackInstructionsForm,errors);
	try{
		if(errors.isEmpty()){
			boolean isUpdated = StudentFeedbackInstructionsHandler.getInstance().addStuFeedbackInstructions(stuFeedbackInstructionsForm,"edit");
			if(isUpdated){
				//if adding is success.
				ActionMessage message = new ActionMessage("knowledgepro.admin.stufeedback.instructions.UpdatedSuccess");
				messages.add("messages", message);
				saveMessages(request, messages);
				stuFeedbackInstructionsForm.reset(mapping, request);	
			}else{
				//if adding is failure.
				ActionMessage message = new ActionMessage("knowledgepro.admin.stufeedback.instructions.UpdatedFailure");
				messages.add("messages", message);
				saveMessages(request, messages);
				stuFeedbackInstructionsForm.reset(mapping, request);	
			}
		}else{
			errors.add(messages);
			request.setAttribute("instructions", "edit");
			saveErrors(request, errors);
		}
	}catch (BusinessException businessException) {
		String msgKey = super.handleBusinessException(businessException);
		ActionMessage message = new ActionMessage(msgKey);
		messages.add(CMSConstants.MESSAGES, message);
		request.setAttribute("instructions", "edit");
		return mapping.findForward(CMSConstants.ERROR_PAGE);
	} catch (Exception exception) {	
		String msg = super.handleApplicationException(exception);
		stuFeedbackInstructionsForm.setErrorMessage(msg);
		stuFeedbackInstructionsForm.setErrorStack(exception.getMessage());
		request.setAttribute("instructions", "edit");
		return mapping.findForward(CMSConstants.ERROR_PAGE);
	}
	setStuFeedbackInstructions(stuFeedbackInstructionsForm);
	return mapping.findForward(CMSConstants.INIT_STU_FEEDBACK_INST);
	}
}
