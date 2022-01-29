package com.kp.cms.transactions.admin;

import java.util.List;
import com.kp.cms.bo.admin.Currency;

public interface ICurrencyMasterTransaction {

	public List<Currency> getCurrencyMasters() throws Exception;
	public Currency isCurrencyMasterExist(String currencyName, int id) throws Exception;
	public Currency isCurrencyShortNameExist(String currencyShortName, int id) throws Exception;
	public boolean addCurrencyMaster(Currency currency) throws Exception;
	public boolean deleteCurrencyMaster(int id, String userId) throws Exception;
	public Currency editCurrencyMaster(int id) throws Exception;
	public boolean updateCurrencyMaster(Currency currency) throws Exception;
	public boolean reActivateCurrencyMaster(String currencyName, String userId) throws Exception;
	
}
