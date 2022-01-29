package com.kp.cms.handlers.fee;

import java.util.List;

import com.kp.cms.bo.admin.AdditionalFees;
import com.kp.cms.bo.admin.FeesClassFee;
import com.kp.cms.bo.admin.FeesFeeDetails;
import com.kp.cms.forms.fee.AdditionalFeesForm;
import com.kp.cms.helpers.fee.AdditionalFeesHelper;
import com.kp.cms.to.fee.AdditionalFeesTo;
import com.kp.cms.to.fee.FeesClassFeeTo;
import com.kp.cms.to.fee.FeesDetailsFeeTo;
import com.kp.cms.transactions.fee.IAdditionalFeesTransaction;
import com.kp.cms.transactionsimpl.fee.AdditionalFeesTxnImpl;

public class AdditionalFeesHandler {
 private static volatile AdditionalFeesHandler additionalFeesHandler = null;
 	public static AdditionalFeesHandler getInstance(){
 		if(additionalFeesHandler == null){
 			additionalFeesHandler = new AdditionalFeesHandler();
 			return additionalFeesHandler;
 		}
 		return additionalFeesHandler;
 	}
 	IAdditionalFeesTransaction transaction = AdditionalFeesTxnImpl.getInstance();
	/**
	 * @param additionalFeesList
	 * @return
	 * @throws Exception
	 */
	public boolean uploadAdditionalFees( List<AdditionalFeesTo> additionalFeesList,AdditionalFeesForm additionalFeesForm)throws Exception {
		
		List<AdditionalFees> additionalFees = AdditionalFeesHelper.getInstance().convertToTOBo(additionalFeesList);
		return transaction.uploadAdditionalFees(additionalFees,additionalFeesForm);
	}
	/**
	 * @param detailsFeeTos
	 * @return
	 */
	public boolean uploadDetailsFee(List<FeesDetailsFeeTo> detailsFeeTos,AdditionalFeesForm additionalFeesForm)throws Exception {
		List<FeesFeeDetails> feeDetails = AdditionalFeesHelper.getInstance().populateTOToBO(detailsFeeTos);
		return transaction.uploadFeeDetails(feeDetails,additionalFeesForm);
	}
	/**
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public boolean uploadClassFees(List<FeesClassFeeTo> list)throws Exception {
		List<FeesClassFee> classFees = AdditionalFeesHelper.getInstance().convertToTOBo1(list);
		return transaction.uploadClassFees(classFees);
	}
	public boolean checkDuplicate(AdditionalFeesForm additionalFeesForm) throws Exception {
		return transaction.isDuplicateYear(additionalFeesForm);
	}
}
