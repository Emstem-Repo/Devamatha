package com.kp.cms.helpers.attendance;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.exam.HolidayBO;
import com.kp.cms.forms.attendance.HolidayNameForm;
import com.kp.cms.handlers.usermanagement.LoginHandler;
import com.kp.cms.to.attendance.HolidayTO;
import com.kp.cms.utilities.CommonUtil;

public class HolidayHelper {
	public static volatile HolidayHelper holidayHelper=null;
	public static final Log log=LogFactory.getLog(HolidayHelper.class);
	private static final String SQL_DATEFORMAT="dd-MMM-yyyy";
	private static final String FROM_DATEFORMAT="dd/MM/yyyy";
	private HolidayHelper(){
		
	}
	public static HolidayHelper getInstance(){
		if(holidayHelper ==null ){
			holidayHelper=new HolidayHelper();
			return holidayHelper;
		}
		return holidayHelper;
	}
	public List<HolidayTO> convertBoToTo(List<HolidayBO> holidayBoList) {
		List<HolidayTO> holidayTolist= new ArrayList<HolidayTO>();
		Iterator<HolidayBO> i=holidayBoList.iterator();
		HolidayBO holidayBO;
		HolidayTO holidayTO;
		while(i.hasNext()){
			holidayTO = new HolidayTO();
			holidayBO= i.next();
			holidayTO.setHolidayId(holidayBO.getHolidayId());
			if(holidayBO.getHolidayDate()!=null){
			    holidayTO.setHolidayDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(holidayBO.getHolidayDate()),HolidayHelper.SQL_DATEFORMAT,HolidayHelper.FROM_DATEFORMAT));
			}
			holidayTO.setHolidayName(holidayBO.getHolidayName());
			holidayTolist.add(holidayTO);
		}
		log.error("ending of convertBoToTo method in HolidayHelper");
		return holidayTolist;
	}
	public HolidayBO convertTOToBO(HolidayNameForm holidayForm, String mode) throws Exception{
		HolidayBO holidayBO=new HolidayBO();
		holidayBO.setHolidayId(holidayForm.getHolidayId());
		if(mode.equals("Update")){
			holidayBO.setModifiedBy(holidayForm.getUserId());
			holidayBO.setLastModifiedDate(new Date());
		}
		if(mode.equals("Add")){
			holidayBO.setCreatedBy(holidayForm.getUserId());
			holidayBO.setCreatedDate(new Date());
		}
		holidayBO.setHolidayName(holidayForm.getHolidayName());
		holidayBO.setHolidayDate(CommonUtil.ConvertStringToSQLDate(holidayForm.getHolidayDate()));
		holidayBO.setIsActive(true);
		return holidayBO;
	}


}
