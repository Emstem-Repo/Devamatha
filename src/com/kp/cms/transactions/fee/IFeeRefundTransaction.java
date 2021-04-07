package com.kp.cms.transactions.fee;

import java.util.List;

import com.kp.cms.bo.admin.FeePaymentMode;
import com.kp.cms.bo.fees.FeeRefund;
import com.kp.cms.forms.fee.FeeRefundForm;

public interface IFeeRefundTransaction {

	public List<Object[]> getStudentDetailsByChallanNo(FeeRefundForm refundForm) throws Exception;

	public List<FeePaymentMode> getPayModeList() throws Exception;

	public FeeRefund checkChallanNoAlreadyExist(FeeRefundForm refundForm)throws Exception;

	public boolean saveOrUpdateFeeRefundAmount(FeeRefund refund,String mode)throws Exception;

	public FeeRefund getFeeRefundDetailsById(int refundId)throws Exception;

}
