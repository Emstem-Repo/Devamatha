package com.kp.cms.handlers.pettycash;

import java.util.List;

import com.kp.cms.bo.admin.AccountHeads;
import com.kp.cms.bo.admin.PettyCashCollection;
import com.kp.cms.bo.admin.PettyCashCollectionDetails;
import com.kp.cms.forms.pettycash.AccountHeadsForm;
import com.kp.cms.helpers.pettycash.AccountHeadsHelper;
import com.kp.cms.to.pettycash.AccountHeadsTo;
import com.kp.cms.to.pettycash.PettyCashCollectionDetailsTo;
import com.kp.cms.to.pettycash.PettyCashCollectionTo;
import com.kp.cms.transactions.pettycash.IAccountHeadsTransaction;
import com.kp.cms.transactionsimpl.pettycash.AccountHeadsTxnImpln;

public class AccountHeadsHandler {
	private static volatile AccountHeadsHandler accountHeadsHandler = null;

	public static AccountHeadsHandler getInstance() {
		if (accountHeadsHandler == null) {
			accountHeadsHandler = new AccountHeadsHandler();
			return accountHeadsHandler;
		}
		return accountHeadsHandler;
	}

	IAccountHeadsTransaction transaction = AccountHeadsTxnImpln.getInstance();

	/**
	 * @param accountHeadsList
	 * @return
	 * @throws Exception
	 */
	public boolean uploadAccountHeads(List<AccountHeadsTo> accountHeadsList)
			throws Exception {
		List<AccountHeads> accountHeads = AccountHeadsHelper.getInstance()
				.convertTOToBO(accountHeadsList);
		return transaction.uploadAccountHeads(accountHeads);
	}

	/**
	 * @param pettyCashCollectionList
	 * @return
	 */
	public boolean uploadPettyCashCollection(
			List<PettyCashCollectionTo> pettyCashCollectionList,AccountHeadsForm accountHeadsForm)
			throws Exception {
		List<PettyCashCollection> cashCollections = AccountHeadsHelper
				.getInstance().convertTOToBO1(pettyCashCollectionList);
		return transaction.uploadPettyCashCollection(cashCollections,accountHeadsForm);
	}

	/**
	 * @param pettyCashCollectionDetailsList
	 * @return
	 * @throws Exception
	 */
	public boolean uploadPettyCashCollectionDetails(
			List<PettyCashCollectionDetailsTo> pettyCashCollectionDetailsList,AccountHeadsForm accountHeadsForm)
			throws Exception {
		List<PettyCashCollectionDetails> cashCollectionDetails = AccountHeadsHelper
				.getInstance().populateToTOBo(pettyCashCollectionDetailsList);
		return transaction
				.uploadPettyCashCollectionDetails(cashCollectionDetails,accountHeadsForm);
	}

	public boolean checkDuplicate(AccountHeadsForm accountHeadsForm) throws Exception{
			return transaction.isDuplicateYear(accountHeadsForm);
	}
}
