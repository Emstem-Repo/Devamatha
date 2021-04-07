package com.kp.cms.helpers.pettycash;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.PcReceipts;
import com.kp.cms.forms.pettycash.CashCollectionForm;


public class PettyCashCancelCashCollectionHelper {
	
	private static volatile PettyCashCancelCashCollectionHelper pCAccountHeadGroupCodeHelper = null;
	private static final Log log = LogFactory.getLog(PettyCashCancelCashCollectionHelper.class);
	private PettyCashCancelCashCollectionHelper() {

	}
	public static PettyCashCancelCashCollectionHelper getInstance() {
		if (pCAccountHeadGroupCodeHelper == null) {
			pCAccountHeadGroupCodeHelper = new PettyCashCancelCashCollectionHelper();
		
		}
		return pCAccountHeadGroupCodeHelper;
	}
	
	public List<PcReceipts> createBoObjcet(CashCollectionForm cashCollectionForm,List<PcReceipts> pcReceiptList)
	{
		log.info("entering into createBoObject  method of CancelCashCollectionHelper class" );
		List<PcReceipts> pcReceipts=new ArrayList<PcReceipts>();
		for(PcReceipts pcReceipt:pcReceiptList)
		{
			pcReceipt.setIsCancelled(true);
			pcReceipt.setCancelComments(cashCollectionForm.getCancelComments());
			pcReceipt.setModifiedBy(cashCollectionForm.getUserId());
			pcReceipt.setLastModifiedDate(new Date());
			pcReceipt.setAcademicYear(pcReceipt.getAcademicYear());
			//pcReceipt.getFinYearId(String.valueOf(pcReceipt.getPcFinancialYear().getId()));
			//pcReceipt.setFinYearId(pcReceipt.getId());
			pcReceipts.add(pcReceipt);
		}		log.info("leaving createBoObject method of CancelCashCollectionHelper class" );
		return pcReceipts;
	}

}
