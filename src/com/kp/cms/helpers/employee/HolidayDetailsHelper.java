package com.kp.cms.helpers.employee;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.PayScaleBO;
import com.kp.cms.bo.employee.Holidays;
import com.kp.cms.forms.employee.HolidayDetailsForm;
import com.kp.cms.forms.employee.PayScaleDetailsForm;
import com.kp.cms.helpers.admission.AdmissionFormHelper;
import com.kp.cms.to.employee.HolidayDetailsTO;
import com.kp.cms.to.employee.PayScaleTO;
import com.kp.cms.utilities.CommonUtil;

public class HolidayDetailsHelper {
	
	private static final String FROM_DATEFORMAT="dd/MM/yyyy";
	private static final String SQL_DATEFORMAT="dd-MMM-yyyy";
	private static final Log log = LogFactory.getLog(HolidayDetailsHelper.class);
	public static volatile HolidayDetailsHelper holidaysHelper = null;

	public static HolidayDetailsHelper getInstance() {
		if (holidaysHelper == null) {
			holidaysHelper = new HolidayDetailsHelper();
			return holidaysHelper;
		}
		return holidaysHelper;
	}
	public Holidays convertFormTOBO(HolidayDetailsForm holidaysForm)throws Exception{
		Holidays holidays=new Holidays();
		Date startDate=CommonUtil.ConvertStringToDate(holidaysForm.getStartDate());
		Date endDate=CommonUtil.ConvertStringToDate(holidaysForm.getEndDate());
		holidays.setStartDate(startDate);
		holidays.setEndDate(endDate);
		holidays.setIsActive(true);
		holidays.setCreatedDate(new Date());
		holidays.setCreatedBy(holidaysForm.getUserId());
		holidays.setDescription(holidaysForm.getDescription());
		return holidays;
	}
	public List<HolidayDetailsTO> convertBosToTOs(List<Holidays> holidays){
    	List<HolidayDetailsTO> holidaysList=new ArrayList<HolidayDetailsTO>();
		Iterator itr=holidays.iterator();
		while(itr.hasNext()){
			Holidays holiday=(Holidays)itr.next();
			HolidayDetailsTO holidaysTO=new HolidayDetailsTO();
			holidaysTO.setId(holiday.getId());
			holidaysTO.setEndDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(holiday.getEndDate()), HolidayDetailsHelper.SQL_DATEFORMAT,HolidayDetailsHelper.FROM_DATEFORMAT));
			holidaysTO.setStartDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(holiday.getStartDate()), HolidayDetailsHelper.SQL_DATEFORMAT,HolidayDetailsHelper.FROM_DATEFORMAT));
			holidaysTO.setDescription(holiday.getDescription());
			holidaysList.add(holidaysTO);
		}
		return holidaysList;
	}
	/**
	 * @param holidaysForm
	 * @param holidays
	 */
	public void setBotoForm(HolidayDetailsForm holidaysForm,Holidays holidays){
		if(holidays!=null){
			holidaysForm.setStartDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(holidays.getStartDate()), HolidayDetailsHelper.SQL_DATEFORMAT,HolidayDetailsHelper.FROM_DATEFORMAT));
			holidaysForm.setEndDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(holidays.getEndDate()), HolidayDetailsHelper.SQL_DATEFORMAT,HolidayDetailsHelper.FROM_DATEFORMAT));
			holidaysForm.setDescription(holidays.getDescription());
		}
	}
public Holidays convertFormToBo(HolidayDetailsForm holidaysForm){
	Holidays holidays=new Holidays();
	holidays.setId(holidaysForm.getId());
	holidays.setStartDate(CommonUtil.ConvertStringToDate(holidaysForm.getStartDate()));
	holidays.setEndDate(CommonUtil.ConvertStringToDate(holidaysForm.getEndDate()));
	holidays.setDescription(holidaysForm.getDescription());
	holidays.setLastModifiedDate(new Date());
	holidays.setModifiedBy(holidaysForm.getUserId());
	holidays.setIsActive(true);
	return holidays;
}
}
