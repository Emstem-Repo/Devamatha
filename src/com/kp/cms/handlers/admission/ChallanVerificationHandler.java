package com.kp.cms.handlers.admission;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.FeePayment;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.forms.admission.ChallanVerificationForm;
import com.kp.cms.helpers.admission.ChallanVerificationHelper;
import com.kp.cms.to.admin.StudentTO;
import com.kp.cms.transactions.admission.IChallanVerificationTransaction;
import com.kp.cms.transactions.admission.IUncheckGeneratedSmartCardTransaction;
import com.kp.cms.transactionsimpl.admission.ChallanVerificationTransactionimpl;
import com.kp.cms.transactionsimpl.admission.UncheckGeneratedSmartCardTransactionimpl;

public class ChallanVerificationHandler {

	/**
	 * Singleton object of ChallanVerificationHandler
	 */
	private static volatile ChallanVerificationHandler challanVerificationHandler = null;
	private static final Log log = LogFactory.getLog(ChallanVerificationHandler.class);
	private ChallanVerificationHandler() {
		
	}
	/**
	 * return singleton object of ChallanVerificationHandler.
	 * @return
	 */
	public static ChallanVerificationHandler getInstance() {
		if (challanVerificationHandler == null) {
			challanVerificationHandler = new ChallanVerificationHandler();
		}
		return challanVerificationHandler;
	}
	
	IChallanVerificationTransaction txn= ChallanVerificationTransactionimpl.getInstance();
	/**
	 * passing the form to impl to get the student BO
	 * @param challanForm
	 * @return
	 * @throws Exception
	 */
	public List<StudentTO> getStudentsInfo(ChallanVerificationForm challanForm) throws Exception {
		String query = ChallanVerificationHelper.getInstance().getQuery(challanForm);
		List<FeePayment> feeList=txn.getStudentListBO(query);
		List<StudentTO> stuTo=ChallanVerificationHelper.getInstance().convertBostoTO(feeList);
		return stuTo;
	}
	/**
	 * passing the selected students id to impl to set the flag
	 * @param challanForm
	 * @return
	 * @throws Exception
	 */
	public boolean updateFlag(ChallanVerificationForm challanForm) throws Exception{
		boolean flagSet=txn.updateVerifyFlag(challanForm);
		return flagSet;
	}
	
}
