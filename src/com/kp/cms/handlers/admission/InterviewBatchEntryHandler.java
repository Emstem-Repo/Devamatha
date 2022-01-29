package com.kp.cms.handlers.admission;

import java.util.List;

import com.kp.cms.bo.admin.InterviewResult;
import com.kp.cms.forms.admission.InterviewBatchEntryForm;
import com.kp.cms.helpers.admission.InterviewBatchEntryHelper;
import com.kp.cms.to.admission.InterviewResultTO;
import com.kp.cms.transactions.admission.IInterviewBatchEntryTransaction;
import com.kp.cms.transactionsimpl.admission.InterviewBatchEntryTransactionImpl;

public class InterviewBatchEntryHandler {
	
	private static volatile InterviewBatchEntryHandler interviewBatchEntryHandler = null;

	private InterviewBatchEntryHandler() {

	}

	public static InterviewBatchEntryHandler getInstance() {
		if (interviewBatchEntryHandler == null) {
			interviewBatchEntryHandler = new InterviewBatchEntryHandler();
		}
		return interviewBatchEntryHandler;
	}
	
	/**
	 * @param interviewBatchEntryForm
	 * @return selectedCandidatesList
	 * @throws Exception
	 */
	public List<InterviewResultTO> getAddIntResultCandidates(InterviewBatchEntryForm interviewBatchEntryForm) throws Exception {
		IInterviewBatchEntryTransaction iInterviewBatchEntryTransaction = new InterviewBatchEntryTransactionImpl();
		InterviewBatchEntryHelper interviewBatchEntryHelper = new InterviewBatchEntryHelper();
		
		InterviewResultTO interviewBatchEntryTO = interviewBatchEntryHelper.convertFormtoTo(interviewBatchEntryForm);
		
		List getSelectedCandidatesList = iInterviewBatchEntryTransaction.getSelectedCandidates(interviewBatchEntryTO);
		
		List<InterviewResultTO> selectedCandidates = interviewBatchEntryHelper.convertBotoTo(getSelectedCandidatesList, interviewBatchEntryForm.getInterviewTypeId(), interviewBatchEntryForm.getInterviewSubroundId(), interviewBatchEntryForm.getInterviewersPerPanel());
		
		return selectedCandidates;
	}
	
	/**
	 * @param interviewBatchEntryForm
	 * @return boolean value, true if result updated successfully else false
	 * @throws Exception
	 */
	public boolean resultBatchUpdate(InterviewBatchEntryForm interviewBatchEntryForm) throws Exception {
		
		IInterviewBatchEntryTransaction iInterviewBatchEntryTransaction = new InterviewBatchEntryTransactionImpl();
		InterviewBatchEntryHelper interviewBatchEntryHelper = new InterviewBatchEntryHelper();
		boolean batchResultUpdated = false;
		String userId = interviewBatchEntryForm.getUserId();
		
		List<InterviewResultTO> list = interviewBatchEntryForm.getSelectedCandidates();
		
		List<InterviewResult> batchInterviewResult = interviewBatchEntryHelper.convertTotoBo(list, userId);
		
		batchResultUpdated = iInterviewBatchEntryTransaction.batchResultUpdate(batchInterviewResult);
		
		return batchResultUpdated;
	}
}
