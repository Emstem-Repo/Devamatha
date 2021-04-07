package com.kp.cms.actions.admin;

import java.util.Calendar;

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
import com.kp.cms.forms.admin.OpenSyllabusEntryForm;
import com.kp.cms.handlers.admin.OpenSyllabusEntryHandler;

public class OpenSyllabusEntryAction extends BaseDispatchAction{
	OpenSyllabusEntryHandler openSyllabusEntryHandler=OpenSyllabusEntryHandler.getInstance();
	/**
	 * init page for open syllabus entry
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return load the first page of the password template.
	 * @throws Exception
	 *         
	 */
	public ActionForward initOpenSyllabusEntry(ActionMapping mapping,
										   ActionForm form, HttpServletRequest request,
										   HttpServletResponse response) throws Exception {
		OpenSyllabusEntryForm openEntryForm=(OpenSyllabusEntryForm)form;
		clearPage(openEntryForm);
		//get all the open syllabus entries
		openSyllabusEntryHandler.getAllOpenSyllabusEntries(openEntryForm);
		return mapping.findForward("initOpenSyllabusEntry");
	}
	/**
	 * clear the first page
	 * @param openEntryForm
	 */
	private void clearPage(OpenSyllabusEntryForm openEntryForm) {
		int year = Calendar.getInstance().get(Calendar.YEAR);
		openEntryForm.setBatch(String.valueOf(year));
		openEntryForm.setTempBatch(String.valueOf(year));
		openEntryForm.setStartDate(null);
		openEntryForm.setEndDate(null);
		openEntryForm.setTempStartDate(null);
		openEntryForm.setTempEndDate(null);
		openEntryForm.setList(null);
	}	
	/**
	 * method used to add  open syllabus entry
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward add(ActionMapping mapping, ActionForm form, HttpServletRequest request,
											HttpServletResponse response) throws Exception {
		OpenSyllabusEntryForm openEntryForm=(OpenSyllabusEntryForm)form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = openEntryForm.validate(mapping, request);
		boolean isAdded=false;
		openEntryForm.setTempBatch(openEntryForm.getBatch());
		try {
			if(errors.isEmpty()){
				setUserId(request, openEntryForm);
				boolean duplicate=openSyllabusEntryHandler.checkDuplicate(openEntryForm,"add");
				if(duplicate){
					//duplicate
					errors.add("error", new ActionError("knowledgepro.admin.open.syllabus.entry.exists"));
					saveErrors(request, errors);
					return mapping.findForward("initOpenSyllabusEntry");
				}
				//check startdate is less than enddate or not
				boolean flag=openSyllabusEntryHandler.validateDates(openEntryForm);
				if(flag){
					errors.add("error", new ActionError("knowledgepro.startdate.connotbeless"));
					saveErrors(request, errors);
					return mapping.findForward("initOpenSyllabusEntry");
				}
				isAdded=openSyllabusEntryHandler.add(openEntryForm);
				if(isAdded){
					// success .
					ActionMessage message = new ActionMessage("knowledgepro.exam.subjectSection.addsuccess","Open Syllabus Entry");
					messages.add("messages", message);
					saveMessages(request, messages);
					clearPage(openEntryForm);
				
				}else{
					// failed
					errors.add("error", new ActionError("knowledgepro.admin.open.syllabus.entry.fail"));
					saveErrors(request, errors);
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
				openEntryForm.setErrorMessage(msg);
				openEntryForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		openSyllabusEntryHandler.getAllOpenSyllabusEntries(openEntryForm);
		return mapping.findForward("initOpenSyllabusEntry");
	}
	/**
	 * editing 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward edit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		OpenSyllabusEntryForm openEntryForm=(OpenSyllabusEntryForm)form;
		clearPage(openEntryForm);
		try{
			openSyllabusEntryHandler.edit(openEntryForm);
			request.setAttribute("admOperation", "edit");
		}catch (Exception e) {
			String msg = super.handleApplicationException(e);
			openEntryForm.setErrorMessage(msg);
			openEntryForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		openSyllabusEntryHandler.getAllOpenSyllabusEntries(openEntryForm);
		return mapping.findForward("initOpenSyllabusEntry");
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
	public ActionForward delete(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		OpenSyllabusEntryForm openEntryForm=(OpenSyllabusEntryForm)form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = new ActionErrors();
		boolean isDeleted = false;
		try {
				setUserId(request, openEntryForm);
				isDeleted = openSyllabusEntryHandler.delete(openEntryForm);
				if (isDeleted) {
					// success deleted
					ActionMessage message = new ActionMessage("knowledgepro.admin.deletesuccess","Open Syllabus Entry");
					messages.add("messages", message);
					saveMessages(request, messages);
					clearPage(openEntryForm);
				} else {
					// failure error message.
					errors.add("error", new ActionError("knowledgepro.admin.open.syllabus.entry.delete.fail"));
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
				openEntryForm.setErrorMessage(msg);
				openEntryForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		openSyllabusEntryHandler.getAllOpenSyllabusEntries(openEntryForm);
		return mapping.findForward("initOpenSyllabusEntry");
	}
	/**
	 * method used to add  open syllabus entry
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward update(ActionMapping mapping, ActionForm form, HttpServletRequest request,
											HttpServletResponse response) throws Exception {
		OpenSyllabusEntryForm openEntryForm=(OpenSyllabusEntryForm)form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = openEntryForm.validate(mapping, request);
		boolean isupdate=false;
		openEntryForm.setTempBatch(openEntryForm.getBatch());
		try {
			if(errors.isEmpty()){
				setUserId(request, openEntryForm);
				boolean duplicate=openSyllabusEntryHandler.checkDuplicate(openEntryForm,"update");
				if(duplicate){
					//duplicate
					request.setAttribute("admOperation", "edit");
					errors.add("error", new ActionError("knowledgepro.admin.open.syllabus.entry.exists"));
					saveErrors(request, errors);
					return mapping.findForward("initOpenSyllabusEntry");
				}
				boolean flag=openSyllabusEntryHandler.validateDates(openEntryForm);
				if(flag){
					errors.add("error", new ActionError("knowledgepro.startdate.connotbeless"));
					saveErrors(request, errors);
					return mapping.findForward("initOpenSyllabusEntry");
				}
				isupdate=openSyllabusEntryHandler.update(openEntryForm);
				if(isupdate){
					// success .
					ActionMessage message = new ActionMessage("knowledgepro.admin.updatesuccess","Open Syllabus Entry");
					messages.add("messages", message);
					saveMessages(request, messages);
					clearPage(openEntryForm);
				
				}else{
					// failed
					request.setAttribute("admOperation", "edit");
					errors.add("error", new ActionError("knowledgepro.admin.open.syllabus.entry.update.fail"));
					saveErrors(request, errors);
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
				openEntryForm.setErrorMessage(msg);
				openEntryForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		openSyllabusEntryHandler.getAllOpenSyllabusEntries(openEntryForm);
		return mapping.findForward("initOpenSyllabusEntry");
	}

}
