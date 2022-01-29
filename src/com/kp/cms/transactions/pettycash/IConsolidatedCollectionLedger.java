package com.kp.cms.transactions.pettycash;

import java.util.List;

import com.kp.cms.bo.admin.Users;

public interface IConsolidatedCollectionLedger {

	List getListOfData(String searchCriteria) throws Exception;

	Users verifyUser(String userName) throws Exception;

}
