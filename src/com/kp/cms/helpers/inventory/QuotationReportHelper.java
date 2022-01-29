package com.kp.cms.helpers.inventory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.InvQuotation;
import com.kp.cms.bo.admin.InvQuotationItem;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.to.inventory.InvItemTO;
import com.kp.cms.to.inventory.InvQuotationItemTO;
import com.kp.cms.to.inventory.InvQuotationTO;
import com.kp.cms.to.inventory.InvVendorTO;
import com.kp.cms.to.inventory.ItemTO;
import com.kp.cms.utilities.CommonUtil;


public class QuotationReportHelper {
	private static final Log log = LogFactory.getLog(QuotationReportHelper.class);
	public static volatile QuotationReportHelper quotationReportHelper = null;
	
	public static QuotationReportHelper getInstance() {
		if (quotationReportHelper == null) {
			quotationReportHelper = new QuotationReportHelper();
			return quotationReportHelper;
		}
		return quotationReportHelper;
	}	
	
		
	/**
	 * 
	 * @param quotationList
	 * @return
	 */
	public List<InvQuotationTO> copyQuotationBosToTos(List<InvQuotation> quotationList) {
		log.debug("inside copyQuotationBosToTos");
		List<InvQuotationTO> invQuotationTOList = new ArrayList<InvQuotationTO>();
		Iterator<InvQuotation> iterator = quotationList.iterator();
		
		InvQuotation invQuotation;
		InvQuotationTO invQuotationTO;
		InvVendorTO  invVendorTO;
		//iterator = quotationList.iterator();
		while (iterator.hasNext()) {
			invQuotation = (InvQuotation) iterator.next();
			invQuotationTO = new InvQuotationTO();
			invQuotationTO.setId(invQuotation.getId());
			invQuotationTO.setAdditionalCost(invQuotation.getAdditionalCost());
			if(invQuotation.getDeliverySite()!= null){
				invQuotationTO.setDeliverySite(invQuotation.getDeliverySite());
			}
			if(invQuotation.getInvVendor()!= null){
				invVendorTO = new InvVendorTO(); 
				invVendorTO.setId(invQuotation.getInvVendor().getId());
				invVendorTO.setName(invQuotation.getInvVendor().getName());
				invQuotationTO.setInvVendorTO(invVendorTO);
			}
			if(invQuotation.getQuoteDate()!= null){
				invQuotationTO.setQuoteDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(invQuotation.getQuoteDate()), CMSConstants.SOURCE_DATE,CMSConstants.DEST_DATE));
			}
//			if(invQuotation.getQuoteNo()!= null){
//				invQuotationTO.setQuoteNo(invQuotation.getQuoteNo().toString());
//			}
			if(invQuotation.getRemarks()!= null){
				invQuotationTO.setRemarks(invQuotation.getRemarks());
			}
			if(invQuotation.getTermsandconditions()!= null){
				invQuotationTO.setTermsandconditions(invQuotation.getTermsandconditions());
			}
			invQuotationTO.setTotalCost(invQuotation.getTotalCost());
			
			invQuotationTOList.add(invQuotationTO);
		}
		log.debug("leaving copyQuotationBosToTos");
		return invQuotationTOList;
	}

	/**
	 * 
	 * @param quotationList
	 * @return
	 */
	public List<InvQuotationItemTO> copyQuotationItemBosToTos(List<InvQuotationItem> quotationList) {
		log.debug("inside copyQuotationBosToTos");
		List<InvQuotationItemTO> invQuotationItemTOList = new ArrayList<InvQuotationItemTO>();
		Iterator<InvQuotationItem> iterator = quotationList.iterator();
		
		InvQuotationItem invQuotationItem;
		InvQuotationItemTO invQuotationItemTO;
		InvItemTO invItemTO;
		//iterator = quotationList.iterator();
		while (iterator.hasNext()) {
			invQuotationItemTO = new InvQuotationItemTO();
			invQuotationItem = (InvQuotationItem) iterator.next();
			invQuotationItemTO.setId(invQuotationItem.getId());
			if(invQuotationItem.getInvItem()!= null){
				invItemTO = new InvItemTO();
				invItemTO.setId(invQuotationItem.getInvItem().getId());
				invItemTO.setName(invQuotationItem.getInvItem().getName());
				invQuotationItemTO.setInvItemTO(invItemTO);
			}
			invQuotationItemTO.setQuantity(invQuotationItem.getQuantity());
			invQuotationItemTO.setUnitCost(invQuotationItem.getUnitCost());
			
			invQuotationItemTOList.add(invQuotationItemTO);
		}
		log.debug("leaving copyQuotationItemBosToTos");
		return invQuotationItemTOList;
	}
	
	/**
	 * Used to prepare the items
	 */
	public List<ItemTO> prepareItems(InvQuotation quotation)throws Exception{
		List<ItemTO> itemList = new ArrayList<ItemTO>();
		ItemTO itemTO = null;
		if(quotation!=null && quotation.getInvQuotationItems()!=null){
			Iterator<InvQuotationItem> iterator = quotation.getInvQuotationItems().iterator();
			while (iterator.hasNext()) {
				double quantity = 0.0;
				double purchaseCost = 0.0;
				double totalCost = 0.0;
				InvQuotationItem invQuotationItem = iterator.next();
				if(invQuotationItem.getIsActive()){
					StringBuffer buffer = new StringBuffer();
					itemTO = new ItemTO();
					if(invQuotationItem.getInvItem()!=null){
						if(invQuotationItem.getInvItem().getName()!=null){
							buffer.append(invQuotationItem.getInvItem().getName());
						}
						if(invQuotationItem.getInvItem().getCode()!=null){
							buffer.append("("+invQuotationItem.getInvItem().getCode()+")");
						}
					}
					itemTO.setNameWithCode(buffer.toString());
					if(quotation.getInvVendor()!=null && quotation.getInvVendor().getName()!=null){
						itemTO.setVendorName(quotation.getInvVendor().getName());
					}
					if(quotation.getRemarks()!=null){
						itemTO.setRemarks(quotation.getRemarks());
					}
					if(invQuotationItem.getQuantity()!=null){
						itemTO.setQuantityIssued(String.valueOf(invQuotationItem.getQuantity()));
						quantity = invQuotationItem.getQuantity().doubleValue();
					}
					if(invQuotationItem.getUnitCost()!=null){
						itemTO.setPurchaseCost(new BigDecimal(String.valueOf(invQuotationItem.getUnitCost())));	
						purchaseCost = invQuotationItem.getUnitCost().doubleValue();
					}
					totalCost = Double.valueOf(purchaseCost) * Double.valueOf(quantity);
					itemTO.setTotalCost(String.valueOf(totalCost));
					itemList.add(itemTO);
				}
			}
		}
		return itemList;
	}
	
}
