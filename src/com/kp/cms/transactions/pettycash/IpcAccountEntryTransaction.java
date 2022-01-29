package com.kp.cms.transactions.pettycash;

import com.kp.cms.bo.admin.PcBankAccNumber;

public interface IpcAccountEntryTransaction {

	PcBankAccNumber checkDuplicate(String accNo) throws Exception;

	boolean savePcBankAccNo(PcBankAccNumber pb1) throws Exception;

	boolean updatePcBankAccNo(PcBankAccNumber pb1)throws Exception;

	boolean deletePcBankAccNo(String bankAccid, String userId) throws Exception;

	boolean reactivatePcBankAccNo(String bankAccid, String userId)throws Exception;

	PcBankAccNumber getPcBankAccDetailsWithId(String bankAccid) throws Exception;

}
