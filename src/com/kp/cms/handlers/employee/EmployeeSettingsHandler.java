package com.kp.cms.handlers.employee;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.EmpLeaveType;
import com.kp.cms.bo.employee.EmployeeSettings;
import com.kp.cms.bo.employee.Holidays;
import com.kp.cms.forms.employee.EmployeeSettingsForm;
import com.kp.cms.forms.employee.HolidayDetailsForm;
import com.kp.cms.helpers.employee.EmployeeSettingsHelper;
import com.kp.cms.helpers.employee.HolidayDetailsHelper;
import com.kp.cms.to.employee.EmployeeSettingsTO;
import com.kp.cms.to.employee.HolidayDetailsTO;
import com.kp.cms.transactions.employee.IEmployeeSettingsTxn;
import com.kp.cms.transactions.employee.IHolidayDetailsTransaction;
import com.kp.cms.transactionsimpl.employee.EmployeeSettingsTxnImpl;
import com.kp.cms.transactionsimpl.employee.HolidayDetailsTxnImpl;

public class EmployeeSettingsHandler {
	private static final Log log = LogFactory.getLog(EmployeeSettingsHandler.class);
	public static volatile EmployeeSettingsHandler empSettingsHandler = null;
	public static EmployeeSettingsHandler getInstance() {
		if (empSettingsHandler == null) {
			empSettingsHandler = new EmployeeSettingsHandler();
			return empSettingsHandler;
		}
		return empSettingsHandler;
	}
	public List<EmployeeSettingsTO> getEmpSettingsList(){
		IEmployeeSettingsTxn transaction=new EmployeeSettingsTxnImpl();
		List<EmployeeSettings> empSettings=transaction.getEmpSettList();
		List<EmployeeSettingsTO> empSettTO=EmployeeSettingsHelper.getInstance().convertBosToTOs(empSettings);
		return empSettTO;
	}
	public Map<Integer,String> getEmpLeveType(){
		IEmployeeSettingsTxn transaction=new EmployeeSettingsTxnImpl();
		List<EmpLeaveType> empLeave=transaction.getEmpLeaveType();
		Map<Integer,String> employLeave=EmployeeSettingsHelper.getInstance().setToMap(empLeave);
		return employLeave;
	}
	public void editEmpSettings(EmployeeSettingsForm empSettingsForm)throws Exception{
		IEmployeeSettingsTxn transaction=new EmployeeSettingsTxnImpl();
		EmployeeSettings empSettings=transaction.getEmpSettingsListById(empSettingsForm.getId());
		EmployeeSettingsHelper.getInstance().setBotoForm(empSettingsForm, empSettings);
	}
	public boolean checkApplicationNo(EmployeeSettingsForm empSettingsForm){
		IEmployeeSettingsTxn transaction=new EmployeeSettingsTxnImpl();
		Integer currentAppNo=transaction.getCurrentApplicationNO();
		boolean isCheck=EmployeeSettingsHelper.getInstance().checkApplicationNo(currentAppNo, empSettingsForm);
		return isCheck;
	}
	public boolean updateEmpSettings(EmployeeSettingsForm empSettingsForm){
		EmployeeSettings empSettings=EmployeeSettingsHelper.getInstance().convertFormToBo(empSettingsForm);
		IEmployeeSettingsTxn transaction=new EmployeeSettingsTxnImpl();
		boolean isUpdated=transaction.updateEmpSettings(empSettings);
		return isUpdated;
	}
}
