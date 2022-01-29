package com.kp.cms.transactions.inventory;

import java.util.List;

import com.kp.cms.bo.admin.InvQuotation;
import com.kp.cms.bo.admin.InvQuotationItem;

public interface IQuotationReportTransaction {
	public InvQuotation getQuotationDetails(String quotationNo) throws Exception;
	public List<InvQuotationItem> getQuotationItemsId(int quotId) throws Exception;
	public String getPrefixByType(String type) throws Exception;
	
}
