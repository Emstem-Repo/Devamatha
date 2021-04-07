package com.kp.cms.transactionsimpl.exam;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.transactions.exam.IExamRevaluationOfflineAppTransaction;

public class ExamRevaluationOfflineAppTransactionimpl implements
		IExamRevaluationOfflineAppTransaction {
	/**
	 * Singleton object of ExamRevaluationOfflineAppTransactionimpl
	 */
	private static volatile ExamRevaluationOfflineAppTransactionimpl examRevaluationOfflineAppTransactionimpl = null;
	private static final Log log = LogFactory.getLog(ExamRevaluationOfflineAppTransactionimpl.class);
	private ExamRevaluationOfflineAppTransactionimpl() {
		
	}
	/**
	 * return singleton object of ExamRevaluationOfflineAppTransactionimpl.
	 * @return
	 */
	public static ExamRevaluationOfflineAppTransactionimpl getInstance() {
		if (examRevaluationOfflineAppTransactionimpl == null) {
			examRevaluationOfflineAppTransactionimpl = new ExamRevaluationOfflineAppTransactionimpl();
		}
		return examRevaluationOfflineAppTransactionimpl ;
	}
}
