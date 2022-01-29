package com.kp.cms.transactions.admission;

import java.util.List;

import com.kp.cms.bo.admin.CandidatePGIDetails;
import com.kp.cms.forms.admission.GenerateSettlementOrRefundPgiForm;

public interface IGenerateSettlementOrRefundPgiTransation {
	public	List<CandidatePGIDetails> getSettlementOrRefundPgiData(String fromDate, String toDate)throws Exception;
	public void updateRefundFlag( List<Integer> idList,GenerateSettlementOrRefundPgiForm form, String string)throws Exception;
}
