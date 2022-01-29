package com.kp.cms.handlers.employee;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;

import com.kp.cms.bo.admin.PayScaleBO;
import com.kp.cms.bo.employee.Holidays;
import com.kp.cms.forms.employee.HolidayDetailsForm;
import com.kp.cms.forms.employee.PayScaleDetailsForm;
import com.kp.cms.helpers.employee.HolidayDetailsHelper;
import com.kp.cms.helpers.employee.PayScaleDetailsHelper;
import com.kp.cms.to.employee.HolidayDetailsTO;
import com.kp.cms.to.employee.PayScaleTO;
import com.kp.cms.transactions.employee.IHolidayDetailsTransaction;
import com.kp.cms.transactions.employee.IPayScaleTransactions;
import com.kp.cms.transactionsimpl.employee.HolidayDetailsTxnImpl;
import com.kp.cms.transactionsimpl.employee.PayScaleTransactionImpl;
import com.kp.cms.utilities.CommonUtil;

public class HolidayDetailsHandler {
	private static final Log log = LogFactory.getLog(HolidayDetailsHandler.class);
	public static volatile HolidayDetailsHandler holidaysHandler = null;
	public static HolidayDetailsHandler getInstance() {
		if (holidaysHandler == null) {
			holidaysHandler = new HolidayDetailsHandler();
			return holidaysHandler;
		}
		return holidaysHandler;
	}
	public boolean addHolidays(HolidayDetailsForm holidaysForm)throws Exception{
		Holidays holidays=HolidayDetailsHelper.getInstance().convertFormTOBO(holidaysForm);
		IHolidayDetailsTransaction transaction=new HolidayDetailsTxnImpl();
		boolean isAdded=transaction.addPayScale(holidays);
		return isAdded;
	}
	public List<HolidayDetailsTO> getHolidaysList(){
		IHolidayDetailsTransaction transaction=new HolidayDetailsTxnImpl();
		List<Holidays> holidays=transaction.getHolidaysList();
		List<HolidayDetailsTO> holidaysTO=HolidayDetailsHelper.getInstance().convertBosToTOs(holidays);
		return holidaysTO;
	}
	public boolean duplicateCheck(HolidayDetailsForm holidaysForm,ActionErrors errors,HttpSession session){
		IHolidayDetailsTransaction transaction=new HolidayDetailsTxnImpl();
		Date startDate=CommonUtil.ConvertStringToDate(holidaysForm.getStartDate());
		Date endDate=CommonUtil.ConvertStringToDate(holidaysForm.getEndDate());
		boolean duplicate=transaction.duplicateCheck(startDate,endDate,session,errors,holidaysForm);
		return duplicate;
	}
	public void editHolidays(HolidayDetailsForm holidaysForm)throws Exception{
		IHolidayDetailsTransaction transaction=new HolidayDetailsTxnImpl();
		Holidays holidays=transaction.getHolidaysListById(holidaysForm.getId());
		HolidayDetailsHelper.getInstance().setBotoForm(holidaysForm, holidays);
	}
	public boolean updatePayScale(HolidayDetailsForm holidaysForm){
		Holidays holidays=HolidayDetailsHelper.getInstance().convertFormToBo(holidaysForm);
		IHolidayDetailsTransaction transaction=new HolidayDetailsTxnImpl();
		boolean isUpdated=transaction.updateHolidays(holidays);
		return isUpdated;
	}
    public boolean deleteHolidays(HolidayDetailsForm holidaysForm)throws Exception{
		
	IHolidayDetailsTransaction transaction=new HolidayDetailsTxnImpl();
			boolean isDeleted=transaction.deleteHolidays(holidaysForm.getId());
			return isDeleted;
	}
    public boolean reactivateHolidays(HolidayDetailsForm holidaysForm,String userId)throws Exception{
    	IHolidayDetailsTransaction transaction=new HolidayDetailsTxnImpl();
	/* Date startDate=CommonUtil.ConvertStringToDate(holidaysForm.getStartDate());
	 Date endDate= CommonUtil.ConvertStringToDate(holidaysForm.getEndDate());*/
     return transaction.reactivateHolidays(holidaysForm);
    }
}
