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
import com.kp.cms.forms.admin.PeersEvaluationGroupsForm;
import com.kp.cms.handlers.admin.PeersEvaluationGroupsHandler;
import com.kp.cms.to.admin.PeersEvaluationGroupsTO;

public class PeersEvaluationGroupsAction extends BaseDispatchAction{
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initPeersEvaluationGroups(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PeersEvaluationGroupsForm peersEvaluationGroupsForm = (PeersEvaluationGroupsForm)form;
		setUserId(request, peersEvaluationGroupsForm);
		peersEvaluationGroupsForm.clear(mapping,request);
		try{
			String formName = mapping.getName();
			request.getSession().setAttribute(CMSConstants.FORMNAME, formName);
			setGroupsListToRequest(peersEvaluationGroupsForm);
		}catch (Exception e) {
			String msg = super.handleApplicationException(e);
			peersEvaluationGroupsForm.setErrorMessage(msg);
			peersEvaluationGroupsForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.INIT_PEERS_EVAL_GROUPS);
	}

	/**
	 * @param peersEvaluationGroupsForm
	 * @throws Exception
	 */
	/**
	 * @param peersEvaluationGroupsForm
	 * @throws Exception
	 */
	private void setGroupsListToRequest( PeersEvaluationGroupsForm peersEvaluationGroupsForm) throws Exception{
		List<PeersEvaluationGroupsTO> groupsListTO = PeersEvaluationGroupsHandler.getInstance().getPeersEvaluationGroupsList();
		peersEvaluationGroupsForm.setPeersEvaluationGroupList(groupsListTO);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addPeersEvaluationGroups(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PeersEvaluationGroupsForm peersEvaluationGroupsForm = (PeersEvaluationGroupsForm)form;
		setUserId(request, peersEvaluationGroupsForm);
		ActionMessages messages = new ActionMessages();
		ActionErrors errors=peersEvaluationGroupsForm.validate(mapping, request);
		boolean isAdded = false;
		try{
			if (errors.isEmpty()) {
			boolean isDuplicate = PeersEvaluationGroupsHandler.getInstance().checkDuplicate(peersEvaluationGroupsForm);
			if(!isDuplicate){
				isAdded = PeersEvaluationGroupsHandler.getInstance().addPeersEvaluationGroups(peersEvaluationGroupsForm,"Add");
				if(isAdded){
					ActionMessage message = new ActionError( "knowledgepro.admin.peers.evaluation.groups.addsuccess");
					messages.add("messages", message);
					saveMessages(request, messages);
					peersEvaluationGroupsForm.clear(mapping,request);
				}else{
					errors .add( "error", new ActionError( "knowledgepro.admin.peers.evaluation.groups.addfailure"));
					saveErrors(request, errors);
				}
			}else{
				errors .add( "error", new ActionError( "knowledgepro.admin.peers.evaluation.groups.exists.already"));
				saveErrors(request, errors);
			}
			}else {
				saveErrors(request, errors);
			}
		}catch (BusinessException businessException) {
			String msgKey = super.handleBusinessException(businessException);
			ActionMessage message = new ActionMessage(msgKey);
			messages.add(CMSConstants.MESSAGES, message);
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} catch (Exception exception) {
			String msg = super.handleApplicationException(exception);
			peersEvaluationGroupsForm.setErrorMessage(msg);
			peersEvaluationGroupsForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		setGroupsListToRequest(peersEvaluationGroupsForm);
		return mapping.findForward(CMSConstants.INIT_PEERS_EVAL_GROUPS);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward updatePeersEvaluationGroups(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PeersEvaluationGroupsForm peersEvaluationGroupsForm = (PeersEvaluationGroupsForm)form;
		setUserId(request, peersEvaluationGroupsForm);
		 ActionErrors errors=peersEvaluationGroupsForm.validate(mapping, request);
		ActionMessages messages = new ActionMessages();
		boolean isUpdate = false;
		try{
			if (errors.isEmpty()) {
			boolean isDuplicate = PeersEvaluationGroupsHandler.getInstance().checkDuplicate(peersEvaluationGroupsForm);
			if(!isDuplicate){
				isUpdate = PeersEvaluationGroupsHandler.getInstance().addPeersEvaluationGroups(peersEvaluationGroupsForm,"Edit");
				if(isUpdate){
					ActionMessage message = new ActionError( "knowledgepro.admin.peers.evaluation.groups.updatesuccess");
					messages.add("messages", message);
					saveMessages(request, messages);
					peersEvaluationGroupsForm.clear(mapping,request);
				}else{
					request.setAttribute("peersEvaluationGroups", "edit");
					errors .add( "error", new ActionError( "knowledgepro.admin.peers.evaluation.groups.updatefailed"));
					saveErrors(request, errors);
				}
			}else{
				request.setAttribute("peersEvaluationGroups", "edit");
				errors .add( "error", new ActionError( "knowledgepro.admin.peers.evaluation.groups.exists.already"));
				saveErrors(request, errors);
			}
			}else {
				request.setAttribute("peersEvaluationGroups", "edit");
				saveErrors(request, errors);
			}
		}catch (BusinessException businessException) {
			String msgKey = super.handleBusinessException(businessException);
			ActionMessage message = new ActionMessage(msgKey);
			messages.add(CMSConstants.MESSAGES, message);
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} catch (Exception exception) {
			String msg = super.handleApplicationException(exception);
			peersEvaluationGroupsForm.setErrorMessage(msg);
			peersEvaluationGroupsForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		setGroupsListToRequest(peersEvaluationGroupsForm);
		return mapping.findForward(CMSConstants.INIT_PEERS_EVAL_GROUPS);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deletePeersEvaluationGroups(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PeersEvaluationGroupsForm peersEvaluationGroupsForm = (PeersEvaluationGroupsForm)form;
		setUserId(request, peersEvaluationGroupsForm);
		ActionMessages messages = new ActionMessages();
		ActionErrors errors=peersEvaluationGroupsForm.validate(mapping, request);
		boolean isDeleted =false;
		try{
			if (errors.isEmpty()) {
			isDeleted = PeersEvaluationGroupsHandler.getInstance().deletePeersEvaluationGroups(peersEvaluationGroupsForm);
			if(isDeleted){
				ActionMessage message = new ActionError( "knowledgepro.admin.peers.evaluation.groups.deletesuccess");
				messages.add("messages", message);
				saveMessages(request, messages);
				peersEvaluationGroupsForm.clear(mapping,request);
			}else{
				errors .add( "error", new ActionError( "knowledgepro.admin.peers.evaluation.groups.deletefailed"));
				saveErrors(request, errors);
			}
			}else {
				saveErrors(request, errors);
			}
		}catch (Exception exception) {
			String msg = super.handleApplicationException(exception);
			peersEvaluationGroupsForm.setErrorMessage(msg);
			peersEvaluationGroupsForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		setGroupsListToRequest(peersEvaluationGroupsForm);
		return mapping.findForward(CMSConstants.INIT_PEERS_EVAL_GROUPS);
	}
}
