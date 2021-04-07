package com.kp.cms.transactions.pettycash;

import java.util.List;

import com.kp.cms.bo.admin.PcFinancialYear;
import com.kp.cms.bo.admin.PcReceiptNumber;
import com.kp.cms.forms.pettycash.LastReceiptNumberForm;

public interface IReceiptNumberTransaction {
	
	public List<PcReceiptNumber> getReceiptnumber() throws Exception;
	public boolean addReceiptNumber(PcReceiptNumber pcReceiptNumber,String mode) throws Exception;
	public PcReceiptNumber isPcReceiptnumberDuplicated(PcReceiptNumber pcreceiptnumber,String mode) throws Exception ;
	public PcFinancialYear getFinancialYear() throws Exception ;
	public List<PcFinancialYear> getFinancialYearList() throws Exception ;
	public boolean deleteReceiptNumber( int receiptNoId,Boolean activate,LastReceiptNumberForm lastReceiptNumberForm) throws Exception;
	public int maxReceiptNumber() throws Exception;
	public List<Object> financialYearList() throws Exception;
	public PcReceiptNumber getPreviouYearReceiptNumberDetails(int prevfinanicalYear) throws Exception;
	public PcFinancialYear getFinancialYear(int id) throws Exception ;
	public PcFinancialYear getFinancialYear(String finYear) throws Exception;
}
