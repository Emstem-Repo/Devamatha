package com.kp.cms.handlers.admission;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.CandidatePGIDetails;
import com.kp.cms.forms.admission.GenerateSettlementOrRefundPgiForm;
import com.kp.cms.helpers.admission.GenerateSettlementOrRefundHelper;
import com.kp.cms.to.admission.GenerateSettlementOrRefundPgiTo;
import com.kp.cms.transactions.admission.IGenerateSettlementOrRefundPgiTransation;
import com.kp.cms.transactionsimpl.admission.GenerateSettlementOrRefundPgiTransactionImpl;

public class GenerateSettlementOrRefundPgiHandler {
	private static volatile GenerateSettlementOrRefundPgiHandler generateSettlementOrRefundPgiHandler = null;
	private static final Log log = LogFactory.getLog(GenerateSettlementOrRefundPgiHandler.class);
	
	public static GenerateSettlementOrRefundPgiHandler getInstance() {
		if (generateSettlementOrRefundPgiHandler == null) {
			generateSettlementOrRefundPgiHandler = new GenerateSettlementOrRefundPgiHandler();
		}
		return generateSettlementOrRefundPgiHandler;
	}
	IGenerateSettlementOrRefundPgiTransation txn=GenerateSettlementOrRefundPgiTransactionImpl.getInstance();
	/**
	 * @param generateSettlementOrRefundPgiForm
	 * @return
	 * @throws Exception
	 */
	public List<GenerateSettlementOrRefundPgiTo> generateFilesForSettlementOrRefund(GenerateSettlementOrRefundPgiForm generateSettlementOrRefundPgiForm) throws Exception{
		List<CandidatePGIDetails> settlementBoList=txn.getSettlementOrRefundPgiData(generateSettlementOrRefundPgiForm.getFromDate(),generateSettlementOrRefundPgiForm.getToDate());
		List<GenerateSettlementOrRefundPgiTo> generateSettlementPgiTo=GenerateSettlementOrRefundHelper.getInstance().convertBOtoTO(settlementBoList);
		return generateSettlementPgiTo;
	}
	public void updateRefundFlag( List<Integer> idList,GenerateSettlementOrRefundPgiForm form, String string) throws Exception
	{
		txn.updateRefundFlag( idList,form,string);
	}
}
