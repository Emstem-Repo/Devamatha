package com.kp.cms.transactions.admission;

import java.util.List;

import com.kp.cms.bo.admin.FeePayment;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.forms.admission.ChallanVerificationForm;

public interface IChallanVerificationTransaction {

	List<FeePayment> getStudentListBO(String query) throws Exception;

	boolean updateVerifyFlag(ChallanVerificationForm challanForm) throws Exception;

}
