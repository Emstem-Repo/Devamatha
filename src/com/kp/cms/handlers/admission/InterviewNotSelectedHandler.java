package com.kp.cms.handlers.admission;

import java.util.List;

import com.kp.cms.forms.admission.InterviewNotSelectedForm;
import com.kp.cms.helpers.admission.InterviewNotSelectedHelper;
import com.kp.cms.to.admission.InterviewNotSelectedTO;
import com.kp.cms.transactions.admission.IInterviewNotSelectedTxn;
import com.kp.cms.transactionsimpl.admission.InterviewNotSelectedTxnImpl;

public class InterviewNotSelectedHandler {
	
	private static volatile InterviewNotSelectedHandler interviewNotSelectedHandler = null;

	private InterviewNotSelectedHandler() {

	}

	public static InterviewNotSelectedHandler getInstance() {
		if (interviewNotSelectedHandler == null) {
			interviewNotSelectedHandler = new InterviewNotSelectedHandler();
		}
		return interviewNotSelectedHandler;
	}
	
	/**
	 * 
	 * @param interviewNotSelectedForm
	 * @return
	 * @throws Exception
	 */
	public List<InterviewNotSelectedTO> notSelectedCandidates(InterviewNotSelectedForm interviewNotSelectedForm) throws Exception {
		IInterviewNotSelectedTxn iInterviewNotSelectedTxn = new InterviewNotSelectedTxnImpl();
		
		List<Object[]> getNotSelectedCandidatesList = iInterviewNotSelectedTxn.getNotSelectedCandidates(Integer.parseInt(interviewNotSelectedForm.getInterviewTypeId()));
		
		List<InterviewNotSelectedTO> notSelectedCandidates = InterviewNotSelectedHelper.getInstance().convertBotoTo(getNotSelectedCandidatesList);
		
		return notSelectedCandidates;
	}

	/**
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws Exception
	 */
	public boolean sendInterviewNotSelectedMail(InterviewNotSelectedForm interviewNotSelectedForm) throws Exception {
		if(InterviewNotSelectedHelper.getInstance().sendInterviewNotSelectedMail(interviewNotSelectedForm)){
			return true;
		}
		return false;
	}
}