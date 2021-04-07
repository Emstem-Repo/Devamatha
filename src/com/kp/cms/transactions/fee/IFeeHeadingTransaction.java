package com.kp.cms.transactions.fee;

import java.util.List;

import com.kp.cms.bo.admin.FeeHeading;

/**
 * @Date 19/jan/2009
 *  This is an Interface For FeeApplicable Transaction Impl
 */
public interface IFeeHeadingTransaction {
		
	public List getAllFeeHeadings(int groupId) throws Exception;
	
	public List getAllFeeHeadings() throws Exception;
	
	public FeeHeading isFeeHeadingNameExist(int feeGroupId, String feesName) throws Exception;
	
	public boolean addFeeHeading(FeeHeading feeHeading) throws Exception;

	public List<FeeHeading> editFeeHeading(int id) throws Exception;
	
	public boolean updateFeeHeadings(FeeHeading feeHeading) throws Exception;
	
	public boolean deleteFeeHeadings(int id, String userId) throws Exception;
	
	public void reActivateFeeHeadings(String feesName, String userId)throws Exception;
}
