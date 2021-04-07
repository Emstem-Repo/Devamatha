package com.kp.cms.actions.hostel;

import java.util.HashMap;
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
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.hostel.AvailableSeatsForm;
import com.kp.cms.forms.hostel.HolidaysForm;
import com.kp.cms.handlers.hostel.AvailableSeatsHandler;
import com.kp.cms.handlers.hostel.HolidaysHandler;
import com.kp.cms.to.hostel.AvailableSeatsTo;
import com.kp.cms.to.hostel.HostelHolidaysTo;

public class AvailableSeatsAction extends BaseDispatchAction{
	private static final Log log = LogFactory.getLog(AvailableSeatsAction.class);
	/**
	 * initAvailableSeats() to set required fields to form
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initAvailableSeats(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		AvailableSeatsForm availableSeatsForm=(AvailableSeatsForm)form;
		availableSeatsForm.reset();
		setRequiredDatatoForm(availableSeatsForm, request);
		setAvailableSeatsList(availableSeatsForm);
		return mapping.findForward(CMSConstants.AVAILABLE_SEATS);
	}
	/**
	 * set required data to form
	 * @param holidaysForm
	 * @param request
	 * @throws Exception
	 */
	private void setRequiredDatatoForm(AvailableSeatsForm availableSeatsForm,HttpServletRequest request) throws Exception{
		 Map<String, String> hostelMap = AvailableSeatsHandler.getInstance().getHostelMap();
		availableSeatsForm.setHostelMap(hostelMap);
	}
	/**
	 * saveAvailableSeats
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward saveAvailableSeats(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		AvailableSeatsForm availableSeatsForm = (AvailableSeatsForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = availableSeatsForm.validate(mapping, request);
		try {
			if (errors.isEmpty()) {
				if(Integer.parseInt(availableSeatsForm.getNumOfAvailableSeats())>Integer.parseInt(availableSeatsForm.getTotalSeats())){
					errors .add( "error", new ActionError( "knowledgepro.hostel.availableseats.greaterornot"));
					saveErrors(request, errors);
					setRequiredDatatoForm(availableSeatsForm, request);
					setAvailableSeatsList(availableSeatsForm);
					setRoomTypeMapToForm(availableSeatsForm);
					availableSeatsForm.setTotalNumOfSeatsAvail(availableSeatsForm.getTotalSeats());
					return mapping .findForward(CMSConstants.AVAILABLE_SEATS);
				}
				setUserId(request, availableSeatsForm);
				boolean duplicatecheck = AvailableSeatsHandler.getInstance().duplicateCheck(availableSeatsForm);
				if (!duplicatecheck){
					boolean isAdded = AvailableSeatsHandler.getInstance().submitAvailableSeats(availableSeatsForm);
					if (isAdded) {
						ActionMessage message = new ActionError( "knowledgepro.hostel.availableseats.addsuccess");
						messages.add("messages", message);
						saveMessages(request, messages);
						availableSeatsForm.reset();
					} else {
						availableSeatsForm.reset();
						errors .add( "error", new ActionError( "knowledgepro.hostel.availableseats.fail"));
						saveErrors(request, errors);
					}
				}else {
						ActionMessage message = new ActionError( "knowledgepro.hostel.availableseats.alreadyexists");
						messages.add("messages", message);
						saveMessages(request, messages);
						availableSeatsForm.reset();
					}
				} else {
				saveErrors(request, errors);
			}

		} catch (BusinessException businessException) {
			log.info("Exception submitOpenConnectionDetails");
			String msgKey = super.handleBusinessException(businessException);
			ActionMessage message = new ActionMessage(msgKey);
			messages.add(CMSConstants.MESSAGES, message);
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} catch (Exception exception) {
			String msg = super.handleApplicationException(exception);
			availableSeatsForm.setErrorMessage(msg);
			availableSeatsForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		setRequiredDatatoForm(availableSeatsForm, request);
		setAvailableSeatsList(availableSeatsForm);
		return mapping .findForward(CMSConstants.AVAILABLE_SEATS);
	}
	/**
	 * set the availableSeats details to form
	 * @param holidaysForm
	 * @throws Exception
	 */
	private void setAvailableSeatsList( AvailableSeatsForm availableSeatsForm) throws Exception {
		List<AvailableSeatsTo> toList = AvailableSeatsHandler.getInstance().getDetails();
		availableSeatsForm.setAvailableSeatsList(toList);
	}
	/**
	 * delete AvailableSeats Details
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deleteAvailableSeats(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		AvailableSeatsForm availableSeatsForm = (AvailableSeatsForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = availableSeatsForm.validate(mapping, request);
		try {
			if (errors.isEmpty()) {
				boolean isDeleted = AvailableSeatsHandler.getInstance().deleteAvailableSeats(availableSeatsForm);
				if (isDeleted) {
					ActionMessage message = new ActionError( "knowledgepro.hostel.availableseats.delete.success");
					messages.add("messages", message);
					saveMessages(request, messages);
					availableSeatsForm.reset();
				} else {
					errors .add( "error", new ActionError( "knowledgepro.hostel.availableseats.delete.fail"));
					saveErrors(request, errors);
				}
			}
		} catch (BusinessException businessException) {
			log.info("Exception submitOpenConnectionDetails");
			String msgKey = super.handleBusinessException(businessException);
			ActionMessage message = new ActionMessage(msgKey);
			messages.add(CMSConstants.MESSAGES, message);
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} catch (Exception exception) {
			String msg = super.handleApplicationException(exception);
			availableSeatsForm.setErrorMessage(msg);
			availableSeatsForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		setAvailableSeatsList(availableSeatsForm);
		return mapping .findForward(CMSConstants.AVAILABLE_SEATS);
	}
	/**
	 * edit Available Seats details
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward editAvailableSeatsDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		AvailableSeatsForm availableSeatsForm = (AvailableSeatsForm) form;
		ActionMessages messages = new ActionMessages();
		boolean flag = true;
		try {
			availableSeatsForm.reset();
			setUserId(request, availableSeatsForm);
			AvailableSeatsHandler.getInstance().getAvailableSeatsDetails(availableSeatsForm);
			
		} catch (BusinessException businessException) {
			log.info("Exception submitOpenConnectionDetails");
			String msgKey = super.handleBusinessException(businessException);
			ActionMessage message = new ActionMessage(msgKey);
			messages.add(CMSConstants.MESSAGES, message);
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} catch (Exception exception) {
			String msg = super.handleApplicationException(exception);
			availableSeatsForm.setErrorMessage(msg);
			availableSeatsForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		request.setAttribute("openConnection", "edit");
		setRequiredDatatoForm(availableSeatsForm, request);
		setRoomTypeMapToForm(availableSeatsForm);
		setAvailableSeatsList(availableSeatsForm);
		return mapping.findForward(CMSConstants.AVAILABLE_SEATS);
	}
	/**
	 * set roomTypeMap to form
	 * @param holidaysForm
	 * @throws Exception
	 */
		private void setRoomTypeMapToForm(AvailableSeatsForm availableSeatsForm) throws Exception {
			String hostelId=null;
			Map<Integer, String> roomTypeMap = new HashMap<Integer, String>();
			if(availableSeatsForm.getHostelId()!=null){
			hostelId=availableSeatsForm.getHostelId();
			roomTypeMap = AvailableSeatsHandler.getInstance().getRoomTypeMap(hostelId);
			}
			availableSeatsForm.setRoomTypeMap(roomTypeMap);
		}
		/**
		 * updating the available Seats details
		 * @param mapping
		 * @param form
		 * @param request
		 * @param response
		 * @return
		 * @throws Exception
		 */
		public ActionForward updateAvailableSeats(ActionMapping mapping,
				ActionForm form, HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			AvailableSeatsForm availableSeatsForm = (AvailableSeatsForm) form;
			setUserId(request, availableSeatsForm);
			ActionMessages messages = new ActionMessages();
			ActionErrors errors = availableSeatsForm.validate(mapping, request);
			try {
				if (errors.isEmpty()) {
					if(Integer.parseInt(availableSeatsForm.getNumOfAvailableSeats())>Integer.parseInt(availableSeatsForm.getTotalNumOfSeatsAvail())){
						errors .add( "error", new ActionError( "knowledgepro.hostel.availableseats.greaterornot"));
						saveErrors(request, errors);
						request.setAttribute("openConnection", "edit");
						setRequiredDatatoForm(availableSeatsForm, request);
						setRoomTypeMapToForm(availableSeatsForm);
						return mapping .findForward(CMSConstants.AVAILABLE_SEATS);
					}
					boolean isUpdated = AvailableSeatsHandler.getInstance().updateAvailableSeats(availableSeatsForm);
						if (isUpdated) {
							ActionMessage message = new ActionError("knowledgepro.hostel.availableseats.updatesuccess");
							messages.add("messages", message);
							saveMessages(request, messages);
							availableSeatsForm.reset();
						} else {
							
							errors .add( "error", new ActionError( "knowledgepro.hostel.availableseats.updatesucces"));
							request.setAttribute("openConnection", "edit");
							setRequiredDatatoForm(availableSeatsForm, request);
							setRoomTypeMapToForm(availableSeatsForm);
							return mapping.findForward(CMSConstants.AVAILABLE_SEATS);
						}
					}
				else {
					saveErrors(request, errors);
					request.setAttribute("openConnection", "edit");
					setRequiredDatatoForm(availableSeatsForm, request);
					setRoomTypeMapToForm(availableSeatsForm);
					return mapping .findForward(CMSConstants.AVAILABLE_SEATS);
				}
			} catch (BusinessException businessException) {
				log.info("Exception submitOpenConnectionDetails");
				String msgKey = super.handleBusinessException(businessException);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add(CMSConstants.MESSAGES, message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} catch (Exception exception) {
				String msg = super.handleApplicationException(exception);
				availableSeatsForm.setErrorMessage(msg);
				availableSeatsForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
			request.setAttribute("openConnection", "add");
			setRequiredDatatoForm(availableSeatsForm, request);
			setAvailableSeatsList(availableSeatsForm);
			return mapping .findForward(CMSConstants.AVAILABLE_SEATS);
		}
}
