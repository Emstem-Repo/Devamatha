package com.kp.cms.transactions.fee;

import java.util.List;

import com.kp.cms.bo.admin.FeeFinancialYear;
import com.kp.cms.bo.admin.FeePayment;
import com.kp.cms.bo.admin.FeePaymentMode;

public interface IInstallmentPaymentTransaction {

	public List<FeePaymentMode> getAllPaymentType()throws Exception;

	public List<FeePayment>  getStudentPaymentDetails(String createSearchCriteria)throws Exception;

	public boolean submitInstallMentPaymentDetails(
			List<FeePayment> installmentPaymentBOList)throws Exception;

}
