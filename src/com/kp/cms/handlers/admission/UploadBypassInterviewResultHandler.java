package com.kp.cms.handlers.admission;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.to.admission.AdmApplnTO;
import com.kp.cms.transactions.admission.IUploadBypassInterviewTransaction;
import com.kp.cms.transactionsimpl.admission.UploadBypassInterviewTransactionImpl;

public class UploadBypassInterviewResultHandler {

	/**
	 * This method will return a unique instance of UploadInterviewResultHandler.
	 */
	private static volatile UploadBypassInterviewResultHandler bypassInterviewResultHandler = null;
	private UploadBypassInterviewResultHandler() {
	}
	private static final Log log = LogFactory.getLog(UploadBypassInterviewResultHandler.class);
	
	public static UploadBypassInterviewResultHandler getInstance() {
		
		if (bypassInterviewResultHandler == null) {
			bypassInterviewResultHandler = new UploadBypassInterviewResultHandler();
		}
		return bypassInterviewResultHandler;
	}
	
	public boolean addUploadBypassInterviewData(List<AdmApplnTO> interviewResult, String user) throws Exception{
		log.info("call of addUploadBypassInterviewData method in UploadBypassInterviewResultHandler class.");
		boolean isAdd = false;
		IUploadBypassInterviewTransaction transaction = UploadBypassInterviewTransactionImpl.getInstance(); 
		isAdd = transaction.addUploadedData(interviewResult, user);
		log.info("end of addUploadBypassInterviewData method in UploadBypassInterviewResultHandler class.");
		return isAdd;
	}
}