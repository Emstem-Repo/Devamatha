package com.kp.cms.handlers.exam;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.exam.ExamStudentFinalMarkDetailsBO;
import com.kp.cms.helpers.exam.UploadExamStudentFinalMarksHelper;
import com.kp.cms.to.exam.UploadInternalOverAllTo;
import com.kp.cms.transactions.exam.IUploadExamStudentFinalMarksTransaction;
import com.kp.cms.transactionsimpl.exam.UploadExamStudentFinalMarksTransactionImpl;

public class UploadExamStudentFinalMarksHandler {
	
	IUploadExamStudentFinalMarksTransaction transaction=new UploadExamStudentFinalMarksTransactionImpl();
	/**
	 * Singleton object of UploadExamStudentFinalMarksHandler
	 */
	private static volatile UploadExamStudentFinalMarksHandler uploadExamStudentFinalMarksHandler = null;
	private static final Log log = LogFactory.getLog(UploadExamStudentFinalMarksHandler.class);
	private UploadExamStudentFinalMarksHandler() {
		
	}
	/**
	 * return singleton object of UploadExamStudentFinalMarksHandler.
	 * @return
	 */
	public static UploadExamStudentFinalMarksHandler getInstance() {
		if (uploadExamStudentFinalMarksHandler == null) {
			uploadExamStudentFinalMarksHandler = new UploadExamStudentFinalMarksHandler();
		}
		return uploadExamStudentFinalMarksHandler;
	}
	public boolean addUploadedData(List<UploadInternalOverAllTo> result,
			String user, String action) throws Exception {

		List<ExamStudentFinalMarkDetailsBO> list=UploadExamStudentFinalMarksHelper.getInstance().convertToListToBOList(result,user,action);
		return transaction.saveExamStudentOverallInternalMarkDetailsBOList(list,action);
	}
}
