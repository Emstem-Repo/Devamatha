package com.kp.cms.transactions.admission;

import java.util.List;

import com.kp.cms.bo.admin.AdmAppln;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.forms.admission.AdmLoanLetterForm;

public interface IAdmLoanLetterTransaction {
	
	List<Student> getStudentListBO(String query) throws Exception;

	boolean addLoan(AdmLoanLetterForm letterForm) throws Exception;

	List<Object[]> getStudentDetails(String query) throws Exception;

}
