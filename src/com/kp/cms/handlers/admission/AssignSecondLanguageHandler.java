package com.kp.cms.handlers.admission;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.Student;
import com.kp.cms.forms.admission.AssignSecondLanguageForm;
import com.kp.cms.helpers.admission.AssignSecondLanguageHelper;
import com.kp.cms.to.admission.AssignSecondLanguageTo;
import com.kp.cms.transactions.admission.IAssignSecondLanguageTransaction;
import com.kp.cms.transactionsimpl.admission.AssignSecondLanguageTransactionImpl;

public class AssignSecondLanguageHandler {
	/**
	 * Singleton object of assignSecondLanguageHandler
	 */
	private static volatile AssignSecondLanguageHandler assignSecondLanguageHandler = null;
	private static final Log log = LogFactory.getLog(AssignSecondLanguageHandler.class);
	private AssignSecondLanguageHandler() {
		
	}
	/**
	 * return singleton object of assignSecondLanguageHandler.
	 * @return
	 */
	public static AssignSecondLanguageHandler getInstance() {
		if (assignSecondLanguageHandler == null) {
			assignSecondLanguageHandler = new AssignSecondLanguageHandler();
		}
		return assignSecondLanguageHandler;
	}
	public List<AssignSecondLanguageTo> getListOfStudents(AssignSecondLanguageForm assignSecondLanguageForm) throws Exception{
		IAssignSecondLanguageTransaction transaction=new AssignSecondLanguageTransactionImpl();
		List<Student> list=transaction.getDataForQuery(AssignSecondLanguageHelper.getInstance().getQuery(assignSecondLanguageForm));
		return AssignSecondLanguageHelper.getInstance().convertBotoToList(list);
	}
}
