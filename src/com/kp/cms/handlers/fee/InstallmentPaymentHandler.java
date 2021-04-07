package com.kp.cms.handlers.fee;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.FeePayment;
import com.kp.cms.bo.admin.FeePaymentMode;
import com.kp.cms.forms.fee.InstallmentPaymentForm;
import com.kp.cms.helpers.fee.InstallmentPaymentHelper;
import com.kp.cms.to.admin.SingleFieldMasterTO;
import com.kp.cms.to.fee.InstallmentPaymentTO;
import com.kp.cms.transactions.fee.IInstallmentPaymentTransaction;
import com.kp.cms.transactionsimpl.fee.InstallmentPaymentTransactionImpl;

public class InstallmentPaymentHandler {
	private static final Log log = LogFactory.getLog(InstallmentPaymentHandler.class);
	public static InstallmentPaymentHandler installmentPaymentHandler = null;

	private InstallmentPaymentHandler(){
		
	}
	
	/**
	 * 
	 * @returns a single instance (Singleton)every time. 
	 */
	public static InstallmentPaymentHandler getInstance() {
		if (installmentPaymentHandler == null) {
			installmentPaymentHandler = new InstallmentPaymentHandler();
		}
		return installmentPaymentHandler;
	}
	/**
	 * 
	 * @return all payment types
	 * @throws Exception
	 */
	public List<SingleFieldMasterTO> getAllPaymentType() throws Exception{
		log.info("Entering into getAllPaymentType of InstallmentPaymentHandler");
		IInstallmentPaymentTransaction transaction = new InstallmentPaymentTransactionImpl();
		List<FeePaymentMode> paymentTypeBOList = transaction.getAllPaymentType();
		log.info("Leaving into getAllPaymentType of InstallmentPaymentHandler");
		return InstallmentPaymentHelper.getInstance().copyFeePaymentBOToTO(paymentTypeBOList);
	}
	/**
	 * 
	 * @param paymentForm
	 * @returns the old fee payment details based on selection made in 1st screen
	 * @throws Exception
	 */
	public List<InstallmentPaymentTO> getStudentPaymentDetails(InstallmentPaymentForm paymentForm)throws Exception {
		log.info("Entering into getStudentPaymentDetails of InstallmentPaymentHandler");
		IInstallmentPaymentTransaction transaction = new InstallmentPaymentTransactionImpl();
		List<FeePayment> feePaymentList = transaction.getStudentPaymentDetails(InstallmentPaymentHelper.getInstance().createSearchCriteria(paymentForm));;
		log.info("Leaving into getStudentPaymentDetails of InstallmentPaymentHandler");
		return InstallmentPaymentHelper.getInstance().populateFeePaymentBOToTO(feePaymentList, paymentForm);
	}
	/**
	 * 
	 * @param paymentForm
	 * @return submits the installment payment details
	 * @throws Exception
	 */
	public boolean submitInstallMentPaymentDetails(
			InstallmentPaymentForm paymentForm)throws Exception {
		log.info("Entering into submitInstallMentPaymentDetails of InstallmentPaymentHandler");
		IInstallmentPaymentTransaction transaction = new InstallmentPaymentTransactionImpl();
		List<FeePayment> installmentPaymentBOList = InstallmentPaymentHelper.getInstance().populateInstallMentPaymentBOS(paymentForm);
		log.info("Leaving into submitInstallMentPaymentDetails of InstallmentPaymentHandler");
		return transaction.submitInstallMentPaymentDetails(installmentPaymentBOList);
	}

	
	
}
