package com.kp.cms.handlers.employee;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;

import com.kp.cms.bo.admin.EmpLeaveType;
import com.kp.cms.bo.admin.PayScaleBO;
import com.kp.cms.bo.employee.EmpLeaveAllotment;
import com.kp.cms.bo.employee.EmpType;
import com.kp.cms.forms.employee.EmpLeaveAllotmentForm;
import com.kp.cms.forms.employee.PayScaleDetailsForm;
import com.kp.cms.handlers.attendance.AttendanceSummaryReportHandler;
import com.kp.cms.helpers.employee.EmpLeaveAllotmentHelper;
import com.kp.cms.helpers.employee.EmployeeSettingsHelper;
import com.kp.cms.helpers.employee.PayScaleDetailsHelper;
import com.kp.cms.to.employee.EmpLeaveAllotTO;
import com.kp.cms.to.employee.PayScaleTO;
import com.kp.cms.transactions.employee.IEmpLeaveAllotmentTxn;
import com.kp.cms.transactions.employee.IEmployeeSettingsTxn;
import com.kp.cms.transactions.employee.IPayScaleTransactions;
import com.kp.cms.transactionsimpl.employee.EmpLeaveAllotmentTxnImpl;
import com.kp.cms.transactionsimpl.employee.EmployeeSettingsTxnImpl;
import com.kp.cms.transactionsimpl.employee.PayScaleTransactionImpl;

public class EmpLeaveAllotmentHandler {
	private static final Log log = LogFactory.getLog(EmpLeaveAllotmentHandler.class);
	public static volatile EmpLeaveAllotmentHandler empLeaveAll=null;
	/**
	 * @return
	 * This method gives instance of this method
	 */
	public static EmpLeaveAllotmentHandler getInstance(){
		if(empLeaveAll==null){
			empLeaveAll= new EmpLeaveAllotmentHandler();}
		return empLeaveAll;
	}
	public Map<Integer,String> getEmpType()throws Exception{
		IEmpLeaveAllotmentTxn transaction=new EmpLeaveAllotmentTxnImpl();
		List<EmpType> empLeave=transaction.getEmpType();
		Map<Integer,String> employLeave=EmpLeaveAllotmentHelper.getInstance().setToMap(empLeave);
		return employLeave;
	}
	public List<EmpLeaveAllotTO> getLeaveAllotList()throws Exception{
		IEmpLeaveAllotmentTxn transaction=new EmpLeaveAllotmentTxnImpl();
		List<EmpLeaveAllotment> leaveAllotment=transaction.getLeaveAllotments();
		List<EmpLeaveAllotTO> leaveAllotmentTO=EmpLeaveAllotmentHelper.getInstance().convertBosToTOs(leaveAllotment);
		return leaveAllotmentTO;
	}
	public boolean addLeaveAllotment(EmpLeaveAllotmentForm empLeaveAllotForm)throws Exception{
		EmpLeaveAllotment leaveAllot=EmpLeaveAllotmentHelper.getInstance().convertFormTOBO(empLeaveAllotForm);
		IEmpLeaveAllotmentTxn transaction=new EmpLeaveAllotmentTxnImpl();
		boolean isAdded=transaction.addLeaveAllot(leaveAllot);
		return isAdded;
	}
	public void editLeaveAllotment(EmpLeaveAllotmentForm empLeaveAllotForm)throws Exception{
		IEmpLeaveAllotmentTxn transaction=new EmpLeaveAllotmentTxnImpl();
		EmpLeaveAllotment leaveAllotment=transaction.getLeaveAllotmentById(empLeaveAllotForm.getId());
		EmpLeaveAllotmentHelper.getInstance().setBotoForm(empLeaveAllotForm, leaveAllotment);
	}
	public boolean updateLeaveAllotment(EmpLeaveAllotmentForm empLeaveAllotForm)throws Exception{
		EmpLeaveAllotment leaveAllotment=EmpLeaveAllotmentHelper.getInstance().convertFormToBo(empLeaveAllotForm);
		IEmpLeaveAllotmentTxn transaction=new EmpLeaveAllotmentTxnImpl();
		boolean isUpdated=transaction.updateLeaveAllotment(leaveAllotment);
		return isUpdated;
	}
    public boolean deleteLeaveAllotment(EmpLeaveAllotmentForm empLeaveAllotForm)throws Exception{
	    IEmpLeaveAllotmentTxn transaction=new EmpLeaveAllotmentTxnImpl();
		boolean isDeleted=transaction.deleteLeaveAllotment(empLeaveAllotForm.getId());
		return isDeleted;
	}
public boolean duplicateCheck(EmpLeaveAllotmentForm empLeaveAllotForm,HttpSession session,ActionErrors errors)throws Exception{
	IEmpLeaveAllotmentTxn transaction=new EmpLeaveAllotmentTxnImpl();
	String empType=empLeaveAllotForm.getEmpType();
	String leaveType=empLeaveAllotForm.getLeaveType();
	boolean duplicate=transaction.duplicateCheck(empType,leaveType,session,errors,empLeaveAllotForm.getId(),empLeaveAllotForm);
	return duplicate;
}
public int getEmpSettingsLeaveType()throws Exception{
	IEmpLeaveAllotmentTxn transaction=new EmpLeaveAllotmentTxnImpl();
	int settingsLeave=transaction.getEmpSettingsLeaveType();
	return settingsLeave;
}
public Map<Integer,String> getEmpLeveType(int settingsLeave)throws Exception{
	IEmpLeaveAllotmentTxn transaction=new EmpLeaveAllotmentTxnImpl();
	List<EmpLeaveType> empLeave=transaction.getEmpLeaveType(settingsLeave);
	Map<Integer,String> employLeave=EmployeeSettingsHelper.getInstance().setToMap(empLeave);
	return employLeave;
}
public boolean reactivateLeaveAllotment(EmpLeaveAllotmentForm empLeaveAllotForm)
throws Exception{
	IEmpLeaveAllotmentTxn transaction=new EmpLeaveAllotmentTxnImpl();
   return transaction.reactivateLeaveAllotment(empLeaveAllotForm);
}
}
