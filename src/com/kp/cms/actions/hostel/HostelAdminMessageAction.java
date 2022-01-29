package com.kp.cms.actions.hostel;

import java.util.ArrayList;
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
import com.kp.cms.bo.admin.HlStatus;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.hostel.HostelAdminMessageForm;
import com.kp.cms.handlers.hostel.HostelAdminMessageHandler;
import com.kp.cms.to.hostel.ComplaintsTO;
import com.kp.cms.to.hostel.HostelAdminMessageTO;
import com.kp.cms.to.hostel.LeaveTypeTo;


@SuppressWarnings("deprecation")
public class HostelAdminMessageAction extends BaseDispatchAction{
	private static final Log log=LogFactory.getLog(HostelAdminMessageAction.class);

	public ActionForward initHostelAdminMessage(
		    ActionMapping mapping,
		    ActionForm form,
		    HttpServletRequest request,
		    HttpServletResponse response) throws Exception{	
		log.info("entering into initHostelAdminMessage  in HostelAdminMessageAction class..");
		HostelAdminMessageForm hostelAdminMessageForm=(HostelAdminMessageForm)form;	
		
		try
		{
			if(hostelAdminMessageForm!=null)
			{
				hostelAdminMessageForm.messageclear();
			}
		setListToForm(hostelAdminMessageForm);
		}
	    catch (Exception e) {
				log.error("error in loading initHostelAdminMessage in HostelAdminMessageAction class...", e);
				if (e instanceof ApplicationException) {
					String msg = super.handleApplicationException(e);
					hostelAdminMessageForm.setErrorMessage(msg);
					hostelAdminMessageForm.setErrorStack(e.getMessage());
					return mapping.findForward(CMSConstants.ERROR_PAGE);
				} else {
					throw e;
				}
			}
		log.info("leaving from initHostelAdminMessage in HostelAdminMessageAction class..");
		return mapping.findForward(CMSConstants.INIT_HOSTEL_ADMN_MESSAGE);
	
	}
	
	public void setHostelStatusMapToForm(HostelAdminMessageForm hostelAdminMessageForm)throws Exception
	{
		log.info("entering into setListToForm in HostelAdminMessageAction class..");
		List<HlStatus> statusList=null;
		statusList=HostelAdminMessageHandler.getInstance().getStatusList();
		Map<Integer,String> statusMap=HostelAdminMessageHandler.getInstance().getHostelStatusMap();
		if(statusMap!=null && !statusMap.isEmpty())
		{
		hostelAdminMessageForm.setStatusMap(statusMap);
		}
		else
		{
			hostelAdminMessageForm.setStatusMap(new HashMap<Integer,String>());
		}
		
		if(statusList!=null && !statusList.isEmpty())
		{
			for(int i=0;i<statusList.size();i++)
			{
				if(statusList.get(i).getId()!=1 && statusList.get(i).getId()!=9 && statusList.get(i).getId()!=10)
				{
					statusList.remove(i);
					i--;
				}
			}
			hostelAdminMessageForm.setStatusList(statusList);
		}
		else
		{
			hostelAdminMessageForm.setStatusList(new ArrayList<HlStatus>());
		}
		log.info("leaving from setListToForm in HostelAdminMessageAction class..");
	}
	
