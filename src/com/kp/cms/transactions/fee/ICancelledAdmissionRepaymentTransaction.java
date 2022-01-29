package com.kp.cms.transactions.fee;

import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admin.StudentCancellationDetails;
import com.kp.cms.forms.fee.CancelledAdmissionRepaymentForm;

public interface ICancelledAdmissionRepaymentTransaction {

	Student getStudentBO(CancelledAdmissionRepaymentForm cancelledAdmissionRepaymentForm) throws Exception;

	boolean saveCancelledRepaymentDetails(StudentCancellationDetails studentCancelDetailsBO) throws Exception;

	StudentCancellationDetails checkDuplicate(CancelledAdmissionRepaymentForm cancelledAdmissionRepaymentForm) throws Exception;

}
