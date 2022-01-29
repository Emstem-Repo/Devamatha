package com.kp.cms.transactions.pettycash;

import java.util.List;

import com.kp.cms.bo.admin.PcAccHeadGroup;
import com.kp.cms.bo.admin.PcAccountHead;
import com.kp.cms.bo.admin.Users;

public interface ICollectionLedger {

	List<PcAccountHead> getAccountList() throws Exception;
	String getAccountName(String accCode) throws Exception;

	List getListOfData(String searchCriteria) throws Exception;

	Users verifyUser(String userName) throws Exception;

	PcAccHeadGroup verifyGroupCode(String groupCode) throws Exception;

}
