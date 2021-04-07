package com.kp.cms.handlers.admission;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.helpers.admission.UploadSecondLanguageHelper;
import com.kp.cms.to.admission.PersonalDataTO;
import com.kp.cms.transactions.admission.IUploadSecondLanguageTransaction;
import com.kp.cms.transactionsimpl.admission.UploadSecondLanguageTransactionImpl;

public class UploadSecondLanguageHandler {
	/**
	 * Singleton object of UploadSecondLanguageHandler
	 */
	private static volatile UploadSecondLanguageHandler uploadSecondLanguageHandler = null;
	private static final Log log = LogFactory.getLog(UploadSecondLanguageHandler.class);
	private UploadSecondLanguageHandler() {
		
	}
	/**
	 * return singleton object of uploadSecondLanguageHandler.
	 * @return
	 */
	public static UploadSecondLanguageHandler getInstance() {
		if (uploadSecondLanguageHandler == null) {
			uploadSecondLanguageHandler = new UploadSecondLanguageHandler();
		}
		return uploadSecondLanguageHandler;
	}
	/**
	 * @param applicationYear
	 * @param courseId
	 * @return
	 */
	public Map<String, Integer> getAppDetails(int applicationYear, int courseId) throws Exception{
		IUploadSecondLanguageTransaction transaction=new UploadSecondLanguageTransactionImpl();
		return transaction.getAppDetails(UploadSecondLanguageHelper.getInstance().getQuery(applicationYear,courseId));
	}
	/**
	 * @param results
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public boolean addUploadedData(List<PersonalDataTO> results, String user) throws Exception{
		IUploadSecondLanguageTransaction transaction=new UploadSecondLanguageTransactionImpl();
		return transaction.updatePersonalData(results,user);
	}
	/**
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> getSeondLanguage() throws Exception{
		IUploadSecondLanguageTransaction transaction=new UploadSecondLanguageTransactionImpl();
		return transaction.getSeondLanguages();
	}
}
