package com.kp.cms.actions.sap;

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
import com.kp.cms.bo.sap.SapVenue;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.forms.sap.SapVenueForm;
import com.kp.cms.handlers.sap.SapVenueHandler;
import com.kp.cms.to.admin.EmployeeWorkLocationTO;
import com.kp.cms.to.sap.SapVenueTO;
import com.kp.cms.utilities.CommonUtil;

/**
 * @author Dilip Pathikonda
 *
 */
public class SapVenueAction extends BaseDispatchAction {
	
	private static final Log log = LogFactory.getLog(SapVenueAction.class);
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initSapVenue(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Start of SapVenueAction --- initSapVenue");
		SapVenueForm sapVenueForm=(SapVenueForm)form;		
		try {
			sapVenueForm.clearAll();
			assignListToForm(sapVenueForm);
			setLocationListToRequest(request);
			
		} catch (Exception e) {
			log.error("Error in initializing initSapVenue",e);
			String msg = super.handleApplicationException(e);
			sapVenueForm.setErrorMessage(msg);
			sapVenueForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		log.info("Leaving of SapVenueAction --- initSapVenue");
		return mapping.findForward(CMSConstants.INIT_SAPVENUE);
	}
	
	public void setLocationListToRequest(HttpServletRequest request) throws Exception {
		log.debug("inside setLocationListToRequest");
		 List<EmployeeWorkLocationTO> locationList = SapVenueHandler.getInstance().getLocationList();
		request.setAttribute("locationList", locationList);
	}
	
	public void assignListToForm(ActionForm form) throws Exception
	{
		SapVenueForm sapVenueForm	= (SapVenueForm) form;
		try {
			List<SapVenueTO> sapVenueList = SapVenueHandler.getInstance().getSapVenueDetails();
			sapVenueForm.setVenueList(sapVenueList);
		} catch (Exception e) {
				String msg = super.handleApplicationException(e);
				sapVenueForm.setErrorMessage(msg);
				sapVenueForm.setErrorStack(e.getMessage());
			}
	}
	
	public ActionForward addSapVenue(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entring into-- SapVenueAction --- addSapVenue");
		SapVenueForm sapVenueForm=(SapVenueForm)form;
		 ActionErrors errors = sapVenueForm.validate(mapping, request);
		ActionMessages messages = new ActionMessages();
		try {
			if(isCancelled(request))
			{
				sapVenueForm.clearAll();
				return mapping.findForward(CMSConstants.RESET_ALL);
			}			
			if(errors.isEmpty())
			{
			setUserId(request, sapVenueForm);
			SapVenue sapVenue=SapVenueHandler.getInstance().checkForDuplicateonNameAndLoc(sapVenueForm.getWorkLocId(),sapVenueForm.getVenueName());
			if(sapVenue!=null){
				if(sapVenue.getIsActive()){
					errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.sap.venue.exists"));
					assignListToForm(sapVenueForm);
					setLocationListToRequest(request);
					}

					else if(!sapVenue.getIsActive()){
					errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.sap.venue.exists.reactivate"));
					assignListToForm(sapVenueForm);
					setLocationListToRequest(request);
					}
			}
			else{
			boolean isSapVenueAdded;

			isSapVenueAdded = SapVenueHandler.getInstance().addSapVenue(sapVenueForm);
			//If add operation is success then display the success message.
			if(isSapVenueAdded)
			{
				messages.add(CMSConstants.MESSAGES, new ActionMessage("knowledgepro.sap.venue.added.success"));
				saveMessages(request, messages);
				assignListToForm(sapVenueForm);
				setLocationListToRequest(request);
				sapVenueForm.clearAll();
				return mapping.findForward(CMSConstants.INIT_SAPVENUE);
			}
			//If add operation is failure then add the error message.
			else{
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.sap.venue.add.fail"));
				saveErrors(request, errors);
				assignListToForm(sapVenueForm);
				setLocationListToRequest(request);
				}
			}
			}
		}catch (Exception e) {
				log.error("Error in addSapVenue in SapVenue Action",e);
				String msg = super.handleApplicationException(e);
				sapVenueForm.setErrorMessage(msg);
				sapVenueForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		log.info("Leaving from -- SapVenueAction --- addSapVenue");
		saveErrors(request, errors);
		assignListToForm(sapVenueForm);
		setLocationListToRequest(request);
		return mapping.findForward(CMSConstants.INIT_SAPVENUE);
	}
	
	public ActionForward deleteSapVenue(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entering into--- SapVenueAction --- deleteSapVenue");
		SapVenueForm sapVenueForm=(SapVenueForm)form;
		 ActionErrors errors = sapVenueForm.validate(mapping, request);
		ActionMessages messages = new ActionMessages();
		
		try {
			setUserId(request, sapVenueForm);
			int sapVenueId = sapVenueForm.getId();
			String userId=sapVenueForm.getUserId();
			boolean isSapVenueDeleted;
			
			isSapVenueDeleted = SapVenueHandler.getInstance().deleteSapVenue(sapVenueId, userId);
			//If delete operation is success then append the success message.
			if (isSapVenueDeleted) {
				messages.add(CMSConstants.MESSAGES, new ActionMessage("knowledgepro.sap.venue.delete.success"));
				saveMessages(request, messages);
				assignListToForm(sapVenueForm);
				setLocationListToRequest(request);
			}
			//If delete operation is failure then add the error message.
			else {
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.sap.venue.delete.fail"));
				saveErrors(request, errors);
				assignListToForm(sapVenueForm);
				setLocationListToRequest(request);
			}
		} catch (Exception e) {
			log.error("Error in deleting SapVenue in SapVenue Action",e);
				String msg = super.handleApplicationException(e);
				sapVenueForm.setErrorMessage(msg);
				sapVenueForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		log.info("Leaving from-- SapVenueAction --- deleteSapVenue");
		return mapping.findForward(CMSConstants.INIT_SAPVENUE);
	}
	
	public ActionForward editSapVenue(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entering into---- SapVenueAction --- editSapVenue");
		SapVenueForm sapVenueForm=(SapVenueForm)form;
		//errors = sapVenueForm.validate(mapping, request);
		try {
			SapVenue sapVenue=SapVenueHandler.getInstance().getDetailsonId(sapVenueForm.getId());
				//Set the TO properties to formbean
			if(sapVenue!=null){
				sapVenueForm.setVenueName(sapVenue.getVenueName());
				sapVenueForm.setCapacity(String.valueOf(sapVenue.getCapacity()));
				sapVenueForm.setWorkLocId(String.valueOf(sapVenue.getWorkLocationId().getId()));
					
			}
			request.setAttribute("venueOperation", CMSConstants.EDIT_OPERATION);
			assignListToForm(sapVenueForm);
			setLocationListToRequest(request);
		} catch (Exception e) {
			log.error("Error in editing SapVenue in Action",e);
				String msg = super.handleApplicationException(e);
				sapVenueForm.setErrorMessage(msg);
				sapVenueForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		log.info("Leaving from-- SapVenueAction --- editSapVenue");
		return mapping.findForward(CMSConstants.INIT_SAPVENUE);
	}
	
	public ActionForward updateSapVenue(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entering into-- SapVenueAction --- updateSapVenue");
		SapVenueForm sapVenueForm=(SapVenueForm)form; 
		 ActionErrors errors = sapVenueForm.validate(mapping, request);
		ActionMessages messages = new ActionMessages();
		try {
			if(errors.isEmpty())
			{
				setUserId(request, sapVenueForm);
				boolean isUpdated;
				
						isUpdated=SapVenueHandler.getInstance().updateSapVenue(sapVenueForm);
						
						if(isUpdated){
							messages.add(CMSConstants.MESSAGES, new ActionMessage("knowledgepro.sap.venue.update.success"));
							saveMessages(request, messages);
							sapVenueForm.clearAll();
							assignListToForm(sapVenueForm);
							setLocationListToRequest(request);
							return mapping.findForward(CMSConstants.INIT_SAPVENUE);				
						}
						else {
							errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.sap.venue.update.fail"));
							saveErrors(request, errors);
							assignListToForm(sapVenueForm);
							setLocationListToRequest(request);
							return mapping.findForward(CMSConstants.INIT_SAPVENUE);
						}
			}
		else{
			request.setAttribute("venueOperation",CMSConstants.EDIT_OPERATION);
		}
		}catch (DuplicateException e1) {
			errors.add("error", new ActionError("knowledgepro.sap.venue.exists"));
			saveErrors(request, errors);
			assignListToForm(sapVenueForm);
			setLocationListToRequest(request);
			request.setAttribute("venueOperation", CMSConstants.EDIT_OPERATION);
			return mapping.findForward(CMSConstants.INIT_SAPVENUE);
		}
		catch (Exception e) {
			log.error("Error in updating HostelBlocks in HostelBlocks Action",e);
				String msg = super.handleApplicationException(e);
				sapVenueForm.setErrorMessage(msg);
				sapVenueForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		log.info("Leaving from --- HostelBlocksAction --- updateHostelBlocks");
		saveErrors(request, errors);
		assignListToForm(sapVenueForm);
		setLocationListToRequest(request);
		return mapping.findForward(CMSConstants.INIT_SAPVENUE);
	}
	
	public ActionForward reActivateSapVenue(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entering into-- SapVenueAction --- reActivateSapVenue");
		SapVenueForm sapVenueForm=(SapVenueForm)form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();		
		try {
			setUserId(request, sapVenueForm);
			boolean isReactivate;
			//Request for reactivate by calling the handler method and pass the name
			isReactivate = SapVenueHandler.getInstance().reActivateSapVenue(sapVenueForm.getVenueName(),sapVenueForm.getUserId());

			if (isReactivate) {
				messages.add(CMSConstants.MESSAGES, new ActionMessage("knowledgepro.sap.venue.reactivated"));
				saveMessages(request, messages);
				sapVenueForm.clearAll();
				assignListToForm(sapVenueForm);
				setLocationListToRequest(request);
				return mapping.findForward(CMSConstants.INIT_SAPVENUE);
			} else {
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.sap.venue.reactivate.failed"));
				saveErrors(request, errors);
				assignListToForm(sapVenueForm);
				setLocationListToRequest(request);
			}
		} catch (Exception e) {
				String msg = super.handleApplicationException(e);
				sapVenueForm.setErrorMessage(msg);
				sapVenueForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		log.info("Leaving from-- SapVenueAction --- reActivateSapVenue");
		return mapping.findForward(CMSConstants.INIT_SAPVENUE);
	}
	
}
