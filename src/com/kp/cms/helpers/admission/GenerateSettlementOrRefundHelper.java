package com.kp.cms.helpers.admission;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.CandidatePGIDetails;
import com.kp.cms.bo.admin.TTClasses;
import com.kp.cms.to.admission.GenerateSettlementOrRefundPgiTo;

public class GenerateSettlementOrRefundHelper {
	private static volatile GenerateSettlementOrRefundHelper generateSettlementOrRefundHelper = null;
	private static final Log log = LogFactory.getLog(GenerateSettlementOrRefundHelper.class);
	
	public static GenerateSettlementOrRefundHelper getInstance() {
		if (generateSettlementOrRefundHelper == null) {
			generateSettlementOrRefundHelper = new GenerateSettlementOrRefundHelper();
		}
		return generateSettlementOrRefundHelper;
	}
	public List<GenerateSettlementOrRefundPgiTo> convertBOtoTO(List<CandidatePGIDetails> SettlementBoList){
		List<GenerateSettlementOrRefundPgiTo> GenerateSettlementOrRefundPgiToList=new ArrayList<GenerateSettlementOrRefundPgiTo> ();
		if(SettlementBoList!=null && !SettlementBoList.isEmpty()){
			Iterator<CandidatePGIDetails> itr=SettlementBoList.iterator();
			while(itr.hasNext()) {
				CandidatePGIDetails bo=itr.next();
				GenerateSettlementOrRefundPgiTo generateSettlementOrRefundPgiTo=new GenerateSettlementOrRefundPgiTo();
				generateSettlementOrRefundPgiTo.setId(bo.getId());
				generateSettlementOrRefundPgiTo.setTxnRefNo(bo.getTxnRefNo());
				generateSettlementOrRefundPgiTo.setCandidateRefNo(bo.getCandidateRefNo());
				generateSettlementOrRefundPgiTo.setTxnAmount(bo.getTxnAmount());
				generateSettlementOrRefundPgiTo.setTxnDate(bo.getTxnDate());
				generateSettlementOrRefundPgiTo.setAdmAppln(bo.getAdmAppln());
				generateSettlementOrRefundPgiTo.setRefundGenerated(bo.getRefundGenerated());
				generateSettlementOrRefundPgiTo.setSettlementGenerated(bo.getSettlementGenerated());
				GenerateSettlementOrRefundPgiToList.add(generateSettlementOrRefundPgiTo);
			}
		}
		return GenerateSettlementOrRefundPgiToList;
		
		
		
	}
}
