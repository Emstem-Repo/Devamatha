package com.kp.cms.handlers.employee;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.AcademicYear;
import com.kp.cms.bo.admin.EmpAcademicHolidays;
import com.kp.cms.bo.admin.EmpAcademicHolidaysDates;
import com.kp.cms.bo.admin.EmployeeTypeBO;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.employee.HolidaysForm;
import com.kp.cms.helpers.employee.HolidaysHelper;
import com.kp.cms.to.employee.HolidaysTO;
import com.kp.cms.transactions.admission.IInterviewDefinitionTransaction;
import com.kp.cms.transactions.employee.IHolidaysTransaction;
import com.kp.cms.transactionsimpl.admission.InterviewDefinitionTransactionImpl;
import com.kp.cms.transactionsimpl.employee.HolidaysTransactionImpl;

public class HolidaysHandler {
	
	private static volatile HolidaysHandler holidaysHandler = null;
	private static final Log log = LogFactory.getLog(HolidaysHandler.class);
	
	public static HolidaysHandler getInstance() {
		if (holidaysHandler == null) {
			holidaysHandler = new HolidaysHandler();
		}
		return holidaysHandler;
	}
	IHolidaysTransaction transaction=new HolidaysTransactionImpl();
	HolidaysHelper helper=HolidaysHelper.getInstance();
	
	public List<HolidaysTO> getEmployeeTypeList() throws Exception{
		log.info("Entering into the getLeaveTypeList in ApplyLeaveHandler");
		List<EmployeeTypeBO> list=transaction.getEmployeeTypeList();
		log.info("Exit from the getLeaveTypeList in ApplyLeaveHandler");
		return helper.convertBoListToTOList(list);
	}
	
	public boolean addHolidays(HolidaysForm holidaysForm,String mode) throws Exception
	{
		boolean result = false;
		int yearId = 0;
		AcademicYear yearBO = transaction.getYearId(holidaysForm);
		if(yearBO!=null)
			yearId = yearBO.getId();
		String query = helper.getQueryforCheckDuplicate(holidaysForm);
		EmpAcademicHolidays empHolidaysBO = transaction.duplicateCheck(query);
		if(empHolidaysBO != null)
		{
			if(empHolidaysBO.getId() != holidaysForm.getId())
			{
				if(empHolidaysBO.getIsActive())
				{
					throw new DuplicateException();
				}
				else
				{
					int id =empHolidaysBO.getId();
					holidaysForm.setId(empHolidaysBO.getId());
					throw new ReActivateException(id);
				}
			}
		}
		EmpAcademicHolidays empHolidays = helper.getBoFromForm(holidaysForm,mode,yearId);
		result = transaction.addHolidays(empHolidays,holidaysForm.getDatesToBeDeleted());
		return result;
	}

	public List<HolidaysTO> getEmployeeHolidaysList() throws Exception{
		 List<EmpAcademicHolidays> empHolidaysList = transaction.getEmployeeHolidaysList();
		return helper.setBOListToTOList(empHolidaysList); 
	}

	public HolidaysTO getHolidaysDetailsToEdit(int id) throws Exception{
		EmpAcademicHolidays empHolidays = transaction.getHolidaysDetailsToEdit(id);
		return helper.setBOListToTOListForEdit(empHolidays);
	}

	public boolean deleteHolidays(int id, boolean activate, String userId) throws Exception{
		
		boolean isHolidayDeleted = false;
		if (transaction != null) {
			isHolidayDeleted = transaction.deleteHolidays(id, activate, userId);
		}
		return isHolidayDeleted;
	}

}
