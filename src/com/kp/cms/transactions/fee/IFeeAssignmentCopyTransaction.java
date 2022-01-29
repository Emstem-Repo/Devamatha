package com.kp.cms.transactions.fee;

import java.util.List;

import com.kp.cms.bo.admin.Fee;

public interface IFeeAssignmentCopyTransaction {

	public List<Fee> getExistingFeeDefinitions(String queryToGetExistingFeesDefinition)throws Exception;

	public boolean isFeeDefinitionToBeCopiedAlreadyExists(String queryToGetFeeDefinitionToBecopiedExists)throws Exception;

	public boolean copyFees(List<Fee> feeListToBeCopied)throws Exception;

}
