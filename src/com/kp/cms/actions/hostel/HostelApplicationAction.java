package com.kp.cms.actions.hostel;

import java.util.List;

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

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.exceptions.StudentLoginInfoNotFoundException;
import com.kp.cms.forms.hostel.HostelApplicationForm;
import com.kp.cms.handlers.hostel.HostelApplicationHandler;
import com.kp.cms.to.hostel.HostelTO;
import com.kp.cms.to.hostel.RoomTypeTO;

@SuppressWarnings("deprecation")
public class HostelApplicationAction extends BaseDispatchAction{
	private static final Log log = LogFactory.getLog(HostelApplicationAction.class);

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @returns Hostel Application Student Homepage
	 * Displays all the hostels along with the details present in master
	 * @throws Exception
	 */
	public ActionForward initHostelApplicationStudent(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entering into initHostelApplicationStudent of HostelApplicationAction");
		HostelApplicationForm applicationForm = (HostelApplicationForm)form;
		try {
			List<HostelTO> hostelList = HostelApplicationHandler.getInstance().getHostelDetails();
			applicationForm.setHostelList(hostelList);
			applicationForm.clear();
		} catch (Exception e) {
			log.error("Error occured in initHostelApplicationStudent of HostelApplicationAction", e);
			String msg = super.handleApplicationException(e);
			applicationForm.setErrorMessage(msg);
			applicationForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.STUDENT_ERROR_PAGE);
		}
		log.info("Leaving into initHostelApplicationStudent of HostelApplicationAction");
		return mapping.findForward(CMSConstants.INIT_HOSTEL_STUDENT);
	}
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @returns Hostel Application Student Apply Page
	 * Gets the roomtype and displays in UI
	 * @throws Exception
	 */
	
	public ActionForward showRequiredDataToApply(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entering into showRequiredDataToApply of HostelApplicationAction");
		HostelApplicationForm applicationForm = (HostelApplicationForm)form;
		try {
			int hostelId = Integer.valueOf(applicationForm.getHostelId());
			List<RoomTypeTO> roomTypeNameList = null/*HostelApplicationHandler.getInstance().getRoomTypesonHostelId(hostelId)*/;
			applicationForm.setRoomTypeNameList(roomTypeNameList);
			applicationForm.setRoomTypeWithAmountList(HostelApplicationHandler.getInstance().getRoomTypwiseFees(hostelId));
		} 
		catch (Exception e) {
			log.error("Error occured in showRequiredDataToApply of HostelApplicationAction", e);
			String msg = super.handleApplicationException(e);
			applicationForm.setErrorMessage(msg);
			applicationForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.STUDENT_ERROR_PAGE);
		}
		log.info("Leaving into showRequiredDataToApply of HostelApplicationAction");
		return mapping.findForward(CMSConstants.HOSTEL_REQUISITION_TO_APPLY);
	}

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @returns the acknowledgement after successful application submission
	 * @throws Exception
	 */
	
	public ActionForward submitHostelApplicationStudent(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entering into submitHostelApplicationStudent of HostelApplicationAction");
		HostelApplicationForm applicationForm = (HostelApplicationForm)form;
		ActionErrors errors = new ActionErrors();
		try {
			errors = validateInformationsWhileApply(errors, applicationForm);
			if(errors.isEmpty()){
				if(applicationForm.getClinicalRemarks()!= null && !applicationForm.getClinicalRemarks().trim().isEmpty() && applicationForm.getClinicalRemarks().length() > 200){
					errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.HEALTH_ISSUES_MAX_LEN));
		    		saveErrors(request,errors);
		    		return mapping.findForward(CMSConstants.HOSTEL_REQUISITION_TO_APPLY);
				}
				if(applicationForm.getSicknessRelatedInfo()!= null && !applicationForm.getSicknessRelatedInfo().trim().isEmpty() && applicationForm.getSicknessRelatedInfo().length() > 200){
					errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.SICKNESS_MAX_LEN));
		    		saveErrors(request,errors);
		    		return mapping.findForward(CMSConstants.HOSTEL_REQUISITION_TO_APPLY);
				}
				
				HttpSession session = request.getSession(false);
				String studentid = (String) session.getAttribute("studentid");
				String studentLoginId = (String)session.getAttribute("uid");
				boolean isApplySuccess = HostelApplicationHandler.getInstance().submitApplicationStudentDetails(applicationForm, studentid, studentLoginId);
				if(isApplySuccess){
					return mapping.findForward(CMSConstants.HOSTEL_GET_ACKNOWLEDGEMENT);
				}
				else{
					errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.HOSTEL_APP_SUBMIT_FAILED));
				}
			}
		} catch (DuplicateException e1) {
			errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.HOSTEL_APPLICATION_EXISTS));
    		saveErrors(request,errors);
    		return mapping.findForward(CMSConstants.HOSTEL_REQUISITION_TO_APPLY);
		} catch (ReActivateException e1) {			
		}catch (StudentLoginInfoNotFoundException e){
	 		errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.USER_NOT_FOUND_IN_STUDENT_LOGIN_INFO));
    		saveErrors(request,errors);
    		return mapping.findForward(CMSConstants.HOSTEL_REQUISITION_TO_APPLY);
		} catch (Exception e) {
			log.error("Error occured in submitHostelApplicationStudent of HostelApplicationAction", e);
			String msg = super.handleApplicationException(e);
			applicationForm.setErrorMessage(msg);
			applicationForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.STUDENT_ERROR_PAGE);
		}
		addErrors(request, errors);
		log.info("Leaving into submitHostelApplicationStudent of HostelApplicationAction");
		return mapping.findForward(CMSConstants.HOSTEL_REQUISITION_TO_APPLY);
	}
	
	/**
	 * Used to validate the selected informations while application submission
	 */
	public ActionErrors validateInformationsWhileApply(ActionErrors errors, HostelApplicationForm applicationForm)throws Exception{
		log.info("Entering into validateInformationsWhileApply of HostelApplicationAction");
		if(applicationForm.getRoomTypeNameList() == null || applicationForm.getRoomTypeNameList().isEmpty()){
			errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.HOSTEL_ROOMTYPE_UNAVAILABLE));
		}
		if(applicationForm.getRoomTypeNameList() != null && !applicationForm.getRoomTypeNameList().isEmpty()){
			if(applicationForm.getRoomTypeCheck()== null || applicationForm.getRoomTypeCheck().isEmpty()){
				errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.HOSTEL_ROOMTYPE_REQUIRED));
			}
		}
		if(!applicationForm.isAccepted()){
			errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.HOSTEL_APPLICATION_ACCEPT));
		}
		log.info("Leaving into validateInformationsWhileApply of HostelApplicationAction");
		return errors;		
	}
	
	/**
	 * Used to display view details
	 * Displays details of the hostel (RoomType, Facilities and Images)
	 */
	public ActionForward viewHostelDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entering into viewHostelDetails of HostelApplicationAction");
		HostelApplicationForm applicationForm = (HostelApplicationForm)form;
		try {
			HostelApplicationHandler.getInstance().getHostelRoomTypesByHostelID(applicationForm, request);
		} catch (Exception e) {
			log.error("Error occured in viewHostelDetails of HostelApplicationAction", e);
			String msg = super.handleApplicationException(e);
			applicationForm.setErrorMessage(msg);
			applicationForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.STUDENT_ERROR_PAGE);
		}
		log.info("Leaving into viewHostelDetails of HostelApplicationAction");
		return mapping.findForward("viewhosteldetails");
	}
	
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initOnlineApplication(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entering into viewHostelDetails of HostelApplicationAction");
		HostelApplicationForm applicationForm = (HostelApplicationForm)form;
		try {
			HostelApplicationHandler.getInstance().getHostelRoomTypesByHostelID(applicationForm, request);
		} catch (Exception e) {
			log.error("Error occured in viewHostelDetails of HostelApplicationAction", e);
			String msg = super.handleApplicationException(e);
			applicationForm.setErrorMessage(msg);
			applicationForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.STUDENT_ERROR_PAGE);
		}
		log.info("Leaving into viewHostelDetails of HostelApplicationAction");
		return mapping.findForward("initOnlineApplication");
	}
	
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward saveOnlineApplication(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entering into viewHostelDetails of HostelApplicationAction");
		HostelApplicationForm applicationForm = (HostelApplicationForm)form;
		try {
			HostelApplicationHandler.getInstance().getHostelRoomTypesByHostelID(applicationForm, request);
		} catch (Exception e) {
			log.error("Error occured in viewHostelDetails of HostelApplicationAction", e);
			String msg = super.handleApplicationException(e);
			applicationForm.setErrorMessage(msg);
			applicationForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.STUDENT_ERROR_PAGE);
		}
		log.info("Leaving into viewHostelDetails of HostelApplicationAction");
		return mapping.findForward("initOnlineApplication");
	}
	
}
