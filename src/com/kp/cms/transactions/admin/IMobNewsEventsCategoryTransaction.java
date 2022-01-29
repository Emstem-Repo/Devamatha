package com.kp.cms.transactions.admin;

import java.util.List;

import com.kp.cms.bo.admin.Currency;
import com.kp.cms.bo.admin.MobNewsEventsCategory;

public interface IMobNewsEventsCategoryTransaction {
	public List<MobNewsEventsCategory> getMobNewsEventsCategory() throws Exception;
	public MobNewsEventsCategory isCategoryExis(String category) throws Exception;
	public boolean addMobNewsEventsCategory(MobNewsEventsCategory mobNewsEventsCategory) throws Exception;
	public boolean deleteMobNewsEventsCategory(int id, String userId) throws Exception;
	public MobNewsEventsCategory editMobNewsEventsCategory(int id) throws Exception;
	public boolean updateMobNewsEventsCategory(MobNewsEventsCategory mobNewsEventsCategory) throws Exception;
	public boolean reActivateMobNewsEventsCategory(int dupId, String userId) throws Exception;
}
