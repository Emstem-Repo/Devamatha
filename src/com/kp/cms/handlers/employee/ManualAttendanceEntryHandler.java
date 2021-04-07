package com.kp.cms.handlers.employee;

import java.util.List;

import com.kp.cms.actions.employee.ManualAttendanceEntryAction;
import com.kp.cms.bo.admin.EmpAttendance;
import com.kp.cms.bo.admin.EmpAttendanceBc;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.employee.ManualAttendanceEntryForm;
import com.kp.cms.helpers.employee.ManualAttendanceEntryHelper;
import com.kp.cms.to.employee.ManualAttendanceEntryTO;
import com.kp.cms.transactions.employee.IManualAttendanceEntryTransaction;
import com.kp.cms.transactionsimpl.employee.ManualAttendanceEntryTxnImpl;
import com.kp.cms.utilities.CommonUtil;

public class ManualAttendanceEntryHandler {

	private static volatile ManualAttendanceEntryHandler manualAttendanceEntryHandler = null;

	private ManualAttendanceEntryHandler() {
	}

	public String getDate(String date) {
		return CommonUtil.formatSqlDate1(date);
		
		
	}
	
	public static ManualAttendanceEntryHandler getInstance() {
		if (manualAttendanceEntryHandler == null) {
			manualAttendanceEntryHandler = new ManualAttendanceEntryHandler();
		}
		return manualAttendanceEntryHandler;
	}

	/**
	 * 
	 * @param departmentId
	 * @param designationId
	 * @return
	 * @throws Exception
	 */
	public List<ManualAttendanceEntryTO> getEmployeesAttendanceList(ManualAttendanceEntryForm manualAttendanceEntryForm) throws Exception {
		IManualAttendanceEntryTransaction manualAttendanceEntryTransaction = new ManualAttendanceEntryTxnImpl();
		ManualAttendanceEntryHelper manualAttendanceEntryHelper = new ManualAttendanceEntryHelper();

		String Query=manualAttendanceEntryHelper.getAttendanceListQuery(manualAttendanceEntryForm);
		List<EmpAttendance> manualAttendanceList = manualAttendanceEntryTransaction.getEmployeesAttendanceList(Query);
		
		List<ManualAttendanceEntryTO> manualAttendanceEntryTOList = manualAttendanceEntryHelper.convertBOstoTOs(manualAttendanceList,manualAttendanceEntryForm);

		return manualAttendanceEntryTOList;
	}

	/**
	 * 
	 * @param manualAttendanceEntryForm
	 * @return
	 * @throws Exception
	 */
	public boolean markEmployeesAttendance(ManualAttendanceEntryForm manualAttendanceEntryForm)throws Exception 
	{
		IManualAttendanceEntryTransaction manualAttendanceEntryTransaction = new ManualAttendanceEntryTxnImpl();
		ManualAttendanceEntryHelper manualAttendanceEntryHelper = new ManualAttendanceEntryHelper();
		EmpAttendance empAttendance =  manualAttendanceEntryTransaction.isAttendanceTaken(manualAttendanceEntryForm.getAttendanceDate(),Integer.parseInt(manualAttendanceEntryForm.getEmployeeId()));
		if(empAttendance != null){
			if(empAttendance.getIsActive()){
				throw new DuplicateException();
			}else{
				//manualAttendanceEntryForm.setId(empAttendance.getId());
				throw new ReActivateException(empAttendance.getId());
			}
			
		}
		EmpAttendance manualAttendance = manualAttendanceEntryHelper.convertFormToBOs(manualAttendanceEntryForm);
		EmpAttendanceBc attendanceBc=manualAttendanceEntryHelper.createBackUp(manualAttendance,"A",manualAttendanceEntryForm);
		return manualAttendanceEntryTransaction.markEmployeesAttendance(manualAttendance,attendanceBc);
	}

	public void editAttendance(ManualAttendanceEntryForm manualAttendanceEntryForm) throws Exception
	{
		IManualAttendanceEntryTransaction manualAttendanceEntryTransaction = new ManualAttendanceEntryTxnImpl();
		ManualAttendanceEntryHelper manualAttendanceEntryHelper = new ManualAttendanceEntryHelper();
		EmpAttendance empAttendance=manualAttendanceEntryTransaction.getEmployeeAttendance(manualAttendanceEntryForm.getId());
		manualAttendanceEntryHelper.convertBotoTo(manualAttendanceEntryForm,empAttendance);
	}

	public boolean deleteAttendance(ManualAttendanceEntryForm manualAttendanceEntryForm) throws Exception
	{
		IManualAttendanceEntryTransaction manualAttendanceEntryTransaction = new ManualAttendanceEntryTxnImpl();
		ManualAttendanceEntryHelper manualAttendanceEntryHelper = new ManualAttendanceEntryHelper();
		EmpAttendance manualAttendance =manualAttendanceEntryTransaction.getEmployeeAttendance(manualAttendanceEntryForm.getId());
		EmpAttendanceBc attendanceBc=manualAttendanceEntryHelper.createBackUp(manualAttendance,"D",manualAttendanceEntryForm);
		return manualAttendanceEntryTransaction.deleteAttendance(manualAttendanceEntryForm.getId(),manualAttendanceEntryForm.getUserId(),attendanceBc);
	}

	public boolean updateEmployeesAttendance(ManualAttendanceEntryForm manualAttendanceEntryForm) throws Exception 
	{
		IManualAttendanceEntryTransaction manualAttendanceEntryTransaction = new ManualAttendanceEntryTxnImpl();
		ManualAttendanceEntryHelper manualAttendanceEntryHelper = new ManualAttendanceEntryHelper();
		if(!manualAttendanceEntryForm.getDummyAttendanceDate().equals(manualAttendanceEntryForm.getAttendanceDate()))
		{
			EmpAttendance empAttendance = manualAttendanceEntryTransaction.isAttendanceTaken(manualAttendanceEntryForm.getAttendanceDate(),Integer.parseInt(manualAttendanceEntryForm.getUserId()));
			if(empAttendance != null){
				if(empAttendance.getIsActive()){
					throw new DuplicateException();
				}else{
					//manualAttendanceEntryForm.setId(empAttendance.getId());
					throw new ReActivateException(empAttendance.getId());
				}
				
			}
		}
		EmpAttendance manualAttendance =manualAttendanceEntryTransaction.getEmployeeAttendance(manualAttendanceEntryForm.getId());
		EmpAttendanceBc attendanceBc=manualAttendanceEntryHelper.createBackUp(manualAttendance,"U",manualAttendanceEntryForm);
		manualAttendance = manualAttendanceEntryHelper.convertFormToBOForEdit(manualAttendanceEntryForm,manualAttendance);
		return manualAttendanceEntryTransaction.markEmployeesAttendance(manualAttendance,attendanceBc);
	}

	public boolean reActivateAttendance(ManualAttendanceEntryForm manualAttendanceEntryForm) throws Exception {
		IManualAttendanceEntryTransaction manualAttendanceEntryTransaction = new ManualAttendanceEntryTxnImpl();
		ManualAttendanceEntryHelper manualAttendanceEntryHelper = new ManualAttendanceEntryHelper();
		EmpAttendance manualAttendance =manualAttendanceEntryTransaction.getEmployeeAttendance(manualAttendanceEntryForm.getId());
		EmpAttendanceBc attendanceBc=manualAttendanceEntryHelper.createBackUp(manualAttendance,"R",manualAttendanceEntryForm);
		return manualAttendanceEntryTransaction.reActivateAttendance(manualAttendanceEntryForm.getId(),manualAttendanceEntryForm.getUserId(),attendanceBc);
	}
	
}