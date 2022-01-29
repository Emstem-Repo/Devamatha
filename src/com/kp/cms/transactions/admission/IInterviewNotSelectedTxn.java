package com.kp.cms.transactions.admission;

import java.util.List;

public interface IInterviewNotSelectedTxn {
	
	public List<Object[]> getNotSelectedCandidates(int interviewTypeId) throws Exception;

}
