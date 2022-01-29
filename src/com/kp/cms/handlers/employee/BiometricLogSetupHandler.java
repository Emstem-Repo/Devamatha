package com.kp.cms.handlers.employee;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.EmpBiometricLogSetupBO;
import com.kp.cms.forms.employee.BiometricLogSetupForm;
import com.kp.cms.helpers.employee.BiometricLogHelper;
import com.kp.cms.to.employee.EmpBiometricSetUpTo;
import com.kp.cms.transactions.employee.IBiometricLogSetupTransaction;
import com.kp.cms.transactionsimpl.employee.BiometricLogSetupTransactionImpl;

public class BiometricLogSetupHandler {
	private static final Log log = LogFactory
			.getLog(BiometricLogSetupHandler.class);
	public static volatile BiometricLogSetupHandler objHandler = null;

	public static BiometricLogSetupHandler getInstance() {
		if (objHandler == null) {
			objHandler = new BiometricLogSetupHandler();
			return objHandler;
		}
		return objHandler;
	}

	/**
	 * @param objForm
	 * @return
	 * @throws Exception
	 */
	public BiometricLogSetupForm getDetails(BiometricLogSetupForm objForm,int id)
			throws Exception {
		IBiometricLogSetupTransaction iTransaction = BiometricLogSetupTransactionImpl
				.getInstance();
		EmpBiometricLogSetupBO BO = iTransaction.getBiometricLogDetailsById(id);
		if (BO != null) {
			objForm.setId(BO.getId());
			objForm.setTerminalId(BO.getTerminalId());
			objForm.setFingerPrintId(BO.getFingerPrintId());
			objForm.setEmployeeCode(BO.getEmployeeCode());
			objForm.setEmployeeName(BO.getEmployeeName());
			objForm.setDatetime(BO.getDatetime());

			objForm.setFunctionkey(BO.getFunctionkey());
			objForm.setStatus(BO.getStatus());
			objForm.setDelimitedWith(BO.getDelimitedWith());
			objForm.setDateFormat(BO.getDateFormat());
			objForm.setTextFilePath(BO.getTextFilePath());
			objForm.setTestCode(BO.getTestCode());	
			objForm.setDummyTerminalId(BO.getTerminalId());
			objForm.setDummyFingerPrintId(BO.getFingerPrintId());
			objForm.setDummyEmployeeCode(BO.getEmployeeCode());
			objForm.setDummyEmployeeName(BO.getEmployeeName());
			objForm.setDummyDatetime(BO.getDatetime());
			objForm.setDummyTestCode(BO.getTestCode());
			objForm.setDummyFunctionkey(BO.getFunctionkey());
			objForm.setDummyStatus(BO.getStatus());
			objForm.setDummyDelimitedWith(BO.getDelimitedWith());
			objForm.setDummyDateFormat(BO.getDateFormat());

		}
		return objForm;
	}

	/**
	 * @param objForm
	 * @return
	 * @throws Exception
	 */
	public boolean addBiometricLog(BiometricLogSetupForm objForm)
			throws Exception {
		log.debug("inside addWorkDiary");
		IBiometricLogSetupTransaction iTransaction = BiometricLogSetupTransactionImpl
				.getInstance();
		boolean isAdded = false;

		String mode = "add";
		isAdded = iTransaction.addBiometricLog(BiometricLogHelper.getInstance()
				.copyDataFromFormToBO(objForm), objForm.getId());
		log.debug("leaving addWorkDiary");
		return isAdded;
	}

	/**
	 * @return
	 * @throws Exception
	 */
	public List<EmpBiometricSetUpTo> getBiometricList() throws Exception{
		IBiometricLogSetupTransaction iTransaction = BiometricLogSetupTransactionImpl
		.getInstance();
		List<EmpBiometricLogSetupBO> list=iTransaction.getBiometricLogDetails();
		return BiometricLogHelper.getInstance().convertBOListToToList(list);
	}

}
