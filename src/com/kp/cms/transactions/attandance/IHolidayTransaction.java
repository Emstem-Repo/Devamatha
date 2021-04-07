package com.kp.cms.transactions.attandance;

import java.util.List;

import com.kp.cms.bo.exam.HolidayBO;
import com.kp.cms.forms.attendance.HolidayNameForm;

public interface IHolidayTransaction {
	public List<HolidayBO> getHolidays()throws Exception;

	public HolidayBO isHolidayDuplicate(HolidayBO oldHolidayBO)throws Exception;
	
	public boolean addHoliday(HolidayBO holidayBO1)throws Exception;

	public boolean updateHoliday(HolidayBO holidayBO1)throws Exception;

	public boolean deleteHoliday(int holidayId, String userId)throws Exception;

	public boolean reActivateHoliday(HolidayBO holidayBO, String userId)throws Exception;

	public HolidayBO isHolidayDuplicate1(HolidayNameForm holidayForm)throws Exception;



}
