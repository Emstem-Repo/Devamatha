package com.kp.cms.handlers.reports;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.InvTx;
import com.kp.cms.forms.reports.IssuedReceivedReportForm;
import com.kp.cms.helpers.reports.IssuedReceivedReportHelper;
import com.kp.cms.to.reports.IssuedReceivedTO;
import com.kp.cms.transactions.reports.IIssuedReceivedReportTxn;
import com.kp.cms.transactionsimpl.reports.IssuedReceivedReportTxnImpl;

public class IssuedReceivedReportHandler {
	private static volatile IssuedReceivedReportHandler self=null;
	private static Log log = LogFactory.getLog(IssuedReceivedReportHandler.class);

	/**
	 * @return
	 * This method will return the instance of this classes
	 */
	public static IssuedReceivedReportHandler getInstance(){
		if(self==null)
			self= new IssuedReceivedReportHandler();
		return self;
	}
	
	private IssuedReceivedReportHandler(){
		
	}

/**
 * @param txDate
 * @param locationId
 * @return
 * @throws Exception
 */
public List<IssuedReceivedTO> getItemTransactions(IssuedReceivedReportForm issuedReceivedReportForm) throws Exception{
	IIssuedReceivedReportTxn txn= IssuedReceivedReportTxnImpl.getInstance();
	String dynamicQuery = IssuedReceivedReportHelper.getSelectionSearchCriteria(issuedReceivedReportForm);
	List<InvTx> txnBos=txn.getItemTransactionsOnDate(dynamicQuery);
	List<IssuedReceivedTO> issuedReceivedTOList =  IssuedReceivedReportHelper.populateTOList(txnBos);
	return issuedReceivedTOList;
}

}
