package com.kp.cms.transactions.fee;

import java.util.List;

import com.kp.cms.bo.admin.AdmFeeMain;
import com.kp.cms.bo.admin.FeesFeeDetails;
import com.kp.cms.forms.fee.FeeMainDetailsReportForm;

public interface IFeeMainDetailsTxn {
	public List<AdmFeeMain> getFeeMainDetails(FeeMainDetailsReportForm feeMainDetailsReportForm)throws Exception;
	public List<FeesFeeDetails> getFeeDetailsWithBillNo(int billNo)throws Exception;
}
