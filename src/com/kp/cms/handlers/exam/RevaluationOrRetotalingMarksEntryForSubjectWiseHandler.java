package com.kp.cms.handlers.exam;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.exam.ExamRevaluationApplication;
import com.kp.cms.forms.exam.NewSecuredMarksEntryForm;
import com.kp.cms.forms.exam.RevaluationOrRetotalingMarksEntryForSubjectWiseForm;
import com.kp.cms.forms.exam.RevaluationOrRetotallingMarksEntryForm;
import com.kp.cms.helpers.exam.RevaluationOrRetotalingMarksEntryForSubjectWiseHelper;
import com.kp.cms.to.exam.RevaluationOrRetotalingMarksEntryForSubjectWiseTo;
import com.kp.cms.transactions.exam.INewSecuredMarksEntryTransaction;
import com.kp.cms.transactions.exam.IRevaluationOrRetotalingMarksEntryForSubjectWiseTransaction;
import com.kp.cms.transactions.exam.IRevaluationOrRetotallingMarksEntryTransaction;
import com.kp.cms.transactionsimpl.exam.NewSecuredMarksEntryTransactionImpl;
import com.kp.cms.transactionsimpl.exam.RevaluationOrRetotalingMarksEntryForSubjectWiseTransactionImpl;
import com.kp.cms.transactionsimpl.exam.RevaluationOrRetotallingMarksEntryTransactionImpl;

public class RevaluationOrRetotalingMarksEntryForSubjectWiseHandler {
	private static volatile RevaluationOrRetotalingMarksEntryForSubjectWiseHandler revaluationOrRetotalingMarksEntryForSubjectWiseHandler = null;
	private static final Log log = LogFactory.getLog(RevaluationOrRetotalingMarksEntryForSubjectWiseHandler.class);
	
	/**
	 * @return
	 */
	public static RevaluationOrRetotalingMarksEntryForSubjectWiseHandler getInstance() {
		if (revaluationOrRetotalingMarksEntryForSubjectWiseHandler == null) {
			revaluationOrRetotalingMarksEntryForSubjectWiseHandler = new RevaluationOrRetotalingMarksEntryForSubjectWiseHandler();
		}
		return revaluationOrRetotalingMarksEntryForSubjectWiseHandler;
	}
	
	
	public List<RevaluationOrRetotalingMarksEntryForSubjectWiseTo> getStudentDetails(RevaluationOrRetotalingMarksEntryForSubjectWiseForm form) throws Exception{
		IRevaluationOrRetotalingMarksEntryForSubjectWiseTransaction transaction=RevaluationOrRetotalingMarksEntryForSubjectWiseTransactionImpl.getInstance();
		List<Object> boList=transaction.getStudentDetailsList(form);
		List<RevaluationOrRetotalingMarksEntryForSubjectWiseTo> toList=RevaluationOrRetotalingMarksEntryForSubjectWiseHelper.getInstance().convertBotoTo(boList,form);
		return toList;
	}
	public String getPropertyValue(int id, String boName, boolean isActive,String property) throws Exception {
		INewSecuredMarksEntryTransaction transaction=NewSecuredMarksEntryTransactionImpl.getInstance();
		return transaction.getPropertyData(id, boName, isActive, property);
	}
	
	public Double getMaxMarkOfSubject(RevaluationOrRetotalingMarksEntryForSubjectWiseForm form) throws Exception {
		IRevaluationOrRetotalingMarksEntryForSubjectWiseTransaction transaction=RevaluationOrRetotalingMarksEntryForSubjectWiseTransactionImpl.getInstance();
			return transaction.getMaxMarkOfSubject(form);
	}
	
	public boolean saveMarks(RevaluationOrRetotalingMarksEntryForSubjectWiseForm form) throws Exception{
		IRevaluationOrRetotalingMarksEntryForSubjectWiseTransaction transaction=RevaluationOrRetotalingMarksEntryForSubjectWiseTransactionImpl.getInstance();
		return transaction.saveMarks(form);
	}
	public Double getMaxMarkOfSubjectValidation(RevaluationOrRetotalingMarksEntryForSubjectWiseForm form,int courseId,int schemeNo) throws Exception {
		IRevaluationOrRetotalingMarksEntryForSubjectWiseTransaction transaction=RevaluationOrRetotalingMarksEntryForSubjectWiseTransactionImpl.getInstance();
			return transaction.getMaxMarkOfSubjectValidation(form,courseId,schemeNo);
	}
	public boolean saveMarksThirdEvlmarks(RevaluationOrRetotalingMarksEntryForSubjectWiseForm form) throws Exception{
		IRevaluationOrRetotalingMarksEntryForSubjectWiseTransaction transaction=RevaluationOrRetotalingMarksEntryForSubjectWiseTransactionImpl.getInstance();
		return transaction.saveMarksThirdEvaluationMarks(form);
	}
	public boolean isStudentAppliedForThirdEvl(RevaluationOrRetotalingMarksEntryForSubjectWiseForm form,int exanRevaluationAppId) throws Exception{
		IRevaluationOrRetotalingMarksEntryForSubjectWiseTransaction transaction=RevaluationOrRetotalingMarksEntryForSubjectWiseTransactionImpl.getInstance();
		return transaction.isStudentAppliedForThirdEvl(form,exanRevaluationAppId);
	}
}
