package com.kp.cms.helpers.attendance;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.kp.cms.bo.admin.AttendanceEntryDay;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.forms.attendance.AttendanceEntryDayForm;
import com.kp.cms.to.attendance.AttendanceEntryDayTO;
import com.kp.cms.utilities.CommonUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class AttendanceEntryDayHelper {
	private static final Log log = LogFactory.getLog(AttendanceEntryDayHelper.class);
	private static AttendanceEntryDayHelper attendanceEntryDayHelper= null;
	public static AttendanceEntryDayHelper getInstance() {
		      if(attendanceEntryDayHelper == null) {
		    	  attendanceEntryDayHelper = new AttendanceEntryDayHelper();
		    	  return attendanceEntryDayHelper;
		      }
	return attendanceEntryDayHelper;
	}

	public static List<AttendanceEntryDayTO> convertBOsToTos(List<AttendanceEntryDay> casteBOList) {
		List<AttendanceEntryDayTO> attList = new ArrayList<AttendanceEntryDayTO>();
		
		if (casteBOList != null) {

			Iterator<AttendanceEntryDay> iterator = casteBOList.iterator();
			while (iterator.hasNext()) {
				AttendanceEntryDay attbo = (AttendanceEntryDay) iterator.next();
				AttendanceEntryDayTO attTO = new AttendanceEntryDayTO();
				attTO.setId(attbo.getId());
				attTO.setDate(String.valueOf(attbo.getDate()));
				attTO.setDay(attbo.getDay());
				attList.add(attTO);
			}
		}
		return attList;
	}
	
	public static AttendanceEntryDay convertTOtoBO(AttendanceEntryDayForm attendanceEntryDayForm,String mode) throws Exception{
		AttendanceEntryDay attBo=new AttendanceEntryDay();
		//attBo.setId(attendanceEntryDayForm.getId());
		if(mode.equals("Update")){
			attBo.setModifiedBy(attendanceEntryDayForm.getUserId());
			attBo.setLastModifiedDate(new Date());
		}else if(mode.equals("Add")){
			attBo.setCreatedBy(attendanceEntryDayForm.getUserId());
			attBo.setCreatedDate(new Date());
		}
		java.sql.Date date = null;
		
		SimpleDateFormat sdf1 = new SimpleDateFormat("dd/mm/yyyy");
		java.util.Date date1 = sdf1.parse(attendanceEntryDayForm.getDate());
		
		date = new java.sql.Date(date1.getTime());
		
		attBo.setDate(date);
		attBo.setDay(attendanceEntryDayForm.getDay());
		attBo.setIsActive(true);
		
		return attBo;
	}
	
	public void setAttendanceEntryDayDetails(AttendanceEntryDayForm attendanceEntryDayForm,
			AttendanceEntryDay attendanceEntryDay) {
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		String date = df.format(attendanceEntryDay.getDate());
		
		if (attendanceEntryDay != null) {
			attendanceEntryDayForm.setDate(date);
			attendanceEntryDayForm.setDay(attendanceEntryDay.getDay());
			attendanceEntryDayForm.setId(String.valueOf(attendanceEntryDay.getId()));
			
		}
		log.error("ending of setDepartmentDetails method in DepartmentEntryHelper");
	}
	public AttendanceEntryDay addAttendanceEntryDay(
			AttendanceEntryDayForm attendanceEntryDayForm , String mode) throws ParseException {
		// TODO Auto-generated method stub
		
		AttendanceEntryDay attendanceEntryDay=new AttendanceEntryDay();
		//attendanceEntryDay.setDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getDate(attendanceEntryDayForm.getDate(), "dd-MMM-yyyy","dd/MM/yyyy"));
		attendanceEntryDay.setDate(CommonUtil.ConvertStringToSQLDate(attendanceEntryDayForm.getDate()));
		attendanceEntryDay.setDay(attendanceEntryDayForm.getDay());
		attendanceEntryDay.setCreatedDate(new Date());
		attendanceEntryDay.setIsActive(true);
		attendanceEntryDay.setCreatedBy(attendanceEntryDayForm.getUserId());
		
	    if (mode.equalsIgnoreCase("Edit")) {
	    	attendanceEntryDay.setLastModifiedDate(new java.util.Date());
	    	attendanceEntryDay.setModifiedBy(attendanceEntryDayForm.getUserId());
	    	attendanceEntryDay.setId(Integer.parseInt(attendanceEntryDayForm.getId()));

		}
	   return attendanceEntryDay;
	}
	
}
