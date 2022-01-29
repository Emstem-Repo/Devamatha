package com.kp.cms.handlers.employee;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.EmpApplyLeave;
import com.kp.cms.bo.admin.EmpLeave;
import com.kp.cms.bo.admin.EmpLeaveType;
import com.kp.cms.forms.employee.ApplyLeaveForm;
import com.kp.cms.helpers.employee.ApplyLeaveHelper;
import com.kp.cms.to.admin.EmpLeaveTO;
import com.kp.cms.to.admin.EmpLeaveTypeTO;
import com.kp.cms.to.employee.EmployeeApproveLeaveTO;
import com.kp.cms.to.employee.EmployeeLeaveTO;
import com.kp.cms.transactions.employee.IApplyLeaveTransaction;
import com.kp.cms.transactionsimpl.employee.ApplyLeaveTransactionImpl;
import com.kp.cms.utilities.CommonUtil;

public class ApplyLeaveHandler {
	/**
	 * Singleton object of applyLeaveHandler
	 */
	private static volatile ApplyLeaveHandler applyLeaveHandler = null;
	private static final Log log = LogFactory.getLog(ApplyLeaveHandler.class);
	private ApplyLeaveHandler() {
		
	}
	/**
	 * return singleton object of applyLeaveHandler.
	 * @return
	 */
	public static ApplyLeaveHandler getInstance() {
		if (applyLeaveHandler == null) {
			applyLeaveHandler = new ApplyLeaveHandler();
		}
		return applyLeaveHandler;
	}
	IApplyLeaveTransaction transaction=new ApplyLeaveTransactionImpl();
	ApplyLeaveHelper helper=ApplyLeaveHelper.getInstance();
	/**
	 * getting the leavetype list which are active
	 * @return
	 * @throws Exception
	 */
	public List<EmpLeaveTypeTO> getLeaveTypeList() throws Exception{
		log.info("Entering into the getLeaveTypeList in ApplyLeaveHandler");
		List<EmpLeaveType> list=transaction.getLeaveTypeList();
		log.info("Exit from the getLeaveTypeList in ApplyLeaveHandler");
		return helper.convertBoListToTOList(list);
	}
	/**
	 * adding the emp leave to database
	 * @param applyLeaveForm
	 * @return
	 * @throws Exception
	 */
	public boolean applyLeave(ApplyLeaveForm applyLeaveForm) throws Exception{
		EmpApplyLeave empAppLeave=helper.getBoFromForm(applyLeaveForm);
		return transaction.applyLeave(empAppLeave);
	}
	/**
	 * @param applyLeaveForm
	 * @return
	 * @throws Exception
	 */
	public boolean checkDuplicateLeaves(ApplyLeaveForm applyLeaveForm) throws Exception{
		String query=helper.getQueryforCheckDuplicate(applyLeaveForm);
		return transaction.checkDuplicateLeaves(query);
	}
	
	public List<EmployeeLeaveTO> getApplyLeaveDetails(String employeeId)throws Exception
	{
		List<EmpApplyLeave> applyLeaveBoList = transaction.getApplyLeaveDetails(employeeId);
		List<EmployeeLeaveTO> applyLeaveToList = ApplyLeaveHelper.getInstance().setBoListToToList(applyLeaveBoList);
		return applyLeaveToList;
		
	}
	
	public List<EmpLeaveTO> getAllotedLeaveTypeList(String employeeId) throws Exception{
		log.info("Entering into the getLeaveTypeList in ApplyLeaveHandler");
		List<EmpLeave> list=transaction.getAllotedLeaveTypeList(employeeId);
		log.info("Exit from the getLeaveTypeList in ApplyLeaveHandler");
		return helper.setAllotedBoListToTOList(list);
	}
	public boolean isHoliday(Date time, String employeeId) throws Exception
	{
		return transaction.isHoliday(CommonUtil.ConvertStringToSQLDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(time), "dd-MMM-yyyy", "dd/MM/yyyy") ),employeeId);
	}
	
	public Integer getRemainingDays(ApplyLeaveForm applyLeaveForm)throws Exception 
	{
		return transaction.getRemainingDays(applyLeaveForm);
	}
	public boolean isReportingMangerAssigned(String employeeId) throws Exception
	{
		return transaction.isReportingMangerAssigned(employeeId);
	}
	public boolean isEmployeeTypeSet(String employeeId)throws Exception
	{
		return transaction.isEmployeeTypeSet(employeeId);
	}
	public boolean cancelLeave(ApplyLeaveForm applyLeaveForm)throws Exception 
	{
		return transaction.cancelLeave(applyLeaveForm.getId());
	}
	public void getLeaveDetails(ApplyLeaveForm applyLeaveForm) throws Exception
	{
		EmpApplyLeave leave=transaction.getLeaveDetails(applyLeaveForm.getId());
		helper.convertBoToTO(leave,applyLeaveForm);
	}
	public boolean startCancellation(ApplyLeaveForm applyLeaveForm) throws Exception
	{
		return transaction.startCancellation(applyLeaveForm);
	}
	public Integer getPendingDays(ApplyLeaveForm applyLeaveForm) throws Exception
	{
		return transaction.getPendingDays(applyLeaveForm);
	}
}
