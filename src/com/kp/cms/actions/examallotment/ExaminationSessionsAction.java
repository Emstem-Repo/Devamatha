package com.kp.cms.actions.examallotment;

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
import com.kp.cms.forms.admission.InterviewSelectionScheduleForm;
import com.kp.cms.forms.examallotment.ExaminationSessionsForm;
import com.kp.cms.handlers.examallotment.ExaminationSessionsHandler;

public class ExaminationSessionsAction extends BaseDispatchAction{
	ExaminationSessionsHandler examinationSessionsHandler=ExaminationSessionsHandler.getInstance();
	//init method
	public ActionForward initExaminationSessions(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ExaminationSessionsForm examinationSessionsForm=(ExaminationSessionsForm)form;
		clear(examinationSessionsForm);
		examinationSessionsHandler.getListOfExaminationSessions(examinationSessionsForm);
		return mapping.findForward("initExaminationSession");
	}
	private void clear(ExaminationSessionsForm examinationSessionsForm) throws Exception{
		examinationSessionsForm.setSession(null);
		examinationSessionsForm.setSessionDesc(null);
		examinationSessionsForm.setOrderNo(null);
		examinationSessionsForm.setTimings(null);
		
	}
	/**
	 * add
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addExaminationSessions(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request,HttpServletResponse response) throws Exception {
		ExaminationSessionsForm examinationSessionsForm=(ExaminationSessionsForm)form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = examinationSessionsForm.validate(mapping, request);
		boolean isAdded=false;
		setUserId(request, examinationSessionsForm);
		try {
			if(errors.isEmpty()){
				boolean isDuplicate=examinationSessionsHandler.checkDuplicate(examinationSessionsForm,"add");
				if(isDuplicate){
					errors.add("error", new ActionError("knowledgepro.admin.programtype.name.exists","Session"));
					saveErrors(request, errors);
				}else {
					isAdded=examinationSessionsHandler.add(examinationSessionsForm);
					if(isAdded){
						clear(examinationSessionsForm);
						ActionMessage message = new ActionMessage("knowledgepro.admin.programtype.name.addsuccess","Examination Session");
						messages.add("messages", message);
						saveMessages(request, messages);
					}else {
						errors.add("error", new ActionError("knowledgepro.admin.programtype.name.addfail","Examination Session"));
						saveErrors(request, errors);
					}
				}
			}else{
				saveErrors(request, errors);
			}
		} catch (Exception e) {
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				examinationSessionsForm.setErrorMessage(msg);
				examinationSessionsForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		examinationSessionsHandler.getListOfExaminationSessions(examinationSessionsForm);
		return mapping.findForward("initExaminationSession");
	}
	/**
	 * delete
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward delete(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request,HttpServletResponse response) throws Exception {
		ExaminationSessionsForm examinationSessionsForm=(ExaminationSessionsForm)form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors=new ActionErrors();
		boolean isDelete=false;
		setUserId(request, examinationSessionsForm);
		try {
			isDelete=examinationSessionsHandler.delete(examinationSessionsForm.getId(),examinationSessionsForm.getUserId());
			if(isDelete){
				ActionMessage message = new ActionMessage("knowledgepro.admin.programtype.name.deletesuccess","Examination Session");
				messages.add("messages", message);
				saveMessages(request, messages);
			}else {
				errors.add("error", new ActionError("knowledgepro.admin.programtype.name.deletefail","Examination Session"));
				saveErrors(request, errors);
			}
		} catch (Exception e) {
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				examinationSessionsForm.setErrorMessage(msg);
				examinationSessionsForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		examinationSessionsHandler.getListOfExaminationSessions(examinationSessionsForm);
		return mapping.findForward("initExaminationSession");
	}
	/**
	 * edit
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward edit(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request,HttpServletResponse response) throws Exception {
		ExaminationSessionsForm examinationSessionsForm=(ExaminationSessionsForm)form;
		ActionMessages messages = new ActionMessages();
		clear(examinationSessionsForm);
		setUserId(request, examinationSessionsForm);
		try {
			examinationSessionsHandler.edit(examinationSessionsForm);
			request.setAttribute("admOperation", "edit");
		} catch (Exception e) {
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				examinationSessionsForm.setErrorMessage(msg);
				examinationSessionsForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		examinationSessionsHandler.getListOfExaminationSessions(examinationSessionsForm);
		return mapping.findForward("initExaminationSession");
	}
	/**
	 * edit
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward update(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request,HttpServletResponse response) throws Exception {
		ExaminationSessionsForm examinationSessionsForm=(ExaminationSessionsForm)form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = examinationSessionsForm.validate(mapping, request);
		setUserId(request, examinationSessionsForm);
		boolean isUpdate;
		try {
			if(errors.isEmpty()){
				boolean isDuplicate=examinationSessionsHandler.checkDuplicate(examinationSessionsForm,"update");
				if(isDuplicate){
					errors.add("error", new ActionError("knowledgepro.admin.programtype.name.exists","Session"));
					saveErrors(request, errors);
					request.setAttribute("admOperation", "edit");
				}else {
					isUpdate=examinationSessionsHandler.update(examinationSessionsForm);
					if(isUpdate){
						clear(examinationSessionsForm);
						ActionMessage message = new ActionMessage("knowledgepro.admin.programtype.name.updatesuccess","Examination Session");
						messages.add("messages", message);
						saveMessages(request, messages);
					}else {
						errors.add("error", new ActionError("knowledgepro.admin.programtype.name.updatefail","Examination Session"));
						saveErrors(request, errors);
						request.setAttribute("admOperation", "edit");
					}
				}
			}else{
				saveErrors(request, errors);
				request.setAttribute("admOperation", "edit");
			}
		} catch (Exception e) {
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				examinationSessionsForm.setErrorMessage(msg);
				examinationSessionsForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		examinationSessionsHandler.getListOfExaminationSessions(examinationSessionsForm);
		return mapping.findForward("initExaminationSession");
	}
}
