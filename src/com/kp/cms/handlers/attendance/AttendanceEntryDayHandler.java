package com.kp.cms.handlers.attendance;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;


import com.kp.cms.bo.admin.AttendanceEntryDay;
import com.kp.cms.bo.admin.Caste;
import com.kp.cms.bo.admin.IsActive;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.admin.CasteForm;
import com.kp.cms.forms.attendance.AttendanceEntryDayForm;
import com.kp.cms.helpers.admin.CasteHelper;
import com.kp.cms.helpers.attendance.AttendanceEntryDayHelper;
import com.kp.cms.to.admin.CasteTO;
import com.kp.cms.to.attendance.AttendanceEntryDayTO;
import com.kp.cms.transactions.admin.ICasteTransaction;
import com.kp.cms.transactions.attandance.IAttendanceEntryDayTransaction;
import com.kp.cms.transactionsimpl.admin.CasteTransactionImpl;
import com.kp.cms.transactionsimpl.attendance.AttendanceEntryDayTransactionImpl;



public class AttendanceEntryDayHandler {

	private static volatile AttendanceEntryDayHandler attendanceEntryDayHandler = null;
    private AttendanceEntryDayHandler(){
    	
    }
	public static AttendanceEntryDayHandler getInstance() {
		if (attendanceEntryDayHandler == null) {
			attendanceEntryDayHandler = new AttendanceEntryDayHandler();
		}
		return attendanceEntryDayHandler;
	}
	
	public List<AttendanceEntryDayTO> getAttendanceEntryDays() throws Exception{
		IAttendanceEntryDayTransaction transaction = new AttendanceEntryDayTransactionImpl();
		List<AttendanceEntryDay> attendanceEntryDayList = transaction.getAttendanceEntyDays();
		List<AttendanceEntryDayTO> attendanceEntryDayToList = AttendanceEntryDayHelper.convertBOsToTos(attendanceEntryDayList);
		
		
		return attendanceEntryDayToList;
	}
	
	public boolean addAttendanceEntryDay(AttendanceEntryDayForm attendanceEntryDayForm,String mode,HttpServletRequest request) throws Exception{
		

		// TODO Auto-generated method stub
		IAttendanceEntryDayTransaction attImpl = new AttendanceEntryDayTransactionImpl();
		AttendanceEntryDay attendanceEntryDay = null;
		
		
		AttendanceEntryDay dupAttendanceEntryDay= attImpl.isDuplicateAttendanceEntryDayDetail(attendanceEntryDayForm, mode);
		if(dupAttendanceEntryDay!=null && dupAttendanceEntryDay.getIsActive()==true)
		{
			throw new DuplicateException();
		}
		if(dupAttendanceEntryDay!=null && dupAttendanceEntryDay.getIsActive()==false){
			request.getSession().setAttribute("AttendanceEntryDay",dupAttendanceEntryDay);
			attendanceEntryDayForm.setAttendanceEntryDay(dupAttendanceEntryDay);
			attendanceEntryDayForm.setDupId(String.valueOf(dupAttendanceEntryDay.getId()));
			throw new ReActivateException();
		}
		else
		{
			attendanceEntryDay=AttendanceEntryDayHelper.getInstance().addAttendanceEntryDay(attendanceEntryDayForm,mode);
		}
	if(mode.equals("Add")){
		attendanceEntryDay.setCreatedBy(attendanceEntryDayForm.getUserId());
		attendanceEntryDay.setModifiedBy(attendanceEntryDayForm.getUserId());
		attendanceEntryDay.setLastModifiedDate(new Date());
		attendanceEntryDay.setCreatedDate(new Date());
		attendanceEntryDay.setIsActive(true);
	}else{ // Edit
		attendanceEntryDay.setLastModifiedDate(new Date());
		attendanceEntryDay.setModifiedBy(attendanceEntryDayForm.getUserId());
	}
	boolean isadded=attImpl.addAttendanceEntryDay(attendanceEntryDay,mode);
	return isadded;
	
	}
	
