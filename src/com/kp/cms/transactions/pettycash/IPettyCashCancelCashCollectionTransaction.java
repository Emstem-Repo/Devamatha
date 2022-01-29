package com.kp.cms.transactions.pettycash;



import java.util.List;

import com.kp.cms.bo.admin.PcReceipts;

public interface IPettyCashCancelCashCollectionTransaction {
	public List<PcReceipts> getAllCashCollectionByReceiptNumber(String number,String finYearId) throws Exception;
	public PcReceipts getCashCollectionByReceiptNumber(String number,String finYearId) throws Exception;
	public boolean manageCashCollection(PcReceipts pcReceipts) throws Exception;
	public int getCurrentFinancialYear() throws Exception;
}
