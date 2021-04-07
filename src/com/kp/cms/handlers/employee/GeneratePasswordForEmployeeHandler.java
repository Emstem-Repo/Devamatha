package com.kp.cms.handlers.employee;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.Users;
import com.kp.cms.forms.employee.GeneratePasswordForEmployeeForm;
import com.kp.cms.helpers.employee.GeneratePasswordForEmployeeHelper;
import com.kp.cms.transactions.admin.IGeneratePasswordTransaction;
import com.kp.cms.transactions.employee.IGeneratePasswordForEmployeeTransaction;
import com.kp.cms.transactionsimpl.employee.GeneratePasswordForEmployeeTransactionimpl;

public class GeneratePasswordForEmployeeHandler {
	/**
	 * Singleton object of GeneratePasswordForEmployeeHandler
	 */
	private static volatile GeneratePasswordForEmployeeHandler generatePasswordForEmployeeHandler = null;
	private static final Log log = LogFactory.getLog(GeneratePasswordForEmployeeHandler.class);
	private GeneratePasswordForEmployeeHandler() {
		
	}
	/**
	 * return singleton object of GeneratePasswordForEmployeeHandler.
	 * @return
	 */
	public static GeneratePasswordForEmployeeHandler getInstance() {
		if (generatePasswordForEmployeeHandler == null) {
			generatePasswordForEmployeeHandler = new GeneratePasswordForEmployeeHandler();
		}
		return generatePasswordForEmployeeHandler;
	}
	/**
	 * updating password for the input criteria
	 * @param gnForm
	 * @return
	 * @throws Exception
	 */
	public boolean updatePassword(GeneratePasswordForEmployeeForm gnForm) throws Exception {
		boolean isUpdated=false;
		String userId=gnForm.getUserId();
		String query=GeneratePasswordForEmployeeHelper.getInstance().getQuery(gnForm);
		IGeneratePasswordForEmployeeTransaction txn=GeneratePasswordForEmployeeTransactionimpl.getInstance();
		isUpdated=txn.updatePassword(query,userId);
		return isUpdated;
	}
}
