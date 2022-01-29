package com.kp.cms.transactions.admin;

import java.util.List;

import com.kp.cms.bo.admin.ExtraCreditActivityType;

public interface IExtraCreditActivityTypeTransaction {

	List<ExtraCreditActivityType> getActivityList();

	public ExtraCreditActivityType isActivityDuplicated(
			ExtraCreditActivityType extraCreditActivityType1)throws Exception;

	boolean addActivity(ExtraCreditActivityType extraCreditActivityType)throws Exception;

	boolean editActivity(ExtraCreditActivityType extraCreditActivityType)throws Exception;

	boolean deleteCaste(int activityId, String userId)throws Exception;

	boolean reActivateCaste(ExtraCreditActivityType type, String userId)throws Exception;

}
