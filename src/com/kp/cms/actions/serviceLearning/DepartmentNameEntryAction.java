package com.kp.cms.actions.serviceLearning;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
import com.kp.cms.actions.admin.CasteAction;
import com.kp.cms.bo.serviceLearning.DepartmentNameEntry;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.admin.CasteForm;
import com.kp.cms.forms.serviceLearning.DepartmentNameEntryForm;
import com.kp.cms.handlers.admin.CasteHandler;
import com.kp.cms.handlers.serviceLearning.DepartmentNameEntryHandler;
import com.kp.cms.to.admin.CasteTO;
import com.kp.cms.to.serviceLearning.DepartmentNameEntryTo;

public class DepartmentNameEntryAction extends BaseDispatchAction  {
	
	private static final Log log = LogFactory.getLog(DepartmentNameEntryAction.class);
	/**
	 * Performs the get Caste action.
     * @param mapping  The ActionMapping used to select this instance
     * @param form     The optional ActionForm bean for this request (if any)
     * @param request  The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return The forward to which control should be transferred.
     * @throws Exception if an exception occurs
	 */
	public ActionForward initgetDepartments(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DepartmentNameEntryForm departmentNameEntryForm = (DepartmentNameEntryForm) form;
		//It use for Help,Don't Remove
		HttpSession session=request.getSession();
		session.setAttribute("field","Caste");
		try{
			List<DepartmentNameEntryTo> departmentNameEntryToList = DepartmentNameEntryHandler.getInstance().getDepartmentNames();
			departmentNameEntryForm.setDepartmentNameEntryToList(departmentNameEntryToList);
			}catch (Exception e) {
				log.error("Error occured in caste Entry Action", e);
				String msg = super.handleApplicationException(e);
				departmentNameEntryForm.setErrorMessage(msg);
				departmentNameEntryForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
				
			}
		return mapping.findForward(CMSConstants.DEPARTMENT_NAME_ENTRY);
	}
	
