package com.kp.cms.handlers.exam;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.transactions.exam.IUploadStudentSubjectHistoryTransaction;
import com.kp.cms.transactionsimpl.exam.UploadStudentSubjectHistoryTransactionImpl;

public class UploadStudentSubjectHistoryHandler {
	/**
	 * Singleton object of UploadStudentSubjectHistoryHandler
	 */
	private static volatile UploadStudentSubjectHistoryHandler uploadStudentSubjectHistoryHandler = null;
	private static final Log log = LogFactory.getLog(UploadStudentSubjectHistoryHandler.class);
	private UploadStudentSubjectHistoryHandler() {
		
	}
	/**
	 * return singleton object of UploadStudentSubjectHistoryHandler.
	 * @return
	 */
	public static UploadStudentSubjectHistoryHandler getInstance() {
		if (uploadStudentSubjectHistoryHandler == null) {
			uploadStudentSubjectHistoryHandler = new UploadStudentSubjectHistoryHandler();
		}
		return uploadStudentSubjectHistoryHandler;
	}
	/**
	 * @param resultMap
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public boolean addUploadedData(Map<String, List<Integer>> resultMap,
			String user) throws Exception {
		IUploadStudentSubjectHistoryTransaction transaction=new UploadStudentSubjectHistoryTransactionImpl();
		return transaction.addUploadedDate(resultMap,user);
	}
}
