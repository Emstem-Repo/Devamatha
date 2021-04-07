package com.kp.cms.handlers.employee;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.EventLocation;
import com.kp.cms.bo.employee.EventLoactionBiometricDetailsBo;
import com.kp.cms.bo.employee.EventScheduleForAttendanceBo;
import com.kp.cms.forms.employee.EventLocationBiometricDetailsForm;
import com.kp.cms.forms.employee.EventScheduleForAttendanceForm;
import com.kp.cms.helpers.employee.EventLoactionBiometricDetailsHelper;
import com.kp.cms.helpers.employee.EventScheduleForAttendanceHelper;
import com.kp.cms.to.employee.EventScheduleForAttendanceTo;
import com.kp.cms.transactions.employee.IEventLoactionBiometricDetailsTransaction;
import com.kp.cms.transactions.employee.IEventScheduleForAttendanceTransaction;
import com.kp.cms.transactionsimpl.employee.EventLoactionBiometricDetailsTransactionImpl;
import com.kp.cms.transactionsimpl.employee.EventScheduleForAttendanceTransactionImpl;


public class EventScheduleForAttendanceHandler {
	private static final Log log = LogFactory.getLog(EventScheduleForAttendanceHandler.class);
	public static volatile EventScheduleForAttendanceHandler biometric=null;
	/**
	 * @return
	 * This method gives instance of this method
	 */
	public static EventScheduleForAttendanceHandler getInstance(){
		if(biometric==null){
			biometric= new EventScheduleForAttendanceHandler();}
		return biometric;
	}
	
	
	public void getEventLocationData(EventScheduleForAttendanceForm eventScheduleform) throws Exception{
		IEventScheduleForAttendanceTransaction transaction=new EventScheduleForAttendanceTransactionImpl();
		Map<String,String> eventLocationMap=transaction.getEventLocationData();
		if(eventLocationMap !=null){
			eventScheduleform.setEventLocationMap(eventLocationMap);
		}
	}
	public boolean addStudentOrStaffData(ArrayList<Integer> listClass,EventScheduleForAttendanceForm eventScheduleform) throws Exception{
		EventScheduleForAttendanceBo boList=EventScheduleForAttendanceHelper.getInstance().convertFormToBo(listClass,eventScheduleform);
		IEventScheduleForAttendanceTransaction transaction=new EventScheduleForAttendanceTransactionImpl();
		boolean isAdded=transaction.addStudentOrStaffData(boList,eventScheduleform);
		return isAdded;
	}
	
	public void getstudentAndStaffData(EventScheduleForAttendanceForm eventScheduleform) throws Exception{
		IEventScheduleForAttendanceTransaction transaction=new EventScheduleForAttendanceTransactionImpl();
		List<EventScheduleForAttendanceBo> boList=transaction.getstudentAndStaffData();
		List<EventScheduleForAttendanceTo> studentList=new ArrayList<EventScheduleForAttendanceTo>();
		List<EventScheduleForAttendanceTo> staffList=new ArrayList<EventScheduleForAttendanceTo>();
		if(boList!=null){
			Iterator<EventScheduleForAttendanceBo> iterator=boList.iterator();
			while(iterator.hasNext()){
				EventScheduleForAttendanceBo bo=iterator.next();
				if(bo.getIsStudent()){
					EventScheduleForAttendanceTo to=new EventScheduleForAttendanceTo();
					to.setEventDate(bo.getEventDate());
					to.setEventDescription(bo.getEventDescription());
					to.setEventLocation(bo.getEventLocation().getName());
					to.setEventTimeFrom(bo.getEventTimeFrom());
					to.setEventTimeTo(bo.getEventTimeTo());
					to.setId(bo.getId());
					studentList.add(to);
				}else {
					EventScheduleForAttendanceTo to=new EventScheduleForAttendanceTo();
					to.setEventDate(bo.getEventDate());
					to.setEventDescription(bo.getEventDescription());
					to.setEventLocation(bo.getEventLocation().getName());
					to.setEventTimeFrom(bo.getEventTimeFrom());
					to.setEventTimeTo(bo.getEventTimeTo());
					to.setId(bo.getId());
					staffList.add(to);
				}
			}
		}
		eventScheduleform.setStudentList(studentList);
		eventScheduleform.setStaffList(staffList);
	}
	
	
	public void editBiometricDetails(EventScheduleForAttendanceForm eventScheduleform)throws Exception{
		IEventScheduleForAttendanceTransaction transaction=new EventScheduleForAttendanceTransactionImpl();
		EventScheduleForAttendanceBo eventSchedule=transaction.getEventSchAttDetailsById(eventScheduleform.getId());
		EventScheduleForAttendanceHelper.getInstance().setBotoForm(eventScheduleform, eventSchedule);
	}
	
	public boolean updateStudentOrStaffDetails(ArrayList<Integer> listClass,EventScheduleForAttendanceForm eventScheduleform) throws Exception{
		boolean isAdded=false;
		EventScheduleForAttendanceBo boList=EventScheduleForAttendanceHelper.getInstance().convertFormToBoInUpdateMode(listClass,eventScheduleform);
		IEventScheduleForAttendanceTransaction transaction=new EventScheduleForAttendanceTransactionImpl();
		boolean isduplicate=transaction.dupcheckingForStudentOrStaffData(boList,eventScheduleform);
		if(!isduplicate)
		 isAdded=transaction.addOrUpdateStudentOrStaffData(boList,eventScheduleform);
		if(isAdded)
		   return true;
		else
			return false;
	}
	public boolean deleteEventScheduleStudentOrStaffDetails(EventScheduleForAttendanceForm eventScheduleform)throws Exception{
		IEventScheduleForAttendanceTransaction transaction=new EventScheduleForAttendanceTransactionImpl();
			boolean isDeleted=transaction.deleteEventScheduleStudentOrStaffDetails(eventScheduleform.getId(),eventScheduleform);
				return isDeleted;
			}
}
