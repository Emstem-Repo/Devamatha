package com.kp.cms.transactions.admin;

import java.math.BigDecimal;
import java.util.List;

import com.kp.cms.bo.admin.FeeAccountAssignment;
import com.kp.cms.bo.admin.FeePayment;
import com.kp.cms.bo.admin.FeePaymentDetailFeegroup;
import com.kp.cms.bo.admin.Sports;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.forms.admin.CommonTemplateForm;

public interface ICommonTemplateTransaction {

	List<Student> getRequiredRegdNos(String regNoFrom, String regNoTo) throws Exception;

	List<FeePayment> getFeeDetails(String stuId, CommonTemplateForm commonTemplateForm) throws Exception;
	List<Sports> getSportsList() throws Exception;

	BigDecimal getTuitionFeeDetails(String stuId, CommonTemplateForm commonTemplateForm) throws Exception;

	List<FeePaymentDetailFeegroup> getFeeGroupDetails(String stuId,	CommonTemplateForm commonTemplateForm) throws Exception;

	List<Object[]> getStudentMarksDetails(Student student) throws Exception;

	List<FeeAccountAssignment> getTuitionFeeForClass(Student student, CommonTemplateForm commonTemplateForm) throws Exception;

	Student getStudent(CommonTemplateForm commonTemplateForm);
	
	List<Object[]> getFeeHeadingDetails(String stuId,	CommonTemplateForm commonTemplateForm) throws Exception;

}
