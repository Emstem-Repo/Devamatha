package com.kp.cms.transactions.fee;

import java.util.List;

import com.kp.cms.bo.admin.FeeGroup;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;

/**
 * 
 * Date 10/Feb/2009
 */
public interface IFeeGroupTransaction {
	public List<FeeGroup> getFeeGroups() throws Exception;
	public FeeGroup getFeeGroup(int groupId) throws Exception;
	public void addFeeGroupEntry(String feeGroupname, String optional, String userId) throws Exception;
	public void reActivateFeeGroupEntry(String feeGroupname, String userId) throws Exception;
	public void deleteFeeGroupEntry(int feeGroupId, String userId) throws Exception;
	public void updateFeeGroupEntry(int feeGroupId, String feeGroupName, String option, String feeGroupNameOriginal, String userId) throws DuplicateException,ReActivateException,Exception;
	public List<FeeGroup> getOptionalFeeGroups() throws Exception;
	public List<FeeGroup> getNonOptionalFeeGroups() throws Exception;
}
