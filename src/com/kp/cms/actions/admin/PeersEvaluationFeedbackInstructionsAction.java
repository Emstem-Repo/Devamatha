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
import com.kp.cms.forms.admin.PeersEvaluationFeedbackInstuctionsForm;
import com.kp.cms.forms.admin.StudentFeedbackInstructionsForm;
import com.kp.cms.handlers.admin.PeersEvaluationFeedbackInstructionsHandler;
import com.kp.cms.handlers.admin.StudentFeedbackInstructionsHandler;
import com.kp.cms.to.admin.StudentFeedbackInstructionsTO;

public class PeersEvaluationFeedbackInstructionsAction extends BaseDispatchAction {
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initInstructions(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PeersEvaluationFeedbackInstuctionsForm instructionsForm = (PeersEvaluationFeedbackInstuctionsForm)form;
		setUserId(request, instructionsForm);
		instructionsForm.reset(mapping, request);
		try{
			setPeersInstructions(instructionsForm);
		}catch (Exception exception) {	
			String msg = super.handleApplicationException(exception);
			instructionsForm.setErrorMessage(msg);
			instructionsForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.INIT_PEERS_INSTRUCTIONS);
				
	}

	/**
	 * @param instructionsForm
	 * @throws Exception
	 */
	private void setPeersInstructions( PeersEvaluationFeedbackInstuctionsForm instructionsForm) throws Exception{
		List<StudentFeedbackInstructionsTO> toList = PeersEvaluationFeedbackInstructionsHandler.getInstance().getPeersInstructionsList();
		instructionsForm.setInstructionsTOsList(toList);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addPeersInstructions(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PeersEvaluationFeedbackInstuctionsForm instructionsForm = (PeersEvaluationFeedbackInstuctionsForm)form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = instructionsForm.validate(mapping, request);
		//call of setUserId method and setting to form.
		setUserId(request, instructionsForm);
		validateDescription(instructionsForm,errors);
		try{
			if(errors.isEmpty()){
				
				boolean isExist = PeersEvaluationFeedbackInstructionsHandler.getInstance().checkDuplicateInstructions(instructionsForm);
				if(!isExist){
					boolean isAdded = PeersEvaluationFeedbackInstructionsHandler.getInstance().addPeersInstructions(instructionsForm,"Add");
					if(isAdded){
						//if adding is success.
						ActionMessage message = new ActionMessage("knowledgepro.admin.stufeedback.instructions.AddedSuccess");
						messages.add("messages", message);
						saveMessages(request, messages);
						instructionsForm.reset(mapping, request);	
					}else{
						//if adding is failure.
						ActionMessage message = new ActionMessage("knowledgepro.admin.stufeedback.instructions.AddedFailure");
						messages.add("messages", message);
						saveMessages(request, messages);
						instructionsForm.reset(mapping, request);	
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
			instructionsForm.setErrorMessage(msg);
			instructionsForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		setPeersInstructions(instructionsForm);
		return mapping.findForward(CMSConstants.INIT_PEERS_INSTRUCTIONS);
	}
	/**
	 * @param instructionsForm
	 * @param errors
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	private void validateDescription( PeersEvaluationFeedbackInstuctionsForm instructionsForm,
			ActionErrors errors)throws Exception {
		if(instructionsForm.getDescription()!=null && !instructionsForm.getDescription().isEmpty() && instructionsForm.getDescription().length()>2000){
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
	public ActionForward editPeersInstructions(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PeersEvaluationFeedbackInstuctionsForm instructionsForm = (PeersEvaluationFeedbackInstuctionsForm)form;
		ActionMessages messages = new ActionMessages();
		setUserId(request, instructionsForm);
		try{
			PeersEvaluationFeedbackInstructionsHandler.getInstance().editFeedbackInstructions(instructionsForm);
			request.setAttribute("instructions", "edit");
		}catch (BusinessException businessException) {
			String msgKey = super.handleBusinessException(businessException);
			ActionMessage message = new ActionMessage(msgKey);
			messages.add(CMSConstants.MESSAGES, message);
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} catch (Exception exception) {	
			String msg = super.handleApplicationException(exception);
			instructionsForm.setErrorMessage(msg);
			instructionsForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		setPeersInstructions(instructionsForm);
		return mapping.findForward(CMSConstants.INIT_PEERS_INSTRUCTIONS);
		}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deletePeersInstructions(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PeersEvaluationFeedbackInstuctionsForm instructionsForm = (PeersEvaluationFeedbackInstuctionsForm)form;
		ActionMessages messages = new ActionMessages();
		setUserId(request, instructionsForm);
		ActionErrors errors = instructionsForm.validate(mapping, request);
		try{
			if(errors.isEmpty()){
				boolean isDeleted = PeersEvaluationFeedbackInstructionsHandler.getInstance().deleteInstructions(instructionsForm);
				if(isDeleted){
					//if delete is success.
					ActionMessage message = new ActionMessage("knowledgepro.admin.stufeedback.instructions.DeleteSuccess");
					messages.add("messages", message);
					saveMessages(request, messages);
					instructionsForm.reset(mapping, request);	
				}else{
					//if delete is failure.
					ActionMessage message = new ActionMessage("knowledgepro.admin.stufeedback.instructions.DeleteFailure");
					messages.add("messages", message);
					saveMessages(request, messages);
					instructionsForm.reset(mapping, request);	
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
			instructionsForm.setErrorMessage(msg);
			instructionsForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		setPeersInstructions(instructionsForm);
		return mapping.findForward(CMSConstants.INIT_PEERS_INSTRUCTIONS);
		}
		/**
		 * @param mapping
		 * @param form
		 * @param request
		 * @param response
		 * @return
		 * @throws Exception
		 */
		public ActionForward updatePeersInstructions(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {
			PeersEvaluationFeedbackInstuctionsForm instructionsForm = (PeersEvaluationFeedbackInstuctionsForm)form;
			ActionMessages messages = new ActionMessages();
			ActionErrors errors = instructionsForm.validate(mapping, request);
			//call of setUserId method and setting to form.
			setUserId(request, instructionsForm);
			validateDescription(instructionsForm,errors);
			try{
				if(errors.isEmpty()){
					
					boolean isExist = PeersEvaluationFeedbackInstructionsHandler.getInstance().checkDuplicateInstructions(instructionsForm);
					if(!isExist){
						boolean isAdded = PeersEvaluationFeedbackInstructionsHandler.getInstance().addPeersInstructions(instructionsForm,"Edit");
						if(isAdded){
							//if adding is success.
							ActionMessage message = new ActionMessage("knowledgepro.admin.stufeedback.instructions.UpdatedSuccess");
							messages.add("messages", message);
							saveMessages(request, messages);
							instructionsForm.reset(mapping, request);	
						}else{
							//if adding is failure.
							ActionMessage message = new ActionMessage("knowledgepro.admin.stufeedback.instructions.UpdatedFailure");
							messages.add("messages", message);
							saveMessages(request, messages);
							instructionsForm.reset(mapping, request);	
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
				instructionsForm.setErrorMessage(msg);
				instructionsForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
			setPeersInstructions(instructionsForm);
			return mapping.findForward(CMSConstants.INIT_PEERS_INSTRUCTIONS);
		}
}

