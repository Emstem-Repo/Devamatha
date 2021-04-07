package com.kp.cms.handlers.pettycash;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.PcAccountHead;
import com.kp.cms.bo.admin.PcReceipts;
import com.kp.cms.exceptions.DataNotFoundException;
import com.kp.cms.forms.pettycash.ModifyCashCollectionForm;
import com.kp.cms.helpers.pettycash.CashCollectionHelper;
import com.kp.cms.helpers.pettycash.ModifyCashCollectionHelper;
import com.kp.cms.to.pettycash.AccountHeadTO;
import com.kp.cms.to.pettycash.CashCollectionTO;
import com.kp.cms.transactions.pettycash.ICashCollectionTransaction;
import com.kp.cms.transactions.pettycash.IModifyCashCollectionTransaction;
import com.kp.cms.transactionsimpl.pettycash.CashCollectionTransactionImpl;
import com.kp.cms.transactionsimpl.pettycash.ModifyCashCollectionTransactionImpl;

public class ModifyCashCollectionHandler {
	private static final Log log = LogFactory
			.getLog(ModifyCashCollectionHandler.class);

	private static volatile ModifyCashCollectionHandler modifyCashCollectionHandler;

	public static ModifyCashCollectionHandler getinstance() {
		if (modifyCashCollectionHandler == null) {
			modifyCashCollectionHandler = new ModifyCashCollectionHandler();
			return modifyCashCollectionHandler;
		}
		return modifyCashCollectionHandler;

	}

	/*
	 * Get the details as per the receipt number entered 
	 * modifyCashCollectionForm
	 */
	public List<CashCollectionTO> getDetailsToFill(
			ModifyCashCollectionForm modifyCashCollectionForm) throws Exception {
		IModifyCashCollectionTransaction modfyCashCollectionImpl = ModifyCashCollectionTransactionImpl
				.getInstance();
		List<PcReceipts> pcReceiptList = null;
		List<CashCollectionTO> cashCollectionToList = null;
		pcReceiptList = modfyCashCollectionImpl
				.getDetailsToFill(modifyCashCollectionForm.getRecNumber(),modifyCashCollectionForm.getFinYearId());
		if (pcReceiptList != null && !pcReceiptList.isEmpty()) {
			cashCollectionToList = ModifyCashCollectionHelper.getInstance()
					.covertBoListToToList(pcReceiptList);
			return cashCollectionToList;
		}

		return cashCollectionToList;

	}

	public List<CashCollectionTO> getAccountNameWithCodeToList(
			ModifyCashCollectionForm modifyCashCollectionform) throws Exception {

		log.info("entering into getAccountNameWithCodeToList in CashCollectionHandler class..");
		ICashCollectionTransaction cashCollectionTransaction = CashCollectionTransactionImpl
				.getInstance();
		List<PcAccountHead> pcAccountHeadBoList = cashCollectionTransaction
				.getAccountNameWithCodeToList("Modify");

		List<CashCollectionTO> pcAccountHeadTo = ModifyCashCollectionHelper
				.getInstance().convertAccDetailsToTo(pcAccountHeadBoList,
						modifyCashCollectionform);

		log.info("the list after getting years is in handler after coverting to to");

		log.info("leaving from getAccountNameWithCodeToList in CashCollectionHandler class..");
		return pcAccountHeadTo;
	}

	public AccountHeadTO getAmount(ModifyCashCollectionForm modifyCashcollectionForm) throws Exception {

		log.info("entering into getAmount in CashCollectionHandler class..");
		CashCollectionTO accNameAndCode = null;
		accNameAndCode = ModifyCashCollectionHelper.getInstance()
				.getAccnameAndCode(modifyCashcollectionForm);
		String accId = accNameAndCode.getAccId();
		ICashCollectionTransaction cashCollectionTransaction = CashCollectionTransactionImpl
				.getInstance();
		List amountList = cashCollectionTransaction.getAmount(accId);

		AccountHeadTO accTo = CashCollectionHelper.getInstance()
				.convertAmountsListToTo(amountList);

		log.info("leaving from getFineDetails in CashCollectionHandler class..");
		return accTo;
	}

	public boolean updateCashCollection(ModifyCashCollectionForm modifyCashCollectionForm) throws Exception {
		boolean isUpdated = false;
		log.info("entering into saveCashCollection in ..");
		
		
		IModifyCashCollectionTransaction modifyCashCollectionTxn = ModifyCashCollectionTransactionImpl
				.getInstance();
		
		List<PcReceipts> updatedList = ModifyCashCollectionHelper.getInstance().prepareBoObjectsToSaveAndUpdate(modifyCashCollectionForm);
		isUpdated = modifyCashCollectionTxn.updateCashCollection(updatedList);
		return isUpdated;
	}
	
	public void getPrintWhileSaveInModify(
			ModifyCashCollectionForm modifyCashCollectionForm,
			HttpServletRequest request) throws Exception{
		ICashCollectionTransaction cashCollectionTransaction = CashCollectionTransactionImpl.getInstance();
		//int finYearIdEntered = Integer.valueOf(modifyCashCollectionForm.getFinancialYearId());
		int finacialYearId = cashCollectionTransaction.getFinancialYear();
		List<PcReceipts> pcReceiptsList = null;
		//int finacialYearId = Integer.parseInt(cashCollectionForm.getFinYearId());
		pcReceiptsList = cashCollectionTransaction.getReceiptDetailsForEdit(Integer.parseInt(modifyCashCollectionForm.getRecNoResult()),finacialYearId);
		
		if(pcReceiptsList == null || pcReceiptsList.isEmpty()) {
			throw new DataNotFoundException();
		}
		ModifyCashCollectionHelper.getInstance().setReceiptNumberDatatoForm(pcReceiptsList,modifyCashCollectionForm);
		log.debug("Leaving the getReceiptDetails");
		
	}
}
