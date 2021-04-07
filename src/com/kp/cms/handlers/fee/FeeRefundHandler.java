package com.kp.cms.handlers.fee;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.kp.cms.bo.admin.FeePaymentMode;
import com.kp.cms.bo.fees.FeeRefund;
import com.kp.cms.forms.fee.FeeRefundForm;
import com.kp.cms.helpers.fee.FeeRefundHelper;
import com.kp.cms.transactions.fee.IFeeRefundTransaction;
import com.kp.cms.transactionsimpl.fee.FeeRefundTxnImpl;

public class FeeRefundHandler {
	
	IFeeRefundTransaction transaction = FeeRefundTxnImpl.getInstance();
    public static volatile FeeRefundHandler feeRefundHandler = null;
    
    
    /**
     * @return
     */
    public static FeeRefundHandler getInstance() {
		if (feeRefundHandler == null) {
			feeRefundHandler = new FeeRefundHandler();
			return feeRefundHandler;
		}
		return feeRefundHandler;
	}


	/**
	 * @param refundForm
	 * @return
	 * @throws Exception
	 */
	public boolean getStudentDetailsByChallanNo(FeeRefundForm refundForm) throws Exception {
		List<Object[]> paymentList= transaction.getStudentDetailsByChallanNo(refundForm);	
      return FeeRefundHelper.getInstance().setStudentDataToForm(refundForm,paymentList);
	}


	/**
	 * @return
	 * @throws Exception
	 */
	public Map<Integer, String> getPayMentModeMap() throws Exception {
		Map<Integer, String> paymentModeMap=new HashMap<Integer, String>();
		List<FeePaymentMode> paymentModeList=transaction.getPayModeList();
		if(paymentModeList!=null && !paymentModeList.isEmpty()){
			for (FeePaymentMode feePaymentMode : paymentModeList) {
				paymentModeMap.put(feePaymentMode.getId(), feePaymentMode.getName());
			}
		}
		return paymentModeMap;
		
	}


	/**
	 * @param refundForm
	 * @return
	 * @throws Exception
	 */
	public FeeRefund checkChallanNoAlreadyExist(FeeRefundForm refundForm) throws Exception {
		FeeRefund refund= transaction.checkChallanNoAlreadyExist(refundForm);
		return refund;
	}


	/**
	 * @param refundForm
	 * @return
	 * @throws Exception
	 */
	public boolean saveOrUpdateFeeRefundAmount(FeeRefundForm refundForm,String mode) throws Exception {
		 FeeRefund refund=FeeRefundHelper.getInstance().convertFormToBo(refundForm,mode);
		 return transaction.saveOrUpdateFeeRefundAmount(refund,mode);
	}


	/**
	 * @param refundForm
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	public boolean deleteFeeRefundAmount(FeeRefundForm refundForm, String mode) throws Exception {
		FeeRefund refund=transaction.getFeeRefundDetailsById(Integer.parseInt(refundForm.getRefundId()));
		boolean isDeleted=false;
		if(refund!=null){
			refund.setIsActive(false);
			refund.setModifiedBy(refundForm.getUserId());
			refund.setLastModifiedDate(new Date());
			isDeleted=transaction.saveOrUpdateFeeRefundAmount(refund, mode);
		}
		return isDeleted;
	}


	/**
	 * @param refundForm
	 * @return
	 * @throws Exception
	 */
	public boolean getRefunDetailsIfExist(FeeRefundForm refundForm)throws Exception {
		FeeRefund refund= transaction.checkChallanNoAlreadyExist(refundForm);
		boolean isExist=false;
		if(refund!=null){
			FeeRefundHelper.getInstance().convertBoToForm(refund, refundForm);
			isExist=true;
		}
		return isExist;
		
	}


}
