package com.kp.cms.transactions.pettycash;

import java.util.List;

import com.kp.cms.bo.admin.AccountHeads;
import com.kp.cms.bo.admin.PcAccHeadGroup;
import com.kp.cms.bo.admin.PcAccountHead;
import com.kp.cms.bo.admin.PcBankAccNumber;
import com.kp.cms.bo.admin.PettyCashCollection;
import com.kp.cms.bo.admin.PettyCashCollectionDetails;
import com.kp.cms.forms.pettycash.PettyCashAccountHeadsForm;


public interface IPettyCashAccountHeads {
	
	public List<PcAccountHead> getAllpettyCashAccHeads() throws Exception;
	public List<PcAccHeadGroup> getAllpettyCashAccHeadGroup() throws Exception;
	public List<PcBankAccNumber> getAllpettyCashBankAccNumber() throws Exception;
	public boolean manageAccountHead(PcAccountHead pcAccountHead,String mode) throws Exception;
	public PcAccountHead getPettyCashAccountHead(Integer id) throws Exception;
	public PcAccountHead existanceCheck(String  accCode) throws Exception;
	public PcBankAccNumber getPettyCashBankAccNumberById(String id) throws Exception;
	public List<AccountHeads> getAccountHeadsDetails(PettyCashAccountHeadsForm pettyCashAccHeadForm)throws Exception;
	public List<PettyCashCollection> getCollectionDetails(PettyCashAccountHeadsForm pettyCashAccHeadForm)throws Exception;
	public List<PettyCashCollectionDetails> getCollectionDetailsWithReceiptNoAndAppNo(int recieptNo,String AccAppNo)throws Exception;
}
