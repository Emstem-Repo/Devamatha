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
import com.kp.cms.forms.hostel.HostelReservationForm;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.handlers.hostel.HostelApplicationHandler;
import com.kp.cms.handlers.hostel.HostelEntryHandler;
import com.kp.cms.handlers.hostel.HostelReservationHandler;
import com.kp.cms.to.hostel.HostelReservationTO;
import com.kp.cms.to.hostel.HostelTO;
import com.kp.cms.to.hostel.RoomTypeTO;
import com.kp.cms.utilities.CommonUtil;

@SuppressWarnings("deprecation")
public class HostelReservationAction extends BaseDispatchAction{
	
	private static Log log = LogFactory.getLog(HostelReservationAction.class);
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initHostelReservation(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HostelReservationForm hostelReservationForm = (HostelReservationForm) form;
		hostelReservationForm.setPrint("false");
		hostelReservationForm.resetFields();
		setHostelEntryDetailsToRequest(request);
		setUserId(request, hostelReservationForm);
		return mapping.findForward(CMSConstants.HOSTEL_RESERVATION1);
	}
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getApplicantDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		HostelReservationForm hostelReservationForm = (HostelReservationForm) form;
		hostelReservationForm.setPrint("false");
		hostelReservationForm.resetSecondPageFields();
		hostelReservationForm.setReservationDate(CommonUtil.getTodayDate());
		 ActionErrors errors =  hostelReservationForm.validate(mapping, request);
		
		if (!errors.isEmpty()) {
			setHostelEntryDetailsToRequest(request);
			log.info("Entered HostelReservationAction errors size > 0");
			saveErrors(request, errors);
			return mapping.findForward(CMSConstants.HOSTEL_RESERVATION1);
		}else{
			try {		
				HostelReservationTO applicantHostelDetails = HostelReservationHandler.getInstance().getApplicantHostelDetails(hostelReservationForm);
				
				if( applicantHostelDetails == null){
					setHostelEntryDetailsToRequest(request);
					errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.KNOWLEDGEPRO_ADMISSION_NORECORDSFOUND));
					saveErrors(request, errors);
					return mapping.findForward(CMSConstants.HOSTEL_RESERVATION1);
				} else{
					hostelReservationForm.setApplicantHostelDetails(applicantHostelDetails);
					HttpSession sess=request.getSession();
					sess.setAttribute("hosteldetails", applicantHostelDetails);
					setFloorMapToRequest(request,hostelReservationForm);
					setRoomMapToRequest(request,hostelReservationForm);
				}
			} catch (Exception exception) {	
				String msg = super.handleApplicationException(exception);
				hostelReservationForm.setErrorMessage(msg);
				hostelReservationForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		}

		return mapping.findForward(CMSConstants.HOSTEL_RESERVATION2);
	}
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward submitReservationDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HostelReservationForm hostelReservationForm = (HostelReservationForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors =  hostelReservationForm.validate(mapping, request);
		boolean isReservationDetailsSaved = false;
		if(hostelReservationForm.getRemarks()!= null && !hostelReservationForm.getRemarks().trim().isEmpty() && hostelReservationForm.getRemarks().length() > 250){
			errors.add("error", new ActionError("knowledgepro.hostel.reservation.remarks.length"));
		}
		if (!errors.isEmpty()) {
			log.info("Entered HostelReservationAction errors size > 0");
			saveErrors(request, errors);
			setFloorMapToRequest(request,hostelReservationForm);
			setRoomMapToRequest(request,hostelReservationForm);
			return mapping.findForward(CMSConstants.HOSTEL_RESERVATION2);
		}
		else
		{
			try
			{
				boolean canReservationCanBeDone= HostelReservationHandler.getInstance().canReservationBeDone(hostelReservationForm);
				if(!canReservationCanBeDone)
				{
					errors.add("error", new ActionError("knowledgepro.hostel.reservation.room.full"));
				}
				if (errors != null && !errors.isEmpty()) {
					log.info("Entered HostelReservationAction errors size > 0");
					saveErrors(request, errors);
					setFloorMapToRequest(request,hostelReservationForm);
					setRoomMapToRequest(request,hostelReservationForm);
					return mapping.findForward(CMSConstants.HOSTEL_RESERVATION2);
				}
				else
				{	
					isReservationDetailsSaved = HostelReservationHandler.getInstance().saveReservationDetails(hostelReservationForm);
					setHostelEntryDetailsToRequest(request);
				}	
			} catch (Exception exception) {	
				String msg = super.handleApplicationException(exception);
				hostelReservationForm.setErrorMessage(msg);
				hostelReservationForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		}
		// success .
		if (isReservationDetailsSaved) {
			hostelReservationForm.setPrint("true");
			ActionMessage message = new ActionMessage("knowledgepro.hostel.reservation.add.success");
			messages.add("messages", message);
			saveMessages(request, messages);
			hostelReservationForm.resetFields();
			HttpSession sess=request.getSession();
			if(sess.getAttribute("hosteldetails")!=null){				
				hostelReservationForm.setApplicantHostelDetails((HostelReservationTO)sess.getAttribute("hosteldetails"));
				hostelReservationForm.setBillNo(HostelReservationHandler.getInstance().getBillNumber());
			}
		} else {
			// failed
			errors.add("error", new ActionError("knowledgepro.hostel.reservation.add.failure"));
			saveErrors(request, errors);
		}
		return mapping.findForward(CMSConstants.HOSTEL_RESERVATION_SLIP);
	}
	
	/**
	 * 
	 * @param request
	 * @throws Exception 
	 */
	public void setHostelEntryDetailsToRequest(HttpServletRequest request) throws Exception{
		log.debug("start setHostelEntryDetailsToRequest");
		List<HostelTO> hostelList = HostelEntryHandler.getInstance().getHostelDetails();
		request.setAttribute("hostelList", hostelList);
		log.debug("exit setHostelEntryDetailsToRequest");
	}
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward displayPage(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			   HttpServletResponse response) throws Exception {
		log.debug("Entering displayPage ");		
		return mapping.findForward(CMSConstants.HOSTEL_RESERVATION_PRINT);
	}
	
	public void setFloorMapToRequest(HttpServletRequest request,  HostelReservationForm resForm) {
		if (resForm.getHostelId() != null && !(resForm.getHostelId().isEmpty())) {
			Map<Integer, String> floorMap = CommonAjaxHandler.getInstance().getFloorsByHostel(Integer.parseInt(resForm.getHostelId()));
			request.setAttribute("floorMap", floorMap);
		}
	}
	
	public void setRoomMapToRequest(HttpServletRequest request,  HostelReservationForm resForm) {
		if (resForm.getFloorNo() != null && !(resForm.getFloorNo().isEmpty())) {
			Map<Integer, String> roomMap = CommonAjaxHandler.getInstance().getRoomsByFloors(Integer.parseInt(resForm.getApplicantHostelDetails().getHostelId()),Integer.parseInt(resForm.getFloorNo()),resForm.getApplicantHostelDetails().getRoomTypeId());
			request.setAttribute("roomMap", roomMap);
		}
		else
		{	
			Map<Integer, String> roomMap = new HashMap<Integer, String>();
			request.setAttribute("roomMap",roomMap);
		}	
	}	
	/**
	 * Method to validate the time format
	 * @param interviewBatchEntryForm
	 * @param errors
	 */
}
