package com.kp.cms.transactions.admission;

import com.kp.cms.bo.admin.AdmAppln;
import com.kp.cms.bo.admin.Student;


public interface IDocumentVerificationEntryTransaction {

	public Student getStudentDetails(String registerNo) throws Exception;
	
	public boolean saveStudentDocs(AdmAppln appBO) throws Exception;
	
}
