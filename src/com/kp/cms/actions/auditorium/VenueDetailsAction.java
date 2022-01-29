package com.kp.cms.actions.auditorium;

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
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.auditorium.VenueDetailsForm;
import com.kp.cms.forms.employee.PayScaleDetailsForm;
import com.kp.cms.handlers.auditorium.VenueDetailsHandler;
import com.kp.cms.handlers.employee.PayScaleDetailsHandler;
import com.kp.cms.to.auditorium.VenueDetailsTO;
import com.kp.cms.to.employee.PayScaleTO;

public class VenueDetailsAction extends BaseDispatchAction{
	private static final Log log = LogFactory.getLog(VenueDetailsAction.class);
    
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initVenueDetails(ActionMapping mapping, ActionForm form,
    		HttpServletRequest request, HttpServletResponse response)
    		throws Exception {
    	VenueDetailsForm venueDetailsForm = (VenueDetailsForm)form;
    	venueDetailsForm.reset();
    	setDataToForm(venueDetailsForm);
    	return mapping.findForward(CMSConstants.INIT_VENUE_DETAILS);
    }
	/**
	 * @param venueDetailsForm
	 * @throws Exception
	 */
	public void setDataToForm(VenueDetailsForm venueDetailsForm)throws Exception{
		Map<Integer,String> blockMap = VenueDetailsHandler.getInstance().getBlockDetails();
		venueDetailsForm.setBlockMap(blockMap);
		List<VenueDetailsTO> venuesTO = VenueDetailsHandler.getInstance().getVenueDetails(venueDetailsForm);
		
		venueDetailsForm.setVenuesTO(venuesTO);
		
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addVenueDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		VenueDetailsForm venueDetailsForm = (VenueDetailsForm)form;
		setUserId(request, venueDetailsForm);
		ActionMessages messages = new ActionMessages();
		HttpSession session = request.getSession();
		ActionErrors errors = venueDetailsForm.validate(mapping, request);
		String mode = "Add";
		if (errors.isEmpty()) {
		try{
			boolean isAdded = false;
			boolean isDuplicate=VenueDetailsHandler.getInstance().duplicateCheck(venueDetailsForm,errors,session);
			if(!isDuplicate){
				isAdded = VenueDetailsHandler.getInstance().addVenueDetails(venueDetailsForm, mode);
				if (isAdded) {
					//ActionMessage message = new ActionMessage( "knowledgepro.employee.payScale.addsuccess");// Adding // added // message.
					messages.add(CMSConstants.MESSAGES, new ActionMessage("knowledgepro.auditorium.venue.addsuccess"));
					saveMessages(request, messages);
					venueDetailsForm.reset();
				} else {
					errors.add("error", new ActionError("knowledgepro.auditorium.venue.addfailed"));
					addErrors(request, errors);
					venueDetailsForm.reset();
				}
			}else{
				addErrors(request, errors);
			}
		} catch (Exception e) {
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				venueDetailsForm.setErrorMessage(msg);
				venueDetailsForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		}else {
			saveErrors(request, errors);
			setDataToForm(venueDetailsForm);
			return mapping.findForward(CMSConstants.INIT_VENUE_DETAILS);
		}
		setDataToForm(venueDetailsForm);
		return mapping.findForward(CMSConstants.INIT_VENUE_DETAILS);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward editVenueDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		VenueDetailsForm venueDetailsForm = (VenueDetailsForm)form;
		try {
			// employeeResumeForm.reset(mapping, request);
			// String formName = mapping.getName();
			// request.getSession().setAttribute(CMSConstants.FORMNAME,
			// formName);
			VenueDetailsHandler.getInstance().editVenueDetails(venueDetailsForm);
			request.setAttribute("operation", "edit");
			//request.setAttribute("Update", "Update");// setting update attribute
		} catch (Exception e) {
			log.error("error in editing venueDetails...", e);
			String msg = super.handleApplicationException(e);
			venueDetailsForm.setErrorMessage(msg);
			venueDetailsForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.INIT_VENUE_DETAILS);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward updateVenueDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.debug("Enter: updatePayScale Action");
		VenueDetailsForm venueDetailsForm = (VenueDetailsForm)form;
		HttpSession session=request.getSession();
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = venueDetailsForm.validate(mapping, request);
		boolean isUpdated = false;
        if(errors.isEmpty()){
		try {
			// This condition works when reset button will click in update mode
			if (isCancelled(request)) {
				venueDetailsForm.reset(mapping, request);
				String formName = mapping.getName();
				request.getSession().setAttribute(CMSConstants.FORMNAME,
						formName);
				VenueDetailsHandler.getInstance().editVenueDetails(venueDetailsForm);
				request.setAttribute("operation", "edit");
				return mapping.findForward(CMSConstants.EMP_PAYSCALE);
			}
			setUserId(request, venueDetailsForm); // setting user id to update
			// boolean
			// isDuplicate=TeacherDepartmentEntryHandler.getInstance().checkDuplicate(teacherDepartmentForm);

			// if(isDuplicate)
			// errors.add(CMSConstants.ERROR, new
			// ActionError(CMSConstants.DUPLICATE_RECORDS));
			// else
			boolean isDuplicate=VenueDetailsHandler.getInstance().duplicateCheck(venueDetailsForm,errors,session);
			if(!isDuplicate){
				isUpdated = VenueDetailsHandler.getInstance().addVenueDetails(venueDetailsForm, "Edit");
			if (isUpdated) {
				ActionMessage message = new ActionMessage(
						"knowledgepro.auditorium.venue.update.success");
				messages.add("messages", message);
				saveMessages(request, messages);
				//employeeResumeForm.reset(mapping, request);
				venueDetailsForm.reset();
			} else {
				errors.add("error", new ActionError(
						"knowledgepro.auditorium.venue.update.failed"));
				//saveErrors(request, errors);
				addErrors(request, errors);
				venueDetailsForm.reset();
			}}else{
				request.setAttribute("operation", "edit");
				//errors.add("error", new ActionError("knowledgepro.auditorium.venue.exists"));
				addErrors(request, errors);
				//payScaleForm.reset();
			}
		} catch (Exception e) {
			log.error("Error occured in edit Venue Details", e);
			String msg = super.handleApplicationException(e);
			venueDetailsForm.setErrorMessage(msg);
			venueDetailsForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}}else{
			saveErrors(request, errors);
			setDataToForm(venueDetailsForm);
			request.setAttribute("operation", "edit");
			return mapping.findForward(CMSConstants.INIT_VENUE_DETAILS);
		}
        setDataToForm(venueDetailsForm);
		log.debug("Exit: action class updatePayScale");
		return mapping.findForward(CMSConstants.INIT_VENUE_DETAILS);

	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deleteVenueDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.debug("Entering ");
		VenueDetailsForm venueDetailsForm = (VenueDetailsForm)form;
		ActionMessages messages = new ActionMessages();

		try {
			boolean isDeleted = VenueDetailsHandler.getInstance().deleteVenueDetails(venueDetailsForm);
			if (isDeleted) {
				ActionMessage message = new ActionMessage(
						"knowledgepro.auditorium.venue.delete.success");
				messages.add("messages", message);
				saveMessages(request, messages);
			} else {
				ActionMessage message = new ActionMessage(
						"knowledgepro.auditorium.venue.delete.failed");
				messages.add("messages", message);
				saveMessages(request, messages);
			}
			venueDetailsForm.reset();
			setDataToForm(venueDetailsForm);
		} catch (Exception e) {
			log.error("error submit Venue Details...", e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				venueDetailsForm.setErrorMessage(msg);
				venueDetailsForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				String msg = super.handleApplicationException(e);
				venueDetailsForm.setErrorMessage(msg);
				venueDetailsForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		}
		log.debug("Action class. Delete Venue Details ");
		return mapping.findForward(CMSConstants.INIT_VENUE_DETAILS);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward reactivateVenueDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entering reactivateVenueDetails VenueDetailsAction");
		VenueDetailsForm venueDetailsForm = (VenueDetailsForm)form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		HttpSession session=request.getSession();
		try {
			setUserId(request, venueDetailsForm);
			boolean isReactivate;
			//int olddocTypeId =Integer.parseInt(documentExamEntryForm.getDocTypeId());
			//String oldExamName = documentExamEntryForm.getExamName().trim();
			String duplicateId=session.getAttribute("ReactivateId").toString();
			venueDetailsForm.setId(Integer.parseInt(duplicateId));
			isReactivate = VenueDetailsHandler.getInstance().reactivateVenueDetails(venueDetailsForm);
			if(isReactivate){
				messages.add(CMSConstants.MESSAGES,new ActionMessage("knowledgepro.auditorium.venue.details.activate"));
				setDataToForm(venueDetailsForm);
				venueDetailsForm.reset();
				saveMessages(request, messages);
			}
			else{
				setDataToForm(venueDetailsForm);
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.auditorium.venue.details.activate.fail"));
				addErrors(request, errors);
			}
			
		} catch (Exception e) {
			log.error("Error occured in reactivateVenueDetails of VenueDetailsAction", e);
			String msg = super.handleApplicationException(e);
			venueDetailsForm.setErrorMessage(msg);
			venueDetailsForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("Leaving into reactivateVenueDetails of VenueDetailsAction");
		return mapping.findForward(CMSConstants.INIT_VENUE_DETAILS); 
	}
}
