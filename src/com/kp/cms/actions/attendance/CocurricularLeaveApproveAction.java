package com.kp.cms.actions.attendance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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
import org.apache.xmlbeans.impl.jam.mutable.MPackage;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.attendance.CocurricularLeaveApproveForm;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.handlers.attendance.AssignCocurricularSubjectTeacherHandler;
import com.kp.cms.handlers.attendance.CocurricularLeaveApproveHandler;
import com.kp.cms.to.attendance.ApproveCocurricularLeaveTO;

public class CocurricularLeaveApproveAction  extends BaseDispatchAction{
	private static Log log = LogFactory.getLog(CocurricularLeaveApproveAction.class);
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * Approve CocurricularLeave Leave 
	 * @throws Exception
	 */
	public ActionForward initApproveCocurricularLeave(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		log.debug("call of initApproveCocurricularLeave method in CocurricularLeaveApproveAction.class");
		CocurricularLeaveApproveForm cocurricularLeaveApproveForm = (CocurricularLeaveApproveForm) form;
		ActionMessages messages = new ActionMessages();
		try
		{
			setUserId(request, cocurricularLeaveApproveForm);
			cocurricularLeaveApproveForm.ResetAllData();
			setRequiredDataToForm(request,cocurricularLeaveApproveForm);
		}
		catch (ApplicationException e) {
			String msg = super.handleApplicationException(e);
			cocurricularLeaveApproveForm.setErrorMessage(msg);
			cocurricularLeaveApproveForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		catch (Exception e) {
			e.printStackTrace();
			log.error("Error in Block Mark Entry.." + e.getMessage());
			String msg = super.handleApplicationException(e);
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.debug("end of initApproveCocurricularLeave method in CocurricularLeaveApproveAction.class");
		return mapping.findForward(CMSConstants.APPORVE_COCURRICULAR_ATTENDACE_ENTRY);
	}
	private void setRequiredDataToForm(HttpServletRequest request,CocurricularLeaveApproveForm cocurricularLeaveApproveForm)  throws Exception{
		log.debug("call of setRequiredDataToForm mehtod in CocurricularLeaveApproveAction.class");
		Map<Integer, String> userMap= new HashMap<Integer, String>();
		Map<Integer, String> activityMap = new HashMap<Integer, String>();
		userMap = AssignCocurricularSubjectTeacherHandler.getInstance().getUsers();
		cocurricularLeaveApproveForm.setTeacherID(cocurricularLeaveApproveForm.getUserId());
		activityMap = CocurricularLeaveApproveHandler.getInstance().getAssignedActivityMap(cocurricularLeaveApproveForm.getUserId());
		cocurricularLeaveApproveForm.setTeacherName(userMap.get(cocurricularLeaveApproveForm.getUserId()));
		cocurricularLeaveApproveForm.setUserMap(userMap);
		cocurricularLeaveApproveForm.setActivityMap(activityMap);
		log.debug("end of setRequiredDataToForm mehtod in CocurricularLeaveApproveAction.class");
	}
			
	public ActionForward getCocurricularApplications(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		log.debug("call of getCocurricularApplications method in CocurricularLeaveApproveAction.class");
		CocurricularLeaveApproveForm cocurricularLeaveApproveForm = (CocurricularLeaveApproveForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = cocurricularLeaveApproveForm.validate(mapping, request);
		try
		{
			if(errors.isEmpty())
			{

				List<ApproveCocurricularLeaveTO> approveCocurricularLeaveList  = new ArrayList<ApproveCocurricularLeaveTO>(); 
				approveCocurricularLeaveList = CocurricularLeaveApproveHandler.getInstance().getCocurricularApplications(cocurricularLeaveApproveForm);
				cocurricularLeaveApproveForm.setScreenLabel("View Application");
				cocurricularLeaveApproveForm.setList(approveCocurricularLeaveList);

			}
			else
			{
				saveErrors(request, errors);
				setRequiredDataToForm(request,cocurricularLeaveApproveForm);
				return mapping.findForward(CMSConstants.APPORVE_COCURRICULAR_ATTENDACE_ENTRY);
			}
		}
		catch (Exception e) {
			log.info("exception in saveblockMarksEntryForRole");
			String msg = super.handleApplicationException(e);
			cocurricularLeaveApproveForm.setErrorMessage(msg);
			cocurricularLeaveApproveForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
			// TODO: handle exception
		}
		log.debug("end of getCocurricularApplications method in CocurricularLeaveApproveAction.class");
		return mapping.findForward(CMSConstants.APPORVE_COCURRICULAR_ATTENDACE_ENTRY_SECOND);
	}
	
	
	
	
	
	public ActionForward approveCocurricularApplicationsByTeacher(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		log.debug("call of approveCocurricularApplicationsByTeacher method in CocurricularLeaveApproveAction.class");
		CocurricularLeaveApproveForm cocurricularLeaveApproveForm = (CocurricularLeaveApproveForm) form;
		ActionMessages messages = new ActionMessages();
		boolean isAdded = false;
		int count  =0;
		try
		{
			List<ApproveCocurricularLeaveTO> cocurricularList = new ArrayList<ApproveCocurricularLeaveTO>();
			cocurricularList = cocurricularLeaveApproveForm.getList();
			Iterator<ApproveCocurricularLeaveTO>  iterator = cocurricularList.iterator();
			while(iterator.hasNext())
			{
				ApproveCocurricularLeaveTO to = iterator.next();
				if(to.isChecked())
				{
					count = count+1;
				}
			}
			if(count==0)
			{
				ActionMessage message = new ActionMessage("knowledgepro.attendance.approve.cocurricular.attendance.application.nomodification");
				messages.add("messages", message);
				saveMessages(request, messages);
				cocurricularLeaveApproveForm.ResetAllData();
				setRequiredDataToForm(request, cocurricularLeaveApproveForm);
			}
			else
			{
				isAdded = CocurricularLeaveApproveHandler.getInstance().approveCocurricularApplicationsByTeacher(cocurricularLeaveApproveForm);
			}
			if(isAdded)
			{
				
				ActionMessage message = new ActionMessage("knowledgepro.attendance.approve.cocurricular.attendance.application.approve.success",count);
				messages.add("messages", message);
				saveMessages(request, messages);
				List<ApproveCocurricularLeaveTO> approveCocurricularLeaveList  = new ArrayList<ApproveCocurricularLeaveTO>(); 
				approveCocurricularLeaveList = CocurricularLeaveApproveHandler.getInstance().searchCocurricularApplications(cocurricularLeaveApproveForm,"View Application");
			    cocurricularLeaveApproveForm.setList(approveCocurricularLeaveList);
			}
			else
			{
				ActionMessage message = new ActionMessage("knowledgepro.attendance.approve.cocurricular.attendance.application.approve.failed");
				messages.add("messages", message);
				saveMessages(request, messages);
				cocurricularLeaveApproveForm.ResetAllData();
				setRequiredDataToForm(request, cocurricularLeaveApproveForm);
			}
			
			
		}
		catch (Exception e) {
			log.error("Error in approveCocurricularApplicationsByTeacher method in CocurricularLeaveApproveAction.class");
			throw new ApplicationException(e);
		}
		
		log.debug("end of approveCocurricularApplicationsByTeacher method in CocurricularLeaveApproveAction.class");
		return mapping.findForward(CMSConstants.APPORVE_COCURRICULAR_ATTENDACE_ENTRY_SECOND);
	}
	
	public ActionForward  searchCocurricularApplications(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		log.debug("call of searchCocurricularApplications method in CocurricularLeaveApproveAction.class");
		CocurricularLeaveApproveForm cocurricularLeaveApproveForm = (CocurricularLeaveApproveForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = new ActionErrors();
		try
		{
			String searchType  = cocurricularLeaveApproveForm.getSearchValue();
			if(searchType!=null && !searchType.isEmpty())
			{
				List<ApproveCocurricularLeaveTO> approveCocurricularLeaveList  = new ArrayList<ApproveCocurricularLeaveTO>(); 
				approveCocurricularLeaveList = CocurricularLeaveApproveHandler.getInstance().searchCocurricularApplications(cocurricularLeaveApproveForm,searchType);
				if(approveCocurricularLeaveList==null && approveCocurricularLeaveList.isEmpty())
				{
					errors.add("error", new ActionError("knowledgepro.norecords"));
					saveErrors(request, errors);
					setRequiredDataToForm(request,cocurricularLeaveApproveForm);
					return mapping.findForward(CMSConstants.APPORVE_COCURRICULAR_ATTENDACE_ENTRY);
				}
				else
				{
					cocurricularLeaveApproveForm.setScreenLabel(searchType);
					request.setAttribute("stateOperation", searchType);
					cocurricularLeaveApproveForm.setList(approveCocurricularLeaveList);
				}
				
			}
			else
			{
				errors.add("error", new ActionError("knowledgepro.norecords"));
				saveErrors(request, errors);
				setRequiredDataToForm(request,cocurricularLeaveApproveForm);
				return mapping.findForward(CMSConstants.APPORVE_COCURRICULAR_ATTENDACE_ENTRY);
			}
		}
		catch (Exception e) {
			log.error("Error in searchCocurricularApplications method in CocurricularLeaveApproveAction.class");
			throw new ApplicationException(e);
			// TODO: handle exception
		}
		
		log.debug("end of searchCocurricularApplications method in CocurricularLeaveApproveAction.class");
		return mapping.findForward(CMSConstants.APPORVE_COCURRICULAR_ATTENDACE_ENTRY_SECOND);
	}
	
	public ActionForward cancelCocurricularApplicationsByTeacher(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		log.debug("call of cancelCocurricularApplicationsByTeacher method in CocurricularLeaveApproveAction.class");
		CocurricularLeaveApproveForm cocurricularLeaveApproveForm = (CocurricularLeaveApproveForm) form;
		ActionMessages messages = new ActionMessages();
		boolean isCanceled = false;
		int count=0;
		try
		{
			Iterator<ApproveCocurricularLeaveTO> iterator = cocurricularLeaveApproveForm.getList().iterator();
			while(iterator.hasNext())
			{
				ApproveCocurricularLeaveTO to = iterator.next();
				if(to.isChecked())
				{
					count=count+1;
				}
			}
			if(count>0)
			{
				isCanceled = CocurricularLeaveApproveHandler.getInstance().cancelCocurricularApplicationsByTeacher(cocurricularLeaveApproveForm);
				if(isCanceled)
				{
					ActionMessage message = new ActionMessage("knowledgepro.attendance.approve.cocurricular.attendance.application.cancel.success",count);
					messages.add("messages", message);
					saveMessages(request, messages);
					setRequiredDataToForm(request, cocurricularLeaveApproveForm);
				}
				else
				{
					ActionMessage message = new ActionMessage("knowledgepro.attendance.approve.cocurricular.attendance.application.cancel.failure");
					messages.add("messages", message);
					saveMessages(request, messages);
					setRequiredDataToForm(request, cocurricularLeaveApproveForm);
				}
			}
			else
			{
				ActionMessage message = new ActionMessage("knowledgepro.attendance.approve.cocurricular.attendance.application.nomodification");
				messages.add("messages", message);
				saveMessages(request, messages);
				return mapping.findForward(CMSConstants.APPORVE_COCURRICULAR_ATTENDACE_ENTRY_SECOND);
			}
			List<ApproveCocurricularLeaveTO> approveCocurricularLeaveList  = new ArrayList<ApproveCocurricularLeaveTO>(); 
			approveCocurricularLeaveList = CocurricularLeaveApproveHandler.getInstance().searchCocurricularApplications(cocurricularLeaveApproveForm,cocurricularLeaveApproveForm.getSearchValue());
		    cocurricularLeaveApproveForm.setList(approveCocurricularLeaveList);
		}
		catch (Exception e) {
			log.error("Error in cancelCocurricularApplicationsByTeacher method in CocurricularLeaveApproveAction.class");
			throw new ApplicationException(e);
			// TODO: handle exception
		}
		
		log.debug("call of cancelCocurricularApplicationsByTeacher method in CocurricularLeaveApproveAction.class");
		return mapping.findForward(CMSConstants.APPORVE_COCURRICULAR_ATTENDACE_ENTRY_SECOND);
	}
	
	
	
	
}
