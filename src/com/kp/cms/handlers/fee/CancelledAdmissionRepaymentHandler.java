package com.kp.cms.handlers.fee;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.AdmAppln;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admin.StudentCancellationDetails;
import com.kp.cms.forms.fee.CancelledAdmissionRepaymentForm;
import com.kp.cms.helpers.fee.CancelledAdmissionRepaymentHelper;
import com.kp.cms.to.admission.AdmApplnTO;
import com.kp.cms.transactions.fee.ICancelledAdmissionRepaymentTransaction;
import com.kp.cms.transactionsimpl.fee.CancelledAdmissionRepaymentTransactionimpl;

public class CancelledAdmissionRepaymentHandler {
	/**
	 * Singleton object of CancelledAdmissionRepaymentHandler
	 */
	private static volatile CancelledAdmissionRepaymentHandler cancelledAdmissionRepaymentHandler = null;
	private static final Log log = LogFactory.getLog(CancelledAdmissionRepaymentHandler.class);
	private CancelledAdmissionRepaymentHandler() {
		
	}
	/**
	 * return singleton object of CancelledAdmissionRepaymentHandler.
	 * @return
	 */
	public static CancelledAdmissionRepaymentHandler getInstance() {
		if (cancelledAdmissionRepaymentHandler == null) {
			cancelledAdmissionRepaymentHandler = new CancelledAdmissionRepaymentHandler();
		}
		return cancelledAdmissionRepaymentHandler;
	}
	/**
	 * returns AdmApplnTO with canceled details if student is canceled  
	 * @param cancelledAdmissionRepaymentForm
	 * @return
	 * @throws Exception
	 */
	public AdmApplnTO getStudentCanceledDetails(
			CancelledAdmissionRepaymentForm cancelledAdmissionRepaymentForm) throws Exception {
		ICancelledAdmissionRepaymentTransaction txn=CancelledAdmissionRepaymentTransactionimpl.getInstance();
			Student stuBo=txn.getStudentBO(cancelledAdmissionRepaymentForm);
			AdmApplnTO admTo=CancelledAdmissionRepaymentHelper.getInstance().convertBOtoTO(stuBo,cancelledAdmissionRepaymentForm);
		return admTo;
	}
	/**
	 * redirects the form to impl to save the details to database
	 * @param cancelledAdmissionRepaymentForm
	 * @return
	 * @throws Exception
	 */
	public boolean saveRepaymentDetails(CancelledAdmissionRepaymentForm cancelledAdmissionRepaymentForm) throws Exception{
		boolean saved=false;
		ICancelledAdmissionRepaymentTransaction txn=CancelledAdmissionRepaymentTransactionimpl.getInstance();
		StudentCancellationDetails studentCancelDetailsBO=CancelledAdmissionRepaymentHelper.convertTOtoBO(cancelledAdmissionRepaymentForm);
		saved=txn.saveCancelledRepaymentDetails(studentCancelDetailsBO);
		return saved;
	}
}
