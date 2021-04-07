package com.kp.cms.actions.attendance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.attendance.AssignCocurricularSubjectTeacherForm;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.handlers.attendance.AssignCocurricularSubjectTeacherHandler;
import com.kp.cms.to.attendance.AssignCocurricularSubjectTeacherTO;

public class AssignCocurricularSubjectTeacherAction extends BaseDispatchAction{
	private static Log log = LogFactory.getLog(AssignCocurricularSubjectTeacherAction.class);
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * Assign Co curricular subject to teachers
	 * @throws Exception
	 */
	public ActionForward initAssignCocurricularSubjectTeacher(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		log.debug("call of initAssignCocurricularSubjectTeacher method in AssignCocurricularSubjectTeacherAction.class");
		AssignCocurricularSubjectTeacherForm assignCocurricularSubjectTeacherForm = (AssignCocurricularSubjectTeacherForm)form;
		ActionMessages messages = new ActionMessages();
		try
		{
			assignCocurricularSubjectTeacherForm.ResetAllData();
			setRequiredDataToForm(request,assignCocurricularSubjectTeacherForm);
		}
		catch (ApplicationException e) {
			String msg = super.handleApplicationException(e);
			assignCocurricularSubjectTeacherForm.setErrorMessage(msg);
			assignCocurricularSubjectTeacherForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		catch (Exception e) {
			e.printStackTrace();
			log.error("Error in Block Mark Entry.." + e.getMessage());
			String msg = super.handleApplicationException(e);
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.debug("end of initAssignCocurricularSubjectTeacher method in AssignCocurricularSubjectTeacherAction.class");
		return mapping.findForward(CMSConstants.ASSIGN_COCURRICULAR_SUBJECT_TO_TEACHER_ENTRY);
	}
	private void setRequiredDataToForm(HttpServletRequest request,AssignCocurricularSubjectTeacherForm assignCocurricularSubjectTeacherForm) throws Exception {
		log.debug("call of setRequiredDataToForm method in AssignCocurricularSubjectTeacherAction.class");
		Map<Integer,String> userMap = new HashMap<Integer, String>();
	    Map<Integer, String> activityMap = new HashMap<Integer, String>();
	    userMap =  AssignCocurricularSubjectTeacherHandler.getInstance().getUsers();
	    activityMap =  AssignCocurricularSubjectTeacherHandler.getInstance().getActivityMap();
		setUserId(request, assignCocurricularSubjectTeacherForm);
		assignCocurricularSubjectTeacherForm.setActivityMap(activityMap);
		assignCocurricularSubjectTeacherForm.setUserMap(userMap);
		List<AssignCocurricularSubjectTeacherTO> cocurricularSubjectTeacherTOList = new ArrayList<AssignCocurricularSubjectTeacherTO>();
		cocurricularSubjectTeacherTOList  = AssignCocurricularSubjectTeacherHandler.getInstance().getCocurricularList();
		assignCocurricularSubjectTeacherForm.setAssignCocurricularSubjectTeacherTOList(cocurricularSubjectTeacherTOList);
		log.debug("end of setRequiredDataToForm method in AssignCocurricularSubjectTeacherAction.class");
		
	}
    
	public ActionForward saveCocurricularSubjectTeacher(ActionMapping mapping, ActionForm form,	HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		log.debug("call of saveCocurricularSubjectTeacher method in AssignCocurricularSubjectTeacherAction.class");
		AssignCocurricularSubjectTeacherForm assignCocurricularSubjectTeacherForm = (AssignCocurricularSubjectTeacherForm)form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = assignCocurricularSubjectTeacherForm.validate(mapping, request);
		boolean isAdded = false;
		try
		{
			if(!errors.isEmpty())
			{
				saveErrors(request, errors);
				setRequiredDataToForm(request,assignCocurricularSubjectTeacherForm);
			}
			else
			{
				
					isAdded = AssignCocurricularSubjectTeacherHandler.getInstance().saveCocurricularSubjectTeacher(assignCocurricularSubjectTeacherForm);
					if(isAdded)
					{

						ActionMessage message = new ActionMessage("knowledgepro.attendance.assign.cocurricular.approver.addsucces");
						messages.add("messages", message);
						saveMessages(request, messages);
						assignCocurricularSubjectTeacherForm.ResetAllData();
						setRequiredDataToForm(request, assignCocurricularSubjectTeacherForm);
					}
					else
					{
						ActionMessage message = new ActionMessage("knowledgepro.attendance.assign.cocurricular.approver.addfailure");
						messages.add("messages", message);
						saveMessages(request, messages);
						assignCocurricularSubjectTeacherForm.ResetAllData();
						setRequiredDataToForm(request, assignCocurricularSubjectTeacherForm);
					}
				
			}
		}
		catch (DuplicateException e1) {
			errors.add("error", new ActionError("knowledgepro.attendance.assign.cocurricular.approver.name.exists"));
			saveErrors(request, errors);
			assignCocurricularSubjectTeacherForm.ResetAllData();
			setRequiredDataToForm(request, assignCocurricularSubjectTeacherForm);
			// TODO: handle exception
		}
		catch (ReActivateException e2) {
			errors.add("error", new ActionError("knowledgepro.attendance.assign.cocurricular.approver.reactivate"));
			saveErrors(request, errors);
			
			setRequiredDataToForm(request, assignCocurricularSubjectTeacherForm);
		}
		catch (Exception e) {

			log.info("exception in saveblockMarksEntryForRole");
			String msg = super.handleApplicationException(e);
			assignCocurricularSubjectTeacherForm.setErrorMessage(msg);
			assignCocurricularSubjectTeacherForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.debug("end of saveCocurricularSubjectTeacher method in AssignCocurricularSubjectTeacherAction.class");
		return mapping.findForward(CMSConstants.ASSIGN_COCURRICULAR_SUBJECT_TO_TEACHER_ENTRY);
	}
	
	public ActionForward activateCocuricularSubjectTeacher(ActionMapping mapping, ActionForm form,	HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		log.debug("call of activateCocuricularSubjectTeacher method in AssignCocurricularSubjectTeacherAction.class");
		AssignCocurricularSubjectTeacherForm assignCocurricularSubjectTeacherForm = (AssignCocurricularSubjectTeacherForm)form;
		ActionMessages messages = new ActionMessages();
		boolean isReactiveted  = false;
		if(assignCocurricularSubjectTeacherForm.getDupliateId()!= null && !assignCocurricularSubjectTeacherForm.getDupliateId().isEmpty())
		{
				isReactiveted =  AssignCocurricularSubjectTeacherHandler.getInstance().reactiveCocurricularSubjectTeacher(assignCocurricularSubjectTeacherForm.getDupliateId(),assignCocurricularSubjectTeacherForm.getUserId());
				if(isReactiveted)
				{
					ActionMessage message = new ActionMessage("knowledgepro.attendance.assign.cocurricular.approver.reactivate.success");
					messages.add("messages", message);
					saveMessages(request, messages);
					assignCocurricularSubjectTeacherForm.ResetAllData();
					setRequiredDataToForm(request, assignCocurricularSubjectTeacherForm);
				}
				else
				{
					ActionMessage message = new ActionMessage("knowledgepro.attendance.assign.cocurricular.approver.reactivate.failure");
					messages.add("messages", message);
					saveMessages(request, messages);
					assignCocurricularSubjectTeacherForm.ResetAllData();
					setRequiredDataToForm(request, assignCocurricularSubjectTeacherForm);
				}
		}
		else
		{
			ActionMessage message = new ActionMessage("knowledgepro.attendance.assign.cocurricular.approver.reactivate.failure");
			messages.add("messages", message);
			saveMessages(request, messages);
			assignCocurricularSubjectTeacherForm.ResetAllData();
			setRequiredDataToForm(request, assignCocurricularSubjectTeacherForm);
		}
		log.debug("end of activateCocuricularSubjectTeacher method in AssignCocurricularSubjectTeacherAction.class");
		return mapping.findForward(CMSConstants.ASSIGN_COCURRICULAR_SUBJECT_TO_TEACHER_ENTRY);
	}
	public ActionForward deleteCocurricularTeacher(ActionMapping mapping, ActionForm form,	HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		log.debug("call of deleteCocurricularTeacher method in AssignCocurricularSubjectTeacherAction.class");
		AssignCocurricularSubjectTeacherForm assignCocurricularSubjectTeacherForm = (AssignCocurricularSubjectTeacherForm)form;
		ActionMessages messages = new ActionMessages();
		boolean isDeleted  = false;
		if(assignCocurricularSubjectTeacherForm.getId()!= null && !assignCocurricularSubjectTeacherForm.getId().isEmpty())
		{
			
			isDeleted =  AssignCocurricularSubjectTeacherHandler.getInstance().deleteCocurricularTeacher(assignCocurricularSubjectTeacherForm);
			if(isDeleted)
			{
				ActionMessage message = new ActionMessage("knowledgepro.attendance.assign.cocurricular.approver.delete.success");
				messages.add("messages", message);
				saveMessages(request, messages);
				assignCocurricularSubjectTeacherForm.ResetAllData();
				setRequiredDataToForm(request, assignCocurricularSubjectTeacherForm);
			}
			else
			{
				ActionMessage message = new ActionMessage("knowledgepro.attendance.assign.cocurricular.approver.delete.failed");
				messages.add("messages", message);
				saveMessages(request, messages);
				assignCocurricularSubjectTeacherForm.ResetAllData();
				setRequiredDataToForm(request, assignCocurricularSubjectTeacherForm);
			}
		}
		else
		{
			ActionMessage message = new ActionMessage("knowledgepro.attendance.assign.cocurricular.approver.delete.failed");
			messages.add("messages", message);
			saveMessages(request, messages);
			assignCocurricularSubjectTeacherForm.ResetAllData();
			setRequiredDataToForm(request, assignCocurricularSubjectTeacherForm);
		}
		log.debug("end of deleteCocurricularTeacher method in AssignCocurricularSubjectTeacherAction.class");
		return mapping.findForward(CMSConstants.ASSIGN_COCURRICULAR_SUBJECT_TO_TEACHER_ENTRY);
	}
	
	public ActionForward editCocurricularTeacher(ActionMapping mapping, ActionForm form,	HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		log.debug("call of editCocurricularTeacher method in AssignCocurricularSubjectTeacherAction.class");
		AssignCocurricularSubjectTeacherForm assignCocurricularSubjectTeacherForm = (AssignCocurricularSubjectTeacherForm)form;
		ActionMessages messages = new ActionMessages();
		if(assignCocurricularSubjectTeacherForm.getId()!= null && !assignCocurricularSubjectTeacherForm.getId().isEmpty())
		{
			AssignCocurricularSubjectTeacherHandler.getInstance().editCocurricularTeacher(assignCocurricularSubjectTeacherForm);
			request.setAttribute("stateOperation", "edit");
		}
		else
		{
			assignCocurricularSubjectTeacherForm.ResetAllData();
			setRequiredDataToForm(request, assignCocurricularSubjectTeacherForm);
		}
		
		log.debug("end of editCocurricularTeacher method in AssignCocurricularSubjectTeacherAction.class");
		return mapping.findForward(CMSConstants.ASSIGN_COCURRICULAR_SUBJECT_TO_TEACHER_ENTRY);
	}
	
	public ActionForward updateCocurricularSubjectTeacher(ActionMapping mapping, ActionForm form,	HttpServletRequest request, HttpServletResponse response)throws Exception
	{
		log.debug("call of updateCocurricularSubjectTeacher method in AssignCocurricularSubjectTeacherAction.class");
		AssignCocurricularSubjectTeacherForm assignCocurricularSubjectTeacherForm = (AssignCocurricularSubjectTeacherForm)form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = assignCocurricularSubjectTeacherForm.validate(mapping, request);
		boolean isUpdated = false;
		try
		{
			if(!errors.isEmpty())
			{
				saveErrors(request, errors);
				setRequiredDataToForm(request,assignCocurricularSubjectTeacherForm);
			}
			else
			{
				isUpdated = AssignCocurricularSubjectTeacherHandler.getInstance().updateCocurricularSubjectTeacher(assignCocurricularSubjectTeacherForm);
				if(isUpdated)
				{
					ActionMessage message = new ActionMessage("knowledgepro.attendance.assign.cocurricular.approver.update.success");
					messages.add("messages", message);
					saveMessages(request, messages);
					assignCocurricularSubjectTeacherForm.ResetAllData();
					setRequiredDataToForm(request, assignCocurricularSubjectTeacherForm);
				}
				else
				{
					ActionMessage message = new ActionMessage("knowledgepro.attendance.assign.cocurricular.approver.update.failed");
					messages.add("messages", message);
					saveMessages(request, messages);
					assignCocurricularSubjectTeacherForm.ResetAllData();
					setRequiredDataToForm(request, assignCocurricularSubjectTeacherForm);
				}
			}
		}
		catch (Exception e) {
			log.info("exception in saveblockMarksEntryForRole");
			String msg = super.handleApplicationException(e);
			assignCocurricularSubjectTeacherForm.setErrorMessage(msg);
			assignCocurricularSubjectTeacherForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
			// TODO: handle exception
		}
		
		log.debug("end of updateCocurricularSubjectTeacher method in AssignCocurricularSubjectTeacherAction.class");
		return mapping.findForward(CMSConstants.ASSIGN_COCURRICULAR_SUBJECT_TO_TEACHER_ENTRY);
	}
	
}
