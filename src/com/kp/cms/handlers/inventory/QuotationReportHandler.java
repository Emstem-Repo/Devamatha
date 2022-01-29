package com.kp.cms.handlers.inventory;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.InvQuotation;
import com.kp.cms.bo.admin.InvQuotationItem;
import com.kp.cms.helpers.inventory.QuotationReportHelper;
import com.kp.cms.to.inventory.InvQuotationItemTO;
import com.kp.cms.to.inventory.InvQuotationTO;
import com.kp.cms.to.inventory.ItemTO;
import com.kp.cms.transactions.inventory.IQuotationReportTransaction;
import com.kp.cms.transactionsimpl.inventory.QuotationReportTransactionImpl;

public class QuotationReportHandler {
	private static final Log log = LogFactory.getLog(QuotationReportHandler.class);
	public static volatile QuotationReportHandler quotationReportHandler = null;
	
	public static QuotationReportHandler getInstance() {
		if (quotationReportHandler == null) {
			quotationReportHandler = new QuotationReportHandler();
			return quotationReportHandler;
		}
		return quotationReportHandler;
	}	
	
	/**
	 * @return list of InvQuotationTO objects, will be used in UI to dispaly.
	 * @throws Exception
	 */
	public List<ItemTO> getQuotationDetails(String quotationNo) throws Exception {
		log.debug("inside getQuotationDetails");
		IQuotationReportTransaction iqTransaction = QuotationReportTransactionImpl.getInstance();
		//List<InvQuotation> quotationList = iqTransaction.getQuotationDetails(quotationNo);
		//List<InvQuotationTO> invQuotationTOList = QuotationReportHelper.getInstance().copyQuotationBosToTos(quotationList);
		InvQuotation quotation = iqTransaction.getQuotationDetails(quotationNo);
		log.debug("leaving getQuotationDetails");
		return QuotationReportHelper.getInstance().prepareItems(quotation);
	}		
	/**
	 * @return list of InvQuotationItemTO objects, will be used in UI to dispaly.
	 * @throws Exception
	 */
	public List<InvQuotationItemTO> getQuotationItemsById(int quotId) throws Exception {
		log.debug("inside getQuotationItemsById");
		IQuotationReportTransaction iqTransaction = QuotationReportTransactionImpl.getInstance();
		List<InvQuotationItem> quotationList = iqTransaction.getQuotationItemsId(quotId);
		List<InvQuotationItemTO> invQuotationItemTOList = QuotationReportHelper.getInstance().copyQuotationItemBosToTos(quotationList); 
		log.debug("leaving getQuotationItemsById");
		return invQuotationItemTOList;
	}

	public String getQuotationPrefix(String type) throws Exception {
		IQuotationReportTransaction iqTransaction = QuotationReportTransactionImpl.getInstance();
		return iqTransaction.getPrefixByType(type);
	}		

	
}
