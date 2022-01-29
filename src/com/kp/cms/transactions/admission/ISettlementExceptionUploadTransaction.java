package com.kp.cms.transactions.admission;

import java.util.List;
import java.util.Map;

import com.kp.cms.bo.admin.CandidatePGIDetails;

public interface ISettlementExceptionUploadTransaction {
	public	Map<String,CandidatePGIDetails> getTnxPendingStatuscandiates(List<String> candidateRefNoList) throws Exception;
	
	public boolean updateCandidatePGIDetails(List<CandidatePGIDetails> boList)throws Exception;

}
