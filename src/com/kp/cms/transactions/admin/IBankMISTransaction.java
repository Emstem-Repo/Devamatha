package com.kp.cms.transactions.admin;

import java.util.List;

import com.kp.cms.bo.admin.BankMis;
import com.kp.cms.to.admin.BankMISTO;

public interface IBankMISTransaction {
	
	public List getRefNos() throws Exception;
	public boolean saveBankDetails(List<BankMISTO> bankList) throws Exception;

}
