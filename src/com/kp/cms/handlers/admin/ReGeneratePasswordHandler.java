package com.kp.cms.handlers.admin;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.StudentLogin;
import com.kp.cms.forms.admin.ReGeneratePasswordForm;
import com.kp.cms.helpers.admin.ReGeneratePasswordHelper;
import com.kp.cms.transactions.admin.IReGeneratePasswordTransaction;
import com.kp.cms.transactionsimpl.admin.ReGeneratePasswordTransactionImpl;

public class ReGeneratePasswordHandler {
	/**
	 * Singleton object of ReGeneratePasswordHandler
	 */
	private static volatile ReGeneratePasswordHandler reGeneratePasswordHandler = null;
	private static final Log log = LogFactory.getLog(ReGeneratePasswordHandler.class);
	private ReGeneratePasswordHandler() {
		
	}
	/**
	 * return singleton object of ReGeneratePasswordHandler.
	 * @return
	 */
	public static ReGeneratePasswordHandler getInstance() {
		if (reGeneratePasswordHandler == null) {
			reGeneratePasswordHandler = new ReGeneratePasswordHandler();
		}
		return reGeneratePasswordHandler;
	}
	/**
	 * @param registerNoList
	 * @return
	 * @throws Exception
	 */
	public List<StudentLogin> getStudentLoginsByRegisterNo(
			ArrayList<String> registerNoList)  throws Exception{
		IReGeneratePasswordTransaction transaction=new ReGeneratePasswordTransactionImpl();
		return transaction.getStudentLogins(registerNoList);
	}
	/**
	 * @param registerNoList
	 * @return
	 * @throws Exception
	 */
	public List<String> getStudentsByRegisterNo(ArrayList<String> registerNoList) throws Exception {
		IReGeneratePasswordTransaction transaction=new ReGeneratePasswordTransactionImpl();
		return transaction.getStudentsByRegisterNo(registerNoList);
	}
	/**
	 * @param studentLogins
	 * @param reGeneratePasswordForm
	 * @throws Exception
	 */
	public boolean updatePassword(List<StudentLogin> studentLogins,ReGeneratePasswordForm reGeneratePasswordForm) throws Exception {
		List<StudentLogin> finalStudentLogins=ReGeneratePasswordHelper.getInstance().getFinalStudentLogins(studentLogins,reGeneratePasswordForm);
		IReGeneratePasswordTransaction transaction=new ReGeneratePasswordTransactionImpl();
		return transaction.updateStudentLogin(finalStudentLogins);
	}
}
