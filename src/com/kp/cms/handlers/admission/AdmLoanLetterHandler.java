package com.kp.cms.handlers.admission;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.Student;
import com.kp.cms.forms.admission.AdmLoanLetterForm;
import com.kp.cms.helpers.admission.AdmLoanLetterHelper;
import com.kp.cms.to.admission.AdmLoanLetterDetailsTO;
import com.kp.cms.transactions.admission.IAdmLoanLetterTransaction;
import com.kp.cms.transactionsimpl.admission.AdmLoanLetterTransactionImpl;

public class AdmLoanLetterHandler {
	
	private static volatile AdmLoanLetterHandler admLoanLetterHandler = null;
	private static final Log log = LogFactory.getLog(AdmLoanLetterHandler.class);
	
	private AdmLoanLetterHandler() {
		
	}
	
	public static AdmLoanLetterHandler getInstance() {
		if (admLoanLetterHandler == null) {
			admLoanLetterHandler = new AdmLoanLetterHandler();
		}
		return admLoanLetterHandler;
	}
	
	IAdmLoanLetterTransaction txn= AdmLoanLetterTransactionImpl.getInstance();
	
	/**
	 * passing the form to impl to get the student BO
	 * @param letterForm
	 * @return
	 * @throws Exception
	 */
	public List<AdmLoanLetterDetailsTO> getStudentsInfo(AdmLoanLetterForm letterForm) throws Exception {
		String query = AdmLoanLetterHelper.getInstance().getQuery(letterForm);
		List<Student> applnList=txn.getStudentListBO(query);
		List<AdmLoanLetterDetailsTO> stuTo=AdmLoanLetterHelper.getInstance().convertBostoTO(applnList);
		
		return stuTo;
	}
	
	
	/**
	 * passing the selected students id to impl to set the flag
	 * @param letterForm
	 * @return
	 * @throws Exception
	 */
	public boolean addLoan(AdmLoanLetterForm letterForm) throws Exception{
		boolean flagSet=txn.addLoan(letterForm);
		return flagSet;
	}

}
