package com.kp.cms.actions.hostel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.hostel.HostelStudentViewMessageForm;
import com.kp.cms.handlers.hostel.HostelAdminMessageHandler;
import com.kp.cms.to.hostel.ComplaintsTO;
import com.kp.cms.to.hostel.LeaveTypeTo;


public class HostelStudentViewMessageAction extends BaseDispatchAction{
	
	private static final Log log=LogFactory.getLog(HostelAdminMessageAction.class);

	public ActionForward initHostelStudentViewMessage(
		    ActionMapping mapping,
		    ActionForm form,
		    HttpServletRequest request,
		    HttpServletResponse response) throws Exception{	
		log.info("entering into initHostelAdminMessage  in HostelAdminMessageAction class..");
		HostelStudentViewMessageForm hostelStudentViewMessageForm=(HostelStudentViewMessageForm)form;	
		try
		{
			if(hostelStudentViewMessageForm!=null)
			{
				hostelStudentViewMessageForm.clear();
			}
		setListToForm(hostelStudentViewMessageForm);
		}
	    catch (Exception e) {
				log.error("error in loading initHostelAdminMessage in HostelAdminMessageAction class...", e);
				if (e instanceof ApplicationException) {
					String msg = super.handleApplicationException(e);
					hostelStudentViewMessageForm.setErrorMessage(msg);
					hostelStudentViewMessageForm.setErrorStack(e.getMessage());
					return mapping.findForward(CMSConstants.STUDENT_ERROR_PAGE);
				} else {
					throw e;
				}
			}
		log.info("leaving from initHostelAdminMessage in HostelAdminMessageAction class..");
		return mapping.findForward(CMSConstants.INIT_HOSTEL_STUDENT_VIEW_MESSAGE);
	
	}
	
