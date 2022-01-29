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
import com.kp.cms.forms.admin.EvaluationStudentFeedbackSessionForm;
import com.kp.cms.handlers.admin.EvaluationStudentFeedbackSessionHandler;
import com.kp.cms.to.admin.EvaluationStudentFeedbackSessionTo;

public class EvaluationStudentFeedbackSessionAction extends BaseDispatchAction {
 /**
 * @param mapping
 * @param form
 * @param request
 * @param response
 * @return
 * @throws Exception
 */
public ActionForward initEvaluationStuFeedbackSession(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {
	EvaluationStudentFeedbackSessionForm evaStuFeedbackSessionForm = (EvaluationStudentFeedbackSessionForm)form;
	setEvaStudentFeedbackSession(evaStuFeedbackSessionForm);
	evaStuFeedbackSessionForm.clear(mapping, request);
	return mapping.findForward(CMSConstants.INIT_EVAL_STU_FEEDBACK_SESSION);
}

/**
 * @param evaStuFeedbackSessionForm
 * @throws Exception
 */
private void setEvaStudentFeedbackSession( EvaluationStudentFeedbackSessionForm evaStuFeedbackSessionForm) throws Exception{
	List<EvaluationStudentFeedbackSessionTo> sessionList =  EvaluationStudentFeedbackSessionHandler.getInstance().getFeedbackSessionList();
	evaStuFeedbackSessionForm.setSessionList(sessionList);
}
 /**
 * @param mapping
 * @param form
 * @param request
 * @param response
 * @return
 * @throws Exception
 */
public ActionForward addStuFeedbackSession(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
	EvaluationStudentFeedbackSessionForm evaStuFeedbackSessionForm = (EvaluationStudentFeedbackSessionForm)form;
	ActionMessages messages = new ActionMessages();
	ActionErrors errors = evaStuFeedbackSessionForm.validate(mapping, request);
	//call of setUserId method and setting to form.
	setUserId(request, evaStuFeedbackSessionForm);
	try{
		if(errors!=null && errors.isEmpty()){
			boolean isExist = EvaluationStudentFeedbackSessionHandler.getInstance().checkExistStuFeedbackSession(evaStuFeedbackSessionForm);
			if(!isExist){
			boolean isAdded = EvaluationStudentFeedbackSessionHandler.getInstance().addStuFeedbackSession(evaStuFeedbackSessionForm,"add");
			if(isAdded){
				//if adding is success.
				ActionMessage message = new ActionMessage("knowledgepro.admin.stufeedback.session.AddedSuccess");
				messages.add("messages", message);
				saveMessages(request, messages);
				evaStuFeedbackSessionForm.reset(mapping, request);	
			}else{
				//if adding is failure.
				ActionMessage message = new ActionMessage("knowledgepro.admin.stufeedback.session.AddedFailure");
				messages.add("messages", message);
				saveMessages(request, messages);
				evaStuFeedbackSessionForm.reset(mapping, request);	
			}
			}else{
				errors.add("CMSConstants.ERROR", new ActionError("knowledgepro.admin.stufeedback.session.Exist"));
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
		evaStuFeedbackSessionForm.setErrorMessage(msg);
		evaStuFeedbackSessionForm.setErrorStack(exception.getMessage());
		return mapping.findForward(CMSConstants.ERROR_PAGE);
	}
	evaStuFeedbackSessionForm.clear(mapping, request);
	setEvaStudentFeedbackSession(evaStuFeedbackSessionForm);
	return mapping.findForward(CMSConstants.INIT_EVAL_STU_FEEDBACK_SESSION);
	}
/**
 * @param mapping
 * @param form
 * @param request
 * @param response
 * @return
 * @throws Exception
 */
public ActionForward editStuFeedbackSession(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
	EvaluationStudentFeedbackSessionForm evaStuFeedbackSessionForm = (EvaluationStudentFeedbackSessionForm)form;
	ActionMessages messages = new ActionMessages();
	setUserId(request, evaStuFeedbackSessionForm);
		try{
			EvaluationStudentFeedbackSessionHandler.getInstance().editStuFeedbackSession(evaStuFeedbackSessionForm);
			request.setAttribute("sessionList", "edit");
		}catch (BusinessException businessException) {
			String msgKey = super.handleBusinessException(businessException);
			ActionMessage message = new ActionMessage(msgKey);
			messages.add(CMSConstants.MESSAGES, message);
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} catch (Exception exception) {	
			String msg = super.handleApplicationException(exception);
			evaStuFeedbackSessionForm.setErrorMessage(msg);
			evaStuFeedbackSessionForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
	setEvaStudentFeedbackSession(evaStuFeedbackSessionForm);
	return mapping.findForward(CMSConstants.INIT_EVAL_STU_FEEDBACK_SESSION);
	}
/**
 * @param mapping
 * @param form
 * @param request
 * @param response
 * @return
 * @throws Exception
 */
public ActionForward deleteStuFeedbackSession(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
	EvaluationStudentFeedbackSessionForm evaStuFeedbackSessionForm = (EvaluationStudentFeedbackSessionForm)form;
	ActionMessages messages = new ActionMessages();
	setUserId(request, evaStuFeedbackSessionForm);
	ActionErrors errors = evaStuFeedbackSessionForm.validate(mapping, request);
	try{
		if(errors!=null && errors.isEmpty()){
			boolean isDeleted = EvaluationStudentFeedbackSessionHandler.getInstance().deleteEvaStudentFeedbackSession(evaStuFeedbackSessionForm);
			if(isDeleted){
				//if delete is success.
				ActionMessage message = new ActionMessage("knowledgepro.admin.stufeedback.session.DeleteSuccess");
				messages.add("messages", message);
				saveMessages(request, messages);
				evaStuFeedbackSessionForm.reset(mapping, request);	
			}else{
				//if delete is failure.
				ActionMessage message = new ActionMessage("knowledgepro.admin.stufeedback.session.DeleteFailure");
				messages.add("messages", message);
				saveMessages(request, messages);
				evaStuFeedbackSessionForm.reset(mapping, request);	
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
		evaStuFeedbackSessionForm.setErrorMessage(msg);
		evaStuFeedbackSessionForm.setErrorStack(exception.getMessage());
		return mapping.findForward(CMSConstants.ERROR_PAGE);
	}
	setEvaStudentFeedbackSession(evaStuFeedbackSessionForm);
	return mapping.findForward(CMSConstants.INIT_EVAL_STU_FEEDBACK_SESSION);
	}
/**
 * @param mapping
 * @param form
 * @param request
 * @param response
 * @return
 * @throws Exception
 */
public ActionForward updateEvaluationStuFeedbackSession(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
	EvaluationStudentFeedbackSessionForm evaStuFeedbackSessionForm = (EvaluationStudentFeedbackSessionForm)form;
	ActionMessages messages = new ActionMessages();
	setUserId(request, evaStuFeedbackSessionForm);
	ActionErrors errors = evaStuFeedbackSessionForm.validate(mapping, request);
	try{
		if(errors.isEmpty()){
			boolean isExist = EvaluationStudentFeedbackSessionHandler.getInstance().checkExistStuFeedbackSession(evaStuFeedbackSessionForm);
			if(!isExist){
			boolean isUpdated = EvaluationStudentFeedbackSessionHandler.getInstance().addStuFeedbackSession(evaStuFeedbackSessionForm, "edit");
			if(isUpdated){
				ActionMessage message = new ActionMessage("knowledgepro.admin.stufeedback.session.UpdatedSuccess");
				messages.add("messages", message);
				saveMessages(request, messages);
				evaStuFeedbackSessionForm.reset(mapping, request);	
			}else{
				ActionMessage message = new ActionMessage("knowledgepro.admin.stufeedback.session.UpdatedFailure");
				messages.add("messages", message);
				saveMessages(request, messages);
				evaStuFeedbackSessionForm.reset(mapping, request);	
				}
			}else{
				errors.add("CMSConstants.ERROR", new ActionError("knowledgepro.admin.stufeedback.session.Exist"));
				saveErrors(request, errors);
			}
		}else{
			errors.add(messages);
			request.setAttribute("sessionList", "edit");
			saveErrors(request, errors);
		}
	}catch (BusinessException businessException) {
		String msgKey = super.handleBusinessException(businessException);
		ActionMessage message = new ActionMessage(msgKey);
		messages.add(CMSConstants.MESSAGES, message);
		request.setAttribute("sessionList", "edit");
		return mapping.findForward(CMSConstants.ERROR_PAGE);
	} catch (Exception exception) {	
		String msg = super.handleApplicationException(exception);
		evaStuFeedbackSessionForm.setErrorMessage(msg);
		evaStuFeedbackSessionForm.setErrorStack(exception.getMessage());
		request.setAttribute("sessionList", "edit");
		return mapping.findForward(CMSConstants.ERROR_PAGE);
	}
	evaStuFeedbackSessionForm.clear(mapping, request);
	setEvaStudentFeedbackSession(evaStuFeedbackSessionForm);
	return mapping.findForward(CMSConstants.INIT_EVAL_STU_FEEDBACK_SESSION);
	}
}