	public void setHostelMessageTypeToForm(HostelAdminMessageForm hostelAdminMessageForm)throws Exception
	{
		log.info("entering into getHostelMessageTypeMap in HostelAdminMessageHandler class..");
		Map<Integer,String> messageTypeMap=HostelAdminMessageHandler.getInstance().getHostelMessageType();
		hostelAdminMessageForm.setMessageTypeMap(messageTypeMap);
		log.info("leaving from getHostelMessageTypeMap in HostelAdminMessageHandler class..");
		
	}
	
	
	public ActionForward getHostelAdminMessageList(
		    ActionMapping mapping,
		    ActionForm form,
		    HttpServletRequest request,
		    HttpServletResponse response) throws Exception{	
		log.info("entering into getHostelAdminMessageList  in HostelAdminMessageAction class..");
		HostelAdminMessageForm hostelAdminMessageForm=(HostelAdminMessageForm)form;	
		ActionMessages messages=new ActionMessages();
		ActionMessage message=null;
		ActionErrors errors = hostelAdminMessageForm.validate(mapping, request);
		try
		{
			setUserId(request, hostelAdminMessageForm);
			hostelAdminMessageForm.clearList();
			if(errors.isEmpty())
			{
			List<HostelAdminMessageTO> hostelAdminMessageTOList=null;
			hostelAdminMessageTOList = HostelAdminMessageHandler.getInstance().getHostelAdminMessageList(hostelAdminMessageForm);
			if(hostelAdminMessageTOList!=null && !hostelAdminMessageTOList.isEmpty()){
				hostelAdminMessageForm.setAdminMessageTOList(hostelAdminMessageTOList);
				HttpSession session=request.getSession(false);
				if(session.getAttribute("hostelAdminMessageTOList")!=null)
				{
					session.removeAttribute("hostelAdminMessageTOList");
				}
				session.setAttribute("hostelAdminMessageTOList", hostelAdminMessageTOList);
				if(session.getAttribute("messageTypeId")!=null)
				{
					session.removeAttribute("messageTypeId");
				}
				session.setAttribute("messageTypeId", hostelAdminMessageForm.getMessageTypeId());
			}
			else
			{
				message = new ActionMessage("knowledgepro.pettycash.no.active.record");
				messages.add("messages", message);
				saveMessages(request, messages);
				hostelAdminMessageForm.messageclear();
				setListToForm(hostelAdminMessageForm);
				return mapping.findForward(CMSConstants.INIT_HOSTEL_ADMN_MESSAGE);
			}
			}
			else
			{
				saveErrors(request, errors);
				hostelAdminMessageForm.clear();
				setListToForm(hostelAdminMessageForm);
				return mapping.findForward(CMSConstants.INIT_HOSTEL_ADMN_MESSAGE);
			}
		}
	    catch (Exception e) {
				log.error("error in loading getHostelAdminMessageList in HostelAdminMessageAction class...", e);
				if (e instanceof ApplicationException) {
					String msg = super.handleApplicationException(e);
					hostelAdminMessageForm.setErrorMessage(msg);
					hostelAdminMessageForm.setErrorStack(e.getMessage());
					return mapping.findForward(CMSConstants.ERROR_PAGE);
				} else {
					throw e;
				}
			}
		log.info("leaving from getHostelAdminMessageList in HostelAdminMessageAction class..");
		return mapping.findForward(CMSConstants.GET_HOSTEL_ADMNMESSAGE_LIST);
	
	}
		
