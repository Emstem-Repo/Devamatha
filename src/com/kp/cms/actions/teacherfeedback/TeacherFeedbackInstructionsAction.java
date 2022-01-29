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
import com.kp.cms.forms.teacherfeedback.TeacherFeedbackInstructionsForm;
import com.kp.cms.handlers.teacherfeedback.TeacherFeedbackInstructionsHandler;
import com.kp.cms.to.teacherfeedback.TeacherFeedbackInstructionsTO;

public class TeacherFeedbackInstructionsAction extends BaseDispatchAction {
	 /**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initTeacherFeedbackInstructions(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TeacherFeedbackInstructionsForm teacherFeedbackInstructionsForm = (TeacherFeedbackInstructionsForm)form;
		teacherFeedbackInstructionsForm.reset(mapping, request);
		setTeacherFeedbackInstructions(teacherFeedbackInstructionsForm);
		return mapping.findForward(CMSConstants.INIT_TEACHER_FEEDBACK_INST);
	}
	/**
	 * @param teacherFeedbackInstructionsForm
	 * @throws Exception
	 */
	private void setTeacherFeedbackInstructions( TeacherFeedbackInstructionsForm teacherFeedbackInstructionsForm) throws Exception{
		List<TeacherFeedbackInstructionsTO> instructionsList = TeacherFeedbackInstructionsHandler.getInstance().getInstructions();
			teacherFeedbackInstructionsForm.setTeacherFeedbackInsToList(instructionsList);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addTeacherFeedbackInstructions(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
				throws Exception {
		TeacherFeedbackInstructionsForm teacherFeedbackInstructionsForm = (TeacherFeedbackInstructionsForm)form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = teacherFeedbackInstructionsForm.validate(mapping, request);
		//call of setUserId method and setting to form.
		setUserId(request, teacherFeedbackInstructionsForm);
		validateDescription(teacherFeedbackInstructionsForm,errors);
		try{
			if(errors!=null && errors.isEmpty()){
				boolean isExist = TeacherFeedbackInstructionsHandler.getInstance().checkDuplicateInstructions(teacherFeedbackInstructionsForm);
				if(!isExist){
					boolean isAdded= TeacherFeedbackInstructionsHandler.getInstance().addTeacherFeedbackInstructions(teacherFeedbackInstructionsForm,"add");
					if(isAdded){
						//if adding is success.
						ActionMessage message = new ActionMessage("knowledgepro.admin.stufeedback.instructions.AddedSuccess");
						messages.add("messages", message);
						saveMessages(request, messages);
						teacherFeedbackInstructionsForm.reset(mapping, request);	
					}else{
						//if adding is failure.
						ActionMessage message = new ActionMessage("knowledgepro.admin.stufeedback.instructions.AddedFailure");
						messages.add("messages", message);
						saveMessages(request, messages);
						teacherFeedbackInstructionsForm.reset(mapping, request);	
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
			teacherFeedbackInstructionsForm.setErrorMessage(msg);
			teacherFeedbackInstructionsForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		//call of setLeaveAppInstructions method.
		setTeacherFeedbackInstructions(teacherFeedbackInstructionsForm);
		return mapping.findForward(CMSConstants.INIT_TEACHER_FEEDBACK_INST);
		}
	/**
	 * @param teacherFeedbackInstructionsForm
	 * @param errors
	 * @throws Exception
	 */
	private void validateDescription( TeacherFeedbackInstructionsForm teacherFeedbackInstructionsForm, ActionErrors errors)throws Exception {
		if(teacherFeedbackInstructionsForm.getDescription()!=null && !teacherFeedbackInstructionsForm.getDescription().isEmpty() && teacherFeedbackInstructionsForm.getDescription().length()>2000){
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
	public ActionForward editTeacherFeedbackInstructions(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
				throws Exception {
		TeacherFeedbackInstructionsForm teacherFeedbackInstructionsForm = (TeacherFeedbackInstructionsForm)form;
		ActionMessages messages = new ActionMessages();
		setUserId(request, teacherFeedbackInstructionsForm);
		try{
			TeacherFeedbackInstructionsHandler.getInstance().editFeedbackInstructions(teacherFeedbackInstructionsForm);
			request.setAttribute("instructions", "edit");
		}catch (BusinessException businessException) {
			String msgKey = super.handleBusinessException(businessException);
			ActionMessage message = new ActionMessage(msgKey);
			messages.add(CMSConstants.MESSAGES, message);
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} catch (Exception exception) {	
			String msg = super.handleApplicationException(exception);
			teacherFeedbackInstructionsForm.setErrorMessage(msg);
			teacherFeedbackInstructionsForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		setTeacherFeedbackInstructions(teacherFeedbackInstructionsForm);
		return mapping.findForward(CMSConstants.INIT_TEACHER_FEEDBACK_INST);
		}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deleteTeacherFeedbackInstructions(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {
		TeacherFeedbackInstructionsForm teacherFeedbackInstructionsForm = (TeacherFeedbackInstructionsForm)form;
		ActionMessages messages = new ActionMessages();
		setUserId(request, teacherFeedbackInstructionsForm);
		ActionErrors errors = teacherFeedbackInstructionsForm.validate(mapping, request);
		try{
			if(errors.isEmpty()){
				boolean isDeleted = TeacherFeedbackInstructionsHandler.getInstance().deleteInstructions(teacherFeedbackInstructionsForm);
				if(isDeleted){
					//if delete is success.
					ActionMessage message = new ActionMessage("knowledgepro.admin.stufeedback.instructions.DeleteSuccess");
					messages.add("messages", message);
					saveMessages(request, messages);
					teacherFeedbackInstructionsForm.reset(mapping, request);	
				}else{
					//if delete is failure.
					ActionMessage message = new ActionMessage("knowledgepro.admin.stufeedback.instructions.DeleteFailure");
					messages.add("messages", message);
					saveMessages(request, messages);
					teacherFeedbackInstructionsForm.reset(mapping, request);	
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
			teacherFeedbackInstructionsForm.setErrorMessage(msg);
			teacherFeedbackInstructionsForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		setTeacherFeedbackInstructions(teacherFeedbackInstructionsForm);
		return mapping.findForward(CMSConstants.INIT_TEACHER_FEEDBACK_INST);
		}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward updateTeacherFeedbackInstructions(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {
		TeacherFeedbackInstructionsForm teacherFeedbackInstructionsForm = (TeacherFeedbackInstructionsForm)form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = teacherFeedbackInstructionsForm.validate(mapping, request);
		//call of setUserId method and setting to form.
		setUserId(request, teacherFeedbackInstructionsForm);
		validateDescription(teacherFeedbackInstructionsForm,errors);
		try{
			if(errors.isEmpty()){
				boolean isUpdated = TeacherFeedbackInstructionsHandler.getInstance().addTeacherFeedbackInstructions(teacherFeedbackInstructionsForm,"edit");
				if(isUpdated){
					//if adding is success.
					ActionMessage message = new ActionMessage("knowledgepro.admin.stufeedback.instructions.UpdatedSuccess");
					messages.add("messages", message);
					saveMessages(request, messages);
					teacherFeedbackInstructionsForm.reset(mapping, request);	
				}else{
					//if adding is failure.
					ActionMessage message = new ActionMessage("knowledgepro.admin.stufeedback.instructions.UpdatedFailure");
					messages.add("messages", message);
					saveMessages(request, messages);
					teacherFeedbackInstructionsForm.reset(mapping, request);	
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
			teacherFeedbackInstructionsForm.setErrorMessage(msg);
			teacherFeedbackInstructionsForm.setErrorStack(exception.getMessage());
			request.setAttribute("instructions", "edit");
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		setTeacherFeedbackInstructions(teacherFeedbackInstructionsForm);
		return mapping.findForward(CMSConstants.INIT_TEACHER_FEEDBACK_INST);
		}
}
