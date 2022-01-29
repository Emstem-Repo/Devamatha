package com.kp.cms.transactions.pettycash;

import java.util.List;

import com.kp.cms.bo.admin.AccountHeads;
import com.kp.cms.bo.admin.PettyCashCollection;
import com.kp.cms.bo.admin.PettyCashCollectionDetails;
import com.kp.cms.forms.pettycash.AccountHeadsForm;

public interface IAccountHeadsTransaction {

	public boolean uploadAccountHeads(List<AccountHeads> accountHeads)throws Exception;

	public boolean uploadPettyCashCollection( List<PettyCashCollection> cashCollections,AccountHeadsForm accountHeadsForm)throws Exception;

	public boolean uploadPettyCashCollectionDetails( List<PettyCashCollectionDetails> cashCollectionDetails,AccountHeadsForm accountHeadsForm)throws Exception;

	public boolean isDuplicateYear(AccountHeadsForm accountHeadsForm)throws Exception;

	
}
