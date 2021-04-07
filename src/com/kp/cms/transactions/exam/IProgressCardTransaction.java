package com.kp.cms.transactions.exam;

import java.util.Date;
import java.util.List;
import java.util.Set;

import com.kp.cms.bo.admin.ApplicantSubjectGroup;
import com.kp.cms.bo.admin.Classes;
import com.kp.cms.bo.admin.EdnQualification;
import com.kp.cms.bo.admin.FeeAdditionalAccountAssignment;
import com.kp.cms.bo.admin.FeeCriteria;
import com.kp.cms.bo.admin.FeePayment;
import com.kp.cms.bo.admin.FeePaymentDetailAmount;
import com.kp.cms.bo.admin.FeePaymentMode;
import com.kp.cms.bo.admin.FeeVoucher;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.exceptions.BillGenerationException;
import com.kp.cms.forms.exam.AdminMarksCardForm;
import com.kp.cms.forms.fee.FeePaymentForm;
/**
 *
 */
public interface IProgressCardTransaction {
	public List<Student> getStudentsByClassName(AdminMarksCardForm adminMarksCardForm, List<Integer> detainedStudents) throws Exception;
	public Student getStudentsDetails(int id) throws Exception;
}
