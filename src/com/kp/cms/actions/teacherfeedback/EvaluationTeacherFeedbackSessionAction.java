package com.kp.cms.actions.teacherfeedback;

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
import com.kp.cms.forms.teacherfeedback.EvaluationTeacherFeedbackSessionForm;
import com.kp.cms.handlers.teacherfeedback.EvaluationTeacherFeedbackSessionHandler;
import com.kp.cms.to.teacherfeedback.EvaluationTeacherFeedbackSessionTo;

public class EvaluationTeacherFeedbackSessionAction extends BaseDispatchAction {
	 /**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initEvaluationTeacherFeedbackSession(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		EvaluationTeacherFeedbackSessionForm evaTeacherFeedbackSessionForm = (EvaluationTeacherFeedbackSessionForm)form;
		setEvaTeacherFeedbackSession(evaTeacherFeedbackSessionForm);
		evaTeacherFeedbackSessionForm.clear(mapping, request);
		return mapping.findForward(CMSConstants.INIT_EVAL_TEACHER_FEEDBACK_SESSION);
	}

	/**
	 * @param evaTeacherFeedbackSessionForm
	 * @throws Exception
	 */
	private void setEvaTeacherFeedbackSession( EvaluationTeacherFeedbackSessionForm evaTeacherFeedbackSessionForm) throws Exception{
		List<EvaluationTeacherFeedbackSessionTo> sessionList =  EvaluationTeacherFeedbackSessionHandler.getInstance().getFeedbackSessionList();
		evaTeacherFeedbackSessionForm.setSessionList(sessionList);
	}
	 /**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addTeacherFeedbackSession(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {
		EvaluationTeacherFeedbackSessionForm evaTeacherFeedbackSessionForm = (EvaluationTeacherFeedbackSessionForm)form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = evaTeacherFeedbackSessionForm.validate(mapping, request);
		//call of setUserId method and setting to form.
		setUserId(request, evaTeacherFeedbackSessionForm);
		try{
			if(errors!=null && errors.isEmpty()){
				boolean isExist = EvaluationTeacherFeedbackSessionHandler.getInstance().checkExistTeacherFeedbackSession(evaTeacherFeedbackSessionForm);
				if(!isExist){
				boolean isAdded = EvaluationTeacherFeedbackSessionHandler.getInstance().addTeacherFeedbackSession(evaTeacherFeedbackSessionForm,"add");
				if(isAdded){
					//if adding is success.
					ActionMessage message = new ActionMessage("knowledgepro.admin.stufeedback.session.AddedSuccess");
					messages.add("messages", message);
					saveMessages(request, messages);
					evaTeacherFeedbackSessionForm.reset(mapping, request);	
				}else{
					//if adding is failure.
					ActionMessage message = new ActionMessage("knowledgepro.admin.stufeedback.session.AddedFailure");
					messages.add("messages", message);
					saveMessages(request, messages);
					evaTeacherFeedbackSessionForm.reset(mapping, request);	
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
			evaTeacherFeedbackSessionForm.setErrorMessage(msg);
			evaTeacherFeedbackSessionForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		evaTeacherFeedbackSessionForm.clear(mapping, request);
		setEvaTeacherFeedbackSession(evaTeacherFeedbackSessionForm);
		return mapping.findForward(CMSConstants.INIT_EVAL_TEACHER_FEEDBACK_SESSION);
		}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward editTeacherFeedbackSession(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
				throws Exception {
		EvaluationTeacherFeedbackSessionForm evaTeacherFeedbackSessionForm = (EvaluationTeacherFeedbackSessionForm)form;
		ActionMessages messages = new ActionMessages();
		setUserId(request, evaTeacherFeedbackSessionForm);
			try{
				EvaluationTeacherFeedbackSessionHandler.getInstance().editTeacherFeedbackSession(evaTeacherFeedbackSessionForm);
				request.setAttribute("sessionList", "edit");
			}catch (BusinessException businessException) {
				String msgKey = super.handleBusinessException(businessException);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add(CMSConstants.MESSAGES, message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} catch (Exception exception) {	
				String msg = super.handleApplicationException(exception);
				evaTeacherFeedbackSessionForm.setErrorMessage(msg);
				evaTeacherFeedbackSessionForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		setEvaTeacherFeedbackSession(evaTeacherFeedbackSessionForm);
		return mapping.findForward(CMSConstants.INIT_EVAL_TEACHER_FEEDBACK_SESSION);
		}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deleteTeacherFeedbackSession(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {
		EvaluationTeacherFeedbackSessionForm evaTeacherFeedbackSessionForm = (EvaluationTeacherFeedbackSessionForm)form;
		ActionMessages messages = new ActionMessages();
		setUserId(request, evaTeacherFeedbackSessionForm);
		ActionErrors errors = evaTeacherFeedbackSessionForm.validate(mapping, request);
		try{
			if(errors!=null && errors.isEmpty()){
				boolean isDeleted = EvaluationTeacherFeedbackSessionHandler.getInstance().deleteEvaTeacherFeedbackSession(evaTeacherFeedbackSessionForm);
				if(isDeleted){
					//if delete is success.
					ActionMessage message = new ActionMessage("knowledgepro.admin.stufeedback.session.DeleteSuccess");
					messages.add("messages", message);
					saveMessages(request, messages);
					evaTeacherFeedbackSessionForm.reset(mapping, request);	
				}else{
					//if delete is failure.
					ActionMessage message = new ActionMessage("knowledgepro.admin.stufeedback.session.DeleteFailure");
					messages.add("messages", message);
					saveMessages(request, messages);
					evaTeacherFeedbackSessionForm.reset(mapping, request);	
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
			evaTeacherFeedbackSessionForm.setErrorMessage(msg);
			evaTeacherFeedbackSessionForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		setEvaTeacherFeedbackSession(evaTeacherFeedbackSessionForm);
		return mapping.findForward(CMSConstants.INIT_EVAL_TEACHER_FEEDBACK_SESSION);
		}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward updateEvaluationTeacherFeedbackSession(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {
		EvaluationTeacherFeedbackSessionForm evaTeacherFeedbackSessionForm = (EvaluationTeacherFeedbackSessionForm)form;
		ActionMessages messages = new ActionMessages();
		setUserId(request, evaTeacherFeedbackSessionForm);
		ActionErrors errors = evaTeacherFeedbackSessionForm.validate(mapping, request);
		try{
			if(errors.isEmpty()){
				boolean isExist = EvaluationTeacherFeedbackSessionHandler.getInstance().checkExistTeacherFeedbackSession(evaTeacherFeedbackSessionForm);
				if(!isExist){
				boolean isUpdated = EvaluationTeacherFeedbackSessionHandler.getInstance().addTeacherFeedbackSession(evaTeacherFeedbackSessionForm, "edit");
				if(isUpdated){
					ActionMessage message = new ActionMessage("knowledgepro.admin.stufeedback.session.UpdatedSuccess");
					messages.add("messages", message);
					saveMessages(request, messages);
					evaTeacherFeedbackSessionForm.reset(mapping, request);	
				}else{
					ActionMessage message = new ActionMessage("knowledgepro.admin.stufeedback.session.UpdatedFailure");
					messages.add("messages", message);
					saveMessages(request, messages);
					evaTeacherFeedbackSessionForm.reset(mapping, request);	
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
			evaTeacherFeedbackSessionForm.setErrorMessage(msg);
			evaTeacherFeedbackSessionForm.setErrorStack(exception.getMessage());
			request.setAttribute("sessionList", "edit");
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		evaTeacherFeedbackSessionForm.clear(mapping, request);
		setEvaTeacherFeedbackSession(evaTeacherFeedbackSessionForm);
		return mapping.findForward(CMSConstants.INIT_EVAL_TEACHER_FEEDBACK_SESSION);
		}
}
