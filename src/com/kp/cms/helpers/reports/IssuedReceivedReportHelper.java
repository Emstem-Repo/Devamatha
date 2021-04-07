package com.kp.cms.helpers.reports;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.InvTx;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.reports.IssuedReceivedReportForm;
import com.kp.cms.to.reports.IssuedReceivedTO;
import com.kp.cms.utilities.CommonUtil;

public class IssuedReceivedReportHelper {

	private static final Log log = LogFactory.getLog(IssuedReceivedReportHelper.class);
	
	/**
	 * @param studentSearchForm
	 * @return
	 * This method will build dynamic query
	 */
	private static String commonSearch(IssuedReceivedReportForm issuedReceivedReportForm) {

		String searchCriteria = "";
		boolean containSearchCriteria = false;
		if (issuedReceivedReportForm.getInvLocationId().trim().length() > 0) {
			containSearchCriteria=true;
			String inventory = "  tx.invLocation.id = "
					+ issuedReceivedReportForm.getInvLocationId();
			searchCriteria = searchCriteria + inventory;
		}
		
		if (issuedReceivedReportForm.getItemId() !=null && issuedReceivedReportForm.getItemId().trim().length() > 0) {
			if(containSearchCriteria) {
				searchCriteria = searchCriteria + "  and  ";
			}
			containSearchCriteria=true;
			String item = "  tx.invItem.id = "
					+ issuedReceivedReportForm.getItemId();
			searchCriteria = searchCriteria + item;
		}
		if (issuedReceivedReportForm.getStartDate().trim().length() > 0) {
			if(containSearchCriteria) {
				searchCriteria = searchCriteria + "  and  ";
			}
			containSearchCriteria=true;
			String startDate = " tx.txDate >= '"
				+ CommonUtil.ConvertStringToSQLDate(issuedReceivedReportForm.getStartDate())+"'" ;
			searchCriteria = searchCriteria + startDate;
		}

		if (issuedReceivedReportForm.getEndDate().trim().length() > 0) {
			if(containSearchCriteria) {
				searchCriteria = searchCriteria + "  and  ";
			}
			containSearchCriteria=true;
			String endDate = " tx.txDate <= '"
				+ CommonUtil.ConvertStringToSQLDate(issuedReceivedReportForm.getEndDate())+"'";
			searchCriteria = searchCriteria + endDate;
		}
		
		return searchCriteria;
	}
	
	/**
	 * @param studentSearchForm
	 * @return
	 * This method will give final query
	 */
	public static String getSelectionSearchCriteria(
			IssuedReceivedReportForm issuedReceivedReportForm) {
		log.info("entered getSelectionSearchCriteria..");
		String statusCriteria = commonSearch(issuedReceivedReportForm);

		String searchCriteria= "";

		//		String ednJoin = "";
		searchCriteria =  " from InvTx tx "
			+ " where " + statusCriteria;					
		log.info("exit getSelectionSearchCriteria..");
		return searchCriteria;

	}

	/**
	 * @param txnBos
	 * @return
	 */
	public static List<IssuedReceivedTO> populateTOList(List<InvTx> txnBos) {
		List<IssuedReceivedTO> issuedReceivedTOList = new ArrayList<IssuedReceivedTO>(); 
		if(txnBos!=null){
			Iterator<InvTx> itrTx = txnBos.iterator();
			while(itrTx.hasNext()){
				InvTx invTx = (InvTx)itrTx.next();
				IssuedReceivedTO issuedReceivedTO = new IssuedReceivedTO();
				if(invTx.getInvLocation()!=null && invTx.getInvLocation().getName()!=null){
					issuedReceivedTO.setInventoryLocation(invTx.getInvLocation().getName());
				}
				if(invTx.getInvItem()!=null && invTx.getInvItem().getName()!=null){
					issuedReceivedTO.setItem(invTx.getInvItem().getName());
				}
				if(invTx.getTxDate()!=null){
					issuedReceivedTO.setTxDate(CommonUtil.getStringDate(invTx.getTxDate()));
				}
				double openingBalance = 0.0;
				double closingBalance = 0.0;
				double differenceBalance = 0.0;
				if(invTx.getOpeningBalance()!=null){
					issuedReceivedTO.setOpeningBalance(invTx.getOpeningBalance().doubleValue());
					openingBalance = invTx.getOpeningBalance().doubleValue();
				}
				if(invTx.getClosingBalance()!=null){
					issuedReceivedTO.setCurrentBalance(invTx.getClosingBalance().doubleValue());
					closingBalance = invTx.getClosingBalance().doubleValue(); 
				}

				if(invTx.getTxType()!=null){
					String txType = "";
					if(invTx.getTxType().equalsIgnoreCase(CMSConstants.RECEIPT_TX_TYPE)){
						txType = "Received";
						differenceBalance = closingBalance - openingBalance;
					}else if(invTx.getTxType().equalsIgnoreCase(CMSConstants.ISSUE_TX_TYPE)){
						txType = "Issued";
						differenceBalance = openingBalance - closingBalance;
					}else if(invTx.getTxType().equalsIgnoreCase(CMSConstants.RETURN_TX_TYPE)){
						txType = "Purchase Return";
						differenceBalance = openingBalance - closingBalance;
					}else if(invTx.getTxType().equalsIgnoreCase(CMSConstants.STOCK_TX_ISSUED)){
						txType = "Stock Issued";
						differenceBalance = openingBalance - closingBalance;
					}else if(invTx.getTxType().equalsIgnoreCase(CMSConstants.STOCK_TX_RECIEVED)){
						txType = "Stock Received";
						differenceBalance = closingBalance - openingBalance;
					}
					else {
						if(invTx.getTxType().equalsIgnoreCase(CMSConstants.SALVAGE_TX_TYPE)){
							txType = "Salvaged";
							differenceBalance = openingBalance - closingBalance;
						}
					}
					issuedReceivedTO.setDifferenceBalance(differenceBalance);
					issuedReceivedTO.setTxType(txType);
				}
				
				issuedReceivedTOList.add(issuedReceivedTO);
			}
		}		
		return issuedReceivedTOList;
	}

}
