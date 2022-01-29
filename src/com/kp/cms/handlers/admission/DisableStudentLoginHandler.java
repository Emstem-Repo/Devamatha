package com.kp.cms.handlers.admission;

import java.util.List;

import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.admission.DisableStudentLoginForm;
import com.kp.cms.helpers.admission.DisableStudentLoginHelper;
import com.kp.cms.transactions.admission.IDisableStudentLoginTransaction;
import com.kp.cms.transactionsimpl.admission.DisableStudentLoginTxnImpl;

public class DisableStudentLoginHandler {
	public static volatile DisableStudentLoginHandler disableStudentLoginHandler = null;
	public static DisableStudentLoginHandler getInstance(){
	 if(disableStudentLoginHandler == null){
		 disableStudentLoginHandler = new DisableStudentLoginHandler();
		 return disableStudentLoginHandler;
	 }
	 return disableStudentLoginHandler;
 }
	/**
	 * @param disableStudentLoginForm
	 * @return
	 * @throws Exception
	 */
	public boolean searchDisableStudentLogin( DisableStudentLoginForm disableStudentLoginForm) throws Exception{
		boolean isSubmit = false;
		IDisableStudentLoginTransaction transaction = DisableStudentLoginTxnImpl.getInstance();
		StringBuffer query = DisableStudentLoginHelper.getInstance().getQuery(disableStudentLoginForm);
		List<String> studentIds=transaction.getStudentDetails(query); 
		if(!studentIds.isEmpty()){
			isSubmit = transaction.checkForDisableStudentLogin(studentIds,disableStudentLoginForm);
		}else{
			throw new ApplicationException();
		}
		return isSubmit;
	}
}
