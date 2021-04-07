package com.kp.cms.transactions.admin;

import java.util.List;

import com.kp.cms.bo.admin.Country;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.DuplicateException;

/**
 * 
 * Interface class for country Transaction
 */
public interface ICountryTransaction {
	
    	public boolean addCountry(Country country) throws DuplicateException,ApplicationException;
    	public List<Country> getCountries();
    	public boolean updateCountry(Country country) throws Exception;
    	public boolean deleteCountry(Country country) throws Exception; 
}
