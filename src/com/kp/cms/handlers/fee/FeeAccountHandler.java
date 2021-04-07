package com.kp.cms.handlers.fee;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;

import com.kp.cms.bo.admin.FeeAccount;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.fee.FeeAccountForm;
import com.kp.cms.helpers.fee.FeeAccountHelper;
import com.kp.cms.to.fee.FeeAccountTO;
import com.kp.cms.transactions.fee.IFeeAccountTransaction;
import com.kp.cms.transactionsimpl.fee.FeeAccountTransactionImpl;

/**
 * @Date 19/jan/2009
 * This handler class for FeeAccouts Management
 *
 */
public class FeeAccountHandler {
	
	private static FeeAccountHandler feeAccountHandler= null;
	public static FeeAccountHandler getInstance() {
	      if(feeAccountHandler == null) {
	    	  feeAccountHandler = new FeeAccountHandler();
	    	  return feeAccountHandler;
	      }
	      return feeAccountHandler;
	}
	
	/**
	 * 
	 * @return list of AllFeeAccounts.
	 * @throws Exception
	 */
	public List<FeeAccount> getAllFeeAccounts() throws Exception {
		IFeeAccountTransaction feeAccountTransaction = FeeAccountTransactionImpl.getInstance();
        List<FeeAccount> feeAccountList = feeAccountTransaction.getAllFeeAccounts();
        return feeAccountList;
	}
	
	/**
	 * 
	 * @return Map of AllFeeAccounts <key,value> EX<1,mgmt><2,govt>.
	 * @throws Exception
	 */
	public Map<Integer,String> getAllFeeAccountsMap() throws Exception {
		IFeeAccountTransaction feeAccountTransaction = FeeAccountTransactionImpl.getInstance();
		Map<Integer,String> feeAccountMap = new HashMap<Integer, String>();
        List<FeeAccount> feeAccountList = feeAccountTransaction.getAllFeeAccounts();
        Iterator itr = feeAccountList.iterator();
        FeeAccount feeAccount;
        while (itr.hasNext()) {
        	feeAccount = (FeeAccount)itr.next();
        	feeAccountMap.put(feeAccount.getId(), feeAccount.getName());
        }
        return feeAccountMap;
	}
	
	public boolean addFeeAccount(ActionForm form,String mode) throws Exception
	{
		IFeeAccountTransaction feeAccountTransaction = FeeAccountTransactionImpl.getInstance();
		FeeAccountForm feeAccountForm=(FeeAccountForm)form;
		FeeAccount feeAccount=null;
		if(mode.equalsIgnoreCase("Delete")||mode.equalsIgnoreCase("Reactivate"))
		{
			feeAccount=FeeAccountHelper.getInstance().createBoObjcet(form,mode);
			feeAccount=feeAccountTransaction.loadFeeAccount(feeAccount);
			feeAccountForm=(FeeAccountForm)FeeAccountHelper.getInstance().createFormObjcet(form,feeAccount);
		}
		feeAccount=FeeAccountHelper.getInstance().createBoObjcet(feeAccountForm,mode);
		if(feeAccount != null && feeAccount.getLogo() != null && feeAccount.getLogo().length <= 0 && mode.equalsIgnoreCase("Update")){
			 FeeAccount account = getFeeAccountData(feeAccountForm.getId());
			 feeAccount.setLogo(account.getLogo());
			 if(!StringUtils.isEmpty(account.getFileName()) && account.getFileName() != null){
				 feeAccount.setFileName(account.getFileName());
			 }
		}
		FeeAccount tempfeeAccount=null;
		if(!(feeAccountForm.getCode().equals(feeAccountForm.getOriginalcode()))&&(mode.equalsIgnoreCase("Update")))
		{
			tempfeeAccount=feeAccountTransaction.existanceCheck(feeAccount);
		}else if(mode.equalsIgnoreCase("Add"))
		{
			tempfeeAccount=feeAccountTransaction.existanceCheck(feeAccount);
		}
		if(tempfeeAccount!=null)
		{
			String name=tempfeeAccount.getName();
			boolean active=tempfeeAccount.getIsActive();
			String code=tempfeeAccount.getCode();
			if((name.equalsIgnoreCase(feeAccountForm.getName()))
					&&(active==false)&&(code.equalsIgnoreCase(feeAccountForm.getCode())))
			{
				feeAccountForm.setId(tempfeeAccount.getId());
				throw new ReActivateException();
			}else
			{
				if(active==true)
				throw new DuplicateException();
			}
		}
		feeAccountTransaction.addFeeAccount(feeAccount,mode);
		return true;
		
	}
	
	public List<FeeAccountTO> getfeeAccounts()throws Exception
	{
		IFeeAccountTransaction feeAccountTransaction = FeeAccountTransactionImpl.getInstance();
		List<FeeAccount> feeaccounts=feeAccountTransaction.getFeeAccounts();
			
		List<FeeAccountTO> getfeeAccounttoList = FeeAccountHelper.getInstance()
			.convertBOstoTos(feeaccounts);
		return getfeeAccounttoList;
	}
	
	public ActionForm getUpdatableForm(ActionForm form,String mode)throws Exception
	{
		IFeeAccountTransaction feeAccountTransaction = FeeAccountTransactionImpl.getInstance();
		FeeAccount feeAccount=FeeAccountHelper.getInstance().createBoObjcet(form,mode);
		feeAccount=feeAccountTransaction.loadFeeAccount(feeAccount);
		form=(FeeAccountForm)FeeAccountHelper.getInstance().createFormObjcet(form,feeAccount);
		return form;
	}
	
	public FeeAccount getFeeAccountData(int id) throws Exception{
		IFeeAccountTransaction feeAccountTransaction = FeeAccountTransactionImpl.getInstance();
		return feeAccountTransaction.getFeeAccountData(id);
	}
}