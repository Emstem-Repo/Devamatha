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
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.admin.PeersFeedbackSessionForm;
import com.kp.cms.handlers.admin.EvaluationStudentFeedbackSessionHandler;
import com.kp.cms.handlers.admin.PeersFeedbackSessionHandler;
import com.kp.cms.to.admin.PeersFeedbackSessionTo;

public class PeersFeedbackSessionAction extends BaseDispatchAction{
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initPeersFeedbackSession(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PeersFeedbackSessionForm peersFeedbackSessionForm = (PeersFeedbackSessionForm)form;
		peersFeedbackSessionForm.reset(mapping, request);
		setUserId(request, peersFeedbackSessionForm);
		try{
			setRequestedDataToForm(peersFeedbackSessionForm);
		}catch (Exception e) {
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				peersFeedbackSessionForm.setErrorMessage(msg);
				peersFeedbackSessionForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		return mapping.findForward(CMSConstants.INIT_PEERS_FEEDBACK_SESSION);
	}

	/**
	 * @param peersFeedbackSessionForm
	 * @throws Exception
	 */
	private void setRequestedDataToForm( PeersFeedbackSessionForm peersFeedbackSessionForm) throws Exception{
		List<PeersFeedbackSessionTo> peersFeedbackToList = PeersFeedbackSessionHandler.getInstance().getPeersFeedbackSessionDetails();
		peersFeedbackSessionForm.setSessionTos(peersFeedbackToList);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addPeersFeedbackDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PeersFeedbackSessionForm peersFeedbackSessionForm = (PeersFeedbackSessionForm)form;
		setUserId(request, peersFeedbackSessionForm);
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = peersFeedbackSessionForm.validate(mapping, request);
		try{
			if(errors.isEmpty()){
				boolean isExist = PeersFeedbackSessionHandler.getInstance().checkExistPeersFeedbackSession(peersFeedbackSessionForm);
				if(!isExist){
				boolean isAdded = PeersFeedbackSessionHandler.getInstance().addPeersFeedbackSession(peersFeedbackSessionForm,"add");
				if(isAdded){
					//if adding is success.
					ActionMessage message = new ActionMessage("knowledgepro.admin.stufeedback.session.AddedSuccess");
					messages.add("messages", message);
					saveMessages(request, messages);
					peersFeedbackSessionForm.reset(mapping, request);	
				}else{
					//if adding is failure.
					ActionMessage message = new ActionMessage("knowledgepro.admin.stufeedback.session.AddedFailure");
					messages.add("messages", message);
					saveMessages(request, messages);
					peersFeedbackSessionForm.reset(mapping, request);	
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
			peersFeedbackSessionForm.setErrorMessage(msg);
			peersFeedbackSessionForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		setRequestedDataToForm(peersFeedbackSessionForm);
		return mapping.findForward(CMSConstants.INIT_PEERS_FEEDBACK_SESSION);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward editPeersFeedbackSession(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PeersFeedbackSessionForm peersFeedbackSessionForm = (PeersFeedbackSessionForm)form;
		setUserId(request, peersFeedbackSessionForm);
		ActionMessages messages = new ActionMessages();
		try{
			PeersFeedbackSessionHandler.getInstance().editPeersFeedbackSession(peersFeedbackSessionForm);
			request.setAttribute("peersSessions", "edit");
		}catch (BusinessException businessException) {
			String msgKey = super.handleBusinessException(businessException);
			ActionMessage message = new ActionMessage(msgKey);
			messages.add(CMSConstants.MESSAGES, message);
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} catch (Exception exception) {	
			String msg = super.handleApplicationException(exception);
			peersFeedbackSessionForm.setErrorMessage(msg);
			peersFeedbackSessionForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		setRequestedDataToForm(peersFeedbackSessionForm);
		return mapping.findForward(CMSConstants.INIT_PEERS_FEEDBACK_SESSION);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward updatePeersFeedbackSession(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PeersFeedbackSessionForm peersFeedbackSessionForm = (PeersFeedbackSessionForm)form;
		setUserId(request, peersFeedbackSessionForm);
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = peersFeedbackSessionForm.validate(mapping, request);
		try{
			if(errors.isEmpty()){
				boolean isExist = PeersFeedbackSessionHandler.getInstance().checkExistPeersFeedbackSession(peersFeedbackSessionForm);
				if(!isExist){
				boolean isUpdated = PeersFeedbackSessionHandler.getInstance().addPeersFeedbackSession(peersFeedbackSessionForm,"edit");
				if(isUpdated){
					ActionMessage message = new ActionMessage("knowledgepro.admin.stufeedback.session.UpdatedSuccess");
					messages.add("messages", message);
					saveMessages(request, messages);
					peersFeedbackSessionForm.reset(mapping, request);	
				}else{
					ActionMessage message = new ActionMessage("knowledgepro.admin.stufeedback.session.UpdatedFailure");
					messages.add("messages", message);
					saveMessages(request, messages);
					peersFeedbackSessionForm.reset(mapping, request);	
					}
				}else{
					errors.add("CMSConstants.ERROR", new ActionError("knowledgepro.admin.stufeedback.session.Exist"));
					request.setAttribute("peersSessions", "edit");
					saveErrors(request, errors);
				}
			}else{
				errors.add(messages);
				request.setAttribute("peersSessions", "edit");
				saveErrors(request, errors);
			}
		}catch (BusinessException businessException) {
			String msgKey = super.handleBusinessException(businessException);
			ActionMessage message = new ActionMessage(msgKey);
			messages.add(CMSConstants.MESSAGES, message);
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} catch (Exception exception) {	
			String msg = super.handleApplicationException(exception);
			peersFeedbackSessionForm.setErrorMessage(msg);
			peersFeedbackSessionForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		request.setAttribute("peersSessions", "add");
		setRequestedDataToForm(peersFeedbackSessionForm);
		return mapping.findForward(CMSConstants.INIT_PEERS_FEEDBACK_SESSION);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deletePeersFeedbackSession(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PeersFeedbackSessionForm peersFeedbackSessionForm = (PeersFeedbackSessionForm)form;
		setUserId(request, peersFeedbackSessionForm);
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = new ActionErrors();
		try{
			if(errors!=null && errors.isEmpty()){
				boolean isDeleted = PeersFeedbackSessionHandler.getInstance().deletePeersFeedbackSession(peersFeedbackSessionForm);
				if(isDeleted){
					//if delete is success.
					ActionMessage message = new ActionMessage("knowledgepro.admin.stufeedback.session.DeleteSuccess");
					messages.add("messages", message);
					saveMessages(request, messages);
					peersFeedbackSessionForm.reset(mapping, request);	
				}else{
					//if delete is failure.
					ActionMessage message = new ActionMessage("knowledgepro.admin.stufeedback.session.DeleteFailure");
					messages.add("messages", message);
					saveMessages(request, messages);
					peersFeedbackSessionForm.reset(mapping, request);	
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
			peersFeedbackSessionForm.setErrorMessage(msg);
			peersFeedbackSessionForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		setRequestedDataToForm(peersFeedbackSessionForm);
		return mapping.findForward(CMSConstants.INIT_PEERS_FEEDBACK_SESSION);
	}
}
