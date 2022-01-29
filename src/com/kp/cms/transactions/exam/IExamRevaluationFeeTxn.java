package com.kp.cms.transactions.exam;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionErrors;

import com.kp.cms.bo.exam.ExamRevaluationFee;
import com.kp.cms.forms.exam.ExamRevaluationFeeForm;

public interface IExamRevaluationFeeTxn {
	public boolean duplicateCheck(HttpSession session,ActionErrors errors,ExamRevaluationFeeForm examRevaluationFeeForm);
	public boolean addRevaluationFee(ExamRevaluationFee revaluationFee);
	public ExamRevaluationFee getRevaluationFeeById(int id);
	public boolean updateRevaluationFee(ExamRevaluationFee revaluationFee);
	public boolean deleteRevaluationFee(int id);
	public boolean reactivateRevaluationFee(ExamRevaluationFeeForm examRevaluationFeeForm)throws Exception;
	public List<ExamRevaluationFee> getRevaluationFeeList();
}
