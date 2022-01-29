package com.kp.cms.actions.pettycash;


import java.util.ArrayList;
import java.util.List;

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
import com.kp.cms.bo.admin.PcAccHeadGroup;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.forms.pettycash.PettyCashAccountHeadGroupCodeForm;
import com.kp.cms.handlers.pettycash.PettyCashAccountHeadGroupCodeHandler;
import com.kp.cms.to.pettycash.PettyCashAccountHeadGroupCodeTO;

@SuppressWarnings("deprecation")
public class PettyCashAccountHeadGroupCodeAction extends BaseDispatchAction{
	
private static Log log = LogFactory.getLog(PettyCashAccountHeadGroupCodeAction.class);
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return To fetch the total account entries from the database
	 * @throws Exception
	 */

	public ActionForward initAccountHeadGroupCode(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{		
		PettyCashAccountHeadGroupCodeForm pettyCashAccHeadGroupCodeForm=(PettyCashAccountHeadGroupCodeForm)form;
		try
		{
			log.info("entering into initpettyCashAccountHeadGroupCode in PettyCashAccountHeadGroupCodeAction class..");
			setListToRequest(request, pettyCashAccHeadGroupCodeForm);
			log.info("leaving from initpettyCashAccountHeadGroupCode in PettyCashAccountHeadGroupCodeAction class..");
		}catch (Exception e) {
			log.error("error in loading pettyCashAccountHeadGroupCode in PettyCashAccountHeadGroupCodeAction class...", e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				pettyCashAccHeadGroupCodeForm.setErrorMessage(msg);
				pettyCashAccHeadGroupCodeForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		return mapping.findForward(CMSConstants.PETTYCASH_INIT_ACCHEAD_GROUP_CODE);
	}
	
	public ActionForward managepettyCashAccHeadGroupCode(
		    ActionMapping mapping,
		    ActionForm form,
		    HttpServletRequest request,
		    HttpServletResponse response) throws Exception{
		log.info("entering into managepettyCashAccHeadGroupCode in PettyCashAccountHeadGroupCodeAction class..");
		PettyCashAccountHeadGroupCodeForm pettyCashAccHeadGroupCodeForm=(PettyCashAccountHeadGroupCodeForm)form;
		PettyCashAccountHeadGroupCodeForm tempPCashAccHeadGroupCodeForm=null;
		
		ActionMessages messages=new ActionMessages();
		ActionErrors errors = pettyCashAccHeadGroupCodeForm.validate(mapping, request);
		String groupName = pettyCashAccHeadGroupCodeForm.getGroupCode();
		
		boolean isAdded=false;
		boolean isUpdated=false;
		String task=null;
		if(pettyCashAccHeadGroupCodeForm.getId()!=null && !pettyCashAccHeadGroupCodeForm.getId().equals(""))
		{
			if(pettyCashAccHeadGroupCodeForm.getId().intValue()==0)
			{
				pettyCashAccHeadGroupCodeForm.setId(null);
			}
		}
		if(pettyCashAccHeadGroupCodeForm.getId()!=null && !pettyCashAccHeadGroupCodeForm.getId().equals(""))
		{
			 task="Update";
		}
		else
		{
			task="Add";
		}
		
		try
		{
			setUserId(request, pettyCashAccHeadGroupCodeForm);
			if(isCancelled(request)){
				if(request.getSession().getAttribute("pettyCashAccHeadGroupCodeForm")!=null)
				{
					 tempPCashAccHeadGroupCodeForm=(PettyCashAccountHeadGroupCodeForm)request.getSession().getAttribute("pettyCashAccHeadGroupCodeForm");
					 tempPCashAccHeadGroupCodeForm.setGroupCode(tempPCashAccHeadGroupCodeForm.getOldGroupCode());
					 tempPCashAccHeadGroupCodeForm.setGroupName(tempPCashAccHeadGroupCodeForm.getOldGroupName());
					 tempPCashAccHeadGroupCodeForm.setId(tempPCashAccHeadGroupCodeForm.getOldId());
				}
				if(tempPCashAccHeadGroupCodeForm!=null)
				{
					setListToRequest(request,tempPCashAccHeadGroupCodeForm);
				}
				request.setAttribute("task", "Update");
				return mapping.findForward(CMSConstants.PETTYCASH_INIT_ACCHEAD_GROUP_CODE);
			}

			
			if(errors.isEmpty())
			{
				if(pettyCashAccHeadGroupCodeForm.getId()!=null && !pettyCashAccHeadGroupCodeForm.getId().equals("") )
				{	
					if(pettyCashAccHeadGroupCodeForm.getGroupCode()!=null && pettyCashAccHeadGroupCodeForm.getOldGroupCode()!=null &&
					pettyCashAccHeadGroupCodeForm.getGroupCode().equalsIgnoreCase(pettyCashAccHeadGroupCodeForm.getOldGroupCode())){
						task="Update";
						isUpdated= PettyCashAccountHeadGroupCodeHandler.getInstance().manageAccountHeadGroupCode(pettyCashAccHeadGroupCodeForm,task);
						pettyCashAccHeadGroupCodeForm.clear();
						setListToRequest(request,pettyCashAccHeadGroupCodeForm);
					}
					
					else{
						task="Update";
						String formGroupCode=pettyCashAccHeadGroupCodeForm.getGroupCode();
						PcAccHeadGroup group = PettyCashAccountHeadGroupCodeHandler.getInstance().checkDuplicate(formGroupCode);
						if(group!=null){
							errors.add("error", new ActionError(
									"knowledgepro.pettycash.accheadgroup.addexist", groupName));
							saveErrors(request,errors);
							
							request.setAttribute("task", task);
							return mapping.findForward("initAccountHeadGroupCode");
						}
						else{
							isUpdated= PettyCashAccountHeadGroupCodeHandler.getInstance().manageAccountHeadGroupCode(pettyCashAccHeadGroupCodeForm,task);
							pettyCashAccHeadGroupCodeForm.clear();
							setListToRequest(request,pettyCashAccHeadGroupCodeForm);
						}
						
					}
					
				}
				else
				{	 task="Add";
					isAdded = PettyCashAccountHeadGroupCodeHandler.getInstance().manageAccountHeadGroupCode(pettyCashAccHeadGroupCodeForm,task);
					pettyCashAccHeadGroupCodeForm.clear();
					setListToRequest(request,pettyCashAccHeadGroupCodeForm);
					request.setAttribute("task",task);
				}
				if (isAdded || isUpdated) 
				{
					ActionMessage message=null;
					if(task.equalsIgnoreCase("Add"))
					{
					 message = new ActionMessage("knowledgepro.pettycash.accheadgroup.addsuccess",
							groupName);
					}
					else
					{
						 message = new ActionMessage("knowledgepro.pettycash.accheadgroup.updatesuccess",
								groupName);
					}
					messages.add("messages", message);
					saveMessages(request, messages);
					
				} else {
					if(task.equalsIgnoreCase("Add"))
					{
					errors.add("error", new ActionError("knowledgepro.pettycash.acchadgroup.addfail",
							groupName));
					}
					else
					{
						errors.add("error", new ActionError("knowledgepro.pettycash.accheadgroup.updatefail",
								groupName));						
					}
					saveErrors(request,errors);
				}
			}else
			{
				saveErrors(request,errors);
			}
		}catch (DuplicateException e1) {
			errors.add("error", new ActionError(
					"knowledgepro.pettycash.accheadgroup.addexist", groupName));
			saveErrors(request,errors);
			return mapping.findForward(CMSConstants.PETTYCASH_INIT_ACCHEAD_GROUP_CODE);
		} 
		
		catch (Exception e) {
			log.error("error in while  ManageAction of program page...", e);
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
				
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				pettyCashAccHeadGroupCodeForm.setErrorMessage(msg);
				pettyCashAccHeadGroupCodeForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
				
			} else {
				throw e;
			}
			
		}
		if(!errors.isEmpty())
		{
			setListToRequest(request,pettyCashAccHeadGroupCodeForm);
			if(request.getAttribute("task")!=null)
			{
				request.setAttribute("task",request.getAttribute("task").toString());
			}
			else
			{
				request.setAttribute("task",task);
			}
		}
		log.info("leaving from managepettyCashAccHeadGroupCode in PettyCashAccountHeadGroupCodeAction class..");
		return mapping.findForward(CMSConstants.PETTYCASH_INIT_ACCHEAD_GROUP_CODE);
		
	}
	
	public ActionForward deletePettyCashAccHeadGroupCode(
		    ActionMapping mapping,
		    ActionForm form,
		    HttpServletRequest request,
		    HttpServletResponse response) throws Exception{
		log.info("entering into deletePettyCashAccHeadGroupCode in PettyCashAccountHeadGroupCodeAction class..");
		PettyCashAccountHeadGroupCodeForm pettyCashAccHeadGroupCodeForm=(PettyCashAccountHeadGroupCodeForm)form;
		ActionMessages messages=new ActionMessages();
		ActionErrors errors = pettyCashAccHeadGroupCodeForm.validate(mapping, request);
		String task="Delete";
		boolean isDelete=false;
		try
		{
			setUserId(request, pettyCashAccHeadGroupCodeForm);
			isDelete =  PettyCashAccountHeadGroupCodeHandler.getInstance().deleteAccountHeadGroupCode(pettyCashAccHeadGroupCodeForm,task);
			pettyCashAccHeadGroupCodeForm.clear();
			setListToRequest(request,pettyCashAccHeadGroupCodeForm);
			if (isDelete) {
				ActionMessage message = new ActionMessage("knowledgepro.pettycash.accheadgroup.deletesuccess");
				messages.add("messages", message);
				saveMessages(request, messages);
				//feeAccountForm.reset(mapping, request);
			} else {
				errors.add("error", new ActionError("AccountHeadGroupCodes is not deleted"));
				saveErrors(request,errors);
			}
		
		}
		catch (Exception e) {
			log.error("error in delete of program page...", e);
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				pettyCashAccHeadGroupCodeForm.setErrorMessage(msg);
				pettyCashAccHeadGroupCodeForm.setErrorStack(e.getMessage());
				
			} else {
				throw e;
			}
			
		}
		log.info("leaving from deletePettyCashAccHeadGroupCode in PettyCashAccountHeadGroupCodeAction class..");
		return mapping.findForward(CMSConstants.PETTYCASH_INIT_ACCHEAD_GROUP_CODE);
	}
	
	public ActionForward editPettyCashAccHeadGroupCode(ActionMapping mapping,
		    ActionForm form,
		    HttpServletRequest request,
		    HttpServletResponse response) throws Exception{
		log.info("enter into editPettyCashAccHeadGroupCode in PettyCashAccountHeadGroupCodeAction class..");
		ActionMessages messages=new ActionMessages();
		PettyCashAccountHeadGroupCodeForm pettyCashAccHeadGroupCodeForm=(PettyCashAccountHeadGroupCodeForm)form;
		try
		{
			PettyCashAccountHeadGroupCodeHandler.getInstance().editAccountHeadGroupCode(pettyCashAccHeadGroupCodeForm.getId(),pettyCashAccHeadGroupCodeForm);
			request.setAttribute("task", "Update");
			setListToRequest(request,pettyCashAccHeadGroupCodeForm);
		}
		catch (Exception e) {
			log.error("error in editing HeadGroupCode...", e);
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				pettyCashAccHeadGroupCodeForm.setErrorMessage(msg);
				pettyCashAccHeadGroupCodeForm.setErrorStack(e.getMessage());
				
			} 
			
		}
		log.info("leaving from editPettyCashAccHeadGroupCode in PettyCashAccountHeadGroupCodeAction class..");
		return mapping.findForward(CMSConstants.PETTYCASH_INIT_ACCHEAD_GROUP_CODE);
	}
	
	public void setListToRequest(HttpServletRequest request,ActionForm accountForm)throws Exception
	{
		log.info("enter into setListToRequest in PettyCashAccountHeadGroupCodeAction class..");
		PettyCashAccountHeadGroupCodeForm pcAccHeadGroupCodeForm=(PettyCashAccountHeadGroupCodeForm)accountForm;
		List<PettyCashAccountHeadGroupCodeTO> pcHeadGroupList=PettyCashAccountHeadGroupCodeHandler.getInstance().getAllPettyCashAccHeadGroupCode();
		if(pcHeadGroupList!=null && !pcHeadGroupList.isEmpty())
		{
		pcAccHeadGroupCodeForm.setPettyCashAccountHeadGroupCodeListTO(pcHeadGroupList);
		}else{
			pcAccHeadGroupCodeForm.setPettyCashAccountHeadGroupCodeListTO(new ArrayList<PettyCashAccountHeadGroupCodeTO>());
		}
		log.info("leaving from setListToRequest in PettyCashAccountHeadGroupCodeAction class..");
	}

}



