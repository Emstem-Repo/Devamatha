package com.kp.cms.transactions.employee;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionErrors;

import com.kp.cms.bo.admin.PayScaleBO;
import com.kp.cms.bo.employee.Holidays;
import com.kp.cms.forms.employee.HolidayDetailsForm;

public interface IHolidayDetailsTransaction {
	public boolean addPayScale(Holidays holidays);
	public List<Holidays> getHolidaysList();
	public boolean duplicateCheck(Date startDate,Date endDate,HttpSession session,ActionErrors errors,HolidayDetailsForm holidaysForm);
	public Holidays getHolidaysListById(int id); 
	public boolean updateHolidays(Holidays holidays);
	public boolean deleteHolidays(int id);
	public boolean reactivateHolidays(HolidayDetailsForm holidaysForm)throws Exception;
}
