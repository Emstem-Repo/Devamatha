package com.kp.cms.transactions.fee;

import java.util.List;

import com.kp.cms.bo.admin.FeeFinancialYear;
import com.kp.cms.bo.admin.FeeVoucher;
import com.kp.cms.forms.fee.ConcessionSlipBooksForm;

public interface IConcessionSlipBooksTxn {
	public FeeVoucher isBookNoDuplcated(ConcessionSlipBooksForm slipBooksForm) throws Exception;
	public boolean addSlipBook(FeeVoucher feeVoucher, String mode) throws Exception;
	public List<FeeVoucher> getSlipBookDetails() throws Exception;
	public boolean deleteSlipBook(int id, Boolean activate, ConcessionSlipBooksForm slipBooksForm) throws Exception;
	public FeeFinancialYear getCurrentFeeFinancialYear() throws Exception;
}
