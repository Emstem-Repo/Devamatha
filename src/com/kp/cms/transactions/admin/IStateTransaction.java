package com.kp.cms.transactions.admin;

import java.util.List;

import com.kp.cms.bo.admin.State;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.forms.admin.CountryStateCityForm;

/**
 * 
 * Interface for State Transactios
 */
public interface IStateTransaction {

	public boolean addState(State state)throws DuplicateException,ApplicationException, BusinessException, Exception;
	public List<State> getStates() throws Exception;
	public List<State> getNativeStates(int countryId) throws Exception;
	public boolean updateState(State state);
	public boolean deleteState(int id, Boolean activate, CountryStateCityForm countryStateCityForm) throws Exception;
	public State isStateNameDuplcated(CountryStateCityForm countryStateCityForm) throws Exception;	
}
