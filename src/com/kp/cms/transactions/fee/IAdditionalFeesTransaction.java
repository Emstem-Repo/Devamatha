package com.kp.cms.transactions.fee;

import java.util.List;

import com.kp.cms.bo.admin.AdditionalFees;
import com.kp.cms.bo.admin.FeesClassFee;
import com.kp.cms.bo.admin.FeesFeeDetails;
import com.kp.cms.forms.fee.AdditionalFeesForm;

public interface IAdditionalFeesTransaction {

	public boolean uploadAdditionalFees(List<AdditionalFees> additionalFees,AdditionalFeesForm additionalFeesForm)throws Exception;

	public boolean uploadFeeDetails(List<FeesFeeDetails> feeDetails,AdditionalFeesForm additionalFeesForm)throws Exception;

	public boolean uploadClassFees(List<FeesClassFee> classFees)throws Exception;

	public boolean isDuplicateYear(AdditionalFeesForm additionalFeesForm) throws Exception;

}