	public boolean deleteAttendanceEntryDay(int id,boolean activate,AttendanceEntryDayForm attendanceEntryDayForm) throws Exception{
		boolean isDeleted=false;
		IAttendanceEntryDayTransaction attImp = new AttendanceEntryDayTransactionImpl();
        isDeleted=attImp.deleteAttendanceEntryDay(id,activate,attendanceEntryDayForm);
		return isDeleted;
		
	}
	
	
	
	public boolean reActivateAttendanceEntryDay(AttendanceEntryDay attendanceEntryDay,String userId) throws Exception{
		IAttendanceEntryDayTransaction attImpl = new AttendanceEntryDayTransactionImpl();
		
		boolean isAttendanceEntryDay=attImpl.reActivateAttendanceEntryDay(attendanceEntryDay,userId);
		return isAttendanceEntryDay;
	}
	
	public void editAttendanceEntryDay(AttendanceEntryDayForm attendanceEntryDayForm) throws Exception{
		IAttendanceEntryDayTransaction attImpl = new AttendanceEntryDayTransactionImpl();
		if(((attendanceEntryDayForm.getId())!= null && !attendanceEntryDayForm.getId().equalsIgnoreCase(""))){
			AttendanceEntryDay attendanceEntryDay = attImpl.getAttendanceEntryDayDetails(attendanceEntryDayForm.getId());
			AttendanceEntryDayHelper.getInstance().setAttendanceEntryDayDetails(attendanceEntryDayForm, attendanceEntryDay);
			
		
		
		}
	}
	public void AddAttendanceEntryUsingJobRemainder( ) throws Exception{
			Map<Integer,String> dayMap=null;
			String day=null;
			int dayId=0;
			int oldDayId=0;
			dayMap=new HashMap<Integer, String>();
			dayMap.put(1, "Sunday");
			dayMap.put(2, "Monday");
			dayMap.put(3, "Tuesday");
			dayMap.put(4, "Wednesday");
			dayMap.put(5, "Thursday");
			dayMap.put(6, "Friday");
			dayMap.put(7, "Saturday");
			
		IAttendanceEntryDayTransaction attImpl = new AttendanceEntryDayTransactionImpl();
		AttendanceEntryDay attendanceEntryDayNew=new AttendanceEntryDay();
			AttendanceEntryDay attendanceEntryDay = attImpl.getLastAttendanceEntryDayDetails();
			attendanceEntryDayNew.setCreatedBy(attendanceEntryDay.getCreatedBy());
			attendanceEntryDayNew.setCreatedDate(new Date());
			attendanceEntryDayNew.setLastModifiedDate(new Date());
			attendanceEntryDayNew.setModifiedBy(attendanceEntryDay.getModifiedBy());
			attendanceEntryDayNew.setIsActive(true);
			for(Map.Entry<Integer,String> entry : dayMap.entrySet()){
				if (entry.getValue()==attendanceEntryDay.getDay() || entry.getValue().equalsIgnoreCase(attendanceEntryDay.getDay())) {
					oldDayId=entry.getKey();
				}
			}
			if (oldDayId==6) 
				dayId=2;
			else
				dayId=oldDayId+1;
			Date date=attendanceEntryDay.getDate();
			Calendar cal=Calendar.getInstance();
			cal.setTime(date);
			cal.add(Calendar.DAY_OF_YEAR, 1);
			if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY || cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
				cal.add(Calendar.DAY_OF_YEAR, 1);
				if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY || cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
					cal.add(Calendar.DAY_OF_YEAR, 1);
					if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY || cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
						cal.add(Calendar.DAY_OF_YEAR, 1);
					}else{
				        	 date=cal.getTime();
				        }
				}else{
			        	 date=cal.getTime();
			        }
			}else{
		        	 date=cal.getTime();
		        }
			
			for(Map.Entry<Integer,String> entry : dayMap.entrySet()){
				if (entry.getKey()==dayId) {
					day=entry.getValue();
				}
			}
			attendanceEntryDayNew.setDate(date);
			attendanceEntryDayNew.setDay(day);
			attImpl.addAttendanceEntryDayByRemainder(attendanceEntryDayNew);
	}
}
