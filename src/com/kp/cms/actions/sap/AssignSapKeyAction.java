package com.kp.cms.actions.sap;

import java.util.ArrayList;
import java.util.Iterator;
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
import com.kp.cms.forms.sap.AssignSapKeyForm;
import com.kp.cms.handlers.sap.AssignSapKeyHandler;
import com.kp.cms.to.hostel.AbsentiesListTo;
import com.kp.cms.to.sap.SapKeysTo;

public class AssignSapKeyAction extends BaseDispatchAction{
	AssignSapKeyHandler handler=AssignSapKeyHandler.getInstance();
	/**
	 * to get initial page as default values
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initAssignSapKey(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		AssignSapKeyForm assignSapKeyForm=(AssignSapKeyForm)form;
		setRequiredData(assignSapKeyForm);
		reset(assignSapKeyForm);
		return mapping.findForward("initAssignSapKey");
	}

	private void setRequiredData(AssignSapKeyForm assignSapKeyForm) throws Exception{
		handler.getclassMap(assignSapKeyForm);
	}

	private void reset(AssignSapKeyForm assignSapKeyForm)throws Exception {
		assignSapKeyForm.setStartDate(null);
		assignSapKeyForm.setEndDate(null);
		assignSapKeyForm.setStatus("Pending");
		assignSapKeyForm.setClassId(null);
	}
	/**
	 * to get the student details who are registered for sap exam
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward registeredData(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		AssignSapKeyForm assignSapKeyForm=(AssignSapKeyForm)form;
		ActionMessages messages = new ActionMessages();
		try{
			handler.getRegisterdStudentsForSap(assignSapKeyForm);
		}catch (Exception e) {
			e.printStackTrace();
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				assignSapKeyForm.setErrorMessage(msg);
				assignSapKeyForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		if(assignSapKeyForm.getStatus().equalsIgnoreCase("Pending")){
			return mapping.findForward("registerdStudentsPage");
		}else{
			return mapping.findForward("registerdStudentsPageWhoHaveKey");
		}
	}
	/**
	 * for selected students the keys are updated in sap registration table
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward updateKeysInSapRegistration(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		AssignSapKeyForm assignSapKeyForm=(AssignSapKeyForm)form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = new ActionErrors();
		try{
				boolean flag=handler.updateKeys(assignSapKeyForm,request);
				if(flag){
					ActionMessage message = new ActionMessage("knowledgepro.sap.keys.update.success");
					messages.add("messages", message);
					saveMessages(request, messages);
				}else{
					errors.add("error", new ActionError("knowledgepro.sap.keys.update.fail"));
					saveErrors(request, errors);
				}
				handler.getRegisterdStudentsForSap(assignSapKeyForm);
			
		}catch (Exception e) {
			e.printStackTrace();
			if(request.getAttribute("keys")!=null && request.getAttribute("keys").equals("key")){
				errors.add("error", new ActionError("knowledgepro.sap.keys.not.available"));
				saveErrors(request, errors);
				return mapping.findForward("registerdStudentsPage");
			}
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				assignSapKeyForm.setErrorMessage(msg);
				assignSapKeyForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		return mapping.findForward("registerdStudentsPage");
	}
}
