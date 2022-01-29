package com.kp.cms.handlers.admission;

import java.util.List;

import com.kp.cms.bo.admission.AdmissionSubject;
import com.kp.cms.helpers.admission.AdmissionSubjectHelper;
import com.kp.cms.to.admission.AdmissionSubjectTo;
import com.kp.cms.transactions.admission.IAdmissionSubjectTransaction;
import com.kp.cms.transactionsimpl.admission.AdmissionStudentImpl;

public class AdmissionSubjectHandler {
	private static volatile AdmissionSubjectHandler  admissionSubjectHandler = null;
	public static AdmissionSubjectHandler getInstance(){
		if(admissionSubjectHandler == null){
			admissionSubjectHandler = new AdmissionSubjectHandler();
			return admissionSubjectHandler;
		}
		return admissionSubjectHandler;
	}
	/**
	 * @param admissionSubjectTo
	 * @return
	 * @throws Exception
	 */
	public boolean uploadAdmSubject(List<AdmissionSubjectTo> admissionSubjectTo) throws Exception{
		IAdmissionSubjectTransaction transaction=AdmissionStudentImpl.getInstance();
		List<AdmissionSubject> admissionSubject=AdmissionSubjectHelper.getInstance().convertToTOBo(admissionSubjectTo);
		return transaction.uploadTcDetails(admissionSubject);
	}
}
