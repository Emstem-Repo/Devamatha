package com.kp.cms.transactions.pettycash;

import java.util.List;

import com.kp.cms.bo.admin.PcAccountHead;
import com.kp.cms.bo.admin.PcReceipts;

public interface IModifyCashCollectionTransaction {
	
	public List<PcReceipts> getDetailsToFill(String recNumber,String financialYearId) throws Exception;
	public boolean updateOrSaveCashCollection(PcReceipts pcReceipts,String mode) throws Exception;
	public PcAccountHead getAccountheadtoAdd(int id) throws Exception; 
	public PcAccountHead getAccountHeaddetails(int id) throws Exception;
	public boolean updateCashCollection(List<PcReceipts> updatedList)throws Exception;
	

}
