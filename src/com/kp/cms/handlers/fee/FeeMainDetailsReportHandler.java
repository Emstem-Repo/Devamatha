package com.kp.cms.handlers.fee;

import java.util.List;

import com.kp.cms.bo.admin.AdmFeeMain;
import com.kp.cms.forms.fee.FeeMainDetailsReportForm;
import com.kp.cms.helpers.fee.FeeMainDetailsReportHelper;
import com.kp.cms.to.fee.FeeMainDetailsTO;
import com.kp.cms.transactions.fee.IFeeMainDetailsTxn;
import com.kp.cms.transactionsimpl.fee.FeeMainDetailsReportTxnImpl;


public class FeeMainDetailsReportHandler {
	IFeeMainDetailsTxn feeMainDetailsTxn=FeeMainDetailsReportTxnImpl.getInstance();
	private static volatile FeeMainDetailsReportHandler feeMainDetailsHandler=null;
	public static FeeMainDetailsReportHandler getInstance() {
		   if(feeMainDetailsHandler == null ){
			   feeMainDetailsHandler = new FeeMainDetailsReportHandler();
			   return feeMainDetailsHandler;
		   }
		   return feeMainDetailsHandler;
	}
	public List<FeeMainDetailsTO> getFeeMainDetails(FeeMainDetailsReportForm feeMainDetailsReportForm)throws Exception{
		List<AdmFeeMain> feeMainList=feeMainDetailsTxn.getFeeMainDetails(feeMainDetailsReportForm);
		List<FeeMainDetailsTO> feeMainTOList=FeeMainDetailsReportHelper.getInstance().convertFeeMainDetailsBoToTO(feeMainList);
		return feeMainTOList;
	}
}