	@SuppressWarnings("deprecation")
	public ActionForward getStudentViewMessageList(
		    ActionMapping mapping,
		    ActionForm form,
		    HttpServletRequest request,
		    HttpServletResponse response) throws Exception{	
		log.info("entering into getStudentViewMessageList  in HostelAdminMessageAction class..");
		HostelStudentViewMessageForm hostelStudentViewMessageForm=(HostelStudentViewMessageForm)form;		
		ActionMessages messages=new ActionMessages();
		ActionMessage message=null;
		ActionErrors errors = hostelStudentViewMessageForm.validate(mapping, request);
		try
		{
			setUserId(request, hostelStudentViewMessageForm);
			if(errors.isEmpty())
			{
				List<LeaveTypeTo> leaveTypeTOList=null;
				leaveTypeTOList = HostelAdminMessageHandler.getInstance().getStudentLeaveList(hostelStudentViewMessageForm);
			if(leaveTypeTOList!=null && !leaveTypeTOList.isEmpty()){
				hostelStudentViewMessageForm.setLeaveTypeTOList(leaveTypeTOList);
				HttpSession session=request.getSession(false);
				if(session.getAttribute("leaveTypeTOList")!=null)
				{
					session.removeAttribute("leaveTypeTOList");
				}
				session.setAttribute("leaveTypeTOList", leaveTypeTOList);
				
			}
			else
			{
				message = new ActionMessage("knowledgepro.pettycash.no.active.record");
				messages.add("messages", message);
				saveMessages(request, messages);
				hostelStudentViewMessageForm.clear();
				setListToForm(hostelStudentViewMessageForm);
				return mapping.findForward(CMSConstants.INIT_HOSTEL_STUDENT_VIEW_MESSAGE);
			}
			}
			else
			{
				saveErrors(request, errors);
				hostelStudentViewMessageForm.clear();
				setListToForm(hostelStudentViewMessageForm);
				return mapping.findForward(CMSConstants.INIT_HOSTEL_STUDENT_VIEW_MESSAGE);
			}
		}
	    catch (Exception e) {
				log.error("error in loading getStudentViewMessageList in HostelAdminMessageAction class...", e);
				if (e instanceof ApplicationException) {
					String msg = super.handleApplicationException(e);
					hostelStudentViewMessageForm.setErrorMessage(msg);
					hostelStudentViewMessageForm.setErrorStack(e.getMessage());
					return mapping.findForward(CMSConstants.STUDENT_ERROR_PAGE);
				} else {
					throw e;
				}
			}
		log.info("leaving from getStudentViewMessageList in HostelAdminMessageAction class..");
		return mapping.findForward(CMSConstants.GET_STUDENT_VIEW_MESSAGE_LIST);
	
	}
	
	
	@SuppressWarnings({ "deprecation", "unchecked" })
	public ActionForward getStudentLeaveStatus(
		    ActionMapping mapping,
		    ActionForm form,
		    HttpServletRequest request,
		    HttpServletResponse response) throws Exception{	
		log.info("entering into getStudentLeaveStatus  in HostelStudentViewMessageAction class..");
		HostelStudentViewMessageForm hostelStudentViewMessageForm=(HostelStudentViewMessageForm)form;		
		ActionMessages messages=new ActionMessages();
		ActionMessage message=null;
		ActionErrors errors=new ActionErrors();
		//HlLeave hlLeave=new HlLeave();
		//String type="Std";
		if(isCancelled(request)){
			if(request.getSession().getAttribute("hostelAdminMessageTOList")!=null)
			{
				List<LeaveTypeTo> hostelStudentViewMessageTOList=null;
				hostelStudentViewMessageTOList=(List<LeaveTypeTo>)request.getSession().getAttribute("leaveTypeTOList");
				hostelStudentViewMessageForm.setLeaveTypeTOList(hostelStudentViewMessageTOList);
			}
			return mapping.findForward(CMSConstants.GET_STUDENT_VIEW_MESSAGE_LIST);
		}
		
		LeaveTypeTo leaveTypeTo=new LeaveTypeTo();
		ComplaintsTO complaintTo=new ComplaintsTO();
		try
		{
			setUserId(request, hostelStudentViewMessageForm);
			if(errors.isEmpty())
			{
				//List<LeaveTypeTo> leaveTypeTOList=null;
				if(hostelStudentViewMessageForm.getMessageTypeId().equals("2"))
				{
					leaveTypeTo = HostelAdminMessageHandler.getInstance().getLeaveStatusByLeaveId(hostelStudentViewMessageForm);
				}
				else
				{
					complaintTo=HostelAdminMessageHandler.getInstance().getComplaintStatusByComplaintId(hostelStudentViewMessageForm);
				}
			if(leaveTypeTo!=null && hostelStudentViewMessageForm.getMessageTypeId().equals("2")){
				hostelStudentViewMessageForm.setLeaveTypeTo(leaveTypeTo);
			}
			else if(complaintTo!=null  && hostelStudentViewMessageForm.getMessageTypeId().equals("1"))
			{
				//hostelStudentViewMessageForm.setComplaintTo(complaintTo);
				if(request.getSession().getAttribute("complaintTypeTo")!=null)
				{
					request.getSession().removeAttribute("complaintTypeTo");
				}
				request.getSession().setAttribute("complaintTypeTo", complaintTo);	
			}
			else
			{
				message = new ActionMessage("knowledgepro.pettycash.no.active.record");
				messages.add("messages", message);
				saveMessages(request, messages);
				hostelStudentViewMessageForm.clear();
				setListToForm(hostelStudentViewMessageForm);
				return mapping.findForward(CMSConstants.GET_STUDENT_VIEW_MESSAGE_LIST);
			}
			}
			else
			{
				saveErrors(request, errors);
				hostelStudentViewMessageForm.clear();
				setListToForm(hostelStudentViewMessageForm);
				return mapping.findForward(CMSConstants.GET_STUDENT_VIEW_MESSAGE_LIST);
			}
		}
	    catch (Exception e) {
				log.error("error in loading getStudentLeaveStatus in HostelStudentViewMessageAction class...", e);
				if (e instanceof ApplicationException) {
					String msg = super.handleApplicationException(e);
					hostelStudentViewMessageForm.setErrorMessage(msg);
					hostelStudentViewMessageForm.setErrorStack(e.getMessage());
					return mapping.findForward(CMSConstants.STUDENT_ERROR_PAGE);
				} else {
					throw e;
				}
			}
		log.info("leaving from getStudentLeaveStatus in HostelStudentViewMessageAction class..");
		
		if(hostelStudentViewMessageForm.getMessageTypeId().equalsIgnoreCase("2"))
		{
			return mapping.findForward(CMSConstants.GET_STUDENT_LEAVE_STATUS);
		}
		else
		{
			return mapping.findForward("getStudentViewComplaintStatus");
		}
	}

	
	public void setListToForm(HostelStudentViewMessageForm hostelStudentViewMessageForm)throws Exception
	{	
		Map<Integer,String> messageTypeMap=HostelAdminMessageHandler.getInstance().getHostelMessageType();
		hostelStudentViewMessageForm.setMessageTypeMap(messageTypeMap);
	}
	
}
