package com.kp.cms.forms.admin;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;


/**
 * 
 * This Form class is used for Country State City. 
 */

public class CountryStateCityForm extends BaseActionForm {
            
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String countryName;
	private String stateName;
	private String cityName;
	private String cityId;
	private String origStateName;
	private int duplStateId;
	private int editedCountryId;
	private String bankStateId;
	private String origBankStateId;

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	
	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}
	

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		this.cityName = null;
		this.countryName = null;
		this.stateName = null;
		this.bankStateId = null;
		super.setCountryId(null);
	}
	
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		
		return actionErrors;
	}

	public String getOrigStateName() {
		return origStateName;
	}

	public void setOrigStateName(String origStateName) {
		this.origStateName = origStateName;
	}

	public int getDuplStateId() {
		return duplStateId;
	}

	public void setDuplStateId(int duplStateId) {
		this.duplStateId = duplStateId;
	}

	public int getEditedCountryId() {
		return editedCountryId;
	}

	public void setEditedCountryId(int editedCountryId) {
		this.editedCountryId = editedCountryId;
	}

	public String getBankStateId() {
		return bankStateId;
	}

	public void setBankStateId(String bankStateId) {
		this.bankStateId = bankStateId;
	}

	public String getOrigBankStateId() {
		return origBankStateId;
	}

	public void setOrigBankStateId(String origBankStateId) {
		this.origBankStateId = origBankStateId;
	}

}
