package com.kp.cms.transactions.admission;

import java.util.List;

import com.kp.cms.bo.admin.InterviewResult;
import com.kp.cms.to.admission.InterviewResultTO;

/**
 * Interface for InterviewBatchEntryTransactionImpl
 */
public interface IInterviewBatchEntryTransaction {
	
	public List getSelectedCandidates(InterviewResultTO interviewBatchEntryTO) throws Exception;
	
	public boolean batchResultUpdate(List<InterviewResult> batchUpdateList) throws Exception;
}