	public ActionForward getLeaveStatusListByadmnApplId(
		    ActionMapping mapping,
		    ActionForm form,
		    HttpServletRequest request,
		    HttpServletResponse response) throws Exception{
		log.info("entering into getLeaveStatusListByadmnApplId in HostelAdminMessageAction class..");
		ActionMessages messages=new ActionMessages();
		//String type="Admin";
		HostelAdminMessageForm hostelAdminMessageForm=(HostelAdminMessageForm)form;	
		
		try
		{
			hostelAdminMessageForm.setStatus(hostelAdminMessageForm.getStatusId());
		if(hostelAdminMessageForm.getMessageTypeId().equalsIgnoreCase("2"))
		{
		setHostelStatusMapToForm(hostelAdminMessageForm);	
		setLeaveStatus(request,hostelAdminMessageForm);
		}
		else{
				setHostelStatusMapToForm(hostelAdminMessageForm);
				setComplaintStatus(request,hostelAdminMessageForm);
				return mapping.findForward("getComplaintStatus");
			}
		}
		catch (Exception e) {
			log.error("error in while  manageLeaveStatus of program page...", e);
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
				
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				hostelAdminMessageForm.setErrorMessage(msg);
				hostelAdminMessageForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
				
			} 
		}
		log.info("leaving from getLeaveStatusListByadmnApplId in HostelAdminMessageAction class..");
		return mapping.findForward(CMSConstants.GET_LEAVE_STATUS_LIST);
	}
	
	
	@SuppressWarnings("unchecked")
	public ActionForward manageLeaveStatus(
		    ActionMapping mapping,
		    ActionForm form,
		    HttpServletRequest request,
		    HttpServletResponse response) throws Exception{
		log.info("entering into manageLeaveStatus in HostelAdminMessageAction class..");
		HostelAdminMessageForm hostelAdminMessageForm=(HostelAdminMessageForm)form;
		ActionMessages messages=new ActionMessages();
		ActionErrors errors = hostelAdminMessageForm.validate(mapping, request);
		boolean isUpdated=false;
		String tempMessageTypeId=hostelAdminMessageForm.getMessageTypeId();
		if(isCancelled(request)){
			if(request.getSession().getAttribute("hostelAdminMessageTOList")!=null)
			{
				List<HostelAdminMessageTO> hostelAdminMessageTOList=null;
				hostelAdminMessageTOList=(List<HostelAdminMessageTO>)request.getSession().getAttribute("hostelAdminMessageTOList");
				hostelAdminMessageForm.setAdminMessageTOList(hostelAdminMessageTOList);
			}
			return mapping.findForward(CMSConstants.GET_HOSTEL_ADMNMESSAGE_LIST);
		}
		
		try
		{
			setUserId(request, hostelAdminMessageForm);
			if(errors.isEmpty())
			{
				
			isUpdated=HostelAdminMessageHandler.getInstance().manageLeaveStatus(hostelAdminMessageForm);
			if(isUpdated)
			{
//				hostelAdminMessageForm.clear();
				if(tempMessageTypeId.equals("2"))
				{
					setLeaveStatus(request,hostelAdminMessageForm);
				}
				else
				{
					setComplaintStatus(request, hostelAdminMessageForm);	
				}
				ActionMessage message=null;
				 message = new ActionMessage("knowledgepro.pettycash.receiptNumber.updated",
						 hostelAdminMessageForm.getCommonId());
				
				messages.add("messages", message);
				saveMessages(request, messages);
				hostelAdminMessageForm.setStatusId(hostelAdminMessageForm.getStatus());
				List<HostelAdminMessageTO> hostelAdminMessageTOList=null;
				hostelAdminMessageTOList = HostelAdminMessageHandler.getInstance().getHostelAdminMessageList(hostelAdminMessageForm);
				if(hostelAdminMessageTOList!=null && !hostelAdminMessageTOList.isEmpty()){
					hostelAdminMessageForm.setAdminMessageTOList(hostelAdminMessageTOList);
					HttpSession session=request.getSession(false);
					if(session.getAttribute("hostelAdminMessageTOList")!=null)
					{
						session.removeAttribute("hostelAdminMessageTOList");
					}
					session.setAttribute("hostelAdminMessageTOList", hostelAdminMessageTOList);
					if(session.getAttribute("messageTypeId")!=null)
					{
						session.removeAttribute("messageTypeId");
					}
					session.setAttribute("messageTypeId", hostelAdminMessageForm.getMessageTypeId());
				}
				return mapping.findForward(CMSConstants.GET_HOSTEL_ADMNMESSAGE_LIST);
			}
			else
			{
				errors.add("error", new ActionError("knowledgepro.pettycash.receiptNumber.failed",
						hostelAdminMessageForm.getCommonId()));						
			
			saveErrors(request,errors);
			}
			}
			else
			{
				saveErrors(request,errors);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			log.error("error in while  manageLeaveStatus of program page...", e);
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
				
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				hostelAdminMessageForm.setErrorMessage(msg);
				hostelAdminMessageForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
				
			} 
		}
		if(!errors.isEmpty())
		{
			if(tempMessageTypeId.equalsIgnoreCase("2"))
			{
			setLeaveStatus(request,hostelAdminMessageForm);
			return mapping.findForward("manageLeaveStatus");
			}
			else
			{
				setComplaintStatus(request, hostelAdminMessageForm);
				return mapping.findForward("getComplaintStatus");
			}
		}
		log.info("leaving from manageLeaveStatus in HostelAdminMessageAction class..");
		if(tempMessageTypeId.equalsIgnoreCase("2"))
		{
		return mapping.findForward("manageLeaveStatus");
		}
		else
		{
			return mapping.findForward("getComplaintStatus");
		}
		
	}
	public void setListToForm(HostelAdminMessageForm hostelAdminMessageForm)throws Exception
	{
		//hostelAdminMessageForm.clear();
		setHostelStatusMapToForm(hostelAdminMessageForm);
		setHostelMessageTypeToForm(hostelAdminMessageForm);
	}
	public void setLeaveStatus(HttpServletRequest request,HostelAdminMessageForm hostelAdminMessageForm)throws Exception
	{
		if(request.getSession().getAttribute("messageTypeId")!=null)
		{
			hostelAdminMessageForm.setMessageTypeId(String.valueOf(request.getSession().getAttribute("messageTypeId")));
		}
		LeaveTypeTo leaveTypeTo=HostelAdminMessageHandler.getInstance().getLeaveStatusListByadmnApplId(hostelAdminMessageForm);
		leaveTypeTo.setCommonId(hostelAdminMessageForm.getCommonId());
		//HttpSession session=request.getSession(false);
		if(request.getSession().getAttribute("leaveTypeTo")!=null)
		{
			request.getSession().removeAttribute("leaveTypeTo");
			//session.removeAttribute("leaveTypeTo");
		}
		
		request.getSession().setAttribute("leaveTypeTo", leaveTypeTo);
		if(leaveTypeTo.getStatusId()!=null && !leaveTypeTo.getStatusId().equals(""))
		{
		hostelAdminMessageForm.setStatusId(leaveTypeTo.getStatusId());
		}
	}
	
	public void setComplaintStatus(HttpServletRequest request,HostelAdminMessageForm hostelAdminMessageForm)throws Exception
	{
		ComplaintsTO complaintTo=HostelAdminMessageHandler.getInstance().getComplaintStatusListByadmnApplId(hostelAdminMessageForm);
		if(request.getSession().getAttribute("complaintTypeTo")!=null)
		{
			request.getSession().removeAttribute("complaintTypeTo");
		}
		request.getSession().setAttribute("complaintTypeTo", complaintTo);	
		if(complaintTo.getStatusId()!=null && !complaintTo.getStatusId().equals(""))
		{
		hostelAdminMessageForm.setStatusId(complaintTo.getStatusId());
		}
	}
	
}
	

