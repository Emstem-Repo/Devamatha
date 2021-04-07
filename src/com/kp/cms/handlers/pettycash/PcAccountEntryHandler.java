package com.kp.cms.handlers.pettycash;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.PcBankAccNumber;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.pettycash.PcAccountEntryForm;
import com.kp.cms.helpers.pettycash.PcAccountEntryHelper;
import com.kp.cms.to.pettycash.PcAccountTo;
import com.kp.cms.transactions.admin.ISingleFieldMasterTransaction;
import com.kp.cms.transactions.pettycash.IpcAccountEntryTransaction;
import com.kp.cms.transactionsimpl.admin.SingleFieldMasterTransactionImpl;
import com.kp.cms.transactionsimpl.pettycash.PcAccountEntryTransactionImpl;

public class PcAccountEntryHandler {
	/**
	 * Singleton object of PcAccountEntryHandler
	 */
	private static volatile PcAccountEntryHandler pcAccountEntryHandler = null;
	private static final Log log = LogFactory.getLog(PcAccountEntryHandler.class);
	IpcAccountEntryTransaction transaction=new PcAccountEntryTransactionImpl();
	private PcAccountEntryHandler() {
		
	}
	/**
	 * return singleton object of PcAccountEntryHandler.
	 * @return
	 */
	public static PcAccountEntryHandler getInstance() {
		if (pcAccountEntryHandler == null) {
			pcAccountEntryHandler = new PcAccountEntryHandler();
		}
		return pcAccountEntryHandler;
	}
	/**
	 * getting the account list which are active.
	 * @return
	 * @throws Exception
	 */
	public List<PcAccountTo> getAccountList() throws Exception{
		ISingleFieldMasterTransaction txn=new SingleFieldMasterTransactionImpl();
		List<PcBankAccNumber> finalList=txn.getBankAccNo();
		return PcAccountEntryHelper.getInstance().convertBotoToList(finalList);
	}
	
	/**
	 * adding the account no and logo to the database
	 * @param accountEntryForm
	 * @return
	 * @throws Exception
	 */
	public boolean addPcAccount(PcAccountEntryForm accountEntryForm, HttpServletRequest request) throws Exception{
		PcBankAccNumber pb=transaction.checkDuplicate(accountEntryForm.getAccountNo().trim());
		if(pb!=null){
			if(pb.getIsActive()){
				throw new DuplicateException();
			}else{
				request.setAttribute("accountId",pb.getId());
				throw new ReActivateException();
			}
		}
		PcBankAccNumber pb1=PcAccountEntryHelper.getInstance().convertTotoBo(accountEntryForm);
		return transaction.savePcBankAccNo(pb1);
	}
	public boolean updatePcAccount(PcAccountEntryForm accountEntryForm, HttpServletRequest request)  throws Exception{
		if(!accountEntryForm.getAccountNo().equals(accountEntryForm.getAccountTo().getAccountNo()) && accountEntryForm.getThefile().getFileData().length<=0){
			PcBankAccNumber pb=transaction.checkDuplicate(accountEntryForm.getAccountNo().trim());
			if(pb!=null){
				if(pb.getIsActive()){
					throw new DuplicateException();
				}else{
					request.setAttribute("accountId",pb.getId());
					throw new ReActivateException();
				}
			}
		}
		PcBankAccNumber pb1=PcAccountEntryHelper.getInstance().convertTotoBoForUpdate(accountEntryForm);
		return transaction.updatePcBankAccNo(pb1);
	}
	/**
	 * deleting the record from database
	 * @param bankAccid
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public boolean deletePcBankAccNo(String bankAccid, String userId) throws Exception {
		return transaction.deletePcBankAccNo(bankAccid,userId);
	}
	public boolean reActivateBankAccNo(String bankAccid, String userId) throws Exception {
		return transaction.reactivatePcBankAccNo(bankAccid,userId);
	}
	public PcAccountTo getPcBankAccDetailsWithId(String bankAccid) throws Exception {
		PcBankAccNumber pcBankAccNumber=transaction.getPcBankAccDetailsWithId(bankAccid);
		return PcAccountEntryHelper.getInstance().convertBotoToById(pcBankAccNumber);
	}
}
