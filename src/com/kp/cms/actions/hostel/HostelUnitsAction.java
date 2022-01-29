package com.kp.cms.actions.hostel;

import java.util.ArrayList;
import java.util.Collections;
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

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.bo.admin.HlUnits;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.forms.hostel.HostelUnitsForm;
import com.kp.cms.handlers.hostel.HostelEntryHandler;
import com.kp.cms.handlers.hostel.HostelUnitsHandler;
import com.kp.cms.to.hostel.HostelTO;
import com.kp.cms.to.hostel.HostelUnitsTO;

public class HostelUnitsAction extends BaseDispatchAction {
	private static final Log log = LogFactory.getLog(HostelUnitsAction.class);
	
	public ActionForward initHostelUnits(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		log.info("Start of HostelUnitsAction --- initHostelUnits");
		HostelUnitsForm hostelUnitsForm=(HostelUnitsForm)form;		
		try {
			String formName = mapping.getName();
			request.getSession().setAttribute(CMSConstants.FORMNAME, formName);
			hostelUnitsForm.clearAll();
			setDataToForm(hostelUnitsForm);
			assignListToForm(hostelUnitsForm);
			setUserId(request, hostelUnitsForm);
		} catch (Exception e) {
			log.error("error initAssignRoomMaster...", e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				hostelUnitsForm.setErrorMessage(msg);
				hostelUnitsForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		log.info("Leaving of HostelUnitsAction --- initHostelUnits");
		return mapping.findForward(CMSConstants.INIT_HOSTELUNITS);
	}
	
	public void assignListToForm(ActionForm form) throws Exception
	{
		log.info("Entering into -- HostelUnitsAction --- assignListToForm");
		HostelUnitsForm hostelUnitsForm	= (HostelUnitsForm) form;
		try {
			List<HostelUnitsTO> hostelUnitsList = HostelUnitsHandler.getInstance().getHostelUnitsDetails();
			hostelUnitsForm.setUnitsList(hostelUnitsList);
		} catch (Exception e) {
			log.error("Error in assignListToForm of HostelBlocks Action",e);
				String msg = super.handleApplicationException(e);
				hostelUnitsForm.setErrorMessage(msg);
				hostelUnitsForm.setErrorStack(e.getMessage());
			}
		log.info("Leaving from -- HostelBlocksAction --- assignListToForm");
	}
	
	private void setDataToForm(HostelUnitsForm unitsForm) throws Exception{
		 List<HostelTO> hostelList = HostelEntryHandler.getInstance().getHostelDetails();
		unitsForm.setHostelList(hostelList);
		if(unitsForm.getHostelId() != null && !unitsForm.getHostelId().trim().isEmpty()){
			unitsForm.setBlockMap(HostelEntryHandler.getInstance().getBlocks(unitsForm.getHostelId()));
		}
	}
	
	public ActionForward addHostelUnits(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entring into-- HostelUnitsAction --- addHostelUnits");
		HostelUnitsForm hostelUnitsForm=(HostelUnitsForm)form;
		 ActionErrors errors = hostelUnitsForm.validate(mapping, request);
		ActionMessages messages = new ActionMessages();
		try {
			if(isCancelled(request))
			{
				hostelUnitsForm.clearAll();
				return mapping.findForward(CMSConstants.RESET_ALL);
			}			
			if(errors.isEmpty())
			{
			setUserId(request, hostelUnitsForm);
			HlUnits hostelUnits=HostelUnitsHandler.getInstance().checkForDuplicateonName(hostelUnitsForm.getBlockId(),hostelUnitsForm.getName());
			if(hostelUnits!=null){
				if(hostelUnits.getIsActive()){
					errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.HOSTEL_HOSTELUNITS_EXISTS));
					setDataToForm(hostelUnitsForm);
					assignListToForm(hostelUnitsForm);
					}

					else if(!hostelUnits.getIsActive()){
					errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.HOSTEL_HOSTELUNITS_REACTIVATE));
					setDataToForm(hostelUnitsForm);
					assignListToForm(hostelUnitsForm);
					}
			}
			else{
			boolean isHostelUnitsAdded;

			isHostelUnitsAdded = HostelUnitsHandler.getInstance().addHostelUnits(hostelUnitsForm);
			//If add operation is success then display the success message.
			if(isHostelUnitsAdded)
			{
				messages.add(CMSConstants.MESSAGES, new ActionMessage(CMSConstants.HOSTEL_HOSTELUNITS_ADD_SUCCESS));
				saveMessages(request, messages);
				hostelUnitsForm.clearAll();
				setDataToForm(hostelUnitsForm);
				assignListToForm(hostelUnitsForm);
				return mapping.findForward(CMSConstants.INIT_HOSTELUNITS);
			}
			//If add operation is failure then add the error message.
			else{
				errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.HOSTEL_HOSTELUNITS_ADD_FAILED));
				saveErrors(request, errors);
				setDataToForm(hostelUnitsForm);
				assignListToForm(hostelUnitsForm);
				}
			}
			}
		}catch (Exception e) {
				log.error("Error in adding HostelUnits in HostelUnits Action",e);
				String msg = super.handleApplicationException(e);
				hostelUnitsForm.setErrorMessage(msg);
				hostelUnitsForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		log.info("Leaving from -- HostelUnitsAction --- addHostelUnits");
		saveErrors(request, errors);
		setDataToForm(hostelUnitsForm);
		assignListToForm(hostelUnitsForm);
		return mapping.findForward(CMSConstants.INIT_HOSTELUNITS);
	}
	
	public ActionForward deleteHostelUnits(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entering into--- HostelUnitsAction --- deleteHostelUnits");
		HostelUnitsForm hostelUnitsForm=(HostelUnitsForm)form;
		 ActionErrors errors = hostelUnitsForm.validate(mapping, request);
		ActionMessages messages = new ActionMessages();
		try {
			setUserId(request, hostelUnitsForm);
			int hostelUnitsId = hostelUnitsForm.getId();
			String userId=hostelUnitsForm.getUserId();
			boolean isHostelUnitsDeleted;
			
			isHostelUnitsDeleted = HostelUnitsHandler.getInstance().deleteHostelUnits(hostelUnitsId, userId);
			//If delete operation is success then append the success message.
			if (isHostelUnitsDeleted) {
				messages.add(CMSConstants.MESSAGES, new ActionMessage(CMSConstants.HOSTEL_HOSTELUNITS_DELETE_SUCCESS));
				saveMessages(request, messages);
				hostelUnitsForm.clearAll();
				setDataToForm(hostelUnitsForm);
				assignListToForm(hostelUnitsForm);
			}
			//If delete operation is failure then add the error message.
			else {
				errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.HOSTEL_HOSTELUNITS_DELETE_FAILED));
				saveErrors(request, errors);
				setDataToForm(hostelUnitsForm);
				assignListToForm(hostelUnitsForm);
			}
		} catch (Exception e) {
			log.error("Error in deleting HostelUnits in HostelUnits Action",e);
				String msg = super.handleApplicationException(e);
				hostelUnitsForm.setErrorMessage(msg);
				hostelUnitsForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		log.info("Leaving from-- HostelUnitsAction --- deleteHostelUnits");
		return mapping.findForward(CMSConstants.INIT_HOSTELUNITS);
	}
	
	public ActionForward editHostelUnits(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entering into---- HostelUnitsAction --- editHostelUnits");
		HostelUnitsForm hostelUnitsForm=(HostelUnitsForm)form;
		hostelUnitsForm.clearAll();
		try {
			HostelUnitsTO hostelUnitsTO=HostelUnitsHandler.getInstance().getDetailsonId(hostelUnitsForm.getId());
				//Set the TO properties to formbean
				if(hostelUnitsTO!=null){
					if (hostelUnitsTO.getName() != null && !hostelUnitsTO.getName().isEmpty()) {
						hostelUnitsForm.setName(hostelUnitsTO.getName());
					}
					if (hostelUnitsTO.getHostelTO() !=null ){
						if(hostelUnitsTO.getHostelTO().getId()>0){
							hostelUnitsForm.setHostelId(String.valueOf(hostelUnitsTO.getHostelTO().getId()));
						}
					}
					if(hostelUnitsTO.getNoOfFloors() != null){
						hostelUnitsForm.setNoOfFloors(String.valueOf(hostelUnitsTO.getNoOfFloors()));
					}
					if(hostelUnitsTO.getPrimaryContactDesignation() != null && !hostelUnitsTO.getPrimaryContactDesignation().isEmpty()){
						hostelUnitsForm.setPrimaryContactDesignation(hostelUnitsTO.getPrimaryContactDesignation());
					}
					if(hostelUnitsTO.getPrimaryContactEmail() != null && !hostelUnitsTO.getPrimaryContactEmail().isEmpty()){
						hostelUnitsForm.setPrimaryContactEmail(hostelUnitsTO.getPrimaryContactEmail());
					}
					if(hostelUnitsTO.getPrimaryContactMobile() != null && !hostelUnitsTO.getPrimaryContactMobile().isEmpty()){
						hostelUnitsForm.setPrimaryContactMobile(hostelUnitsTO.getPrimaryContactMobile());
					}
					if(hostelUnitsTO.getPrimaryContactName() != null && !hostelUnitsTO.getPrimaryContactName().isEmpty()){
						hostelUnitsForm.setPrimaryContactName(hostelUnitsTO.getPrimaryContactName());
					}
					if(hostelUnitsTO.getPrimaryContactPhone() != null && !hostelUnitsTO.getPrimaryContactPhone().isEmpty()){
						hostelUnitsForm.setPrimaryContactPhone(hostelUnitsTO.getPrimaryContactPhone());
					}
					if(hostelUnitsTO.getSecContactDesignation() != null && !hostelUnitsTO.getSecContactDesignation().isEmpty()){
						hostelUnitsForm.setSecContactDesignation(hostelUnitsTO.getSecContactDesignation());
					}
					if(hostelUnitsTO.getSecContactEmail() != null && !hostelUnitsTO.getSecContactEmail().isEmpty()){
						hostelUnitsForm.setSecContactEmail(hostelUnitsTO.getSecContactEmail());
					}
					if(hostelUnitsTO.getSecContactMobile() != null && !hostelUnitsTO.getSecContactMobile().isEmpty()){
						hostelUnitsForm.setSecContactMobile(hostelUnitsTO.getSecContactMobile());
					}
					if(hostelUnitsTO.getSecContactName() != null && !hostelUnitsTO.getSecContactName().isEmpty()){
						hostelUnitsForm.setSecContactName(hostelUnitsTO.getSecContactName());
					}
					if(hostelUnitsTO.getSecContactPhone() != null && !hostelUnitsTO.getSecContactPhone().isEmpty()){
						hostelUnitsForm.setSecContactPhone(hostelUnitsTO.getSecContactPhone());
					}
					
					if(hostelUnitsTO.getHostelBlocksTO()!=null){
						if(hostelUnitsTO.getHostelBlocksTO().getId()>0){
							hostelUnitsForm.setBlockId(String.valueOf(hostelUnitsTO.getHostelBlocksTO().getId()));
						}
					}	
					if(hostelUnitsTO.getOnlineLeave()!=null && !hostelUnitsTO.getOnlineLeave().isEmpty()){
						hostelUnitsForm.setOnlineLeave(hostelUnitsTO.getOnlineLeave());
					}
					if(hostelUnitsTO.getApplyBeforeHours()!=null && !hostelUnitsTO.getApplyBeforeHours().isEmpty()){
						hostelUnitsForm.setApplyBeforeHours(hostelUnitsTO.getApplyBeforeHours());
					}
					else{
						hostelUnitsForm.setApplyBeforeHours(null);
					}
					if(hostelUnitsTO.getApplyBeforeMin()!=null && !hostelUnitsTO.getApplyBeforeMin().isEmpty()){
						hostelUnitsForm.setApplyBeforeMin(hostelUnitsTO.getApplyBeforeMin());
					}
					else{
						hostelUnitsForm.setApplyBeforeMin(null);
					}
					if(hostelUnitsTO.getApplyBeforeNextDayHours()!=null && !hostelUnitsTO.getApplyBeforeNextDayHours().isEmpty()){
						hostelUnitsForm.setApplyBeforeNextDayHours(hostelUnitsTO.getApplyBeforeNextDayHours());
					}
					else{
						hostelUnitsForm.setApplyBeforeNextDayHours(null);
					}
					if(hostelUnitsTO.getApplyBeforeNextDayMin()!=null && !hostelUnitsTO.getApplyBeforeNextDayMin().isEmpty()){
						hostelUnitsForm.setApplyBeforeNextDayMin(hostelUnitsTO.getApplyBeforeNextDayMin());
					}
					else{
						hostelUnitsForm.setApplyBeforeNextDayMin(null);
					}
					if(hostelUnitsTO.getIntervalMails()!=null && !hostelUnitsTO.getIntervalMails().isEmpty()){
						hostelUnitsForm.setIntervalMails(hostelUnitsTO.getIntervalMails());
					}
					if(hostelUnitsTO.getSmsForParents()!=null && !hostelUnitsTO.getSmsForParents().isEmpty()){
						hostelUnitsForm.setSmsForParents(hostelUnitsTO.getSmsForParents());
					}
					if(hostelUnitsTO.getSmsForPrimaryCon()!=null && !hostelUnitsTO.getSmsForPrimaryCon().isEmpty()){
						hostelUnitsForm.setSmsForPrimaryCon(hostelUnitsTO.getSmsForPrimaryCon());
					}
					if(hostelUnitsTO.getSmsForSecondCon()!=null && !hostelUnitsTO.getSmsForSecondCon().isEmpty()){
						hostelUnitsForm.setSmsForSecondCon(hostelUnitsTO.getSmsForSecondCon());
					}
					if(hostelUnitsTO.getSmsOnMorning()!=null && !hostelUnitsTO.getSmsOnMorning().isEmpty()){
						hostelUnitsForm.setSmsOnMorning(hostelUnitsTO.getSmsOnMorning());
					}
					if(hostelUnitsTO.getSmsOnEvening()!=null && !hostelUnitsTO.getSmsOnEvening().isEmpty()){
						hostelUnitsForm.setSmsOnEvening(hostelUnitsTO.getSmsOnEvening());
					}
					if(hostelUnitsTO.getLeaveBeforeNoOfDays()!=null && !hostelUnitsTO.getLeaveBeforeNoOfDays().isEmpty()){
						hostelUnitsForm.setLeaveBeforeNoOfDays(hostelUnitsTO.getLeaveBeforeNoOfDays());
					}
					if(hostelUnitsTO.getPunchExepSundaySession()!=null && !hostelUnitsTO.getPunchExepSundaySession().isEmpty()){
						hostelUnitsForm.setPunchExepSundaySession(hostelUnitsTO.getPunchExepSundaySession());
					}
			}
			request.setAttribute("unitsOperation", CMSConstants.EDIT_OPERATION);
			setDataToForm(hostelUnitsForm);
			assignListToForm(hostelUnitsForm);
		} catch (Exception e) {
			log.error("Error in editing HostelUnits in Action",e);
				String msg = super.handleApplicationException(e);
				hostelUnitsForm.setErrorMessage(msg);
				hostelUnitsForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		log.info("Leaving from-- HostelUnitsAction --- editHostelUnits");
		setDataToForm(hostelUnitsForm);
		assignListToForm(hostelUnitsForm);
		return mapping.findForward(CMSConstants.INIT_HOSTELUNITS);
	}
	
	public ActionForward updateHostelUnits(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entering into-- HostelUnitsAction --- updateHostelUnits");
		HostelUnitsForm hostelUnitsForm=(HostelUnitsForm)form; 
		 ActionErrors errors = hostelUnitsForm.validate(mapping, request);
		ActionMessages messages = new ActionMessages();
		try {
			if(errors.isEmpty())
			{
				setUserId(request, hostelUnitsForm);
				boolean isUpdated;
				
						isUpdated=HostelUnitsHandler.getInstance().updateHostelUnits(hostelUnitsForm);
						
						if(isUpdated){
							messages.add(CMSConstants.MESSAGES, new ActionMessage(CMSConstants.HOSTEL_HOSTELUNITS_UPDATE_SUCCESS));
							saveMessages(request, messages);
							hostelUnitsForm.clearAll();
							setDataToForm(hostelUnitsForm);
							assignListToForm(hostelUnitsForm);
							return mapping.findForward(CMSConstants.INIT_HOSTELUNITS);				
						}
						else {
							errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.HOSTEL_HOSTELUNITS_UPDATE_FAILED));
							saveErrors(request, errors);
							setDataToForm(hostelUnitsForm);
							assignListToForm(hostelUnitsForm);
							return mapping.findForward(CMSConstants.INIT_HOSTELUNITS);
						}
			}
		else{
			request.setAttribute("unitsOperation",CMSConstants.EDIT_OPERATION);
		}
		}catch (DuplicateException e1) {
			errors.add("error", new ActionError("knowledgepro.hostel.units.exists"));
			saveErrors(request, errors);
			setDataToForm(hostelUnitsForm);
			assignListToForm(hostelUnitsForm);
			request.setAttribute("unitsOperation", CMSConstants.EDIT_OPERATION);
			return mapping.findForward(CMSConstants.INIT_HOSTELUNITS);
		}
		catch (Exception e) {
			log.error("Error in updating HostelUnits in HostelUnits Action",e);
				String msg = super.handleApplicationException(e);
				hostelUnitsForm.setErrorMessage(msg);
				hostelUnitsForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		log.info("Leaving from --- HostelUnitsAction --- updateHostelUnits");
		saveErrors(request, errors);
		setDataToForm(hostelUnitsForm);
		assignListToForm(hostelUnitsForm);
		return mapping.findForward(CMSConstants.INIT_HOSTELUNITS);
	}
	
	public ActionForward reActivateHostelUnits(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entering into-- HostelUnitsAction --- reActivateHostelUnits");
		HostelUnitsForm hostelUnitsForm=(HostelUnitsForm)form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();		
		try {
			setUserId(request, hostelUnitsForm);
			boolean isReacivate;
			//Request for reactivate by calling the handler method and pass the name
			isReacivate = HostelUnitsHandler.getInstance().reActivateHostelUnits(hostelUnitsForm.getName(),hostelUnitsForm.getUserId());

			if (isReacivate) {
				messages.add(CMSConstants.MESSAGES, new ActionMessage(CMSConstants.HOSTEL_HOSTELUNITS_REACTIVATE_SUCCESS));
				saveMessages(request, messages);
				hostelUnitsForm.clearAll();
				setDataToForm(hostelUnitsForm);
				assignListToForm(hostelUnitsForm);
				return mapping.findForward(CMSConstants.INIT_HOSTELUNITS);
			} else {
				errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.HOSTEL_HOSTELUNITS_REACTIVATE_FAILED));
				saveErrors(request, errors);
				setDataToForm(hostelUnitsForm);
				assignListToForm(hostelUnitsForm);
			}
		} catch (Exception e) {
			log.error("Error in reactivating HostelUnitsAction",e);
				String msg = super.handleApplicationException(e);
				hostelUnitsForm.setErrorMessage(msg);
				hostelUnitsForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		log.info("Leaving from-- HostelUnitsAction --- reActivateHostelUnits");
		return mapping.findForward(CMSConstants.INIT_HOSTELUNITS);
	}
	
}
