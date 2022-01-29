package com.kp.cms.transactions.admin;

import java.util.List;

import com.kp.cms.bo.admin.TCNumber;
import com.kp.cms.bo.admin.TCPrefix;
import com.kp.cms.forms.admin.TCMasterForm;

public interface ITCMasterTransaction {

	List<TCNumber> getAllTCNumber() throws Exception;

	boolean addTCMaster(TCNumber bo,String mode) throws Exception;

	boolean deleteTCMaster(int id, Boolean activate, TCMasterForm tcMasterForm) throws Exception;

	TCNumber isTCNumberDuplcated(TCMasterForm tcMasterForm) throws Exception;

	List<TCPrefix> getTcPrefix() throws Exception;

}
