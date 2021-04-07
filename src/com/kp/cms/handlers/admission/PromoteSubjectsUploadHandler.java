package com.kp.cms.handlers.admission;

import java.util.List;

import com.kp.cms.bo.admission.PromoteSubjects;
import com.kp.cms.forms.admission.PromoteSubjectsUploadForm;
import com.kp.cms.helpers.admission.PromoteSubjectsUploadHelper;
import com.kp.cms.to.admission.PromoteSubjectsUploadTo;
import com.kp.cms.transactions.admission.IPromoteSubjectsUploadTransaction;
import com.kp.cms.transactionsimpl.admission.PromoteSubjectsUploadTxnImpl;

public class PromoteSubjectsUploadHandler {
	private static volatile PromoteSubjectsUploadHandler handler =null;
	public static PromoteSubjectsUploadHandler getInstance(){
		if(handler == null){
			handler = new PromoteSubjectsUploadHandler();
			return handler;
		}
		return handler;
	}
	public boolean UploadPromoteSubjects( List<PromoteSubjectsUploadTo> promoteSubjectsList)throws Exception {
		IPromoteSubjectsUploadTransaction transaction = PromoteSubjectsUploadTxnImpl.getInstance();
		List<PromoteSubjects> promoteSubjects = PromoteSubjectsUploadHelper.getInstance().convertTOToBO(promoteSubjectsList);
		return transaction.UploadPromoteSubjects(promoteSubjects);
	}
	public boolean checkDuplicate(PromoteSubjectsUploadForm proSubjectsUploadForm) throws Exception{
		IPromoteSubjectsUploadTransaction transaction = PromoteSubjectsUploadTxnImpl.getInstance();
		return transaction.isDuplicateYear(proSubjectsUploadForm);
	}
}
