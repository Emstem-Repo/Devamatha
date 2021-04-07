package com.kp.cms.transactions.admission;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import com.kp.cms.bo.admin.InterviewCard;
import com.kp.cms.bo.admin.InterviewCardHistory;
import com.kp.cms.bo.admin.InterviewSchedule;
import com.kp.cms.to.admission.InterviewTimeChangeTO;

/**
 * @author kalyan.c
 *
 */
public interface IInterviewChangeTimeTxn {
	public List getSelectedCandidates(InterviewTimeChangeTO interviewBatchEntryTO,Set<Integer> interviewTypSet) throws Exception;
	
	public boolean batchResultUpdate(List<InterviewCard> iCardList) throws Exception;

	public boolean batchResultUpdate1(List<InterviewCard> updateInterviewCardList,
			List<InterviewCard> deleteinterviewCardList,
			List<InterviewSchedule> newSchedule,HttpServletRequest request, String userId) throws Exception;

	public List<Integer> getAdmApplnResultEntered(InterviewTimeChangeTO interviewBatchEntryTO,Set<Integer> interviewTypSet) throws Exception;

	public void addHistory(List<String> cardIds, String userId) throws Exception;

	public Map<Integer, String> getHistoryMap() throws Exception;

	public List<InterviewCardHistory> getScheduledHistory(String appNo) throws Exception;

}
