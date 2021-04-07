package com.kp.cms.transactions.pettycash;

import java.util.List;

import com.kp.cms.bo.admin.PcBankAccNumber;
import com.kp.cms.bo.admin.PcReceipts;

public interface ICollectionsTransaction {

	public List<PcBankAccNumber> getAccountNos () throws Exception;

	public List<PcReceipts> getCollectionsDetails(String dynamicQuery)throws Exception;
	
}
