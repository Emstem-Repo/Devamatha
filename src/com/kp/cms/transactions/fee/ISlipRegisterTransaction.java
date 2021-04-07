package com.kp.cms.transactions.fee;

import java.util.List;

public interface ISlipRegisterTransaction {
	
	public List<Object[]> getSlipRegisterRecords(String query) throws Exception;

}
