package com.kp.cms.transactions.fee;

import java.util.List;

import com.kp.cms.bo.admin.FeeAccount;

/**
 * 
 * @date 19/Jan/2009
 * This is an interface for FeeAccountTransaction.
 */
public interface IFeeAccountTransaction {
		
	   public List getAllFeeAccounts() throws Exception;
	   public List getAllFeeAccountIds() throws Exception;
	   public   FeeAccount existanceCheck(FeeAccount  feeAccount) throws Exception;
	   public boolean addFeeAccount(FeeAccount  feeAccount,String mode) throws Exception;
	   public List<FeeAccount> getFeeAccounts() throws Exception;
	   public FeeAccount loadFeeAccount(FeeAccount feeAccount) throws Exception;
	   public FeeAccount getFeeAccountData(int id) throws Exception;
}