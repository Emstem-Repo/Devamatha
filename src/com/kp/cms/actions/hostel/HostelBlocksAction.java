package com.kp.cms.actions.hostel;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
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
import com.kp.cms.actions.hostel.HostelBlocksAction;
import com.kp.cms.bo.admin.HlBlocks;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.forms.hostel.HostelBlocksForm;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.handlers.hostel.HostelBlocksHandler;
import com.kp.cms.handlers.hostel.HostelEntryHandler;
import com.kp.cms.to.hostel.HostelBlocksTO;
import com.kp.cms.to.hostel.HostelTO;
import com.kp.cms.utilities.CommonUtil;

public class HostelBlocksAction extends BaseDispatchAction {
	private static final Log log = LogFactory.getLog(HostelBlocksAction.class);
	
	public ActionForward initHostelBlocks(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Start of HostelBlocksAction --- initHostelBlocks");
		HostelBlocksForm hostelBlocksForm=(HostelBlocksForm)form;		
		try {
			hostelBlocksForm.clearAll();
			assignListToForm(hostelBlocksForm);
			setHostelListToRequest(request);
			
		} catch (Exception e) {
			log.error("Error in initializing HostelBlocks",e);
				log.error("Error occured in initHostelBlocks of HostelBlocksAction");
				String msg = super.handleApplicationException(e);
				hostelBlocksForm.setErrorMessage(msg);
				hostelBlocksForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		log.info("Leaving of HostelBlocksAction --- initHostelBlocks");
		return mapping.findForward(CMSConstants.INIT_HOSTELBLOCKS);
	}
	
	public void assignListToForm(ActionForm form) throws Exception
	{
		log.info("Entering into -- HostelBlocksAction --- assignListToForm");
		HostelBlocksForm hostelBlocksForm	= (HostelBlocksForm) form;
		try {
			List<HostelBlocksTO> hostelBlocksList = HostelBlocksHandler.getInstance().getHostelBlocksDetails();
			hostelBlocksForm.setBlocksList(hostelBlocksList);
		} catch (Exception e) {
			log.error("Error in assignListToForm of HostelBlocks Action",e);
				String msg = super.handleApplicationException(e);
				hostelBlocksForm.setErrorMessage(msg);
				hostelBlocksForm.setErrorStack(e.getMessage());
			}
		log.info("Leaving from -- HostelBlocksAction --- assignListToForm");
	}
	
	public ActionForward addHostelBlocks(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entring into-- HostelBlocksAction --- addHostelBlocks");
		HostelBlocksForm hostelBlocksForm=(HostelBlocksForm)form;
		 ActionErrors errors = hostelBlocksForm.validate(mapping, request);
		ActionMessages messages = new ActionMessages();
		try {
			if(isCancelled(request))
			{
				hostelBlocksForm.clearAll();
				return mapping.findForward(CMSConstants.RESET_ALL);
			}			
			if(errors.isEmpty())
			{
			setUserId(request, hostelBlocksForm);
			HlBlocks hostelBlocks=HostelBlocksHandler.getInstance().checkForDuplicateonName(hostelBlocksForm.getHostelId(),hostelBlocksForm.getName());
			if(hostelBlocks!=null){
				if(hostelBlocks.getIsActive()){
					errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.HOSTEL_HOSTELBLOCKS_EXISTS));
					assignListToForm(hostelBlocksForm);
					setHostelListToRequest(request);
					}

					else if(!hostelBlocks.getIsActive()){
					errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.HOSTEL_HOSTELBLOCKS_REACTIVATE));
					assignListToForm(hostelBlocksForm);
					setHostelListToRequest(request);
					}
			}
			else{
			boolean isHostelBlocksAdded;

			isHostelBlocksAdded = HostelBlocksHandler.getInstance().addHostelBlocks(hostelBlocksForm);
			//If add operation is success then display the success message.
			if(isHostelBlocksAdded)
			{
				messages.add(CMSConstants.MESSAGES, new ActionMessage(CMSConstants.HOSTEL_HOSTELBLOCKS_ADD_SUCCESS));
				saveMessages(request, messages);
				assignListToForm(hostelBlocksForm);
				setHostelListToRequest(request);
				return mapping.findForward(CMSConstants.INIT_HOSTELBLOCKS);
			}
			//If add operation is failure then add the error message.
			else{
				errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.HOSTEL_HOSTELBLOCKS_ADD_FAILED));
				saveErrors(request, errors);
				assignListToForm(hostelBlocksForm);
				setHostelListToRequest(request);
				}
			}
			}
		}catch (Exception e) {
				log.error("Error in adding HostelBlocks in HostelBlocks Action",e);
				String msg = super.handleApplicationException(e);
				hostelBlocksForm.setErrorMessage(msg);
				hostelBlocksForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		log.info("Leaving from -- HostelBlocksAction --- addHostelBlocks");
		saveErrors(request, errors);
		assignListToForm(hostelBlocksForm);
		setHostelListToRequest(request);
		return mapping.findForward(CMSConstants.INIT_HOSTELBLOCKS);
	}
	
	public ActionForward deleteHostelBlocks(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entering into--- HostelBlocksAction --- deleteHostelBlocks");
		HostelBlocksForm hostelBlocksForm=(HostelBlocksForm)form;
		 ActionErrors errors = hostelBlocksForm.validate(mapping, request);
		ActionMessages messages = new ActionMessages();
		
		try {
			setUserId(request, hostelBlocksForm);
			int hostelBlocksId = hostelBlocksForm.getId();
			String userId=hostelBlocksForm.getUserId();
			boolean isHostelBlocksDeleted;
			
			isHostelBlocksDeleted = HostelBlocksHandler.getInstance().deleteHostelBlocks(hostelBlocksId, userId);
			//If delete operation is success then append the success message.
			if (isHostelBlocksDeleted) {
				messages.add(CMSConstants.MESSAGES, new ActionMessage(CMSConstants.HOSTEL_HOSTELBLOCKS_DELETE_SUCCESS));
				saveMessages(request, messages);
				assignListToForm(hostelBlocksForm);
				setHostelListToRequest(request);
			}
			//If delete operation is failure then add the error message.
			else {
				errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.HOSTEL_HOSTELBLOCKS_DELETE_FAILED));
				saveErrors(request, errors);
				assignListToForm(hostelBlocksForm);
				setHostelListToRequest(request);
			}
		} catch (Exception e) {
			log.error("Error in deleting HostelBlocks in HostelBlocks Action",e);
				String msg = super.handleApplicationException(e);
				hostelBlocksForm.setErrorMessage(msg);
				hostelBlocksForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		log.info("Leaving from-- HostelBlocksAction --- deleteHostelBlocks");
		return mapping.findForward(CMSConstants.INIT_HOSTELBLOCKS);
	}
	
	public ActionForward editHostelBlocks(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entering into---- HostelBlocksAction --- editHostelBlocks");
		HostelBlocksForm hostelBlocksForm=(HostelBlocksForm)form;
		try {
			int x;
			HostelBlocksTO hostelBlocksTO=HostelBlocksHandler.getInstance().getDetailsonId(hostelBlocksForm.getId());
				//Set the TO properties to formbean
				if(hostelBlocksTO!=null){
					if (hostelBlocksTO.getName() != null && !hostelBlocksTO.getName().isEmpty()) {
						hostelBlocksForm.setName(hostelBlocksTO.getName());
					}
					
					if (hostelBlocksTO.getHostelTO()!=null){
						if(hostelBlocksTO.getHostelTO().getId()>0){
							hostelBlocksForm.setHostelId(String.valueOf(hostelBlocksTO.getHostelTO().getId()));
						}
					}
			}
			request.setAttribute("blocksOperation", CMSConstants.EDIT_OPERATION);
			assignListToForm(hostelBlocksForm);
			setHostelListToRequest(request);
		} catch (Exception e) {
			log.error("Error in editing HostelBlocks in Action",e);
				String msg = super.handleApplicationException(e);
				hostelBlocksForm.setErrorMessage(msg);
				hostelBlocksForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		log.info("Leaving from-- HostelBlocksAction --- editHostelBlocks");
		assignListToForm(hostelBlocksForm);
		setHostelListToRequest(request);
		return mapping.findForward(CMSConstants.INIT_HOSTELBLOCKS);
	}
	
	public ActionForward updateHostelBlocks(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entering into-- HostelBlocksAction --- updateHostelBlocks");
		HostelBlocksForm hostelBlocksForm=(HostelBlocksForm)form; 
		 ActionErrors errors = hostelBlocksForm.validate(mapping, request);
		ActionMessages messages = new ActionMessages();
		try {
			if(errors.isEmpty())
			{
				setUserId(request, hostelBlocksForm);
				boolean isUpdated;
				
						isUpdated=HostelBlocksHandler.getInstance().updateHostelBlocks(hostelBlocksForm);
						
						if(isUpdated){
							messages.add(CMSConstants.MESSAGES, new ActionMessage(CMSConstants.HOSTEL_HOSTELBLOCKS_UPDATE_SUCCESS));
							saveMessages(request, messages);
							hostelBlocksForm.clearAll();
							assignListToForm(hostelBlocksForm);
							setHostelListToRequest(request);
							return mapping.findForward(CMSConstants.INIT_HOSTELBLOCKS);				
						}
						else {
							errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.HOSTEL_HOSTELBLOCKS_UPDATE_FAILED));
							saveErrors(request, errors);
							assignListToForm(hostelBlocksForm);
							setHostelListToRequest(request);
							return mapping.findForward(CMSConstants.INIT_HOSTELBLOCKS);
						}
			}
		else{
			request.setAttribute("blocksOperation",CMSConstants.EDIT_OPERATION);
		}
		}catch (DuplicateException e1) {
			errors.add("error", new ActionError("knowledgepro.hostel.blocks.exists"));
			saveErrors(request, errors);
			assignListToForm(hostelBlocksForm);
			setHostelListToRequest(request);
			request.setAttribute("blocksOperation", CMSConstants.EDIT_OPERATION);
			return mapping.findForward(CMSConstants.INIT_HOSTELBLOCKS);
		}
		catch (Exception e) {
			log.error("Error in updating HostelBlocks in HostelBlocks Action",e);
				String msg = super.handleApplicationException(e);
				hostelBlocksForm.setErrorMessage(msg);
				hostelBlocksForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		log.info("Leaving from --- HostelBlocksAction --- updateHostelBlocks");
		saveErrors(request, errors);
		assignListToForm(hostelBlocksForm);
		setHostelListToRequest(request);
		return mapping.findForward(CMSConstants.INIT_HOSTELBLOCKS);
	}
	
	public ActionForward reActivateHostelBlocks(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entering into-- HostelBlocksAction --- reActivateHostelBlocks");
		HostelBlocksForm hostelBlocksForm=(HostelBlocksForm)form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();		
		try {
			setUserId(request, hostelBlocksForm);
			boolean isReacivate;
			//Request for reactivate by calling the handler method and pass the name
			isReacivate = HostelBlocksHandler.getInstance().reActivateHostelBlocks(hostelBlocksForm.getName(),hostelBlocksForm.getUserId());

			if (isReacivate) {
				messages.add(CMSConstants.MESSAGES, new ActionMessage(CMSConstants.HOSTEL_HOSTELBLOCKS_REACTIVATE_SUCCESS));
				saveMessages(request, messages);
				hostelBlocksForm.clearAll();
				assignListToForm(hostelBlocksForm);
				setHostelListToRequest(request);
				return mapping.findForward(CMSConstants.INIT_HOSTELBLOCKS);
			} else {
				errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.HOSTEL_HOSTELBLOCKS_REACTIVATE_FAILED));
				saveErrors(request, errors);
				assignListToForm(hostelBlocksForm);
				setHostelListToRequest(request);
			}
		} catch (Exception e) {
			log.error("Error in reactivating HostelBlocksAction",e);
				String msg = super.handleApplicationException(e);
				hostelBlocksForm.setErrorMessage(msg);
				hostelBlocksForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		log.info("Leaving from-- HostelBlocksAction --- reActivateHostelBlocks");
		return mapping.findForward(CMSConstants.INIT_HOSTELBLOCKS);
	}
	
	public void setHostelListToRequest(HttpServletRequest request) throws Exception {
		log.debug("inside setHostelListToRequest");
		 List<HostelTO> hostelList = HostelEntryHandler.getInstance().getHostelDetails();
		request.setAttribute("hostelList", hostelList);
	}	

}
