package com.kp.cms.handlers.sap;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.sap.UploadSAPMarksBo;
import com.kp.cms.forms.sap.UploadSAPMarksForm;
import com.kp.cms.helpers.sap.UploadSAPMarksHelper;
import com.kp.cms.to.sap.UploadSAPMarksTo;
import com.kp.cms.transactions.exam.IUploadBlockListForHallticketOrMarkscardTransaction;
import com.kp.cms.transactions.sap.IUploadSAPMarksTransaction;
import com.kp.cms.transactionsimpl.exam.UploadBlockListForHallticketOrMarkscardTransactionsImpl;
import com.kp.cms.transactionsimpl.sap.UploadSAPMarksTransactionImpl;

public class UploadSAPMarksHandler {
	private static volatile UploadSAPMarksHandler uploadSAPMarksHandler = null;
	private static final Log log = LogFactory.getLog(UploadSAPMarksHandler.class);
	public static UploadSAPMarksHandler getInstance() {
		if (uploadSAPMarksHandler == null) {
			uploadSAPMarksHandler = new UploadSAPMarksHandler();
		}
		return uploadSAPMarksHandler;
	}

	public boolean uploadSapMarks(List<UploadSAPMarksTo> results,UploadSAPMarksForm objform)throws Exception {
		IUploadSAPMarksTransaction transaction=UploadSAPMarksTransactionImpl.getInstance();
		Map<Integer,UploadSAPMarksBo> boList=UploadSAPMarksHelper.getInstance().covertToToBo(results,objform);
		boolean issaved=transaction.saveUploadSAPMarks(boList,objform);
		return issaved;
	}
	public Integer getRegNumByStudentId(int studentId)throws Exception{
		IUploadBlockListForHallticketOrMarkscardTransaction uploadTransaction = UploadBlockListForHallticketOrMarkscardTransactionsImpl.getInstance();
		int regNo=uploadTransaction.getStudentRegisterNo(studentId);
		return regNo;
	}
	
	public Map<String,Integer>  getStudentIdByStudentRegNum(String year,List<String> regNumList)throws Exception {
		IUploadSAPMarksTransaction transaction=UploadSAPMarksTransactionImpl.getInstance();
		return transaction.getStudentIdByStudentRegNum(year,regNumList);
	}
}
