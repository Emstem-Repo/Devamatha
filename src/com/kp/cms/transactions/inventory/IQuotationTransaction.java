package com.kp.cms.transactions.inventory;

import java.util.List;

import com.kp.cms.bo.admin.InvCompany;
import com.kp.cms.bo.admin.InvItem;
import com.kp.cms.bo.admin.InvPurchaseOrder;
import com.kp.cms.bo.admin.InvQuotation;
import com.kp.cms.forms.inventory.QuotationForm;

/**
 * Interface for quotation DB transactions
 *
 */
public interface IQuotationTransaction {

	String getLatestQuoteNo(String quotationCounter,QuotationForm orderForm) throws Exception;
	
	List<InvItem> getItemsList() throws Exception;
	
	boolean placeFinalQuotation(InvQuotation finalOrder,QuotationForm orderForm)throws Exception;
	
	InvQuotation getQuotationForEdit(String quotationNo) throws Exception;

	List<InvCompany> getCompanyList() throws Exception;

}
