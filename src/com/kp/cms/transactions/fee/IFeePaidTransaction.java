package com.kp.cms.transactions.fee;

import java.util.Date;
import java.util.List;
import java.util.Set;

import com.kp.cms.bo.admin.FeePayment;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.forms.fee.FeePaymentForm;
import com.kp.cms.to.fee.FeePaymentTO;

public interface IFeePaidTransaction {
	
	public void markAsPaid(Set<FeePaymentTO> feePaymentToSet, Date paidDate) throws Exception;
	//public  void markAsCancel(Set<Integer> ids) throws Exception;
	public  void markAsCancel(List<FeePaymentTO> ids) throws Exception;
	public  void updatePaidDateInDetailTable(Set<FeePaymentTO> feePaymentToSet, Date paidDate) throws Exception;
	public List<FeePayment> checkPreviousBalance(Student student,
			FeePaymentForm feePaymentForm)throws Exception;
	public void setFinancialYearId(FeePaymentForm feePaymentForm)throws Exception;
}
