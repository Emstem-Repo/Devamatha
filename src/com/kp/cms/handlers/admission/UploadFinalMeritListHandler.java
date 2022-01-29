package com.kp.cms.handlers.admission;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.AdmAppln;
import com.kp.cms.bo.admin.AdmapplnAdditionalInfo;
import com.kp.cms.transactions.admission.IUploadFinalMeritListTxn;
import com.kp.cms.transactionsimpl.admission.UploadFinalMeritListTransactionImpl;

public class UploadFinalMeritListHandler {
private static final Log log = LogFactory.getLog(UploadFinalMeritListHandler.class);
	private static volatile UploadFinalMeritListHandler uploadFinalMeritListHandler = null;

	public static UploadFinalMeritListHandler getInstance() {
		if (uploadFinalMeritListHandler == null) {
			uploadFinalMeritListHandler = new UploadFinalMeritListHandler();
			return uploadFinalMeritListHandler;
		}
		return uploadFinalMeritListHandler;
	}
	/**
	 * 
	 * @return
	 */
	public Map<String, Integer> getCourseMap() {
		IUploadFinalMeritListTxn iUploadFinalMeritListTxn = UploadFinalMeritListTransactionImpl.getInstance();
		Map<String, Integer> courseMap = iUploadFinalMeritListTxn.getCourseByProgram();

		return courseMap;
	}	
	/**
	 * 
	 * @param admApplnList
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public boolean addUploadFinalMeritList(List<AdmAppln> admApplnList, String user,List<AdmapplnAdditionalInfo> admAdditionalList) throws Exception{
		log.info("call of addUploadFinalMeritList method in UploadFinalMeritListHandler class.");
		boolean isAdd = false;
		IUploadFinalMeritListTxn transaction = new UploadFinalMeritListTransactionImpl();
		isAdd = transaction.addFInalMeritUploaded(admApplnList, user,admAdditionalList);
		log.info("end of addUploadFinalMeritList method in UploadFinalMeritListHandler class.");
		return isAdd;
	}	
}