	public ActionForward addHostelNameEntry(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		
		DepartmentNameEntryForm departmentNameEntryForm = (DepartmentNameEntryForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = departmentNameEntryForm.validate(mapping, request);

		boolean isNameAdded = false;
		if (errors.isEmpty()) {
			String name=departmentNameEntryForm.getDepartmentName();
			try{
			setUserId(request, departmentNameEntryForm);
			isNameAdded = DepartmentNameEntryHandler.getInstance().addDepartmentNameEntry(departmentNameEntryForm,"Add",request);
			List<DepartmentNameEntryTo> departmentNameEntryToList = DepartmentNameEntryHandler.getInstance().getDepartmentNames();
			departmentNameEntryForm.setDepartmentNameEntryToList(departmentNameEntryToList);
			}catch (Exception e) {
				if(e instanceof DuplicateException){
					errors.add("error", new ActionError("knowledgepro.servicelearing.departmentname.exists",name));
					saveErrors(request, errors);
					List<DepartmentNameEntryTo> departmentNameEntryToList = DepartmentNameEntryHandler.getInstance().getDepartmentNames();
					departmentNameEntryForm.setDepartmentNameEntryToList(departmentNameEntryToList);
					return mapping.findForward(CMSConstants.DEPARTMENT_NAME_ENTRY);	
				}
				if(e instanceof ReActivateException){
					errors.add("error", new ActionError("knowledgepro.servicelearing.departmentname.addfailure.alreadyexist.reactivate",name));
					saveErrors(request, errors);
					//HttpSession session=request.getSession();
					//session.setAttribute("HostelNameEntry",attendanceEntryDayForm.getAttendanceEntryDay());
					List<DepartmentNameEntryTo> departmentNameEntryToList = DepartmentNameEntryHandler.getInstance().getDepartmentNames();
					departmentNameEntryForm.setDepartmentNameEntryToList(departmentNameEntryToList);
					return mapping.findForward(CMSConstants.DEPARTMENT_NAME_ENTRY);	
				}
				log.error("Error occured in caste Entry Action", e);
				String msg = super.handleApplicationException(e);
				departmentNameEntryForm.setErrorMessage(msg);
				departmentNameEntryForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
				
			}
			if (isNameAdded) {
				departmentNameEntryForm.reset(mapping, request);
				ActionMessage message = new ActionMessage("knowledgepro.servicelearing.departmentname.addsuccess", departmentNameEntryForm.getDepartmentName());
				messages.add("messages", message);
				saveMessages(request, messages);
				departmentNameEntryForm.reset(mapping, request);
				List<DepartmentNameEntryTo> departmentNameEntryToList = DepartmentNameEntryHandler.getInstance().getDepartmentNames();
				departmentNameEntryForm.setDepartmentNameEntryToList(departmentNameEntryToList);
			}else{
				// failed
				errors.add("error", new ActionError("knowledgepro.servicelearing.departmentname.addfailure",  departmentNameEntryForm.getDepartmentName()));
				saveErrors(request, errors);
				List<DepartmentNameEntryTo> departmentNameEntryToList = DepartmentNameEntryHandler.getInstance().getDepartmentNames();
				departmentNameEntryForm.setDepartmentNameEntryToList(departmentNameEntryToList);
			}
		} else {
			saveErrors(request, errors);
			List<DepartmentNameEntryTo> departmentNameEntryToList = DepartmentNameEntryHandler.getInstance().getDepartmentNames();
			departmentNameEntryForm.setDepartmentNameEntryToList(departmentNameEntryToList);
			return mapping.findForward(CMSConstants.DEPARTMENT_NAME_ENTRY);
		}
		
		
		return mapping.findForward(CMSConstants.DEPARTMENT_NAME_ENTRY);
	}
	public ActionForward deleteDepartmentNameEntry(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DepartmentNameEntryForm departmentNameEntryForm = (DepartmentNameEntryForm) form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		int id =Integer.parseInt(departmentNameEntryForm.getId());
		String name=departmentNameEntryForm.getDepartmentName();
		boolean isAttendanceEntryDay=false;
		try{
			setUserId(request, departmentNameEntryForm);
			isAttendanceEntryDay = DepartmentNameEntryHandler.getInstance().deleteDepartmentNameEntry(id, false, departmentNameEntryForm);
				
		}catch (Exception e) {
			log.error("error in submit of delete page...", e);
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				departmentNameEntryForm.setErrorMessage(msg);
				departmentNameEntryForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				String msg = super.handleApplicationException(e);
				departmentNameEntryForm.setErrorMessage(msg);
				departmentNameEntryForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		}
		
		if (isAttendanceEntryDay) {
			ActionMessage message = new ActionMessage("knowledgepro.servicelearing.departmentname.deletesuccess", name);
			messages.add("messages", message);
			saveMessages(request, messages);
			departmentNameEntryForm.reset(mapping, request);
			List<DepartmentNameEntryTo> departmentNameEntryToList = DepartmentNameEntryHandler.getInstance().getDepartmentNames();
			departmentNameEntryForm.setDepartmentNameEntryToList(departmentNameEntryToList);
		}else{
			// failed
			errors.add("error", new ActionError("knowledgepro.servicelearing.departmentname.deletefailure",name));
			saveErrors(request, errors);
			List<DepartmentNameEntryTo> departmentNameEntryToList = DepartmentNameEntryHandler.getInstance().getDepartmentNames();
			departmentNameEntryForm.setDepartmentNameEntryToList(departmentNameEntryToList);
		}
		return mapping.findForward(CMSConstants.DEPARTMENT_NAME_ENTRY);
	}
	
	public ActionForward editDepartmentNameEntry(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {

		DepartmentNameEntryForm departmentNameEntryForm = (DepartmentNameEntryForm) form;
		setUserId(request, departmentNameEntryForm);
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		
		try {
			DepartmentNameEntryHandler.getInstance().editDepartmentNameEntry(departmentNameEntryForm);
			request.setAttribute("department", "edit");

		} catch (BusinessException businessException) {
			log.info("Exception editMenus");
			String msgKey = super.handleBusinessException(businessException);
			ActionMessage message = new ActionMessage(msgKey);
			messages.add(CMSConstants.MESSAGES, message);
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} catch (Exception exception) {	
			String msg = super.handleApplicationException(exception);
			departmentNameEntryForm.setErrorMessage(msg);
			departmentNameEntryForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		//load the details for module drop down and menus list.
		List<DepartmentNameEntryTo> departmentNameEntryToList = DepartmentNameEntryHandler.getInstance().getDepartmentNames();
		departmentNameEntryForm.setDepartmentNameEntryToList(departmentNameEntryToList);
		return mapping.findForward(CMSConstants.DEPARTMENT_NAME_ENTRY);
	}
	
	public ActionForward updateDepartmentNameEntry(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DepartmentNameEntryForm departmentNameEntryForm = (DepartmentNameEntryForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = departmentNameEntryForm.validate(mapping, request);
		String name=departmentNameEntryForm.getDepartmentName();
		boolean isUpdate = false;
		//if (errors.isEmpty()) {
			try{
				if (errors.isEmpty()) {
					List<DepartmentNameEntryTo> departmentNameEntryToList = DepartmentNameEntryHandler.getInstance().getDepartmentNames();
					departmentNameEntryForm.setDepartmentNameEntryToList(departmentNameEntryToList);
				}
			setUserId(request, departmentNameEntryForm);
			
			isUpdate=DepartmentNameEntryHandler.getInstance().addDepartmentNameEntry(departmentNameEntryForm,"Edit",request);

			}catch (DuplicateException e1) {
				errors.add("error", new ActionError("knowledgepro.servicelearing.departmentname.exists"));
				saveErrors(request, errors);
				
				request.setAttribute("department", "edit");
				return mapping.findForward(CMSConstants.DEPARTMENT_NAME_ENTRY);
			} catch (ReActivateException e1) {
				errors.add("error", new ActionError("knowledgepro.servicelearing.departmentname.addfailure.alreadyexist.reactivate"));
				saveErrors(request, errors);
				
				request.setAttribute("department", "edit");
				return mapping.findForward(CMSConstants.DEPARTMENT_NAME_ENTRY);
			} catch (Exception e) {
				log.error("error in update admitted through page...", e);
				if (e instanceof BusinessException) {
					String msgKey = super.handleBusinessException(e);
					ActionMessage message = new ActionMessage(msgKey);
					messages.add("messages", message);
					request.setAttribute("department", "edit");
					return mapping.findForward(CMSConstants.ERROR_PAGE);
				} else if (e instanceof ApplicationException) {
					String msg = super.handleApplicationException(e);
					departmentNameEntryForm.setErrorMessage(msg);
					departmentNameEntryForm.setErrorStack(e.getMessage());
					request.setAttribute("department", "edit");
					return mapping.findForward(CMSConstants.ERROR_PAGE);
				} else {
					throw e;
				}
			}
		
		
		if (isUpdate) {
			ActionMessage message = new ActionMessage("knowledgepro.servicelearing.departmentname.updatesuccess", departmentNameEntryForm.getDepartmentName());
			messages.add("messages", message);
			saveMessages(request, messages);
			departmentNameEntryForm.reset(mapping, request);
			List<DepartmentNameEntryTo> departmentNameEntryToList = DepartmentNameEntryHandler.getInstance().getDepartmentNames();
			departmentNameEntryForm.setDepartmentNameEntryToList(departmentNameEntryToList);
		}else{
			// failed
			errors.add("error", new ActionError("knowledgepro.servicelearing.departmentname.updatefailure",  departmentNameEntryForm.getDepartmentName()));
			saveErrors(request, errors);
			List<DepartmentNameEntryTo> departmentNameEntryToList = DepartmentNameEntryHandler.getInstance().getDepartmentNames();
			departmentNameEntryForm.setDepartmentNameEntryToList(departmentNameEntryToList);
		}
		return mapping.findForward(CMSConstants.DEPARTMENT_NAME_ENTRY);
	}
	
	public ActionForward reActivateDepartmentNameEntry(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DepartmentNameEntryForm departmentNameEntryForm = (DepartmentNameEntryForm) form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		DepartmentNameEntry departmentNameEntry=(DepartmentNameEntry)request.getSession().getAttribute("DepartmentNameEntry");
		boolean isActivate = false;
		try{
			if(departmentNameEntryForm.getDupId()!=null){
				setUserId(request, departmentNameEntryForm);
				isActivate=DepartmentNameEntryHandler.getInstance().reActivateDepartmentNameEntry(departmentNameEntry,  departmentNameEntryForm.getUserId());
			}
		}catch (Exception e) {
			log.error("error in delete Department page...", e);
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				List<DepartmentNameEntryTo> departmentNameEntryToList = DepartmentNameEntryHandler.getInstance().getDepartmentNames();
				departmentNameEntryForm.setDepartmentNameEntryToList(departmentNameEntryToList);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				departmentNameEntryForm.setErrorMessage(msg);
				departmentNameEntryForm.setErrorStack(e.getMessage());
				List<DepartmentNameEntryTo> departmentNameEntryToList = DepartmentNameEntryHandler.getInstance().getDepartmentNames();
				departmentNameEntryForm.setDepartmentNameEntryToList(departmentNameEntryToList);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		
		if(isActivate){
			ActionMessage message = new ActionMessage("knowledgepro.servicelearing.departmentname.activate");
			messages.add("messages",message);
			saveMessages(request, messages);
		}
		else{
			errors.add("error", new ActionError("knowledgepro.servicelearing.departmentname.activatefailure"));
			saveErrors(request, errors);
		}
		List<DepartmentNameEntryTo> departmentNameEntryToList = DepartmentNameEntryHandler.getInstance().getDepartmentNames();
		departmentNameEntryForm.setDepartmentNameEntryToList(departmentNameEntryToList);
		return mapping.findForward(CMSConstants.DEPARTMENT_NAME_ENTRY);
	}
	
	
	

}
