package com.kp.cms.transactions.inventory;

public interface IOpeningBalanceTransaction {

	 public boolean saveItemStock(int locationID, int itemID, double qty, String userid)  throws Exception;

}
