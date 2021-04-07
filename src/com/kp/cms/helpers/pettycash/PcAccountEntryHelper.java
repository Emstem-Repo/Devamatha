package com.kp.cms.helpers.pettycash;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.PcBankAccNumber;
import com.kp.cms.forms.pettycash.PcAccountEntryForm;
import com.kp.cms.handlers.pettycash.PcAccountEntryHandler;
import com.kp.cms.to.pettycash.PcAccountTo;

public class PcAccountEntryHelper {
	/**
	 * Singleton object of PcAccountEntryHelper
	 */
	private static volatile PcAccountEntryHelper pcAccountEntryHelper = null;
	private static final Log log = LogFactory.getLog(PcAccountEntryHandler.class);
	private PcAccountEntryHelper() {
		
	}
	/**
	 * return singleton object of PcAccountEntryHelper.
	 * @return
	 */
	public static PcAccountEntryHelper getInstance() {
		if (pcAccountEntryHelper == null) {
			pcAccountEntryHelper = new PcAccountEntryHelper();
		}
		return pcAccountEntryHelper;
	}
	/**
	 * converting the List of BO to TO for displaying in the jsp
	 * @param finalList
	 * @return
	 * @throws Exception
	 */
	public List<PcAccountTo> convertBotoToList(List<PcBankAccNumber> finalList) throws Exception{
		List<PcAccountTo> accList=new ArrayList<PcAccountTo>();
		if(!finalList.isEmpty()){
			Iterator<PcBankAccNumber> itr=finalList.iterator();
			while (itr.hasNext()) {
				PcBankAccNumber pcBankAccNumber = (PcBankAccNumber) itr.next();
				PcAccountTo pto=new PcAccountTo();
				pto.setId(pcBankAccNumber.getId());
				if(pcBankAccNumber.getAccountNo()!=null){
					pto.setAccountNo(pcBankAccNumber.getAccountNo());
				}
				if(pcBankAccNumber.getLogo()!=null){
				if(pcBankAccNumber.getLogo().length>0){
					pto.setIsPhoto(true);
				}
				}
				accList.add(pto);
			}
		}
		return accList;
	}
	/**
	 * converting the form data to BO for Adding the new Record.
	 * @param accountEntryForm
	 * @return
	 * @throws Exception
	 */
	public PcBankAccNumber convertTotoBo(PcAccountEntryForm accountEntryForm) throws Exception{
		PcBankAccNumber bankAccNumber=new PcBankAccNumber();
		bankAccNumber.setAccountNo(accountEntryForm.getAccountNo().trim());
		bankAccNumber.setIsActive(true);
		bankAccNumber.setCreatedBy(accountEntryForm.getUserId());
		bankAccNumber.setCreatedDate(new Date());
		bankAccNumber.setLastModifiedDate(new Date());
		bankAccNumber.setModifiedBy(accountEntryForm.getUserId());
		bankAccNumber.setLogo(accountEntryForm.getThefile().getFileData());
		return bankAccNumber;
	}
	/**
	 * converting the form data to BO for update.
	 * @param accountEntryForm
	 * @return
	 * @throws Exception
	 */
	public PcBankAccNumber convertTotoBoForUpdate(PcAccountEntryForm accountEntryForm) throws Exception{
		PcBankAccNumber bankAccNumber=new PcBankAccNumber();
		bankAccNumber.setId(Integer.parseInt(accountEntryForm.getAccountId()));
		bankAccNumber.setAccountNo(accountEntryForm.getAccountNo());
		bankAccNumber.setIsActive(true);
		bankAccNumber.setLastModifiedDate(new Date());
		bankAccNumber.setModifiedBy(accountEntryForm.getUserId());
		if(accountEntryForm.getThefile().getFileData().length>0){
		bankAccNumber.setLogo(accountEntryForm.getThefile().getFileData());
		}else if(accountEntryForm.getAccountTo().getLogo().length>0){
			bankAccNumber.setLogo(accountEntryForm.getAccountTo().getLogo());
		}
		return bankAccNumber;
	}
	public PcAccountTo convertBotoToById(PcBankAccNumber pcBankAccNumber) throws Exception{
		PcAccountTo pto=null;
		if(pcBankAccNumber!=null){
		pto=new PcAccountTo();
		pto.setId(pcBankAccNumber.getId());
		if(pcBankAccNumber.getAccountNo()!=null){
			pto.setAccountNo(pcBankAccNumber.getAccountNo());
		}
		if(pcBankAccNumber.getLogo()!=null){
			if(pcBankAccNumber.getLogo().length>0){
				pto.setIsPhoto(true);
				pto.setLogo(pcBankAccNumber.getLogo());
			}
		}else{
			pto.setIsPhoto(false);
		}
		}
		return pto;
	}
}
