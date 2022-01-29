package com.kp.cms.transactions.admission;

import java.util.List;

import com.kp.cms.bo.admission.AdmissionSubject;

public interface IAdmissionSubjectTransaction {

	boolean uploadTcDetails(List<AdmissionSubject> admissionSubject)throws Exception;

}
