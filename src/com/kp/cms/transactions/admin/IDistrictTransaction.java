package com.kp.cms.transactions.admin;

import java.util.List;

import com.kp.cms.bo.admin.District;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.forms.admin.DistrictForm;

/**
 * 
 * Interface for State Transactios
 */
public interface IDistrictTransaction {

	public boolean addDistrict(District state)throws DuplicateException,ApplicationException, BusinessException, Exception;
	public List<District> getDistrict() throws Exception;
	public List<District> getNativeDistrict(int countryId) throws Exception;
	public boolean updateDistrict(District state);
	public boolean deleteDistrict(int id, Boolean activate, DistrictForm countryStateCityForm) throws Exception;
	public District isDistrictNameDuplcated(DistrictForm countryStateCityForm) throws Exception;	
}
