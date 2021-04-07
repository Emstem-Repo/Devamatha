package com.kp.cms.transactions.inventory;

import java.util.List;

import com.kp.cms.bo.admin.InvCounter;
import com.kp.cms.forms.inventory.CounterMasterForm;

public interface ICounterMasterTransaction {
	public List<InvCounter> getCounterDetails() throws Exception;
	public InvCounter isCounterDuplcated(CounterMasterForm counterMasterForm) throws Exception;
	public boolean addCounterMaster(InvCounter invCounter, String mode) throws Exception;	
	public boolean deleteCounter(int id, Boolean activate, CounterMasterForm counterMasterForm) throws Exception;
	public boolean isAlreadyExists(String query) throws Exception;
	public InvCounter getCounterDetails(int id) throws Exception;	
}
