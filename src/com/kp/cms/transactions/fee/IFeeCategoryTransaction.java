package com.kp.cms.transactions.fee;

import java.util.List;

import com.kp.cms.bo.fees.FeeCategory;

public interface IFeeCategoryTransaction {

	public boolean addFeeCategory(FeeCategory feeCategory) throws Exception;

	public boolean deleteFeeCategory(int feeCategoryId, String userId) throws Exception;

	public boolean reActivateFeeCategory(FeeCategory feeCategory, String userId) throws Exception;

	public List<FeeCategory> getFeeCategories() throws Exception;

	public FeeCategory isFeeCategoryDuplcated(FeeCategory feeCategory) throws Exception;

	public boolean updateFeeCategory(FeeCategory feeCategory) throws Exception;
}
