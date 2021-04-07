package com.kp.cms.handlers.inventory;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.InvCashPurchaseItem;
import com.kp.cms.forms.inventory.CashPurchaseReportForm;
import com.kp.cms.helpers.inventory.CashPurchaseReportHelper;
import com.kp.cms.to.inventory.InvCashPurchaseItemTO;
import com.kp.cms.transactions.inventory.ICashPurchaseReportTran;
import com.kp.cms.transactionsimpl.inventory.CashpurchaseReportImpl;
import com.kp.cms.utilities.CommonUtil;


public class CashPurchaseReportHandler {
	private static final Log log = LogFactory.getLog(CashPurchaseReportHandler.class);
	public static volatile CashPurchaseReportHandler cashPurchaseReportHandler = null;
	
	public static CashPurchaseReportHandler getInstance() {
		if(cashPurchaseReportHandler == null){
			cashPurchaseReportHandler = new CashPurchaseReportHandler();
		}
		return cashPurchaseReportHandler;
	}

	/**
	 * 
	 * @param cashPurchaseReportForm
	 * @return
	 * @throws Exception
	 */
	public List<InvCashPurchaseItemTO> getListOfCashPurchases(CashPurchaseReportForm cashPurchaseReportForm) throws Exception{
		log.debug("inside getListOfCashPurchases");
		ICashPurchaseReportTran tran = CashpurchaseReportImpl.getInstance(); 
		List<InvCashPurchaseItem> itemList = tran.getCashPurchaseItems(CommonUtil.ConvertStringToSQLDate(cashPurchaseReportForm.getStartDate()), 
				CommonUtil.ConvertStringToSQLDate(cashPurchaseReportForm.getEndDate()), Integer.parseInt(cashPurchaseReportForm.getInvLocationId())); 
		List<InvCashPurchaseItemTO> cashPurchaseToList = CashPurchaseReportHelper.getInstance().copyBosToTO(itemList);
		log.debug("exit getListOfCashPurchases");
		return cashPurchaseToList;
		
	}
}
