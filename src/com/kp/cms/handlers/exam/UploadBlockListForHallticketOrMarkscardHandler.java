package com.kp.cms.handlers.exam;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.exam.ExamBlockUnblockHallTicketBO;
import com.kp.cms.forms.exam.UploadBlockListForHallticketOrMarkscardForm;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.helpers.exam.UploadBlockListForHallticketOrMarkscardHelper;
import com.kp.cms.to.exam.UploadBlockListForHallticketOrMarkscardTo;
import com.kp.cms.transactions.exam.IUploadBlockListForHallticketOrMarkscardTransaction;
import com.kp.cms.transactionsimpl.exam.UploadBlockListForHallticketOrMarkscardTransactionsImpl;

public class UploadBlockListForHallticketOrMarkscardHandler {

	private static volatile UploadBlockListForHallticketOrMarkscardHandler uploadBlockListForHallticketOrMarkscardHandler = null;
	private static final Log log = LogFactory.getLog(UploadBlockListForHallticketOrMarkscardHandler.class);
	public static UploadBlockListForHallticketOrMarkscardHandler getInstance() {
		if (uploadBlockListForHallticketOrMarkscardHandler == null) {
			uploadBlockListForHallticketOrMarkscardHandler = new UploadBlockListForHallticketOrMarkscardHandler();
		}
		return uploadBlockListForHallticketOrMarkscardHandler;
	}
	
	/**
	 * @return
	 * @throws Exception
	 */
	public Map<String,Integer> getClassIdByClassNameAndYear(String year)throws Exception {
		IUploadBlockListForHallticketOrMarkscardTransaction uploadTransaction = UploadBlockListForHallticketOrMarkscardTransactionsImpl.getInstance();
		return uploadTransaction.getClassIdByClassNameAndYear(year);
	}

	public Map<String,Integer>  getStudentIdByStudentRegNum(String year)throws Exception {
		IUploadBlockListForHallticketOrMarkscardTransaction uploadTransaction = UploadBlockListForHallticketOrMarkscardTransactionsImpl.getInstance();
		return uploadTransaction.getStudentIdByStudentRegNum(year);
	}
	
	public boolean addUploadBlockListForHallticketOrMarkscard(List<UploadBlockListForHallticketOrMarkscardTo> results,UploadBlockListForHallticketOrMarkscardForm objform,Map<String,Integer> registerNumMap )throws Exception {
		Map<Integer,String> classMap = CommonAjaxHandler.getInstance().getClasesByExamName(objform.getExamName());
		Map<Integer,ExamBlockUnblockHallTicketBO> map=UploadBlockListForHallticketOrMarkscardHelper.getInstance().convertToToBo(results,objform,classMap);
		IUploadBlockListForHallticketOrMarkscardTransaction uploadTransaction = UploadBlockListForHallticketOrMarkscardTransactionsImpl.getInstance();
		return uploadTransaction.uploadBlockListForHallticketOrMarkscard(map,objform );
	}
	
	public ExamBlockUnblockHallTicketBO getExamBo(UploadBlockListForHallticketOrMarkscardTo results,UploadBlockListForHallticketOrMarkscardForm objform )throws Exception {
	ExamBlockUnblockHallTicketBO list=UploadBlockListForHallticketOrMarkscardHelper.getInstance().convertToToBoGetExamBo(results,objform);
		IUploadBlockListForHallticketOrMarkscardTransaction uploadTransaction = UploadBlockListForHallticketOrMarkscardTransactionsImpl.getInstance();
		return uploadTransaction.getExamBo(list);
	}
}
