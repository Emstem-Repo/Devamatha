package com.kp.cms.handlers.attendance;

import java.util.List;

import com.kp.cms.bo.admin.AttnMarksUpload;
import com.kp.cms.bo.admin.PucAttnInternalMarks;
import com.kp.cms.bo.admin.PucttnAttendance;
import com.kp.cms.forms.attendance.AttnMarksUploadForm;
import com.kp.cms.helpers.attendance.AttnMarksUploadHelper;
import com.kp.cms.to.attendance.AttnMarksUploadTo;
import com.kp.cms.to.attendance.PucAttnInternalMarksTo;
import com.kp.cms.to.attendance.PucttnAttendanceTo;
import com.kp.cms.transactions.attandance.IAttnMarksUploadTransaction;
import com.kp.cms.transactionsimpl.attendance.AttnMarksUploadTransactionImpl;

public class AttnMarksUploadHandler {
 private static volatile AttnMarksUploadHandler attnMarksUploadHandler = null;
 public static AttnMarksUploadHandler getInstance(){
	 if(attnMarksUploadHandler == null){
		 attnMarksUploadHandler = new AttnMarksUploadHandler();
		 return attnMarksUploadHandler;
	 }
	return attnMarksUploadHandler;
 }
public boolean addAttnMarksUpload(List<AttnMarksUploadTo> attnMarksUploadToList) throws Exception {
	IAttnMarksUploadTransaction transaction = AttnMarksUploadTransactionImpl.getInstance();
	List<AttnMarksUpload> marksUploadsList = AttnMarksUploadHelper.getInstance().populateTOToBO(attnMarksUploadToList);
	return transaction.addAttnMarksUpload(marksUploadsList);
}
/**
 * @param attnUploadToList
 * @return
 * @throws Exception
 */
public boolean addAttnUpload(List<PucttnAttendanceTo> attnUploadToList)throws Exception { 
	IAttnMarksUploadTransaction transaction = AttnMarksUploadTransactionImpl.getInstance();
	List<PucttnAttendance> pucttnAttendances = AttnMarksUploadHelper.getInstance().convertTOToBO(attnUploadToList);
	return transaction.addPucttnAttendanceUpload(pucttnAttendances);
}

public boolean addAttnInternalMarksUpload(List<PucAttnInternalMarksTo> attnInternalMarksToList, AttnMarksUploadForm attnMarksUploadForm) throws Exception {
	IAttnMarksUploadTransaction transaction = AttnMarksUploadTransactionImpl.getInstance();
	List<PucAttnInternalMarks> internalMarksList = AttnMarksUploadHelper.getInstance().populateTOToBOForInternalMarks(attnInternalMarksToList);
	return transaction.addAttnInternalMarks(internalMarksList,attnMarksUploadForm);
}
}
