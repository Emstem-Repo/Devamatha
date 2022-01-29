package com.kp.cms.handlers.admission;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.to.admission.PersonalDataTO;
import com.kp.cms.transactions.admission.IUploadMobileNumbersTransaction;
import com.kp.cms.transactionsimpl.admission.UploadMobileNumbersTransactionImpl;

public class UploadMobileNumbersHandler {
	/**
	 * Singleton object of uploadMobileNumbersHandler
	 */
	private static volatile UploadMobileNumbersHandler uploadMobileNumbersHandler = null;
	private static final Log log = LogFactory.getLog(UploadMobileNumbersHandler.class);
	private UploadMobileNumbersHandler() {
		
	}
	/**
	 * return singleton object of uploadMobileNumbersHandler.
	 * @return
	 */
	public static UploadMobileNumbersHandler getInstance() {
		if (uploadMobileNumbersHandler == null) {
			uploadMobileNumbersHandler = new UploadMobileNumbersHandler();
		}
		return uploadMobileNumbersHandler;
	}
	/**
	 * @param results
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public boolean addUploadedData(List<PersonalDataTO> results, String user) throws Exception {
		IUploadMobileNumbersTransaction transaction=new UploadMobileNumbersTransactionImpl();
		return transaction.uploadData(results,user);
	}
}
