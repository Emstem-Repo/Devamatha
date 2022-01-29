package com.kp.cms.transactions.pettycash;

import java.util.List;

import com.kp.cms.bo.admin.PcFinancialYear;
import com.kp.cms.bo.admin.PcReceiptNumber;

public interface ILastReceiptnumbertransaction {
	
	public List<PcReceiptNumber> getReceiptnumberdetails() throws Exception;
	List<PcFinancialYear> getFinancialYearList() throws Exception;
	public PcReceiptNumber getLastReceiptNumberYear(int year) throws Exception;	
	public boolean addLastReceiptNumber(PcReceiptNumber number) throws Exception;
	public boolean deleteLastReceiptNumber(int receiptId, String userId)throws Exception;
	public boolean reActivateLastReceiptNumber(int year, String userId)throws Exception;
	public PcReceiptNumber getLastReceiptNumberDetailsonId(int id)throws Exception;
	public boolean updateLastReceiptNumber(PcReceiptNumber number)throws Exception;

}
