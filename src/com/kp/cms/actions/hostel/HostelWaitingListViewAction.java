package com.kp.cms.actions.hostel;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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
import com.kp.cms.forms.hostel.HlAdmissionForm;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.handlers.hostel.HostelWaitingListHandler;
import com.kp.cms.to.hostel.HostelWaitingListViewTo;
import com.kp.cms.transactions.hostel.IHladmissionTransaction;
import com.kp.cms.transactionsimpl.hostel.HlAdmissionImpl;

public class HostelWaitingListViewAction extends BaseDispatchAction {
	private static final Log log=LogFactory.getLog(HostelWaitingListViewAction.class);
	
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initHostelWaitingListView(ActionMapping mapping,ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception
	{
		log.info("end of initHlAdmission method in HlAdmissionAction class.");
		HlAdmissionForm waitingListViewForm= (HlAdmissionForm) form;
		waitingListViewForm.reset();
		setHostelToRequest(request,waitingListViewForm);
		setRoomTypeToRequest(request,waitingListViewForm);
		log.debug("Leaving inithlAdmission");
		return mapping.findForward(CMSConstants.INIT_HOSTEL_WAITINGLISTVIEW);
	}
	
	public void setHostelToRequest(HttpServletRequest request,HlAdmissionForm hostelWaitingListViewForm)throws Exception
	{
			log.debug("start setHostelEntryDetailsToRequest");
			Map<Integer, String> hostelmap=null;
			//IHladmissionTransaction transaction = new HlAdmissionImpl();
			hostelmap = CommonAjaxHandler.getInstance().getHostel();
			request.setAttribute("hostelmap", hostelmap);
			log.debug("exit setHostelEntryDetailsToRequest");
	}
	public void setRoomTypeToRequest(HttpServletRequest request, HlAdmissionForm hlAdmissionForm)throws Exception
	{
			log.debug("start setRoomTypeToRequest");
			Map<Integer, String> roomTypeList=null;
			if(hlAdmissionForm.getHostelName()!=null && !hlAdmissionForm.getHostelName().isEmpty()){
				roomTypeList = CommonAjaxHandler.getInstance() .getRoomTypeByHostelBYstudent(Integer.parseInt(hlAdmissionForm.getHostelName()));
			}
			request.setAttribute("roomTypeMap", roomTypeList);
			log.debug("exit setRoomTypeToRequest");
	}
	
	public ActionForward searchStudentsInHostel(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception
	{
		HlAdmissionForm hlAdmissionForm=(HlAdmissionForm) form;
		setUserId(request,hlAdmissionForm);
		ActionErrors errors=hlAdmissionForm.validate(mapping, request);
		if(errors.isEmpty())
		{
		try {
			setRequirementsForHostelWaitingListStudents(hlAdmissionForm,request);
		} catch (Exception exception) {
			log.error("Error occured in caste Entry Action", exception);
			String msg = super.handleApplicationException(exception);
			hlAdmissionForm.setErrorMessage(msg);
			hlAdmissionForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		}else{
			saveErrors(request, errors);
			setHostelToRequest(request,hlAdmissionForm);
			setRoomTypeToRequest(request,hlAdmissionForm);
			return mapping.findForward(CMSConstants.INIT_HOSTEL_WAITINGLISTVIEW);
		}
		return mapping.findForward(CMSConstants.HOSTEL_WAITINGLISTVIEW_SEARCH_STUDENTS);
		
	}
	
	public ActionForward sendMailAndSmsForSelectedStudents(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	{
		HlAdmissionForm hlAdmissionForm=(HlAdmissionForm) form;
		setUserId(request,hlAdmissionForm);
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = new ActionErrors();
		Map<String, String> roomAndAvailableSeatsMap= hlAdmissionForm.getRoomAndAvailableSeatsMap();
		List<HostelWaitingListViewTo> hostelWaitingList=hlAdmissionForm.getHlWaitingListViewList();
		int count=0;
		int noSeats=0;
		boolean sentSMSAndMail=false;
	    try {
	    	for(Map.Entry<String, String> entry :roomAndAvailableSeatsMap.entrySet())
	    	{
	    		if(entry.getKey()!=null && entry.getValue()!=null && !entry.getKey().isEmpty() && !entry.getValue().isEmpty())
	    		{
	    		Iterator iterator=hostelWaitingList.iterator();
	    		while (iterator.hasNext()) {
					HostelWaitingListViewTo hostelWaitingListViewTo = (HostelWaitingListViewTo) iterator.next();
					if(hostelWaitingListViewTo.getChecked()!=null && hostelWaitingListViewTo.getChecked().equalsIgnoreCase("on"))
					{
						if(hostelWaitingListViewTo.getRoomTypeName()!=null && !hostelWaitingListViewTo.getRoomTypeName().isEmpty())
						{
						if(hostelWaitingListViewTo.getRoomTypeName().equalsIgnoreCase(entry.getKey()))
						{
						if(entry.getValue().equalsIgnoreCase("0"))
						{
						noSeats++;	
						}else
							count++;
						}
						}
					}
				}
	    		if(!entry.getValue().equalsIgnoreCase("0") && count>0)
	    		{
	    			if(Integer.parseInt(entry.getValue())>=count)
		    		{
		    			sentSMSAndMail=HostelWaitingListHandler.getInstance().sendMailAndSmsForSelectedStudents(hlAdmissionForm);
		    	    	if(sentSMSAndMail)
		    	    	{
		    	    		messages.add(CMSConstants.MESSAGES, new ActionMessage("knowledgepro.hostel.hlwaitinglistview.intimate.selected.students.success"));
		    				saveMessages(request, messages);
		    				setRequirementsForHostelWaitingListStudents(hlAdmissionForm,request);
		    	    	}
		    		}
	    			else{
		    			errors.add("error", new ActionError( "knowledgepro.hostel.hlwaitinglistview.intimate.selected.students.failed",entry.getValue()));
						addErrors(request, errors);
	    				//setRequirementsForHostelWaitingListStudents(hlAdmissionForm,request);
		    		}
	    		}
	    		if(noSeats>0)
	    		{
	    			errors.add("error", new ActionError( "knowledgepro.hostel.hlwaitinglistview.intimate.selected.students.noseats.available"));
					addErrors(request, errors);
    				setRequirementsForHostelWaitingListStudents(hlAdmissionForm,request);
	    		}
	    		}
	    	}
	    	
	    	/*for purpose of checkbox(selected or not) we need iterate once again*/
	    	checkBoxSelectedOrNot(hlAdmissionForm);
	    	
	    	
	    } catch (Exception exception) {
			log.error("Error occured in caste Entry Action", exception);
			String msg = super.handleApplicationException(exception);
			hlAdmissionForm.setErrorMessage(msg);
			hlAdmissionForm.setErrorStack(exception.getMessage());
		}
		return mapping.findForward(CMSConstants.HOSTEL_WAITINGLISTVIEW_SEARCH_STUDENTS);
	}
	
	public void setRequirementsForHostelWaitingListStudents(HlAdmissionForm hlAdmissionForm,HttpServletRequest request) throws Exception
	{
	Map<String, String> roomAndAvailableSeatsMap=new HashMap<String, String>(); 	
	if(hlAdmissionForm.getRoomTypeName()!=null && !hlAdmissionForm.getRoomTypeName().isEmpty())
	{
		BigDecimal number=CommonAjaxHandler.getInstance().getNumberOfSeatsAvailable(hlAdmissionForm.getHostelName(), hlAdmissionForm.getRoomTypeName(), hlAdmissionForm.getYear(), request);
		if(number==null)
    	{
    		number=new BigDecimal(0);
    	}
		String roomTypeName=HostelWaitingListHandler.getInstance().getRoomTypeName(Integer.parseInt(hlAdmissionForm.getRoomTypeName()));
		roomAndAvailableSeatsMap.put(roomTypeName,String.valueOf(number));
		hlAdmissionForm.setRoomAndAvailableSeatsMap(roomAndAvailableSeatsMap);
	}
	else
	{
		Map<Integer, String> roomTypeList=null;
		if(hlAdmissionForm.getHostelName()!=null && !hlAdmissionForm.getHostelName().isEmpty()){
			roomTypeList = CommonAjaxHandler.getInstance() .getRoomTypeByHostelBYstudent(Integer.parseInt(hlAdmissionForm.getHostelName()));
            for(Integer key:roomTypeList.keySet())
            {
            	BigDecimal number=CommonAjaxHandler.getInstance().getNumberOfSeatsAvailable(hlAdmissionForm.getHostelName(), String.valueOf(key), hlAdmissionForm.getYear(), request);
            	if(number==null)
            	{
            		number=new BigDecimal(0);
            	}
            	String roomTypeName=HostelWaitingListHandler.getInstance().getRoomTypeName(key);
            	roomAndAvailableSeatsMap.put(roomTypeName,String.valueOf(number));
            }
           hlAdmissionForm.setRoomAndAvailableSeatsMap(roomAndAvailableSeatsMap);
		} 				
	}
	List<HostelWaitingListViewTo> hostelWaitingListViewToList=HostelWaitingListHandler.getInstance().searchStudentsInHostel(hlAdmissionForm);
	hlAdmissionForm.setHlWaitingListViewList(hostelWaitingListViewToList);
	String hostelName=HostelWaitingListHandler.getInstance().getHostelNameById(Integer.parseInt(hlAdmissionForm.getHostelName()));
	hlAdmissionForm.setHostelTempName(hostelName);
	}
	
	/**
	 * @param hlAdmissionForm
	 * checkbox selected or not if it is selected then we r setting to hidden property TemCheckedSelected
	 */
	public void checkBoxSelectedOrNot(HlAdmissionForm hlAdmissionForm)
	{
		List<HostelWaitingListViewTo> hostelWaitingList=hlAdmissionForm.getHlWaitingListViewList();
		List<HostelWaitingListViewTo> waitingListViewToList=new ArrayList<HostelWaitingListViewTo>();
		Iterator iterator=hostelWaitingList.iterator();
		    		while (iterator.hasNext()) {
						HostelWaitingListViewTo hostelWaitingListViewTo = (HostelWaitingListViewTo) iterator.next();
						if(hostelWaitingListViewTo.getChecked()!=null && hostelWaitingListViewTo.getChecked().equalsIgnoreCase("on"))
						{
							hostelWaitingListViewTo.setTemCheckedSelected("on");
						}
						waitingListViewToList.add(hostelWaitingListViewTo);
		    		}
		    		hlAdmissionForm.setHlWaitingListViewList(waitingListViewToList);
	}
	
	public ActionForward sendSmsAndMailAfterConfirm(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	{
		HlAdmissionForm hlAdmissionForm=(HlAdmissionForm) form;
		setUserId(request,hlAdmissionForm);
		ActionMessages messages = new ActionMessages();
		boolean sentSMSAndMail=false;
		try {
			sentSMSAndMail=HostelWaitingListHandler.getInstance().sendMailAndSmsForSelectedStudents(hlAdmissionForm);
    	 if(sentSMSAndMail)
    	{
    		messages.add(CMSConstants.MESSAGES, new ActionMessage("knowledgepro.hostel.hlwaitinglistview.intimate.selected.students.success"));
			saveMessages(request, messages);
			setRequirementsForHostelWaitingListStudents(hlAdmissionForm,request);
    	}
		} catch (Exception exception) {
			log.error("Error occured in caste Entry Action", exception);
			String msg = super.handleApplicationException(exception);
			hlAdmissionForm.setErrorMessage(msg);
			hlAdmissionForm.setErrorStack(exception.getMessage());
		}
		return mapping.findForward(CMSConstants.HOSTEL_WAITINGLISTVIEW_SEARCH_STUDENTS);
		
	}
}
