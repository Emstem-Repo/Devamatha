package com.kp.cms.transactions.inventory;

import java.util.List;

import com.kp.cms.bo.admin.InvAmc;

public interface IAmcDetailsTransaction {
	public List<InvAmc> getAmcDetails(int itemCategId, String itemNo) throws Exception;
	public boolean addAmcDetails(List<InvAmc> amcBOList) throws Exception;
	public List<InvAmc> getWarrenty(int itemCategId, String itemNo) throws Exception;
	public List<InvAmc> getAmcHistoryDetails(int itemCategId, String selectedItemNo) throws Exception;
}
