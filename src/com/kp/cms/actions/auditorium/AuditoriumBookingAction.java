package com.kp.cms.actions.auditorium;



import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.auditorium.AuditoriumBookingForm;
import com.kp.cms.handlers.auditorium.AuditoriumBookingHandler;
import com.kp.cms.to.auditorium.AuditoriumBookingTo;
import com.kp.cms.transactions.auditorium.IAuditoriumBookingTransaction;
import com.kp.cms.transactionsimpl.auditorium.AuditoriumBookingTxnImpl;

public class AuditoriumBookingAction extends BaseDispatchAction{
	private static final Log log = LogFactory.getLog(AuditoriumBookingAction.class);
	public IAuditoriumBookingTransaction transaction = AuditoriumBookingTxnImpl.getInstance();
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initNewCalendar(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("entering into initNewCalendar action");
		//HttpSession session = request.getSession();
		AuditoriumBookingForm auditoriumBookingForm = (AuditoriumBookingForm) form;
		 String searchButton=request.getParameter("searchButton");
		if(searchButton==null){
			auditoriumBookingForm.reset();
		}else{
			Map<Integer, String> venueMap=transaction.getVenuesByBlockDetails(Integer.parseInt(auditoriumBookingForm.getBlockId()));
			request.setAttribute("blockOrVenueMap", venueMap);
		}
		setDataToForm(auditoriumBookingForm);
		Map<Integer,String> blockMap = AuditoriumBookingHandler.getInstance().getBlockDetailsMap();
		Map<Integer,String> requirementsMap = AuditoriumBookingHandler.getInstance().getRequirementsMap();
		auditoriumBookingForm.setBlockMap(blockMap);
		auditoriumBookingForm.setBookingRequirements(requirementsMap);
		setUserId(request, auditoriumBookingForm);
		log.info("exit from  initAuditoriumAllocation  action");
		return mapping.findForward(CMSConstants.CALENDAR);
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getVenuesByBlock(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
		AuditoriumBookingForm auditoriumBookingForm = (AuditoriumBookingForm)form;
		Map<Integer,String> venueMap = new HashMap<Integer, String>();
		if(auditoriumBookingForm.getBlockId()!=null && !auditoriumBookingForm.getBlockId().isEmpty()){
			venueMap=AuditoriumBookingHandler.getInstance().getVenuesByBlock(Integer.parseInt(auditoriumBookingForm.getBlockId()));
		}
		request.setAttribute("optionMap", venueMap);
		return mapping.findForward("ajaxResponseForOptions");
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public void saveCalendarEvents(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
		AuditoriumBookingForm auditoriumBookingForm = (AuditoriumBookingForm)form;
		setUserId(request, auditoriumBookingForm);
		boolean value = AuditoriumBookingHandler.getInstance().saveCalendarEvents(auditoriumBookingForm);
		if(value)
		{
			response.getWriter().write("Venue has been booked successfully");
		}
	}
	/**
	 * @param auditoriumBookingForm
	 * @throws Exception
	 */
	public void setDataToForm(AuditoriumBookingForm auditoriumBookingForm) throws Exception{
		Map<Integer,String> blockMap = AuditoriumBookingHandler.getInstance().getBlockDetailsMap();
		auditoriumBookingForm.setBlockMap(blockMap);
		Map<Integer,String> bookingRequirementsMap = AuditoriumBookingHandler.getInstance().getRequirementsMap();
		auditoriumBookingForm.setBookingRequirements(bookingRequirementsMap);
		
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void getCalendarEventsData(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
       
		AuditoriumBookingForm auditoriumBookingForm = (AuditoriumBookingForm)form;
		setUserId(request, auditoriumBookingForm);
		try {
				JSONArray jsonArray=AuditoriumBookingHandler.getInstance().getCalendarEventsData(auditoriumBookingForm);
				if(jsonArray!=null && !jsonArray.isEmpty())
				{
					response.setContentType("application/json"); 
					response.setCharacterEncoding("utf-8"); 
					String bothJson = jsonArray.toString(); //Put both objects in an array of 2 elements
					response.getWriter().write(bothJson);
				}
			} catch (Exception e) {
				log.debug(e.getMessage());
			}
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void checkDuplicateCalendarEvents(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
       
		AuditoriumBookingForm auditoriumBookingForm = (AuditoriumBookingForm)form;
		String checkDuplicateEvents="duplicateEvents";
		auditoriumBookingForm.setCheckDuplicateCalendarEvents(checkDuplicateEvents);
		setUserId(request, auditoriumBookingForm);
		try {
				JSONArray jsonArray=AuditoriumBookingHandler.getInstance().getCalendarEventsData(auditoriumBookingForm);
				if(jsonArray!=null && !jsonArray.isEmpty())
				{
					response.setContentType("application/json"); 
					response.setCharacterEncoding("utf-8"); 
					String bothJson = jsonArray.toString(); //Put both objects in an array of 2 elements
					response.getWriter().write(bothJson);
				}
			} catch (Exception e) {
				log.debug(e.getMessage());
			}
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void updateCalendarEventsByEventId(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		AuditoriumBookingForm  bookingForm =  (AuditoriumBookingForm) form;
		setUserId(request, bookingForm);
		boolean updated=AuditoriumBookingHandler.getInstance().updateCalendarEventsData(bookingForm);
		if(updated){
			   if(bookingForm.getMode().equalsIgnoreCase("AdminApproved"))
			   {
				   response.getWriter().write("Venue has been Approved successfully");
			   }
			   else if(bookingForm.getMode().equalsIgnoreCase("AdminModify") || bookingForm.getMode().equalsIgnoreCase("AdminDropAndResize") )
			   {
				   response.getWriter().write("Venue has been Modified and Approved successfully");
			   }
			   else if(bookingForm.getMode().equalsIgnoreCase("UserUpdate")|| bookingForm.getMode().equalsIgnoreCase("UserDropAndResize"))
			   {
				   response.getWriter().write("Venue has been Updated successfully");
			   }
		}
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void deleteEventByEventId(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		AuditoriumBookingForm  bookingForm =  (AuditoriumBookingForm) form;
		setUserId(request, bookingForm);
		boolean deleted=AuditoriumBookingHandler.getInstance().deleteEventByEventId(bookingForm);
		if(deleted){
			if(bookingForm.getMode().equalsIgnoreCase("CancelEventByUser")){
				response.getWriter().write("Booked Venue has been Canceled successfully.");
			}else{
				response.getWriter().write("Venue has been Deleted successfully.");	
			}
			 
		}
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initApprovingEvents(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("entering into initAuditoriumAllocation action");
		AuditoriumBookingForm auditoriumBookingForm = (AuditoriumBookingForm) form;
		 String searchButton=request.getParameter("searchButton");
			if(searchButton==null){
				auditoriumBookingForm.reset();
			}else{
				Map<Integer, String> venueMap=transaction.getVenuesByBlockDetails(Integer.parseInt(auditoriumBookingForm.getBlockId()));
				request.setAttribute("blockOrVenueMap", venueMap);
			}
		Map<Integer,String> blockMap = AuditoriumBookingHandler.getInstance().getBlockDetailsMap();
		Map<Integer,String> requirementsMap = AuditoriumBookingHandler.getInstance().getRequirementsMap();
		auditoriumBookingForm.setBlockMap(blockMap);
		auditoriumBookingForm.setBookingRequirements(requirementsMap);
		setUserId(request, auditoriumBookingForm);
		log.info("exit from  initAuditoriumAllocation  action");
		return mapping.findForward(CMSConstants.APPROVING_EVENTS);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void rejectEventByApprover(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		AuditoriumBookingForm  bookingForm =  (AuditoriumBookingForm) form;
		setUserId(request, bookingForm);
		boolean rejected=AuditoriumBookingHandler.getInstance().rejectEventByApprover(bookingForm);
		if(rejected){
			 if(bookingForm.getMode().equalsIgnoreCase("Rejected"))
			   {
				   response.getWriter().write("Venue has been Rejected.");
			   }
		}
		
	}
   /**
 * @param mapping
 * @param form
 * @param request
 * @param response
 * @return
 * @throws Exception
 */
public ActionForward getApprovedEvents(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {
	   AuditoriumBookingForm  bookingForm =  (AuditoriumBookingForm) form;
	   setUserId(request, bookingForm);
		try{
			Map<String, List<AuditoriumBookingTo>> audiBookingMap=AuditoriumBookingHandler.getInstance().getApprovedEvents();
			request.setAttribute("audiBookingMap", audiBookingMap);
			//bookingForm.setAudiBookingMap(audiBookingMap);
		}catch (Exception exception) {
			String msg = super.handleApplicationException(exception);
			bookingForm.setErrorMessage(msg);
			bookingForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.DISPLAY_AUDITORIUM_APPROVED_AJAX);
}

/**
 * @param mapping
 * @param form
 * @param request
 * @param response
 * @return
 * @throws Exception
 */
public void getFacilityAvailableByBlockAndVenue(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
	AuditoriumBookingForm auditoriumBookingForm = (AuditoriumBookingForm)form;
	String faciltyAvalable="";
	if(auditoriumBookingForm.getBlockId()!=null && !auditoriumBookingForm.getBlockId().isEmpty() && auditoriumBookingForm.getVenueId()!=null && !auditoriumBookingForm.getVenueId().isEmpty()){
		 faciltyAvalable=AuditoriumBookingHandler.getInstance().getFacilityAvailableByBlockAndVenue(auditoriumBookingForm);
	}
	 response.getWriter().write(faciltyAvalable);
}

/**
 * @param mapping
 * @param form
 * @param request
 * @param response
 * @return
 * @throws Exception
 */
public ActionForward getPendingApprovedEvents(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {
	   AuditoriumBookingForm  bookingForm =  (AuditoriumBookingForm) form;
	   setUserId(request, bookingForm);
		try{
			Map<String, List<AuditoriumBookingTo>> audiBookingMap=AuditoriumBookingHandler.getInstance().getPendingEvents();
			request.setAttribute("audiBookingMap", audiBookingMap);
			//bookingForm.setAudiBookingMap(audiBookingMap);
		}catch (Exception exception) {
			String msg = super.handleApplicationException(exception);
			bookingForm.setErrorMessage(msg);
			bookingForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.DISPLAY_AUDITORIUM_APPROVED_AJAX);
}
}
