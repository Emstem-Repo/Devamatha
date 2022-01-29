package com.kp.cms.handlers.exam;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.exam.ExamStudentOverallInternalMarkDetailsBO;
import com.kp.cms.helpers.exam.UploadInternalOverAllHelper;
import com.kp.cms.to.exam.UploadInternalOverAllTo;
import com.kp.cms.transactions.exam.IUploadInternalOverAllTransaction;
import com.kp.cms.transactionsimpl.exam.UploadInternalOverAllTransactionImpl;

public class UploadInternalOverAllHandler {
	IUploadInternalOverAllTransaction transaction=new UploadInternalOverAllTransactionImpl();
	/**
	 * Singleton object of UploadInternalOverAllHandler
	 */
	private static volatile UploadInternalOverAllHandler uploadInternalOverAllHandler = null;
	private static final Log log = LogFactory.getLog(UploadInternalOverAllHandler.class);
	private UploadInternalOverAllHandler() {
		
	}
	/**
	 * return singleton object of UploadInternalOverAllHandler.
	 * @return
	 */
	public static UploadInternalOverAllHandler getInstance() {
		if (uploadInternalOverAllHandler == null) {
			uploadInternalOverAllHandler = new UploadInternalOverAllHandler();
		}
		return uploadInternalOverAllHandler;
	}
	/**
	 * getting all student registerno who is not cancelled 
	 * @return
	 * @throws Exception
	 */
	public Map<String, Integer> getStudentMap() throws Exception {
		return transaction.getStudentMap();
	}
	/**
	 * get classes with year based
	 * @return
	 * @throws Exception
	 */
	public Map<String, Integer> getClassMap() throws Exception {
		return transaction.getclassMap();
	}
	/**
	 * get the subect map using subject code and id
	 * @return
	 */
	public Map<String, Integer> getSubjectCodeMap() throws Exception {
		return transaction.getSubjectCodeMap();
	}
	/**
	 * @return
	 * @throws Exception
	 */
	public Map<String, Integer> getExamMap() throws Exception {
		return transaction.getExamMap();
	}
	/**
	 * @param result
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public boolean addUploadedData(List<UploadInternalOverAllTo> result,
			String user,String action) throws Exception {
		List<ExamStudentOverallInternalMarkDetailsBO> list=UploadInternalOverAllHelper.getInstance().convertToListToBOList(result,user,action);
		return transaction.saveExamStudentOverallInternalMarkDetailsBOList(list,action);
	}
}
