package com.kp.cms.handlers.exam;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.exam.UGConsolidateMarksCardBO;
import com.kp.cms.forms.exam.ExamConsolidateMarksCardForm;
import com.kp.cms.transactions.exam.IExamConsolidateMarksCardTransaction;
import com.kp.cms.transactionsimpl.exam.ExamConsolidateMarksCardTransImpl;

public class ExamConsolidateMarksCardHandler {
	
	private static final Log log = LogFactory.getLog(ExamConsolidateMarksCardHandler.class);
	private static volatile ExamConsolidateMarksCardHandler examConsolidateMarksCardHandler= null;
	IExamConsolidateMarksCardTransaction iTransaction = new ExamConsolidateMarksCardTransImpl();
	/*
	 *  returns the singleton object of ExamConsolidateMarksCardHandler.
	 */
	public static ExamConsolidateMarksCardHandler getInstance() {
	      if(examConsolidateMarksCardHandler == null) {
	    	  examConsolidateMarksCardHandler = new ExamConsolidateMarksCardHandler();
	      }
	      return examConsolidateMarksCardHandler;
	}

	public boolean saveConsolidateMarksCardDetails(ExamConsolidateMarksCardForm examConsolidateMarksCardForm) throws Exception {
		
		int courseId=0,year=0;
		if(examConsolidateMarksCardForm.getCourseId()!=null && !examConsolidateMarksCardForm.getCourseId().isEmpty()){
			 courseId=Integer.parseInt(examConsolidateMarksCardForm.getCourseId().trim());
		}
		if(examConsolidateMarksCardForm.getYear()!=null && !examConsolidateMarksCardForm.getYear().isEmpty()){
			 year = Integer.parseInt(examConsolidateMarksCardForm.getYear().trim());
		}
		List<Object[]> ugConsolidateMarksCardBO = iTransaction.getConsolidateMarksCardDetails(courseId,year);
		//UGConsolidateMarksCardBO ugConsolidateMarksCardBO = ExamConsolidateMarksCardHelper.getInstance().convertFormToBO(examConsolidateMarksCardForm);
		return iTransaction.saveConsolidateMarksCardDetails(ugConsolidateMarksCardBO);
	}

}
